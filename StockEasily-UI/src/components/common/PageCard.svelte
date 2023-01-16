<script lang="ts">
    import TemplateCard from '$components/template/card/TemplateCard.svelte';

    export let headlineClass = '';
    export let title = '';
</script>

<TemplateCard {headlineClass} {title}>
    <slot></slot>
</TemplateCard>