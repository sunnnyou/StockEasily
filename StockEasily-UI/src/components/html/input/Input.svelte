<script lang="ts">
    import {InputType} from './input-type';

    export let className = '';
    export let id = '';
    export let min = Number.MIN_VALUE;
    export let max = Number.MAX_VALUE;
    export let name = '';
    export let placeholder = '';
    export let step = 1;
    export let type = InputType.Text;
    export let value = '';
</script>

<input class={className}
       {id}
       min={min === Number.MIN_VALUE ? '' : min}
       max={max === Number.MAX_VALUE ? '' : max}
       {name}
       {placeholder}
       {step}
       {type}
       {value}
>