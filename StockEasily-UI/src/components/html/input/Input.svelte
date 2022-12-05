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
           {type}
           use:typeAction
           {value}
           on:change
           on:input
    >
{/if}

<style>
    input[type=number], input[type=text], input[type=search] {
        border: 1px solid #d8dbdf;
        box-sizing: border-box;
        height: 40px;
        position: relative;
        display: flex;
        align-items: center;
        width: 100%;
    }
</style>