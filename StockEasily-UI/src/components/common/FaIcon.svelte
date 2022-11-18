<script lang="ts">
    import Fa from 'svelte-fa/src/fa.svelte';

    export let className = '';
    export let fw = false;
    export let icon: any;
</script>

<span class="inline">
    <i class="inline-block {className}">
        <Fa {icon} {fw}/>
    </i>
    <span> <slot/> </span>
</span>