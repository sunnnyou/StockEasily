<script lang="ts">
    import {ButtonPriority} from '$components/html/button/button-priority';
    import {faPen, faPlus} from '@fortawesome/free-solid-svg-icons';
    import {onMount} from 'svelte';
    import {LabelOptions} from './label-options';
    import {PropertyRequestDto} from '../../../dto/property-request-dto';
    import {t} from '$i18n/i18n';

    import Button from '$components/html/button/Button.svelte';
    import FaIcon from '$components/common/FaIcon.svelte';
    import InputFlexContainer from '$components/common/input/InputFlexContainer.svelte';
    import Label from '$components/html/input/Label.svelte';
    import LabeledInput from '$components/common/input/LabeledInput.svelte';

    export let addMarginTop: boolean = true;
    export let edit = false;
    export let forceEdit = false;
    export let leftLabelOptions: LabelOptions | undefined;
    export let leftPlaceholder = '';
    export let onSave: Function | undefined = undefined;
    export let parentClass: string | undefined = undefined;
    export let parentId: string | undefined;
    export let parentLabelOptions: LabelOptions;
    export let property: PropertyRequestDto | undefined = undefined;
    export let rightLabelOptions: LabelOptions | undefined;
    export let rightPlaceholder = '';

    let edit = false;

    onMount(() => {
        edit = editInitially;
        setHideProp();
    });

    function setHideProp() {
        leftLabelOptions = {...leftLabelOptions, hide: !edit};
        rightLabelOptions = {...rightLabelOptions, hide: !edit};
        leftLabelOptions = leftLabelOptions;
        rightLabelOptions = rightLabelOptions;
    }

    function toggleEdit() {
        if (forceEdit) {
            return;
        }
        edit = !edit;
        setHideProp();
    }

    function onButtonClick() {

        toggleEdit();
        console.log('changed to edit: ', edit);

        if (!edit && onSave) {
            onSave(property);
        }
    }
</script>

<div>
    <div class="flex flex-col{parentClass ? ' ' + parentClass : ''}">
        <div class="flex items-end h-10 {addMarginTop ? ' mt-2' : ''}">
            <Label className={parentLabelOptions.className}
                   name={parentLabelOptions.name}
                   bold={parentLabelOptions.isBold}
            >
                {#if parentLabelOptions?.text?.length > 0}
                    {parentLabelOptions.text}
                {:else}
                    {$t('props') + ':'}
                {/if}
            </Label>
        </div>
    </div>
    <div class="w-full flex flex-row"
         id={parentLabelOptions.name}
    >
        <div class="w-11/12">
            <InputFlexContainer {parentId}
                                leftClass="w-1/2"
                                rightClass="w-1/2"
            >
                <LabeledInput disabled={!edit}
                              labelOptions={leftLabelOptions}
                              placeholder={leftPlaceholder || $t('props.name.placeholder')}
                              value={property?.name}
                              on:change={event => property.name = event.target.value}
                              slot="left"
                />

                <LabeledInput disabled={!edit}
                              labelOptions={rightLabelOptions}
                              placeholder={rightPlaceholder || $t('props.description.placeholder')}
                              value={property?.description}
                              on:change={event => property.description = event.target.value}
                              slot="right"
                />
            </InputFlexContainer>
        </div>
        <Button className="self-end h-10"
                priority={ButtonPriority.Transparent}
                on:click={() => onButtonClick()}
        >
            <FaIcon icon={edit ? faPlus : faPen}
                    parentClass="mx-1"
            />
        </Button>
    </div>
</div>