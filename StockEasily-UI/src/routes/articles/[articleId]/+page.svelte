<script lang="ts">
    import type {ValidatableArticle} from '../../../dto/create-article-request-dto';

    import {AcceptType} from '$components/common/input/file/accept-type.js';
    import {ButtonPriority} from '$components/html/button/button-priority.js';
    import {ButtonType} from '$components/html/button/button-type.js';
    import {getImageResponseMessage, onImageSelected} from '$common/image-input-utils';
    import {onMount} from 'svelte';
    import {onSaveProperty} from '$common/property-utils';
    import {page} from '$app/stores';
    import {responseErrors, selectedFileName} from '$common/image-input-utils';
    import {SESSION_INFO} from '../../../common/session-util';
    import {t} from '$i18n/i18n';
    import {to_number} from 'svelte/internal';

    import Button from '$components/html/button/Button.svelte';
    import HorizontalRuler from '$components/html/HorizontalRuler.svelte';
    import InputFlexContainer from '$components/common/input/InputFlexContainer.svelte';
    import LabeledFileInput from '$components/common/input/file/LabeledFileInput.svelte';
    import LabeledInput from '$components/common/input/LabeledInput.svelte';
    import LabeledNumericInput from '$components/common/input/LabeledNumericInput.svelte';
    import PageCard from '$components/common/PageCard.svelte';
    import PageContent from '$components/common/PageContent.svelte';
    import PropertyInput from '$components/common/input/PropertyInput.svelte';

    type Property = {
        id: number;
        name: string;
        description: string;
    }

    type Article = {
        id: number;
        name: string;
        properties: Property[];
        quantity: number;
        category: object;
        image: string;
    }

    async function getJson() {
        let response = await fetch(SESSION_INFO.API_ENDPOINT + '/api/v1/articles/' + $page.params.articleId);
        return JSON.parse(await response.text());
    }

    let article: Article;
    let image;
    let properties: Property[];

    async function setArticle() {
        article = await getJson();
        properties = article.properties;
        image = article.image;
    }

    function handleOnSubmit() {

    }

    let validatableArticle: ValidatableArticle = {
        category: {value: '', error: ''},
        image: {value: '', error: ''},
        name: {value: '', error: ''},
        properties: [],
        quantity: {value: 1, error: ''},
    };

    onMount(() => {

    });

</script>

