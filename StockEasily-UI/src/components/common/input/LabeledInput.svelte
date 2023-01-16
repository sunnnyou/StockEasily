<script lang="ts">
    import {AcceptType} from './file/accept-type';
    import {InputType} from '$components/html/input/input-type';
    import {LabelOptions} from './label-options';
    import {PreviewImageOptions} from './preview-image-options';

    import Input from '$components/html/input/Input.svelte';
    import Label from '$components/html/input/Label.svelte';

    export let accept = AcceptType.Any;
    export let addMarginTop = true;
    export let allowMultiple = true;
    export let disabled = false;
    export let error = '';
    export let className = '';
    export let labelOptions: LabelOptions | undefined;
    export let max = '';
    export let maxLength = '';
    export let min = '';
    export let name = '';
    export let onDelete: Function | undefined = undefined;
    export let onFileChange: ((files: File[]) => void) = () => {};
    export let parentClass = '';
    export let placeholder = '';
    export let previewImageOptions: PreviewImageOptions = {alt: '', show: false, src: ''};
    export let step = 1;
    export let title = '';
    export let type = InputType.Text;
    export let value = '';

    function getLabelParentClass() {
        return type === InputType.File ? 'w-1/2 mx-auto text-center' : '';
    }

    function showLabel() {
        return labelOptions !== undefined && !labelOptions.hide;
    }
</script>

<div class="flex flex-col{parentClass ? ' ' + parentClass : ''}">
    {#if showLabel()}
        <div class="flex items-end h-10 mb-2{addMarginTop ? ' mt-2' : ''}">
            {#if !labelOptions.placeAfterInput}
                <div class={getLabelParentClass()}>
                    <Label bold={labelOptions.isBold}
                           className={labelOptions.className}
                           id={labelOptions.id}
                           name={labelOptions.name}
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

            {#if labelOptions.placeAfterInput}
                <div class={getLabelParentClass()}>
                    <Label bold={labelOptions.isBold}
                           className={labelOptions.className}
                           id={labelOptions.id}
                           name={labelOptions.name}
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
    {/if}

    <div class={'max-h-min'}>
        <Input {accept}
               {allowMultiple}
               {className}
               {disabled}
               {error}
               id={labelOptions.name}
               {max}
               {min}
               {maxLength}
               {name}
               {onDelete}
               {onFileChange}
               {placeholder}
               {previewImageOptions}
               {step}
               {title}
               {type}
               bind:value
               on:change
               on:input
        />
    </div>
    <!--
     Note: This is a workaround to hide the falsy JS warning:
     '<LabeledInput> received an unexpected slot "default"'
     which is currently a bug in svelte and appears when passing slots without whitespaces:
     See more: https://github.com/sveltejs/svelte/issues/4546#issuecomment-630756451
    -->
    {#if false}
        <slot/>
    {/if}
</div>
