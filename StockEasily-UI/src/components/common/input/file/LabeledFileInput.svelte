<script lang="ts">
    import {AcceptType} from './accept-type';
    import {InputType} from '$components/html/input/input-type';
    import {LabelOptions} from '../label-options';
    import {PreviewImageOptions} from '../preview-image-options';

    import LabeledInput from '$components/common/input/LabeledInput.svelte';

    export let addMarginTop = true;
    export let accept = AcceptType.Any;
    export let allowMultiple = true;
    export let className = '';
    export let error = '';
    export let labelOptions: LabelOptions | undefined;
    export let name = '';
    export let onDelete: Function | undefined = undefined;
    export let onFileChange: ((files: File[]) => void) = () => {};
    export let parentClass: string | undefined = undefined;
    export let previewImageOptions: PreviewImageOptions = {alt: '', show: false, src: ''};
</script>

<LabeledInput {accept}
              {addMarginTop}
              {allowMultiple}
              className="hidden file{(className?.length > 0 ? ' ' + className : '')}"
              {error}
              {labelOptions}
              {name}
              {onDelete}
              {onFileChange}
              {parentClass}
              {previewImageOptions}
              type={InputType.File}
              on:change
>
    <slot name="label"/>
    <slot name="inner"/>
</LabeledInput>
