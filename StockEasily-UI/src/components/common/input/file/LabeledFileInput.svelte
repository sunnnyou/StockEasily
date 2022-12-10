<script lang="ts">
    import {AcceptType} from './accept-type';
    import {InputType} from '$components/html/input/input-type';
    import {PreviewImageOptions} from '../preview-image-options';

    import LabeledInput from '$components/common/input/LabeledInput.svelte';

    export let addMarginTop = true;
    export let accept = AcceptType.Any;
    export let allowMultiple = true;
    export let className = '';
    export let files: File[] = [];
    export let forName = '';
    export let id = '';
    export let label: string;
    export let labelAfterInput = true;
    export let labelClass: string | undefined = undefined;
    export let name = '';
    export let parentClass: string | undefined = undefined;
    export let previewImageOptions: PreviewImageOptions = {alt: '', show: false, src: ''};
</script>

<LabeledInput {accept}
              {addMarginTop}
              {allowMultiple}
              className="hidden file{(className?.length > 0 ? ' ' + className : '')}"
              {forName}
              {id}
              {label}
              {labelAfterInput}
              {labelClass}
              {name}
              {parentClass}
              {previewImageOptions}
              type={InputType.File}
              on:change
              bind:files>
    <slot name="label"/>
    <slot name="inner"/>
</LabeledInput>
