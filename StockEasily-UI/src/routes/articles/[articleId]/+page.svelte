<script lang="ts">
    import type {GetArticleResponseDto} from '$dto/response/get-article-response-dto';

    import {AcceptType} from '$components/common/input/file/accept-type.js';
    import {ButtonPriority} from '$components/html/button/button-priority.js';
    import {ButtonType} from '$components/html/button/button-type.js';
    import {getImageResponseMessage, onImageSelected} from '$common/image-input-utils';
    import {goto} from '$app/navigation';
    import {onMount} from 'svelte';
    import {onSaveProperty} from '$common/property-utils';
    import {responseErrors, selectedFileName} from '$common/image-input-utils';
    import {t} from '$i18n/i18n';
    import {to_number} from 'svelte/internal';
    import {ValidatableArticle} from '$dto/create-article-request-dto';

    import Button from '$components/html/button/Button.svelte';
    import HorizontalRuler from '$components/html/HorizontalRuler.svelte';
    import InputFlexContainer from '$components/common/input/InputFlexContainer.svelte';
    import LabeledFileInput from '$components/common/input/file/LabeledFileInput.svelte';
    import LabeledInput from '$components/common/input/LabeledInput.svelte';
    import LabeledNumericInput from '$components/common/input/LabeledNumericInput.svelte';
    import LabeledText from '$components/common/article/LabeledText.svelte';
    import PageCard from '$components/common/PageCard.svelte';
    import PageContent from '$components/common/PageContent.svelte';
    import PreviewImage from '$components/common/article/PreviewImage.svelte';
    import PropertiesLabel from '$components/common/article/PropertiesLabel.svelte';
    import PropertyInput from '$components/common/input/PropertyInput.svelte';

    /** @type {import('./$types').PageData} */
    export let data;

    let article: GetArticleResponseDto;
    let validatableArticle: ValidatableArticle = {
        category: {value: '', error: ''},
        image: {value: '', error: ''},
        name: {value: '', error: ''},
        properties: [],
        quantity: {value: 1, error: ''},
    };

    function handleOnSubmit() {
        toggleEdit();
    }

    onMount(async () => {
        if (data.result === undefined) {
            await goto('/articles');
        }
        console.debug('Loaded data', data.result);
        article = data.result;
        validatableArticle = new ValidatableArticle(article);
    });

    let edit = false;

    function toggleEdit() {
        edit = !edit;
    }

</script>

