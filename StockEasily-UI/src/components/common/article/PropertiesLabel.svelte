<script lang="ts">
    import {LabelOptions} from '../input/label-options';
    import {t} from '$i18n/i18n';

    import Label from '$components/html/input/Label.svelte';

    export let addMarginTop = false;
    export let className = '';
    export let labelOptions: LabelOptions;
</script>

<div class="flex flex-col{className ? ` ${className}` : ''}">
    <div class="flex items-end h-10 {addMarginTop ? ' mt-2' : ''}">
        <Label className={labelOptions?.className || ''}
               name={labelOptions?.name || ''}
               bold={labelOptions?.isBold === undefined ? false : labelOptions.isBold}
        >
            {$t('props') + ':'}
        </Label>
    </div>
</div>