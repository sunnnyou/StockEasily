<script lang="ts">
    import {AcceptType} from '$components/common/input/file/accept-type';
    import {ButtonPriority} from '$components/html/button/button-priority';
    import {ButtonType} from '$components/html/button/button-type';
    import {CreateArticleRequestDto} from '$dto/create-article-request-dto';
    import {PropertyRequestDto} from '../../../dto/property-request-dto';
    import {t} from '$i18n/i18n';
    import {onMount} from 'svelte';
    import {to_number} from 'svelte/internal';

    import Button from '$components/html/button/Button.svelte';
    import Form from '$components/html/Form.svelte';
    import HorizontalRuler from '$components/html/HorizontalRuler.svelte';
    import InputFlexContainer from '$components/common/input/InputFlexContainer.svelte';
    import LabeledFileInput from '$components/common/input/file/LabeledFileInput.svelte';
    import LabeledInput from '$components/common/input/LabeledInput.svelte';
    import LabeledNumericInput from '$components/common/input/LabeledNumericInput.svelte';
    import PageCard from '$components/common/PageCard.svelte';
    import PageContent from '$components/common/PageContent.svelte';
    import PropertyInput from '$components/common/input/PropertyInput.svelte';

    const IMAGE_MAXIMUM_SIZE = 524288;

    let inputData: CreateArticleRequestDto = {category: undefined, image: '', name: '', properties: [], quantity: 1};
    let selectedFileName = '';

    function isValid() {
        // TODO implement
        return false;
    }

    function handleOnSubmit() {
        console.log('onSubmit', inputData);

        // console.log(imageInputRef.getFirstFile());
        // if (isValid()) {
        //
        //
        //     let formData = new FormData();
        //     formData.append('name', inputData.name);
        //     formData.append('category', inputData.category);
        //     formData.append('image', inputData.image);
        //     formData.append('quantity', inputData.quantity);
        //     formData.append('', inputData.image);
        //
        //     const upload = fetch('http://localhost:8080/file', {
        //         method: 'POST',
        //         body: formData
        //     }).then((response) => response.json()).then((result) => {
        //         console.log('Success:', result);
        //     })
        //         .catch((error) => {
        //             console.error('Error:', error);
        //         });
        // }
    }

    function isEditingExistingProperty(index: number) {
        return index !== Number.NaN && inputData.properties.length > index;
    }

    function onSaveProperty(property: PropertyRequestDto, index: number = Number.NaN) {
        if (isEditingExistingProperty(index)) {
            inputData.properties[index] = property;
        } else {
            inputData.properties = [...inputData.properties, property];
        }
    }

    function onImageSelected(event) {
        const image = event.target.files[0];
        console.log('image size:', image.size);
        if (image.size > IMAGE_MAXIMUM_SIZE) {
            console.warn('Image select is too big', '(' + image.size + ' bytes or', image.size / 1024 + 'KB )', 'image maximum size: ', IMAGE_MAXIMUM_SIZE / 1024, 'bytes');
            return;
        }

        // console.log(image, image);
        let reader = new FileReader();
        reader.readAsDataURL(image);
        reader.onload = e => {
            inputData.image = e.target.result;
            selectedFileName = image.name;
            console.log('selected file:', image.name);
        };
    }

    onMount(() => {
        console.debug('Showing', inputData.properties.length, 'properties');
    });
</script>

<PageContent>
    <PageCard title={$t('menu.addArticle')}>

        <Form className="inline-block w-full"
              on:submit={() => handleOnSubmit()}>
            <!-- Submit button -->
            <div class="float-left w-full">
                <div class="float-left w-1/2 vr">
                    <!-- input name -->
                    <LabeledInput addMarginTop={false}
                                  labelOptions={{
                                      className: 'text-gray-600',
                                      isBold: true,
                                      name: 'article-name',
                                      text: $t('general.name')
                                  }}
                                  placeholder={$t('general.name.placeholder')}
                                  on:change={event => inputData.name = event.target.value}
                    />

                    <!-- input category, quantity -->
                    <InputFlexContainer leftClass="w-65p"
                                        rightClass="w-34p"
                    >
                        <LabeledInput labelOptions={{
                                          className: 'text-gray-600',
                                          isBold: true,
                                          name: 'article-category',
                                          text: $t('general.category')
                                      }}
                                      placeholder={$t('general.category.placeholder')}
                                      on:change={event => inputData.category.name = event.target.value}
                                      slot="left"
                        />

                        <LabeledNumericInput className="w-full text-sm"
                                             iconParentClass="pr-1 mt-1.5 text-sm"
                                             labelOptions={{
                                                 className: 'text-gray-600',
                                                 isBold: true,
                                                 name: 'article-quantity',
                                                 text: $t('general.quantity')
                                             }}
                                             min="0"
                                             offerSmallerSteps={true}
                                             bind:value={inputData.quantity}
                                             on:change={event => inputData.quantity = to_number(event.target.value)}
                                             slot="right"
                        >
                        </LabeledNumericInput>
                    </InputFlexContainer>

                    <HorizontalRuler className="border-b-1 border-gray-300 mt-8 mx-4"></HorizontalRuler>

                    {#each inputData?.properties as property, i}
                        <PropertyInput edit={false}
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
                                       {property}
                                       onSave={property => onSaveProperty(property, i)}
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
                                       hide: inputData?.properties.length > 0,
                                       isBold: true,
                                   }}
                                   rightLabelOptions={{
                                           className: 'text-gray-600 ml-2',
                                           isBold: true,
                                           name: 'prop-inner-description-new',
                                           text: $t('props.description'),
                                       }}
                                   onSave={property => onSaveProperty(property)}
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
    div > div {
        padding: 0 1rem;
    }

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