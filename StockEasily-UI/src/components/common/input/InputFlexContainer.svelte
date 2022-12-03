<script lang="ts">
    export let shrinkRight = true;
</script>

<div class="inline-flex w-full">
    {#if $$slots.left}
        <div class={shrinkRight ? 'w-70p' : ''}>
            <div class="w-full">
                <slot name="left"/>
            </div>
        </div>
    {/if}
    {#if $$slots.right}
        <div class={shrinkRight ? 'w-30p' : ''}>
            <slot name="right"/>
        </div>
    {/if}
</div>

<style>
    div.inline-flex.w-full {
        gap: 1%;
    }

</style>