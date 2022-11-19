<script lang="ts">
    import type {MetricCardType} from './metric-card-type';

    import FaIcon from '$components/common/FaIcon.svelte';

    export let content: MetricCardType = {color: 'green', title: '', value: ''};
</script>

<div class="bg-white border rounded shadow p-2">
    <div class="flex flex-row items-center">
        <div class="flex-shrink pr-4">
            <div class="rounded p-3 bg-{content.color}-600">
                {#if content.icon}
                    <FaIcon color="white" icon={content.icon} fw={true} scale={1.5}></FaIcon>
                {/if}
            </div>
        </div>
        <div class="flex-1 text-right md:text-center">
            <h5 class="font-bold uppercase text-gray-400">{content.title}</h5>
            <h3 class="font-bold text-3xl text-gray-600">{content.value}</h3>
        </div>
    </div>
</div>