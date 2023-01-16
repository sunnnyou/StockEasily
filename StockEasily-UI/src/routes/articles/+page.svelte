<script lang="ts">
    import {onMount} from "svelte";
    import {goto} from "$app/navigation";
    onMount(() => {
        goto("articles/page/1")
    })
</script>