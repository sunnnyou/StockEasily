<script lang="ts">
    import {AcceptType} from '$components/common/input/file/accept-type';
    import {ButtonPriority} from '$components/html/button/button-priority';
    import {ButtonType} from '$components/html/button/button-type';
    import {CreateArticleRequestDto} from '$dto/create-article-request-dto';

    import {goto} from '$app/navigation';
    import {getImageResponseMessage, onImageSelected} from '$common/image-input-utils';
    import {imageSelected, selectedFileName, selectedFiles} from '$common/image-input-utils';
    import {onDeleteProperty} from '$common/property-utils';
    import {onDestroy, onMount} from 'svelte';
    import {onSaveProperty} from '$common/property-utils';
    import {SESSION_INFO} from '../../../common/session-util';
    import {t} from '$i18n/i18n';
    import {to_number} from 'svelte/internal';
    import {validateForm} from '../../../validation/validation';

    import Button from '$components/html/button/Button.svelte';
    import HorizontalRuler from '$components/html/HorizontalRuler.svelte';
    import InputFlexContainer from '$components/common/input/InputFlexContainer.svelte';
    import LabeledFileInput from '$components/common/input/file/LabeledFileInput.svelte';
    import LabeledInput from '$components/common/input/LabeledInput.svelte';
    import LabeledNumericInput from '$components/common/input/LabeledNumericInput.svelte';
    import PageCard from '$components/common/PageCard.svelte';
    import PageContent from '$components/common/PageContent.svelte';
    import PropertyInput from '$components/common/input/PropertyInput.svelte';
    import {validatableArticleStore} from '../../../stores/validatable-stores';

    async function handleOnSubmit() {
        if (!validateForm($validatableArticleStore, $t)) {
            // refresh UI
            validatableArticleStore.set($validatableArticleStore);
            console.error('Could not validate input data', $validatableArticleStore);
            return;
        }

        if (imageSelected?.size > SESSION_INFO.IMAGE_MAX_SIZE) {
            $validatableArticleStore.image = undefined;
            console.debug('File size is too big (' + imageSelected.size + ') => aborting');
            return;
        }

        fetch(SESSION_INFO.API_ENDPOINT + '/api/v1/articles', {
            method: 'POST',
            body: JSON.stringify(new CreateArticleRequestDto($validatableArticleStore)),
            headers: {
                'Content-Type': 'application/json',
            },
        }).then(response => {
            if (!response.ok) {
                $validatableArticleStore.image.errors = response.json()['errors'];
                console.error('Could not POST article, response errors:', $validatableArticleStore.image.errors);
                return;
            }

            goto('/articles');
        }).catch(error => {
            if (!error) {
                console.error('Could not reach backend, probably offline?');
                return;
            }
            console.error('Unknown error: Could not POST article, response error:', error);
        });

    }

    onDestroy(() => {
        selectedFileName.set(undefined);
        selectedFiles.set([]);
        validatableArticleStore.set(undefined);
    });

    onMount(() => {
        validatableArticleStore.set({
            category: {value: '', error: ''},
            image: {value: '', errors: []},
            name: {value: '', error: ''},
            properties: [],
            quantity: {value: 1, error: ''},
        });
    });
</script>

