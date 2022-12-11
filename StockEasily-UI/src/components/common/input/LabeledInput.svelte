<script lang="ts">
    import {AcceptType} from './file/accept-type';
    import {InputType} from '$components/html/input/input-type';
    import {LabelOptions} from './label-options';
    import {onMount} from 'svelte';
    import {PreviewImageOptions} from './preview-image-options';

    import Input from '$components/html/input/Input.svelte';
    import Label from '$components/html/input/Label.svelte';

    export let accept = AcceptType.Any;
    export let addMarginTop = true;
    export let allowMultiple = true;
    export let files: File[] = [];
    export let className = '';
    export let forName: string | undefined = undefined;
    export let id: string;
    export let labelOptions: LabelOptions = {className: '', isBold: true, placeAfterInput: false, text: ''};
    export let max = '';
    export let min = '';
    export let name = '';
    export let parentClass = '';
    export let placeholder = '';
    export let previewImageOptions: PreviewImageOptions = {alt: '', show: false, src: ''};
    export let step = 1;
    export let title = '';
    export let type = InputType.Text;
    export let value = '';

    let internalValue = undefined;

    onMount(() => {
        internalValue = value?.length > 0 ? value : (type == InputType.Number ? '1' : '');
    });

    function getLabelParentClass() {
        return type === InputType.File ? 'w-1/2 mx-auto text-center' : '';
    }
</script>

<div class="flex flex-col{parentClass ? ' ' + parentClass : ''}">
    <div class="flex items-end h-10 mb-2{addMarginTop ? ' mt-2' : ''}">
        {#if labelOptions && !labelOptions.placeAfterInput}
            <div class={getLabelParentClass()}>
                <Label bold={labelOptions.isBold}
                       className={labelOptions.className || ''}
                       {forName}
                       {id}
                >
                    <slot name="label">
                        {#if labelOptions.text}
                            {labelOptions.text}
                        {/if}
                    </slot>
                </Label>
            </div>
        {/if}

        {#if $$slots.inner}
            <slot name="inner"/>
        {/if}

        {#if labelOptions?.placeAfterInput}
            <div class={getLabelParentClass()}>
                <Label bold={labelOptions.isBold}
                       className={labelOptions.className || ''}
                       {forName}
                       {id}
                >
                    <slot name="label">
                        {#if labelOptions.text}
                            {labelOptions.text}
                        {/if}
                    </slot>
                </Label>
            </div>
        {/if}

    </div>
    <div class={'max-h-min'}>
        <Input {accept}
               {allowMultiple}
               {className}
               {id}
               {max}
               {min}
               name={name?.length > 0 ? name : id}
               {placeholder}
               {previewImageOptions}
               {step}
               {title}
               {type}
               value={internalValue}
               bind:files
               on:change
               on:input
        />
    </div>
    {#if false}
        <!--        <slot></slot>-->
    {/if}
</div>
