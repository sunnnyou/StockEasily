<script lang="ts">
    import {InputType} from '$components/html/input/input-type';
    import {onMount} from 'svelte';

    import Input from '$components/html/input/Input.svelte';
    import Label from '$components/html/input/Label.svelte';
    // import Select from 'svelte-select';

    export let addMarginTop = true;
    export let className = '';
    export let id: string;
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

<div class="flex-container">
    {#if showLabel && label}
        <div id="left-wrapper"
             class="mb-2{addMarginTop ? ' mt-2' : ''}">
            <div class="w-1/2">
                <Label forName={id}>
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

<style>
    .flex-container {
        display: flex;
        flex-direction: column;
    }

    .flex-container > div {
        height: 40px;
    }

    #left-wrapper {
        display: flex;
        align-items: flex-end;
    }
</style>