<script lang="ts">
    import InputFlexContainer from '$components/common/input/InputFlexContainer.svelte';
    import {InputType} from '$components/html/input/input-type';
    import {t} from '$i18n/i18n';

    import LabeledInput from '$components/common/input/LabeledInput.svelte';
    import PageContent from '$components/common/PageContent.svelte';
    import PageCard from '$components/common/PageCard.svelte';
</script>

<PageContent>
    <PageCard title={$t('menu.addArticle')}>
        <Form on:submit={() => handleOnSubmit()}>
            <LabeledInput id="article-name"
                          addMarginTop={false}
                          label={$t('page.addArticle.name')}
                          on:select={event => {
                              console.log('name change try, param:', event);
                              formData.name = event.target.value}}
            />

            <InputFlexContainer>
                <LabeledInput id="article-category"
                              label={$t('page.addArticle.category')}
                              on:select={event => formData.category.name = event.target.value}
                              slot="left"
                />

                <LabeledNumericInput id="article-quantity"
                                     className="w-full text-sm"
                                     iconParentClass="pr-1 mt-1.5 text-sm"
                                     label={$t('page.addArticle.quantity')}
                                     min={0}
                                     offerSmallerSteps={true}
                                     on:change={event => formData.quantity = to_number(event.target.value)}
                                     slot="right"
                >
                </LabeledNumericInput>
            </InputFlexContainer>

    </PageCard>
</PageContent>