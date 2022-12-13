<script lang="ts">
    export let bold = false;
    export let className = '';
    export let id = '';
    export let name: string;
</script>

<!--suppress XmlInvalidId -->
<label class={(bold ? 'font-bold' : '') + (className ? ' ' + className : '')}
       for={name}
       id={id || ''}
>
    <slot/>
</label>