<script lang="ts">
    export let headlineClass = 'uppercase';
    export let title = 'Template';
</script>

<div class="bg-white border rounded shadow">
    <div class="border-b p-3">
        <h5 class="font-bold text-gray-600{headlineClass?.length > 0 ? ' ' +  headlineClass : ''}">{title || ''}</h5>
    </div>

    <div class="p-5">
        {#if $$slots.default}
            <slot/>
        {/if}
    </div>
</div>
