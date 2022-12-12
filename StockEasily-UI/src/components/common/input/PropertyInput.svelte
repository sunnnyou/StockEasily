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
    export let editInitially = false;
    export let id: { name: string, description: string };
    export let parentClass: string | undefined = undefined;
    export let parentId: string | undefined;
    export let parentLabelOptions: LabelOptions = {className: 'text-gray-600 font-bold mt-10', isBold: true};
    export let leftLabelOptions: LabelOptions = {
        className: '',
        hide: false,
        isBold: true,
        placeAfterInput: false,
        text: '',
    };
    export let leftPlaceholder = '';
    export let property: PropertyRequestDto;
    export let rightLabelOptions: LabelOptions = {
        className: '',
        hide: false,
        isBold: true,
        placeAfterInput: false,
        text: '',
    };
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
        edit = !edit;
        setHideProp();
    }
</script>

<div>
    <div class="flex flex-col{parentClass ? ' ' + parentClass : ''}">
        <div class="flex items-end h-10 {addMarginTop ? ' mt-2' : ''}">
            <Label className={parentLabelOptions.className}>
                {#if parentLabelOptions?.text?.length > 0}
                    {parentLabelOptions.text}
                {:else}
                    {$t('props') + ':'}
                {/if}
            </Label>
        </div>
    </div>
    <div class="w-full flex flex-row">
        <div class="w-11/12">
            {#if edit}
                <InputFlexContainer {parentId}
                                    leftClass="w-1/2"
                                    rightClass="w-1/2"
                >
                    <LabeledInput id={id.name}
                                  labelOptions={leftLabelOptions}
                                  placeholder={leftPlaceholder || $t('props.name.placeholder')}
                                  on:change={event => property.name = event.target.value}
                                  value={property.name}
                                  slot="left"
                    />


                    <LabeledInput id={id.description}
                                  labelOptions={rightLabelOptions}
                                  placeholder={rightPlaceholder || $t('props.description.placeholder')}
                                  on:change={event => property.description = event.target.value}
                                  value={property.description}
                                  slot="right"
                    />
                </InputFlexContainer>
            {:else}
                <InputFlexContainer {parentId}
                                    leftClass="w-1/2"
                                    rightClass="w-1/2"
                >
                    <span class="bg-blue-100 text-blue-800 text-l font-medium mr-2 px-2.5 py-0.5 rounded dark:bg-blue-200 dark:text-blue-800 h-10 leading-10 mt-5 pl-2"
                          slot="left"
                    >
                        {property.name}
                    </span>

                    <span class="bg-gray-100 text-gray-800 text-l font-medium mr-2 px-2.5 py-0.5 rounded dark:bg-gray-700 dark:text-gray-300 h-10 leading-10 mt-5 pl-2"
                          slot="right"
                    >
                        {property.description}
                    </span>
                </InputFlexContainer>
            {/if}
        </div>
        <Button
                className="self-end h-10"
                priority={ButtonPriority.Transparent}
                on:click={() => {
                    toggleEdit();
                    console.log('changed to edit: ', edit);
                }
                }
        >
            <FaIcon icon={edit ? faPlus : faPen}
                    parentClass="mx-1"
            />
        </Button>
    </div>
</div>