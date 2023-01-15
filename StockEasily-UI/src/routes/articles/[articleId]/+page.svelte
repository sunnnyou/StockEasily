<script lang="ts">
    import FaIcon from '$components/common/FaIcon.svelte';
    import {GetArticleResponseDto} from '$dto/response/get-article-response-dto';

    import {AcceptType} from '$components/common/input/file/accept-type.js';
    import {ButtonPriority} from '$components/html/button/button-priority.js';
    import {ButtonType} from '$components/html/button/button-type.js';
    import {getImageResponseMessage, onImageSelected} from '$common/image-input-utils';
    import {goto} from '$app/navigation';
    import {faImage} from '@fortawesome/free-solid-svg-icons';
    import {onDestroy, onMount} from 'svelte';
    import {onSaveProperty} from '$common/property-utils';
    import {responseErrors, selectedFileName} from '$common/image-input-utils';
    import {page} from '$app/stores';
    import {SESSION_INFO} from '$common/session-util';
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
    import {PropertyRequestDto} from '$dto/property-request-dto';
    import {UpdateArticleRequestDto} from '$dto/request/update-article-request-dto';
    import {validatableArticleStore} from '../../../stores/validatable-stores';

    /** @type {import('./$types').PageData} */
    export let data;

    let articleId = $page.params.articleId;

    function handleOnSubmit() {
        // toggleEdit();
        // if invalid errors

        if (!articleId) {
            console.error('Cannot submit PATCH article form, article ID is', articleId);
            return;
        }
        requestUpdate();
    }

    onDestroy(() => {
        validatableArticleStore.set(undefined);
    });

    onMount(async () => {
        if (data.result === undefined) {
            await goto('/articles');
        }
        console.debug('Loaded data', data.result);
        const GET_ARTICLE_RESPONSE = new GetArticleResponseDto(data.result);
        validatableArticleStore.set(GET_ARTICLE_RESPONSE.toValidatable());
    });

    let edit = false;

    // may show errors on $t but still works
    function deleteArticle() {
        if (confirm($t('articles.confirm.delete'))) {
            fetch(SESSION_INFO.API_ENDPOINT + '/api/v1/articles/' + articleId, {
                method: 'DELETE',
            }).then(response => {
                if (!response.ok) {
                    console.error('Could not delete article');
                    alert($t('articles.error.unknown') + ' ' + $t('articles.error.delete'));
                    return;
                }
                goto('/articles');
            }).catch(error => {
                if (!error) {
                    console.error('Could not reach backend, probably offline?');
                    alert($t('articles.error.backend') + ' ' + $t('articles.error.delete'));
                    return;
                }
                console.error('Unknown error: Could not delete article, response error:', error);
                alert($t('articles.error.unknown') + ' ' + $t('articles.error.delete'));
            });
        } else {
            return;
        }
    }

    function onPropertyAdded(property: PropertyRequestDto, i = Number.NaN) {
        const VALIDATABLE_ARTICLE = $validatableArticleStore;
        let properties = onSaveProperty(VALIDATABLE_ARTICLE, property, i);
        const article: ValidatableArticle = {...VALIDATABLE_ARTICLE, properties: properties};
        validatableArticleStore.set(article);
    }

    function toggleEdit() {
        edit = !edit;
    }

    async function requestUpdate() {
        const ROUTE = `/articles/${articleId}`;
        fetch(`${SESSION_INFO.API_ENDPOINT}/api/v1${ROUTE}`, {
            method: 'PATCH',
            body: JSON.stringify(new UpdateArticleRequestDto($validatableArticleStore)),
            headers: {
                'Content-Type': 'application/json',
            },
        }).then(response => {
            if (!response.ok) {
                responseErrors = response.json()['errors'];
                console.error('Could not PATCH article, response errors:', responseErrors);
                return;
            }

            toggleEdit();
        }).catch(error => {
            if (!error) {
                console.error('Could not reach backend, probably offline?');
                return;
            }
            console.error('Unknown error: Could not PATCH article, response error:', error);
        });
    }
</script>

