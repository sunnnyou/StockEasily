<script lang="ts">
    import {AcceptType} from '$components/common/input/file/accept-type';
    import PropertyInput from '$components/common/input/PropertyInput.svelte';
    import {ButtonPriority} from '$components/html/button/button-priority';
    import {ButtonType} from '$components/html/button/button-type';
    import {CreateArticleRequestDto} from '$dto/create-article-request-dto';
    import {t} from '$i18n/i18n';
    import {to_number} from 'svelte/internal';

    import Button from '$components/html/button/Button.svelte';
    import Form from '$components/html/Form.svelte';
    import InputFlexContainer from '$components/common/input/InputFlexContainer.svelte';
    import LabeledFileInput from '$components/common/input/file/LabeledFileInput.svelte';
    import LabeledInput from '$components/common/input/LabeledInput.svelte';
    import LabeledNumericInput from '$components/common/input/LabeledNumericInput.svelte';
    import PageCard from '$components/common/PageCard.svelte';
    import PageContent from '$components/common/PageContent.svelte';

    const IMAGE_MAXIMUM_SIZE = 524288;

    let inputData = new CreateArticleRequestDto();
    let selectedFileName = '';

    function isValid() {
        // TODO implement
        return false;
    }

    function handleOnSubmit() {
        console.log('onSubmit', inputData);

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
                    <LabeledInput addMarginTop={false}
                                  id="article-name"
                                  labelOptions={{className: 'text-gray-600', isBold: true, text: $t('general.name')}}
                                  placeholder={$t('general.name.placeholder')}
                                  on:change={event => inputData.name = event.target.value}
                    />

                    <!-- input category, quantity -->
                    <InputFlexContainer leftClass="w-65p"
                                        rightClass="w-34p"
                    >
                        <LabeledInput id="article-category"
                                      labelOptions={{className: 'text-gray-600', isBold: true, text: $t('general.category')}}
                                      placeholder={$t('general.category.placeholder')}
                                      on:change={event => inputData.category.name = event.target.value}
                                      slot="left"
                        />

                        <LabeledNumericInput id="article-quantity"
                                             className="w-full text-sm"
                                             iconParentClass="pr-1 mt-1.5 text-sm"
                                             labelOptions={{className: 'text-gray-600', isBold: true, text: $t('general.quantity')}}
                                             min="0"
                                             offerSmallerSteps={true}
                                             on:change={event => inputData.quantity = to_number(event.target.value)}
                                             slot="right"
                        >
                        </LabeledNumericInput>
                    </InputFlexContainer>

                    <PropertyInput id={{name: 'nameidhalt', description: 'descidhalt'}}
                                   leftLabelOptions={{
                                       className: 'text-gray-600',
                                       isBold: true,
                                       text: $t('props.name')
                                   }}
                                   parentId="abcid"
                                   rightLabelOptions={{className: 'text-gray-600', isBold: true, text: $t('props.description')}}
                    />
                </div>


                <div class="float-left h-full w-1/2 pl-10">

                    <div class="w-full px-10 m-auto vr h-full">
                        <LabeledFileInput accept={AcceptType.Image}
                                          addMarginTop={false}
                                          allowMultiple={false}
                                          className="h-full"
                                          labelOptions={{className: 'text-gray-600', isBold: true, text: $t('general.image')}}
                                          previewImageOptions={{
                                            alt: selectedFileName,
                                            show: true,
                                            src: inputData.image
                                          }}
                                          on:change={event => onImageSelected(event)}
                        />

                        <Button className="mt-7 float-right"
                                type={ButtonType.Submit}
                                priority={ButtonPriority.Primary}
                        >
                            {$t('general.add')}
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
        height: 90%;
        display: inline-table;
    }
</style>