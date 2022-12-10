<script lang="ts">
    export let className = '';
    export let forName: string | undefined = undefined;
    export let id = '';
    export let name = '';
</script>

<!--suppress XmlInvalidId -->
<label for={forName || name} {id} class={className}>
    <slot/>
</label>