<script lang="ts">
    export let alt: string;
    export let className = '';
    export let src: string;
    export let title = '';
</script>

<img {alt}
     class="mx-auto max-w-sm max-h-80{className?.length > 0 ? ` ${className}` : ''}"
     src={src || ''}
     title={title || alt}
>