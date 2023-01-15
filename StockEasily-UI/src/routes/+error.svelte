<script lang="ts">
    import PageContent from "$components/common/PageContent.svelte";
    import {page} from "$app/stores";
</script>

<PageContent>
    <div class="flex flex-col items-center justify-center">
        <div class="text-9xl" >
            {$page.status}
        </div>
        <div class="text-lg">
            {$page.error.message}
        </div>
    </div>
</PageContent>