<PageContent>
    <PageCard title={$t('article')}>
        <form class="inline-block w-full"
              on:submit|preventDefault={handleOnSubmit}>
            <div class="float-left w-full">
                <div class="float-left w-1/2 vr">
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
                                  on:change={event => validatableArticle.name.value = event.target.value.trim()}
                    />

                    <!-- input category, quantity -->
                    <InputFlexContainer leftClass="w-65p"
                                        rightClass="w-34p"
                    >
                        <LabeledInput error={validatableArticle.category.error}
                                      labelOptions={{
                                          className: 'text-gray-600',
                                          isBold: true,
                                          name: 'article-category',
                                          text: $t('general.category')
                                      }}
                                      maxLength={30}
                                      placeholder={$t('general.category.placeholder')}
                                      on:change={event => validatableArticle.category.value = event.target.value.trim()}
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
                        >
                        </LabeledNumericInput>
                    </InputFlexContainer>

                    <HorizontalRuler className="border-b-1 border-gray-300 mt-8 mx-4"></HorizontalRuler>

                    {#each validatableArticle?.properties as property, i}
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
                                       onSave={property => validatableArticle.properties = onSaveProperty(validatableArticle, property, i)}
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
                                       hide: validatableArticle?.properties.length > 0,
                                       isBold: true,
                                   }}
                                   rightLabelOptions={{
                                           className: 'text-gray-600 ml-2',
                                           isBold: true,
                                           name: 'prop-inner-description-new',
                                           text: $t('props.description'),
                                       }}
                                   onSave={property =>validatableArticle.properties = onSaveProperty(validatableArticle, property)}
                    />
                </div>

                <div class="float-left h-full w-1/2">
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
                                          onFileChange={files => validatableArticle.image = onImageSelected(files, validatableArticle)}
                                          previewImageOptions={{
                                            alt: $selectedFileName,
                                            show: true,
                                            src: validatableArticle.image?.value
                                          }}
                        />

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
                                {$t('general.add')}
                            </Button>
                        </div>
                    </div>
                </div>
            </div>
        </form>


        <!--{#await setArticle() then _}-->
        <!--    <Form className="inline-block w-full">-->
        <!--        &lt;!&ndash; Submit button &ndash;&gt;-->
        <!--        <div class="float-left w-full">-->
        <!--            <div class="float-left w-1/2">-->
        <!--                &lt;!&ndash; name &ndash;&gt;-->
        <!--                <LabeledInput addMarginTop={false}-->
        <!--                              disabled={true}-->
        <!--                              labelOptions={{-->
        <!--                                  className: 'text-gray-600',-->
        <!--                                  isBold: true,-->
        <!--                                  name: 'article-name',-->
        <!--                                  text: $t('general.name')-->
        <!--                              }}-->
        <!--                              placeholder={article.name}-->
        <!--                />-->
        <!--                &lt;!&ndash; category, quantity &ndash;&gt;-->
        <!--                <InputFlexContainer leftClass="w-65p"-->
        <!--                                    rightClass="w-34p"-->
        <!--                >-->
        <!--                    <LabeledInput disabled={true}-->
        <!--                                  labelOptions={{-->
        <!--                                      className: 'text-gray-600',-->
        <!--                                      isBold: true,-->
        <!--                                      name: 'article-category',-->
        <!--                                      text: $t('general.category')-->
        <!--                                  }}-->
        <!--                                  placeholder={article.category.name}-->
        <!--                                  slot="left"-->
        <!--                    />-->

        <!--                    <LabeledInput disabled={true}-->
        <!--                                  className="w-full text-sm"-->
        <!--                                  iconParentClass="pr-1 mt-1.5 text-sm"-->
        <!--                                  labelOptions={{-->
        <!--                                         className: 'text-gray-600',-->
        <!--                                         isBold: true,-->
        <!--                                         name: 'article-quantity',-->
        <!--                                         text: $t('general.quantity')-->
        <!--                                  }}-->
        <!--                                  placeholder={article.quantity}-->
        <!--                                  slot="right"-->
        <!--                    />-->
        <!--                </InputFlexContainer>-->

        <!--                <HorizontalRuler className="border-b-1 border-gray-300 mt-8 mx-4"/>-->

        <!--                <div class="flex flex-col">-->
        <!--                    <div class="flex items-end h-10 mt-2">-->
        <!--                        <Label className="text-gray-600 mt-10"-->
        <!--                               name="prop-inner-parent"-->
        <!--                               bold={true}-->
        <!--                        >-->
        <!--                            {$t('props') + ':'}-->
        <!--                        </Label>-->
        <!--                    </div>-->
        <!--                </div>-->

        <!--                {#each properties as property}-->
        <!--                    <InputFlexContainer leftClass="w-65p"-->
        <!--                                        rightClass="w-34p"-->
        <!--                    >-->
        <!--                        <LabeledInput disabled={true}-->
        <!--                                      labelOptions={{-->
        <!--                                          className: 'text-gray-600',-->
        <!--                                          isBold: true,-->
        <!--                                          name: 'property-name',-->
        <!--                                          text: $t('props.name')-->
        <!--                                      }}-->
        <!--                                      placeholder={property.name}-->
        <!--                                      slot="left"-->
        <!--                        />-->

        <!--                        <LabeledInput className="w-full text-sm"-->
        <!--                                      disabled={true}-->
        <!--                                      iconParentClass="pr-1 mt-1.5 text-sm"-->
        <!--                                      labelOptions={{-->
        <!--                                         className: 'text-gray-600',-->
        <!--                                         isBold: true,-->
        <!--                                         name: 'property-description',-->
        <!--                                         text: $t('props.description')-->
        <!--                                  }}-->
        <!--                                      placeholder={property.description}-->
        <!--                                      slot="right"-->
        <!--                        />-->
        <!--                    </InputFlexContainer>-->

        <!--                {/each}-->

        <!--            </div>-->

        <!--            &lt;!&ndash; image &ndash;&gt;-->

        <!--            <div class="float-left h-full w-1/2 pl-10">-->
        <!--                <div class="w-full px-10 m-auto vr h-full">-->
        <!--                    <img alt=""-->
        <!--                         class="w-full object-contain max-h-96"-->
        <!--                         src={`data:image/png;base64,${image}`}-->
        <!--                    />-->
        <!--                </div>-->
        <!--            </div>-->
        <!--        </div>-->
        <!--    </Form>-->
        <!--{/await}-->
    </PageCard>
</PageContent>

<style>
    div.vr {
        border-left: 1px solid #d8dbdf;
        height: 90%;
        display: inline-table;
    }
</style>