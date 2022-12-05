<script lang="ts">
    import {ButtonPriority} from '$components/html/button/button-priority';
    import {ButtonType} from '$components/html/button/button-type';
    import {CreateArticleRequestDto} from '$dto/create-article-request-dto';
    import {t} from '$i18n/i18n';
    import {to_number} from 'svelte/internal';

    import Button from '$components/html/button/Button.svelte';
    import Form from '$components/html/Form.svelte';
    import InputFlexContainer from '$components/common/input/InputFlexContainer.svelte';
    import LabeledFileInput from '$components/common/input/LabeledFileInput.svelte';
    import LabeledInput from '$components/common/input/LabeledInput.svelte';
    import LabeledNumericInput from '$components/common/input/LabeledNumericInput.svelte';
    import PageCard from '$components/common/PageCard.svelte';
    import PageContent from '$components/common/PageContent.svelte';

    let formData = new CreateArticleRequestDto();

    function handleOnSubmit() {
        console.log('onSubmit', formData);
    }

    // function changeValue(key: string, event: Event) {
    //     console.log(event);
    //     // console.log(event['value);
    //     const KEYS = key.split('.');
    //
    //     let iterator = formData;
    //     const lastIndex = KEYS.length - 1;
    //     for (let i = 0; i < KEYS.length; ++i) {
    //         const KEY = KEYS[i];
    //         if (formData.hasOwnProperty(KEY)) {
    //             if (i != lastIndex) {
    //                 iterator = iterator[KEY];
    //             } else {
    //                 // formData[KEY] = eventvalue;
    //                 console.debug('Set formData[', KEY, '] to ');
    //             }
    //         }
    //     }
    //
    // }
</script>

<PageContent>
    <PageCard title={$t('menu.addArticle')}>

        <Form className="inline-block w-full"
              on:submit={() => handleOnSubmit()}>
            <!-- Submit button -->
            <div class="float-left w-full">
                <div class="float-left w-1/2">
                    <!-- input name -->
                    <LabeledInput id="article-name"
                                  addMarginTop={false}
                                  label={$t('page.addArticle.name')}
                                  on:select={event => {
                              console.log('name change try, param:', event);
                              formData.name = event.target.value}}
                    />

                    <!-- input category, quantity -->
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
                                             min={'0'}
                                             offerSmallerSteps={true}
                                             on:change={event => formData.quantity = to_number(event.target.value)}
                                             slot="right"
                        >
                        </LabeledNumericInput>
                    </InputFlexContainer>
                </div>


                <div class="float-left h-full w-1/2 pl-10">

                    <div class="w-full pl-10 p-4 m-auto vr">
                        <LabeledFileInput parentClass="mt-2" label={$t('page.addArticle.image')}
                        />

                        <Button className="mt-7 float-right"
                                type={ButtonType.Submit}
                                priority={ButtonPriority.Primary}
                        >
                            {$t('page.addArticle.add')}
                        </Button>
                    </div>
                </div>
            </div>
        </Form>
    </PageCard>
</PageContent>

<style>
    div.vr {
        border-left: 1px solid #d8dbdf;
        width:1px;
    }
</style>