<PageContent>
    <PageCard title={$t('menu.addArticle')}>
        {#if $validatableArticleStore !== undefined}

            <form class="inline-block w-full"
                  on:submit|preventDefault={handleOnSubmit}>
                <div class="float-left w-full">
                    <div class="float-left w-1/2 vr py-0 px-4">
                        <!-- input name -->
                        <LabeledInput addMarginTop={false}
                                      error={$validatableArticleStore.name.error}
                                      labelOptions={{
                                          className: 'text-gray-600',
                                          isBold: true,
                                          name: 'article-name',
                                          text: $t('general.name')
                                      }}
                                      maxLength={30}
                                      placeholder={$t('general.name.placeholder')}
                                      on:change={event => $validatableArticleStore.name.value = event.target.value.trim()}
                        />

                        <!-- input category, quantity -->
                        <InputFlexContainer leftClass="w-65p"
                                            rightClass="w-34p"
                        >
                            <LabeledInput error={$validatableArticleStore.category.error}
                                          labelOptions={{
                                              className: 'text-gray-600',
                                              isBold: true,
                                              name: 'article-category',
                                              text: $t('general.category')
                                          }}
                                          maxLength={30}
                                          placeholder={$t('general.category.placeholder')}
                                          on:change={event => $validatableArticleStore.category.value = event.target.value.trim()}
                                          slot="left"
                            />

                            <LabeledNumericInput className="w-full text-sm"
                                                 error={$validatableArticleStore.quantity.error}
                                                 iconParentClass="pr-1 mt-1.5 text-sm"
                                                 labelOptions={{
                                                     className: 'text-gray-600',
                                                     isBold: true,
                                                     name: 'article-quantity',
                                                     text: $t('general.quantity')
                                                 }}
                                                 min="0"
                                                 offerSmallerSteps={true}
                                                 bind:value={$validatableArticleStore.quantity.value}
                                                 on:change={event => $validatableArticleStore.quantity.value = to_number(event.target.value)}
                                                 slot="right"
                            >
                            </LabeledNumericInput>
                        </InputFlexContainer>

                        <HorizontalRuler className="border-b-1 border-gray-300 mt-8 mx-4"></HorizontalRuler>

                        {#each $validatableArticleStore.properties as property, i}
                            <PropertyInput edit={false}
                                           errors={property.errors}
                                           leftLabelOptions={{
                                               className: 'text-gray-600 ml-2',
                                               isBold: true,
                                               name: 'prop-inner-name' + i,
                                               text: $t('props.name'),
                                           }}
                                           parentId="prop-parent{i}"
                                           parentLabelOptions={{
                                               className: 'text-gray-600 mt-10',
                                               hide: i !== 0,
                                               isBold: true,
                                               name: 'prop-inner-parent' + i,
                                           }}
                                           property={property}
                                           onDelete={() => {
                                               onDeleteProperty($validatableArticleStore, i)
                                               $validatableArticleStore = $validatableArticleStore;
                                           }}
                                           onSave={property => $validatableArticleStore.properties = onSaveProperty($validatableArticleStore, property, i)}
                                           rightLabelOptions={{
                                               className: 'text-gray-600 ml-2',
                                               isBold: true,
                                               name: 'prop-inner-description' + i,
                                               text: $t('props.description'),
                                           }}
                            />
                        {/each}
                        <PropertyInput edit={true}
                                       forceEdit={true}
                                       leftLabelOptions={{
                                           className: 'text-gray-600 ml-2',
                                           isBold: true,
                                           name: 'prop-inner-name-new',
                                           text: $t('props.name'),
                                       }}
                                       parentId="prop-parent-new"
                                       parentLabelOptions={{
                                           className: 'text-gray-600 mt-10',
                                           hide: $validatableArticleStore.properties.length > 0,
                                           isBold: true,
                                       }}
                                       rightLabelOptions={{
                                           className: 'text-gray-600 ml-2',
                                           isBold: true,
                                           name: 'prop-inner-description-new',
                                           text: $t('props.description'),
                                       }}
                                       onSave={property => $validatableArticleStore.properties = onSaveProperty($validatableArticleStore, property)}
                        />
                    </div>

                    <div class="float-left h-full w-1/2 py-0 px-4">
                        <div class="w-full m-auto h-full">
                            <LabeledFileInput accept={AcceptType.Image}
                                              addMarginTop={false}
                                              allowMultiple={false}
                                              className="h-full"
                                              labelOptions={{
                                                  className: 'text-gray-600',
                                                  isBold: true,
                                                  name: 'article-image',
                                                  text: $t('general.image')
                                              }}
                                              onFileChange={files => $validatableArticleStore.image = onImageSelected(files, $validatableArticleStore, $t)}
                                              previewImageOptions={{
                                                alt: $selectedFileName,
                                                show: true,
                                                src: $validatableArticleStore.image?.value
                                              }}
                            />

                            <!-- Image error response and submit button area -->
                            <div class="flex p-0 m-0 h-10 mt-4">
                                <div class="w-full text-right my-2">
                                    {#if $validatableArticleStore.image?.errors?.length > 0}
                                        <span class="error w-full leading-10 pr-5">
                                            {getImageResponseMessage($validatableArticleStore, $t)}
                                        </span>
                                    {/if}
                                </div>
                                <!-- Submit button -->
                                <Button className="w-1/8 align-end float-right"
                                        type={ButtonType.Submit}
                                        priority={ButtonPriority.Primary}
                                >
                                    {$t('general.add')}
                                </Button>
                            </div>
                        </div>
                    </div>
                </div>
            </form>
        {/if}
    </PageCard>
</PageContent>

<style>
    .vr {
        border-right: 1px solid #d8dbdf;
    }

    @media only screen and (max-width: 1023px) {
        div > div {
            padding: 0;
        }

        .vr {
            padding: 0 1rem 0 0;
        }
    }
</style>