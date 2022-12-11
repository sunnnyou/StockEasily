<script lang="ts">
    export let bold = false;
    export let className = '';
    export let forName: string | undefined = undefined;
    export let id = '';
    export let name = '';
</script>

<!--suppress XmlInvalidId -->
<label for={forName || name} {id} class={(bold ? 'font-bold ' : '') + className}>
    <slot/>
</label>