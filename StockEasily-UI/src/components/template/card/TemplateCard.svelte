<script lang="ts">
    export let title = 'Template';
</script>

<div class="bg-white border rounded shadow">
    <div class="border-b p-3">
        <h5 class="font-bold uppercase text-gray-600">{title || ''}</h5>
    </div>

    <div class="p-5">
        {#if $$slots.default}
            <slot/>
        {/if}
    </div>
</div>