<PageContent>
    <PageCard title={$t('article')}>
        <form class="inline-block w-full"
              on:submit|preventDefault={handleOnSubmit}>
            <div class="float-left w-full">
                <div class="float-left w-1/2 vr py-0 px-4">
                    {#if !edit}
                        <LabeledText labelText={$t('general.name')}
                                     text={validatableArticle.name.value}
                        />
                    {:else}
                        <!-- input name -->
                        <LabeledInput addMarginTop={false}
                                      error={validatableArticle.name.error}
                                      labelOptions={{
                                          className: 'text-gray-600',
                                          isBold: true,
                                          name: 'article-name',
                                          text: $t('general.name')
                                      }}
                                      maxLength={30}
                                      placeholder={$t('general.name.placeholder')}
                                      value={validatableArticle.name.value}
                                      on:change={event => validatableArticle.name.value = event.target.value.trim()}
                        />
                    {/if}

                    <!-- category, quantity -->
                    {#if !edit}
                        <InputFlexContainer leftClass="w-65p"
                                            rightClass="w-34p"
                        >
                            <LabeledText labelText={$t('general.category')}
                                         text={validatableArticle.category.value}
                                         slot="left"
                            />

                            <LabeledText labelText={$t('general.quantity')}
                                         text={validatableArticle.quantity.value}
                                         slot="right"
                            />
                        </InputFlexContainer>
                    {:else}
                        <InputFlexContainer leftClass="w-65p"
                                            rightClass="w-34p"
                        >
                            <LabeledInput error={validatableArticle?.category?.error}
                                          labelOptions={{
                                              className: 'text-gray-600',
                                              isBold: true,
                                              name: 'article-category',
                                              text: $t('general.category')
                                          }}
                                          maxLength={30}
                                          placeholder={$t('general.category.placeholder')}
                                          on:change={event => validatableArticle.category.value = event.target.value.trim()}
                                          value={validatableArticle.category.value}
                                          slot="left"
                            />

                            <LabeledNumericInput className="w-full text-sm"
                                                 error={validatableArticle.quantity.error}
                                                 iconParentClass="pr-1 mt-1.5 text-sm"
                                                 labelOptions={{
                                                     className: 'text-gray-600',
                                                     isBold: true,
                                                     name: 'article-quantity',
                                                     text: $t('general.quantity')
                                                 }}
                                                 min="0"
                                                 offerSmallerSteps={true}
                                                 bind:value={validatableArticle.quantity.value}
                                                 on:change={event => validatableArticle.quantity.value = to_number(event.target.value)}
                                                 slot="right"
                            />
                        </InputFlexContainer>
                    {/if}

                    <HorizontalRuler className="border-b-1 border-gray-300 mt-8 mx-4"></HorizontalRuler>
                    <PropertiesLabel addMarginTop={true}
                                     className=""
                                     labelOptions={{
                                           className: 'text-gray-600 mt-10',
                                           isBold: true,
                                       }}
                    />
                    {#if validatableArticle?.properties !== undefined}
                        {#each validatableArticle.properties as property, i}
                            {#if !edit}

                                <InputFlexContainer leftClass="w-65p"
                                                    rightClass="w-34p"
                                >
                                    <LabeledText labelText={$t('props.name')}
                                                 text={property.value.name}
                                                 slot="left">
                                    </LabeledText>

                                    <LabeledText labelText={$t('props.description')}
                                                 text={property.value.description}
                                                 slot="right">
                                    </LabeledText>
                                </InputFlexContainer>

                            {:else}

                                <PropertyInput errors={property.errors}
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
                                               property={property.value}
                                               onSave={property => validatableArticle.properties = onSaveProperty(validatableArticle, property, i)}
                                               rightLabelOptions={{
                                                   className: 'text-gray-600 ml-2',
                                                   isBold: true,
                                                   name: 'prop-inner-description' + i,
                                                   text: $t('props.description'),
                                               }}
                                />

                            {/if}
                        {/each}
                    {/if}
                    {#if edit}
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
                                       hide: validatableArticle?.properties.length > 0,
                                       isBold: true,
                                   }}
                                       rightLabelOptions={{
                                           className: 'text-gray-600 ml-2',
                                           isBold: true,
                                           name: 'prop-inner-description-new',
                                           text: $t('props.description'),
                                       }}
                                       onSave={property => validatableArticle.properties = onSaveProperty(validatableArticle, property)}
                        />
                    {/if}
                </div>

                <div class="float-left h-full w-1/2 py-0 px-4">
                    <div class="w-full m-auto h-full">
                        {#if edit}
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
                                              onFileChange={files => validatableArticle.image = onImageSelected(files, validatableArticle)}
                                              previewImageOptions={{
                                                alt: $selectedFileName,
                                                show: true,
                                                src: validatableArticle.image?.value || ''
                                              }}
                            />
                        {:else}
                            <PreviewImage alt={validatableArticle.name.value}
                                          src={validatableArticle.image?.value}
                            />
                        {/if}
                        {#if edit}
                            <!-- Image error response and submit button area -->
                            <div class="flex p-0 m-0 h-10 mt-4">
                                <div class="w-full text-right">
                                    {#if responseErrors && Object.keys(responseErrors)?.length > 0 }
                                    <span class="error w-full leading-10 pr-5">
                                        {getImageResponseMessage()}
                                    </span>
                                    {/if}
                                </div>
                                <!-- Submit button -->
                                <Button className="w-1/8 align-end float-right"
                                        type={ButtonType.Submit}
                                        priority={ButtonPriority.Primary}
                                >
                                    {$t('general.save')}
                                </Button>
                            </div>
                        {:else}
                            <!-- Submit button -->
                            <Button className="w-1/8 align-end float-right"
                                    type={ButtonType.Button}
                                    priority={ButtonPriority.Primary}
                                    on:click={toggleEdit}
                            >
                                {$t('general.edit')}
                            </Button>
                        {/if}
                    </div>
                </div>
            </div>
        </form>
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