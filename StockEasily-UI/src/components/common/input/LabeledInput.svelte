<script lang="ts">
    import {InputType} from '$components/html/input/input-type';
    import {onMount} from 'svelte';

    import Input from '$components/html/input/Input.svelte';
    import Label from '$components/html/input/Label.svelte';

    export let addMarginTop = true;
    export let className = '';
    export let forName: string | undefined = undefined;
    export let id: string;
    export let labelClass: string | undefined;
    export let label: string;
    export let max = '';
    export let min = '';
    export let name = '';
    export let placeholder = '';
    export let step = 1;
    export let type = InputType.Text;
    export let value = '';

    let showLabel = true;
    let internalValue = undefined;

    onMount(() => {
        internalValue = value?.length > 0 ? value : (type == InputType.Number ? '1' : '');
    });

    function getInputHeightClass() {
        return type === InputType.Number ? 'h-10-5' : '';
    }

    function getName() {
        if (name?.length > 0) {
            return name;
        }
        return id;
    }
</script>

<div class="flex flex-col">
    {#if showLabel && label}
        <div class="flex items-end h-10 mb-2{addMarginTop ? ' mt-2' : ''}">
            <div class="w-1/2">
                <Label class={labelClass}
                       {forName}
                       {id}
                >
                    {label}
                </Label>
            </div>

            {#if $$slots.default}
                <div class="text-right w-1/2 inline-flex justify-end">
                    <slot/>
                </div>
            {/if}
        </div>
    {/if}
    <div>
        <Input className="{className}{(className?.length > 0 ? ' ' : '') + getInputHeightClass()}"
               {id}
               {max}
               {min}
               name={getName()}
               on:change
               on:input
               {placeholder}
               {step}
               {type}
               value={internalValue}
        />
    </div>
</div>
