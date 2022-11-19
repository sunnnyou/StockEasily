<script lang="ts">
    import type {MetricCardType} from './metric-card-type';

    import MetricCard from './MetricCard.svelte';

    export let classNames = 'w-full md:w-1/2 xl:w-1/3 p-3';
    export let items: MetricCardType[] = [];
</script>

<div class="flex flex-wrap">
    {#each items as content}
        <div class={classNames || ''}>
            <MetricCard {content}></MetricCard>
        </div>
    {/each}
</div>