<script lang="ts">
    import {containsMinMaxStep} from '$components/html/input/input-type';
    import {containsPlaceholder} from '$components/html/input/input-type';
    import {InputType} from './input-type';

    export let className = '';
    export let id = '';
    export let min = '';
    export let max = '';
    export let name = '';
    export let placeholder = '';
    export let step = 1;
    export let title = '';
    export let type = InputType.Text;
    export let value = '';

    function typeAction(node) {
        node.type = type;
    }
</script>

{#if containsMinMaxStep(type)}
    <input class={className}
           {id}
           min={min?.length > 0 ? min : ''}
           max={max?.length > 0 ? max : ''}
           {name}
           {step}
           {title}
           {type}
           use:typeAction
           {value}
           on:change
           on:input
    >
{:else if containsPlaceholder(type)}
    <input class={className}
           {id}
           {name}
           {placeholder}
           {title}
           {type}
           use:typeAction
           {value}
           on:change
           on:input
    >
{:else}
    <input class={className}
           {id}
           {name}
           {placeholder}
           {title}
           {type}
           use:typeAction
           {value}
           on:change
           on:input
    >
{/if}

<style>
    /* Extend needed styles here! */
    input[type=number], input[type=file], input[type=text], input[type=search] {
        border: 1px solid #d8dbdf;
        box-sizing: border-box;
        height: 40px;
        position: relative;
        display: flex;
        align-items: center;
        width: 100%;
    }

    input[type=file] {
        padding: 1rem;
        height: 10rem;
        width: 10rem;
    }
</style>