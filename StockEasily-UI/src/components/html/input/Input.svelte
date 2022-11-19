<script lang="ts">
    import {InputType} from './input-type';

    export let className = '';
    export let id = '';
    export let name = '';
    export let placeholder = '';
    export let type = InputType.Text;
    export let value = '';
</script>

<input {type} {name} {id} class={className} {placeholder} {value}>