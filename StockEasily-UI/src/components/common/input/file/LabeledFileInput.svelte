<script lang="ts">
    import {InputType} from '$components/html/input/input-type.js';

    import LabeledInput from '$components/common/input/LabeledInput.svelte';

    export let addMarginTop = true;
    export let className = '';
    export let forName = '';
    export let id = '';
    export let label: string;
    export let labelClass: string | undefined = undefined;
    export let name = '';
    export let parentClass: string | undefined = undefined;
    export let previewImageOptions: PreviewImageOptions = {alt: '', show: false, src: ''};
</script>

<LabeledInput {addMarginTop}
              className="hidden file{(className?.length > 0 ? ' ' + className : '')}"
              {forName}
              {id}
              {label}
              labelClass={labelClass}
              {name}
              {parentClass}
              {previewImageOptions}
              type={InputType.File}
/>
