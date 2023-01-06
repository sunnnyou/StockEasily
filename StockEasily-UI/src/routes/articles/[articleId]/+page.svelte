<script lang="ts">
    import {t} from '$i18n/i18n';

    import Form from '$components/html/Form.svelte';
    import HorizontalRuler from '$components/html/HorizontalRuler.svelte';
    import PageCard from '$components/common/PageCard.svelte';
    import PageContent from '$components/common/PageContent.svelte';
    import {page} from '$app/stores';
    import LabeledInput from '$components/common/input/LabeledInput.svelte';
    import InputFlexContainer from '$components/common/input/InputFlexContainer.svelte';
    import Label from '$components/html/input/Label.svelte';

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
        let response = await fetch('http://localhost:8080/api/v1/articles/' + $page.params.articleId);
        return JSON.parse(await response.text());
    }

    let article: Article;
    let properties: Property[];
    let image;

    async function setArticle() {
        article = await getJson();
        properties = article.properties;
        image = article.image;
    }

</script>

<PageContent>
    <PageCard title={$t('article')}>
        {#await setArticle() then _}
            <Form className="inline-block w-full">
                <!-- Submit button -->
                <div class="float-left w-full">
                    <div class="float-left w-1/2">
                        <!-- name -->
                        <LabeledInput addMarginTop={false}
                                      disabled={true}
                                      labelOptions={{
                                          className: 'text-gray-600',
                                          isBold: true,
                                          name: 'article-name',
                                          text: $t('general.name')
                                      }}
                                      placeholder={article.name}
                        />
                        <!-- category, quantity -->
                        <InputFlexContainer leftClass="w-65p"
                                            rightClass="w-34p"
                        >
                            <LabeledInput disabled={true}
                                          labelOptions={{
                                              className: 'text-gray-600',
                                              isBold: true,
                                              name: 'article-category',
                                              text: $t('general.category')
                                          }}
                                          placeholder={article.category.name}
                                          slot="left"
                            />

                            <LabeledInput disabled={true}
                                          className="w-full text-sm"
                                          iconParentClass="pr-1 mt-1.5 text-sm"
                                          labelOptions={{
                                                 className: 'text-gray-600',
                                                 isBold: true,
                                                 name: 'article-quantity',
                                                 text: $t('general.quantity')
                                          }}
                                          placeholder={article.quantity}
                                          slot="right"
                            />
                        </InputFlexContainer>

                        <HorizontalRuler className="border-b-1 border-gray-300 mt-8 mx-4"/>

                        <div class="flex flex-col">
                            <div class="flex items-end h-10 mt-2">
                                <Label className="text-gray-600 mt-10"
                                       name="prop-inner-parent"
                                       bold={true}
                                >
                                    {$t('props') + ':'}
                                </Label>
                            </div>
                        </div>

                        {#each properties as property}
                            <InputFlexContainer leftClass="w-65p"
                                                rightClass="w-34p"
                            >
                                <LabeledInput disabled={true}
                                              labelOptions={{
                                                  className: 'text-gray-600',
                                                  isBold: true,
                                                  name: 'property-name',
                                                  text: $t('props.name')
                                              }}
                                              placeholder={property.name}
                                              slot="left"
                                />

                                <LabeledInput className="w-full text-sm"
                                              disabled={true}
                                              iconParentClass="pr-1 mt-1.5 text-sm"
                                              labelOptions={{
                                                 className: 'text-gray-600',
                                                 isBold: true,
                                                 name: 'property-description',
                                                 text: $t('props.description')
                                          }}
                                              placeholder={property.description}
                                              slot="right"
                                />
                            </InputFlexContainer>

                        {/each}

                    </div>

                    <!-- image -->

                    <div class="float-left h-full w-1/2 pl-10">
                        <div class="w-full px-10 m-auto vr h-full">
                            <img alt=""
                                 class="w-full object-contain max-h-96"
                                 src={`data:image/png;base64,${image}`}
                            />
                        </div>
                    </div>
                </div>
            </Form>
        {/await}
    </PageCard>
</PageContent>

<style>
    div.vr {
        border-left: 1px solid #d8dbdf;
        height: 90%;
        display: inline-table;
    }
</style>