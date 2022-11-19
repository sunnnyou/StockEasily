<script lang="ts">
    export let className = '';
    export let id = '';
    export let name = '';
</script>

<!--suppress XmlInvalidId -->
<label for={name} {id} class={className}>
    {#if $$slots.default}
        <slot/>
    {/if}
</label>