<script lang="ts">
    export let parentId: string | undefined;
    export let leftClass: string | undefined;
    export let parentClass: string = 'inline-flex w-full';
    export let rightClass: string | undefined;
</script>

<div class={parentClass}
     id={parentId || ''}
>
    {#if $$slots.left}
        <div class={leftClass || ''}>
            <div class="w-full">
                <slot name="left"/>
            </div>
        </div>
    {/if}
    {#if $$slots.right}
        <div class={rightClass || ''}>
            <slot name="right"/>
        </div>
    {/if}
</div>

<style>
    /*noinspection CssUnusedSymbol*/
    div.inline-flex.w-full {
        gap: 1%;
    }
</style>