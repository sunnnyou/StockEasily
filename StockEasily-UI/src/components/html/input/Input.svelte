<script lang="ts">
    import {AcceptType} from '$components/common/input/file/accept-type';
    import {containsMinMaxStep} from '$components/html/input/input-type';
    import {containsPlaceholder} from '$components/html/input/input-type';
    import {faFileArrowUp} from '@fortawesome/free-solid-svg-icons';
    import {InputType} from './input-type';
    import {PreviewImageOptions} from '../../common/input/preview-image-options';
    import {t} from '$i18n/i18n';

    import FaIcon from '$components/common/FaIcon.svelte';

    export let accept = AcceptType.Any;
    export let allowMultiple = true;
    export let className = '';
    export let disabled = false;
    export let files: File[] = [];
    export let id = '';
    export let min = '';
    export let max = '';
    export let maxLength: string | undefined = undefined;
    export let name = '';
    export let placeholder = '';
    export let previewImageOptions: PreviewImageOptions = {alt: '', show: false, src: ''};
    export let step = 1;
    export let title = '';
    export let type = InputType.Text;
    export let value = '';

    function handleInput(e: any) {
        files = type.match(/^(number|range)$/) ? +e.target.value : e.target.value;
    }

    function typeAction(node) {
        node.type = type;
    }

    let inputRef: HTMLElement;
</script>

{#if containsMinMaxStep(type)}
    <input class={className}
           {id}
           min={min?.length > 0 ? min : ''}
           max={max?.length > 0 ? max : ''}
           {name}
           {step}
           {title}
           {type}
           {value}
           on:change
           on:input
           use:typeAction
    >
{:else if containsPlaceholder(type)}
    <input class={className}
           {id}
           maxlength={maxLength}
           {name}
           {placeholder}
           {title}
           {type}
           {value}
           on:change
           on:input
           use:typeAction
    >
{:else if type === InputType.File}
    {#if previewImageOptions?.show}
        {#if previewImageOptions.src}
            <img alt={previewImageOptions.alt || $t('general.previewImage')}
                 class="mx-auto max-w-sm max-h-80"
                 src={previewImageOptions.src }
            >
        {:else}

            <div class="text-center mt-10 mb-5">
                {$t('general.input.noImageSelected')}
            </div>
        {/if}
    {/if}
    {#if allowMultiple}
        <input {accept}
               multiple=""
               class={className}
               {id}
               {name}
               {title}
               {type}
               {value}
               bind:this={inputRef}
               on:change
               on:keydown
               on:input={handleInput}
               use:typeAction
        >
    {:else}
        <input {accept}
               class={className}
               {id}
               {name}
               {title}
               {type}
               {value}
               bind:this={inputRef}
               on:change
               on:keydown
               on:input={handleInput}
               use:typeAction
        >
    {/if}
    <div class="mt-5 w-full cursor-pointer" on:click={() => inputRef.click()}>
        <div class="w-14 h-14 mx-auto">
            <FaIcon className="inset-0 w-14 h-14 flex justify-center items-center" icon={faFileArrowUp}
                    scale="3"></FaIcon>
        </div>
        <div class="text-center">{$t(files?.length > 0 ? 'general.replaceImage' : 'general.chooseImage')}</div>
    </div>
{:else if disabled}
    <input class={className}
           disabled
           {id}
           {name}
           {placeholder}
           {title}
           {type}
           {value}
           on:change
           on:keydown
           on:input
           use:typeAction
    >
{:else}
    <input class={className}
           {id}
           {name}
           {placeholder}
           {title}
           {type}
           {value}
           on:change
           on:keydown
           on:input
           use:typeAction
    >
{/if}

<style>
    /* Extend needed styles here! */
    input[type=number], input[type=file], input[type=text], input[type=search] {
        border: 1px solid #d8dbdf;
        box-sizing: border-box;
        height: 40px;
        position: relative;
        align-items: center;
        width: 100%;
    }

    input[type=file] {
        padding: 1rem;
        height: 50px;
        width: 50px;
    }
</style>