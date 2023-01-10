<script lang="ts">
    import {AcceptType} from '$components/common/input/file/accept-type';
    import {containsMinMaxStep} from '$components/html/input/input-type';
    import {containsPlaceholder} from '$components/html/input/input-type';
    import {faFileArrowUp} from '@fortawesome/free-solid-svg-icons';
    import {InputType} from './input-type';
    import {PreviewImageOptions} from '../../common/input/preview-image-options';
    import {selectedFiles} from '$common/image-input-utils';
    import {t} from '$i18n/i18n';

    import FaIcon from '$components/common/FaIcon.svelte';
    import PreviewImage from "$components/common/PreviewImage.svelte";

    export let accept = AcceptType.Any;
    export let allowMultiple = true;
    export let className = '';
    export let disabled = false;
    export let error = '';
    export let id = '';
    export let min = '';
    export let max = '';
    export let maxLength: string | undefined = undefined;
    export let name = '';
    export let onFileChange: ((files: File[]) => void) = () => {};
    export let placeholder = '';
    export let previewImageOptions: PreviewImageOptions = {alt: '', show: false, src: ''};
    export let step = 1;
    export let title = '';
    export let type = InputType.Text;
    export let value = '';

    function handleFileChange(event) {
        const FILES = event.target.files;
        if (FILES === undefined) {
            console.warn('Ignoring selected files, event.target.files is undefined');
            return;
        }
        selectedFiles.set(FILES);
        if (onFileChange) {
            onFileChange(FILES);
        }
    }

    function typeAction(node) {
        node.type = type;
    }

    let inputRef: HTMLElement;
</script>

<!-- special props: min, max, step -->
{#if containsMinMaxStep(type)}
    <input class={className}
           {disabled}
           {id}
           max={max?.length > 0 ? max : ''}
           min={min?.length > 0 ? min : ''}
           {name}
           {step}
           {title}
           {type}
           {value}
           on:change
           on:input
           use:typeAction
    >
    <!-- special props: maxlength, placeholder -->
{:else if containsPlaceholder(type)}
    <input class={className}
           {disabled}
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
    <!-- special props: accept, placeholder -->
{:else if type === InputType.File}
    {#if previewImageOptions?.show}
        {#if previewImageOptions.src}
            <PreviewImage alt={previewImageOptions.alt || $t('general.previewImage')}
                          src={previewImageOptions.src}
            />
        {:else}
            <div class="text-center my-20">
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
               bind:this={inputRef}
               on:change={handleFileChange}
               on:keydown
               use:typeAction
        >
    {:else}
        <input {accept}
               class={className}
               {id}
               {name}
               {title}
               {type}
               bind:this={inputRef}
               on:change={handleFileChange}
               on:keyup
               use:typeAction
        >
    {/if}
    <div class="mt-5 w-full cursor-pointer"
         on:click={() => inputRef.click()}
         on:keyup
    >
        <div class="w-14 h-14 mx-auto">
            <FaIcon className="inset-0 w-14 h-14 flex justify-center items-center" icon={faFileArrowUp}
                    scale="3"></FaIcon>
        </div>
        <div class="text-center">{$t($selectedFiles?.length > 0 ? 'general.replaceImage' : 'general.chooseImage')}</div>
    </div>
{:else}
    <input class={className}
           class:error={error?.length > 0 ? 'border-red-500': ''}
           {disabled}
           {id}
           {maxLength}
           {name}
           {title}
           {type}
           {value}
           on:change
           on:keyup
           on:input
           use:typeAction
    >
{/if}

{#if error?.length > 0}
    <p class="pt-1 error">{error}</p>
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