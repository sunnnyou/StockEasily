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
        <LabeledInput id="article-name" label={$t('page.addArticle.name')} addMarginTop={false}/>

        <InputFlexContainer>
            <LabeledInput id="article-category"
                          label={$t('page.addArticle.category')}
                          slot="left"
            />
            <LabeledInput id="article-quantity"
                          className="w-full"
                          label={$t('page.addArticle.quantity')}
                          min={0}
                          type={InputType.Number}
                          slot="right"
            />
        </InputFlexContainer>

    </PageCard>
</PageContent>