<script lang="ts">
    import type {ValidatableArticle} from '$dto/create-article-request-dto';
    import type {ValidatableProperty} from '../../../dto/property-request-dto';

    import {AcceptType} from '$components/common/input/file/accept-type';
    import {ButtonPriority} from '$components/html/button/button-priority';
    import {ButtonType} from '$components/html/button/button-type';
    import {CreateArticleRequestDto} from '$dto/create-article-request-dto';
    import {imageSelected, responseErrors, selectedFileName} from '$common/image-input-utils';
    import {goto} from '$app/navigation';
    import {formatBytesAsKilobytes} from '../../../common/number-util';
    import {isPropertyDescriptionValid, isPropertyNameValid, PROPERTY_LIMITS} from '../../../dto/property-request-dto';
    import {getImageResponseMessage, onImageSelected} from '$common/image-input-utils';
    import {onSaveProperty} from '$common/property-utils';
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

    let validatableArticle: ValidatableArticle = {
        category: {value: '', error: ''},
        image: {value: '', error: ''},
        name: {value: '', error: ''},
        properties: [],
        quantity: {value: 1, error: ''},
    };

    async function handleOnSubmit() {
        if (!validateForm()) {
            console.error('Could not validate input data');
            return;
        }

        if (imageSelected?.size > SESSION_INFO.IMAGE_MAX_SIZE) {
            validatableArticle.image = undefined;
            console.debug('File size is too big (' + imageSelected.size + ') => aborting');
            return;
        }

        fetch(SESSION_INFO.API_ENDPOINT + '/api/v1/articles', {
            method: 'POST',
            body: JSON.stringify(new CreateArticleRequestDto(validatableArticle)),
            headers: {
                'Content-Type': 'application/json',
            },
        }).then(response => {
            if (!response.ok) {
                responseErrors = response.json()['errors'];
                console.error('Could not POST article, response errors:', responseErrors);
                return;
            }

            goto('/articles');
        }).catch(error => {
            if (!error) {
                console.error('Could not reach backend, probably offline?');
                return;
            }
            console.error('Unknown error: Could not POST article, response error:', error);
        });

    }

    function validateForm() {
        let isValid = true;

        if (!validateTextLengthBetween1And30(validatableArticle.name, 'Name')) {
            isValid = false;
        }
        if (!validateTextLengthBetween1And30(validatableArticle.category, 'Category')) {
            isValid = false;
        }
        if (!validateProperties()) {
            isValid = false;
        }

        // sadly this is currently needed so UI gets refreshed
        validatableArticle = validatableArticle;

        return isValid;
    }

    function validatePropertyDescription(prop: ValidatableProperty): boolean {
        let isValid = true;
        if (!isPropertyDescriptionValid(prop)) {
            isValid = false;
            prop.errors.description = $t('validation.max', {
                entity: 'Description',
                max: PROPERTY_LIMITS.MAX_LENGTH.DESCRIPTION,
            });
        } else {
            prop.errors.description = '';
        }
        return isValid;
    }

    function validatePropertyName(prop: ValidatableProperty): boolean {
        let isValid = true;
        if (!isPropertyNameValid(prop)) {
            isValid = false;
            prop.errors.name = $t('validation.between', {
                entity: 'Name',
                min: PROPERTY_LIMITS.MIN_LENGTH.NAME,
                max: PROPERTY_LIMITS.MAX_LENGTH.NAME,
            });
        } else {
            prop.errors.name = '';
        }
        return isValid;
    }

    function validateProperty(property: ValidatableProperty): boolean {
        let isValid = true;

        if (!property.value) {
            isValid = false;
        } else {

        }
        if (property.value) {
            isValid = isValid ? validatePropertyName(property) : false;
            isValid = isValid ? validatePropertyDescription(property) : false;
        }

        return isValid;
    }

    function validateProperties(): boolean {
        let isValid = true;
        for (const PROPERTY of validatableArticle.properties) {
            isValid = isValid ? validateProperty(PROPERTY) : false;

        }
        return isValid;
    }

    function validateTextLengthBetween(field: { value: string; error: string }, entity: string, min: number, max: number, nullable: boolean = false): boolean {
        const VALUE = field.value;
        const isValid = VALUE ? VALUE.length >= min || VALUE.length <= max : nullable;
        if (!isValid) {
            field.error = $t('validation.between', {entity: entity, min: min, max: max});// `${entity} length must be between ${min} and ${max}`;
            console.warn(`Invalid input ${entity}:`, field.error);
        } else {
            field.error = '';
        }
        return isValid;
    }

    function validateTextLengthBetween1And30(field: { value: any; error: string }, entity: string, nullable: boolean = false): boolean {
        return validateTextLengthBetween(field, entity, 1, 30, nullable);
    }
</script>

<PageContent>
    <PageCard title={$t('menu.addArticle')}>

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
                                   onSave={property => validatableArticle.properties = onSaveProperty(validatableArticle, property)}
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
                                          onFileChange={files => {
                                              validatableArticle.image = onImageSelected(files, validatableArticle);
                                          }}
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