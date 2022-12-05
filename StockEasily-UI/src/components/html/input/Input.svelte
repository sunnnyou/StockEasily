<script lang="ts">
    import {InputType} from './input-type';

    export let type = InputType.Text;
    export let className = '';
    export let id = '';
    export let min = Number.MIN_VALUE;
    export let max = Number.MAX_VALUE;
    export let name = '';
    export let placeholder = '';
    export let step = 1;
    export let value = '';

    function typeAction(node) {
        node.type = type;
    }
</script>

<input class={className}
       {id}
       min={min === Number.MIN_VALUE ? '' : min}
       max={max === Number.MAX_VALUE ? '' : max}
       {name}
       {placeholder}
       {step}
       {type}
       use:typeAction
       {value}
       on:change
       on:input
>

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