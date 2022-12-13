<script lang="ts">
    import Fa from 'svelte-fa/src/fa.svelte';

    export let className = '';
    export let color = '';
    export let fw = false;
    export let icon: any;
    export let parentClass = '';
    export let scale = 1;
    export let slotClass = '';
</script>

<span class="inline flex-container{parentClass?.length > 0 ? ' ' + parentClass : ''}">
    <i class="inline-block{className ? ' ' + className : ''}">
        <Fa {icon} {fw} {color} {scale}/>
    </i>
    <span class="pb-1 md:pb-0 text-sm{slotClass ? ' ' + slotClass : ''}">
        <slot/>
    </span>
</span>

<style>
    /*noinspection CssUnusedSymbol*/
    .flex-container {
        display: flex;
        flex-wrap: nowrap;
        align-items: center;
    }

    .flex-container > i {
        text-align: center;
        line-height: 20px;
    }
</style>