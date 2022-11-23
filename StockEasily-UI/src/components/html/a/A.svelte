<script lang="ts">
    import {ATarget} from './a-target';
    import {AType} from './a-type';

    export let className = '';
    export let href = '.';
    export let target = ATarget.Self;
    export let title = '';
    export let type = AType.TextHtml;
</script>

<a class={className} {href} {target} {title} {type}>
    <slot/>
</a>