<PageContent>
    <PageCard title={$t('article')}>
        {#if $validatableArticleStore?.name !== undefined}
            <form class="inline-block w-full"
                  on:submit|preventDefault={handleOnSubmit}>
                <div class="float-left w-full">
                    <div class="float-left w-1/2 vr py-0 px-4">
                        {#if !edit}
                            <LabeledText labelText={$t('general.name')}
                                         text={$validatableArticleStore.name.value}
                            />
                        {:else}
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
                                          value={$validatableArticleStore.name.value}
                                          on:change={event => $validatableArticleStore.name.value = event.target.value.trim()}
                            />
                        {/if}

                        <!-- category, quantity -->
                        {#if !edit}
                            <InputFlexContainer leftClass="w-65p"
                                                rightClass="w-34p"
                            >
                                <LabeledText labelText={$t('general.category')}
                                             text={$validatableArticleStore.category.value}
                                             slot="left"
                                />

                                <LabeledText labelText={$t('general.quantity')}
                                             text={$validatableArticleStore.quantity.value}
                                             slot="right"
                                />
                            </InputFlexContainer>
                        {:else}
                            <InputFlexContainer leftClass="w-65p"
                                                rightClass="w-34p"
                            >
                                <LabeledInput error={$validatableArticleStore.category?.error}
                                              labelOptions={{
                                              className: 'text-gray-600',
                                              isBold: true,
                                              name: 'article-category',
                                              text: $t('general.category')
                                          }}
                                              maxLength={30}
                                              placeholder={$t('general.category.placeholder')}
                                              on:change={event => $validatableArticleStore.category.value = event.target.value.trim()}
                                              value={$validatableArticleStore.category.value}
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
                                />
                            </InputFlexContainer>
                        {/if}

                        {#if $validatableArticleStore?.properties?.length > 0}
                            <HorizontalRuler className="border-b-1 border-gray-300 my-4 mx-4"/>
                            <PropertiesLabel addMarginTop={true}
                                             className="mb-6"
                                             labelOptions={{
                                                 className: 'text-gray-600 mt-10',
                                                 isBold: true,
                                             }}
                            />

                            {#each $validatableArticleStore.properties as property, i}
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
                                                     slot="right"
                                        >
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
                                                       isBold: true,
                                                       name: 'prop-inner-parent' + i,
                                                   }}
                                                   {property}
                                                   onSave={property => onPropertyAdded(property, i)}
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
                                               hide: $validatableArticleStore?.properties.length > 0,
                                               isBold: true,
                                           }}
                                           rightLabelOptions={{
                                               className: 'text-gray-600 ml-2',
                                               isBold: true,
                                               name: 'prop-inner-description-new',
                                               text: $t('props.description'),
                                           }}
                                           onSave={property => onPropertyAdded(property)}
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
                                                  onFileChange={files => $validatableArticleStore.image = onImageSelected(files, $validatableArticleStore)}
                                                  previewImageOptions={{
                                                      alt: $selectedFileName,
                                                      show: true,
                                                      src: $validatableArticleStore.image?.value || ''
                                                  }}
                                />
                            {:else if $validatableArticleStore.image?.value?.length > 0 }
                                <PreviewImage alt={$validatableArticleStore.name.value}
                                              src={$validatableArticleStore.image.value || ''}
                                />
                            {:else}
                                <div class="w-full">
                                    <FaIcon className="my-8 mx-auto inset-0 w-24 h-24 flex justify-center items-center"
                                            icon={faImage}
                                            scale="10"
                                    />
                                    <p class="mx-auto w-full text-center italic">{`(${$t('general.unset')})`}</p>
                                </div>
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
                                <div class="w-1/3 float-right pt-4">
                                    <InputFlexContainer
                                            leftClass="w-1/2"
                                            parentClass="inline-flex w-full w-52"
                                            rightClass="w-1/2"
                                    >
                                        <!-- Dangerous Delete button -->
                                        <Button className="font-bold font-medium border border-red-400"
                                                priority={ButtonPriority.Dangerous}
                                                on:click={deleteArticle}
                                                slot="left"
                                        >
                                            {$t('general.delete')}
                                        </Button>
                                        <!-- Edit button -->
                                        <Button className="w-1/8 align-end float-right"
                                                type={ButtonType.Button}
                                                priority={ButtonPriority.Primary}
                                                on:click={toggleEdit}
                                                slot="right"
                                        >
                                            {$t('general.edit')}
                                        </Button>
                                    </InputFlexContainer>
                                </div>
                            {/if}
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