<script lang="ts">
    import Fa from 'svelte-fa/src/fa.svelte';

    export let className = '';
    export let slotClass = '';
    export let fw = false;
    export let icon: any;
    export let scale = 1;
    export let color = '';
</script>

<span class="inline flex-container">
    <i class="inline-block {className}">
        <Fa {icon} {fw} {color} {scale}/>
    </i>
    <span class="pb-1 md:pb-0 text-sm{slotClass ? ' ' + slotClass : ''}">
        <slot/>
    </span>
</span>

<style>
    .flex-container {
        display: flex;
        flex-wrap: nowrap;
        align-items: center;
    }

    .flex-container > i {
        /*width: 100px;*/
        /*margin: 10px;*/
        text-align: center;
        line-height: 20px;
    }
</style>