<script lang="ts">
    export let className = '';
</script>

<form class={className}
      on:submit|preventDefault
>
    <slot/>
</form>