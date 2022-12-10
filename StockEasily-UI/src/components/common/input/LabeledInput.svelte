<script lang="ts">
    import {AcceptType} from './file/accept-type';
    import {InputType} from '$components/html/input/input-type';
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
    export let labelClass: string | undefined = undefined;
    export let label: string;
    export let labelAfterInput = false;
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

    let showLabel = true;
    let internalValue = undefined;

    onMount(() => {
        internalValue = value?.length > 0 ? value : (type == InputType.Number ? '1' : '');
    });
</script>

<div class="flex flex-col{parentClass ? ' ' + parentClass : ''}">
    {#if showLabel && label}
        <div class="flex items-end h-10 mb-2{addMarginTop ? ' mt-2' : ''}">
            {#if !labelAfterInput}
                <div class="w-1/2">
                    <Label className={labelClass}
                           {forName}
                           {id}
                    >
                        {label}
                    </Label>
                </div>
            {/if}

            {#if $$slots.default}
                <div class="text-right w-1/2 inline-flex justify-end">
                    <slot/>
                </div>
            {/if}

            {#if labelAfterInput}
                <div class="w-1/2">
                    <Label className={labelClass}
                           {forName}
                           {id}
                    >
                        {label}
                    </Label>
                </div>
            {/if}

        </div>
    {/if}
    <div class={'h-10'}>
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
</div>
