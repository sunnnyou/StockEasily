<script lang="ts">
    import type {PropertyRequestDto} from '$dto/property-request-dto';
    import type {ValidatableProperty} from '$validation/validatable-property';

    import {ButtonPriority} from '$components/html/button/button-priority';
    import {LabelOptions} from './label-options';

    import {faCheck, faPen, faPlus} from '@fortawesome/free-solid-svg-icons';
    import {isPropertyRequestValid} from '$dto/property-request-dto';
    import {onMount} from 'svelte';
    import {PROPERTY_LIMITS} from '$dto/property-request-dto';
    import {t} from '$i18n/i18n';

    import Button from '$components/html/button/Button.svelte';
    import FaIcon from '$components/common/FaIcon.svelte';
    import InputFlexContainer from '$components/common/input/InputFlexContainer.svelte';
    import LabeledInput from '$components/common/input/LabeledInput.svelte';
    import PropertiesLabel from '$components/common/article/PropertiesLabel.svelte';

    export let addMarginTop = true;
    export let edit = false;
    export let errors = {name: '', description: ''};
    export let forceEdit = false;
    export let leftLabelOptions: LabelOptions | undefined;
    export let leftPlaceholder = '';
    export let onSave: Function | undefined = undefined;
    export let parentClass: string | undefined = undefined;
    export let parentId: string | undefined;
    export let parentLabelOptions: LabelOptions;
    export let property: ValidatableProperty | undefined = undefined;
    export let rightLabelOptions: LabelOptions | undefined;
    export let rightPlaceholder = '';

    let internalProperty: PropertyRequestDto | undefined;

    onMount(() => {
        internalProperty = property?.value?.name ?  property.value : {description: '', name: ''};
        setHideProp();
    });

    function setHideProp() {
        leftLabelOptions = {...leftLabelOptions, hide: !edit};
        rightLabelOptions = {...rightLabelOptions, hide: !edit};
    }

    function toggleEdit() {
        if (forceEdit) {
            return;
        }
        edit = !edit;
        setHideProp();
    }

    function onButtonClick() {
        if (!property && edit && !isPropertyRequestValid(internalProperty)) {
            return;
        }

        toggleEdit();

        // on saved
        if (forceEdit || !edit) {
            if (onSave) {
                onSave(internalProperty);
            }
            if (forceEdit) {
                internalProperty = {description: '', name: ''};
            }
        }
    }
</script>

<div>
    {#if parentLabelOptions && !parentLabelOptions.hide}
        <PropertiesLabel {addMarginTop}
                         className={parentClass}
                         labelOptions={parentLabelOptions}
        />
    {/if}
    {#if internalProperty?.name !== undefined }
        <div class="flex flex-row items-end"
             id={parentLabelOptions.name}
        >
            <div class="w-10/12">
                <InputFlexContainer
                        leftClass="w-1/2"
                        {parentId}
                        rightClass="w-1/2"
                >
                    <LabeledInput disabled={!edit}
                                  error={errors?.name}
                                  labelOptions={leftLabelOptions}
                                  maxLength={PROPERTY_LIMITS.MAX_LENGTH.NAME}
                                  placeholder={leftPlaceholder || $t('props.name.placeholder')}
                                  value={internalProperty.name}
                                  on:change={event => internalProperty.name = event.target.value.trim()}
                                  slot="left"
                    />

                    <LabeledInput disabled={!edit}
                                  error={errors?.description}
                                  labelOptions={rightLabelOptions}
                                  maxLength={PROPERTY_LIMITS.MAX_LENGTH.DESCRIPTION}
                                  placeholder={rightPlaceholder || $t('props.description.placeholder')}
                                  value={internalProperty.description}
                                  on:change={event => internalProperty.description = event.target.value.trim()}
                                  slot="right"
                    />
                </InputFlexContainer>
            </div>
            <Button className="m-0 w-0.5/12"
                    priority={ButtonPriority.TransparentHover}
                    title={forceEdit ? $t('general.add') : (edit ? $t('general.save') : $t('general.edit'))}
                    on:click={() => onButtonClick()}
            >
                <FaIcon icon={forceEdit ? faPlus : (edit ? faCheck : faPen)}
                        parentClass="mx-1"
                />
            </Button>
        </div>
    {/if}
</div>