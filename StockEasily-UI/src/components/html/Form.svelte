<script lang="ts">
    export let className = '';
    export let onSubmit: Function;

    function handleSubmit() {
    }
</script>

<form class={className}
      on:submit|preventDefault={handleSubmit}
      on:submit|once={() => onSubmit()}
>
    <slot/>
</form>