<script lang="ts">
    import {LabelOptions} from './label-options';
    import {PropertyRequestDto} from '../../../dto/property-request-dto';
    import {t} from '$i18n/i18n';

    import InputFlexContainer from '$components/common/input/InputFlexContainer.svelte';
    import Label from '$components/html/input/Label.svelte';
    import LabeledInput from '$components/common/input/LabeledInput.svelte';

    export let addMarginTop: boolean = true;
    export let id: { name: string, description: string };
    export let parentClass: string | undefined = undefined;
    export let parentId: string | undefined;
    export let parentLabelOptions: LabelOptions = {className: 'text-gray-600 font-bold mt-10', isBold: true};
    export let leftLabelOptions: LabelOptions = {className: '', isBold: true, placeAfterInput: false, text: ''};
    export let leftPlaceholder = '';
    export let property: PropertyRequestDto;
    export let rightLabelOptions: LabelOptions = {className: '', isBold: true, placeAfterInput: false, text: ''};
    export let rightPlaceholder = '';
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
    <div class="w-full">
        <InputFlexContainer {parentId}
                            leftClass="w-1/2"
                            rightClass="w-1/2"
        >
            <LabeledInput id={id.name}
                          labelOptions={leftLabelOptions}
                          placeholder={leftPlaceholder || $t('props.name.placeholder')}
                          on:change={event => property.name = event.target.value}
                          slot="left">


            </LabeledInput>


            <LabeledInput id={id.description}
                          labelOptions={rightLabelOptions}
                          placeholder={rightPlaceholder || $t('props.description.placeholder')}
                          on:change={event => property.description = event.target.value}
                          slot="right"
            />
        </InputFlexContainer>
    </div>
</div>