<script lang="ts">

    import {ButtonType} from './button-type';

    export let className = '';
    export let id = '';
    export let name = '';
    export let onClick: Function | undefined;
    export let type = ButtonType.Button;
    export let value: string | undefined;

    function onClickCallback() {
        if (onClick) {
            onClick();
        }
    }
</script>

<button {id} class={className} {name} {type} on:click={onClickCallback}>
    {#if $$slots.default}
        <slot/>
    {:else if value?.length > 0}
        {value}
    {/if}
</button>