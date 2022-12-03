<script lang="ts">
    import {InputType} from '$components/html/input/input-type';

    import Input from '$components/html/input/Input.svelte';
    import Label from '$components/html/input/Label.svelte';
    import Select from 'svelte-select';

    export let className = '';
    export let id: string;
    export let label: string;
    export let addMarginTop = true;
    export let name: string;
    export let onClick: Function | undefined;
    export let placeholder = '';
    export let type = InputType.ExternalSelect;

    let showLabel = true;

    function getInputHeightClass() {
        return type === InputType.Number ? 'h-10-5' : '';
    }

    function getName() {
        if (name?.length > 0) {
            return name;
        }
        return id;
    }

    function onClickCallback(event: Event) {
        if (onClick) {
            onClick(event);
        }
    }
</script>

<div class="flex-container">
    {#if showLabel && label}
        <div id="left-wrapper" class="mb-2{addMarginTop ? ' mt-2' : ''}">
            <Label for={id}>
                {label}
            </Label>
        </div>
    {/if}
    <div>
        {#if type !== InputType.ExternalSelect}
            <Input className="{className}{(className?.length > 0 ? ' ' : '') + getInputHeightClass()}" {id}
                   name={getName()} {placeholder} {type} value="1"/>
        {:else}
            <Select isClearable={true}
                    placeholderAlwaysShow={true}
                    {placeholder}

                    on:select={event => onClickCallback(event)}
            />
        {/if}
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