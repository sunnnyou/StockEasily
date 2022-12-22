<script lang="ts">
    import {AcceptType} from '$components/common/input/file/accept-type';
    import {ButtonPriority} from '$components/html/button/button-priority';
    import {ButtonType} from '$components/html/button/button-type';
    import {CreateArticleRequestDto, validateCreateArticleRequest} from '$dto/create-article-request-dto';
    import {PropertyRequestDto} from '../../../dto/property-request-dto';
    import {goto} from '$app/navigation';
    import {onMount} from 'svelte';
    import {t} from '$i18n/i18n';
    import {to_number} from 'svelte/internal';
    import {validatePropertyRequest} from '../../../dto/property-request-dto';

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

    let responseErrors: Object | undefined;
    let inputArticle: CreateArticleRequestDto = {category: undefined, image: '', name: '', properties: [], quantity: 1};
    let selectedFileName = '';
    let imageSelected: any;
    let isFormValid = false;

    let articleInputs: {
        name: HTMLElement,
        category: HTMLElement,
        image: undefined,
        properties: HTMLElement[],
    };

    function getImageResponseMessage(): string | undefined {
        if (imageSelected === undefined) {
            console.debug('No image was selected, returning empty response message');
            return undefined;
        }
        if (responseErrors === undefined) {
            console.debug('Request was sent successfully');
            return undefined;
        }

        const IMAGE_ERROR = responseErrors['image'];
        console.debug('Error:', IMAGE_ERROR);
        return `${$t('general.error')}: ${IMAGE_ERROR}`;
    }

    async function handleOnSubmit() {
        if (!isFormValid) {
            console.log('aborting submit');
            return;
        }
        console.log('onSubmit', inputArticle);

        if (!validate()) {
            console.error('Could not validate input data');
            return;
        }

        if (imageSelected?.size > IMAGE_MAXIMUM_SIZE) {
            inputArticle.image = undefined;
            console.debug('File size is too big (' + imageSelected.size + ') => aborting');
            return;
        }
        const body = JSON.stringify(inputArticle);

        await fetch('http://localhost:8080/api/v1/articles', {
            body: body,
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
        }).then(async (response) => {
            if (!response.ok) {
                responseErrors = (await response.json())['errors'];
                console.error('Could not POST article, response errors:', responseErrors);
                return;
            }

            goto('/articles');
        }).catch(async (response) => {
            if (!response) {
                console.error('Could not reach backend, probably offline?');
                return;
            }
            responseErrors = (await response.json())['errors'];
            console.error('Unknown error: Could not POST article, response error:', response);
        });

    }

    function isEditingExistingProperty(index: number): boolean {
        return index !== Number.NaN && inputArticle.properties.length > index;
    }

    function onAddClick(e) {
        const firstInvalid = e.target.closest('form').querySelector(':invalid');

        if ((isFormValid = !firstInvalid)) {
            console.debug('Form data is valid');
            handleOnSubmit();
            return;
        }
        console.warn('Invalid properties found', firstInvalid);
    }

    function onSaveProperty(property: PropertyRequestDto, index: number = Number.NaN) {
        if (isEditingExistingProperty(index)) {
            inputArticle.properties[index] = property;
        } else {
            inputArticle.properties = [...inputArticle.properties, property];
        }
    }

    function onImageSelected(event) {
        imageSelected = event.target.files[0];
        console.log('image size:', imageSelected.size);
        if (imageSelected.size > IMAGE_MAXIMUM_SIZE) {
            console.warn('Image select is too big', '(' + imageSelected.size + ' bytes or', imageSelected.size / 1024 + 'KB )', 'image maximum size: ', IMAGE_MAXIMUM_SIZE / 1024, 'bytes');
            return;
        }

        // console.log(image, image);
        let reader = new FileReader();
        reader.readAsDataURL(imageSelected);
        reader.onload = e => {
            inputArticle.image = '' + e.target.result;
            selectedFileName = imageSelected.name;
            console.log('selected file:', imageSelected.name);
        };
    }

    function validate(): boolean {
        // TODO validate
        return validateCreateArticleRequest(inputArticle);
    }

    onMount(() => {
        console.debug('Showing', inputArticle.properties.length, 'properties');
        console.log('articleInputs', articleInputs);
    });
</script>

<PageContent>
    <PageCard title={$t('menu.addArticle')}>

        <Form className="inline-block w-full"
              on:submit={() => handleOnSubmit()}>
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
                                  bind:this={inputArticle.name}
                                  on:change={event => inputArticle.name = event.target.value}
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
                                      on:change={event => inputArticle.category = {name: event.target.value}}
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
                                             bind:value={inputArticle.quantity}
                                             on:change={event => inputArticle.quantity = to_number(event.target.value)}
                                             slot="right"
                        >
                        </LabeledNumericInput>
                    </InputFlexContainer>

                    <HorizontalRuler className="border-b-1 border-gray-300 mt-8 mx-4"></HorizontalRuler>

                    {#each inputArticle?.properties as property, i}
                        <PropertyInput edit={false}
                                       leftLabelOptions={{
                                           className: 'text-gray-600 ml-2',
                                           isBold: true,
                                           name: 'prop-inner-name' + i,
                                           text: $t('props.name'),
                                       }}
                                       isValid={property => validatePropertyRequest(property)}
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
                                   isValid={property => validatePropertyRequest(property)}
                                   parentId="prop-parent-new"
                                   parentLabelOptions={{
                                       className: 'text-gray-600 mt-10',
                                       hide: inputArticle?.properties.length > 0,
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
                                            src: inputArticle.image
                                          }}
                                          on:change={event => onImageSelected(event)}
                        />

                        <div class="flex p-0 m-0 h-10 mt-4">
                            <div class="w-full text-right">
                                {#if responseErrors && Object.keys(responseErrors)?.length > 0 }
                                    <span class="w-full leading-10 pr-5">
                                        {getImageResponseMessage()}
                                    </span>
                                {/if}
                            </div>
                            <Button className="w-1/8 align-end float-right"
                                    type={ButtonType.Submit}
                                    priority={ButtonPriority.Primary}
                                    on:click={event => onAddClick(event)}
                            >
                                {$t('general.add')}
                            </Button>
                        </div>
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