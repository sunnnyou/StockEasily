<script lang="ts">
    export let title = '';
    export let description = '';
    export let keywords = '';
</script>

<svelte:head>
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>{title || ''}</title>
    {#if description}
        <meta name="description" content={description}>
    {/if}
    {#if keywords}
        <meta name="keywords" content={keywords}>
    {/if}
</svelte:head>