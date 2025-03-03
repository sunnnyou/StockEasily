<script lang="ts">
    import {faArrowDown, faArrowUp} from '@fortawesome/free-solid-svg-icons';
    import {InputType} from '$components/html/input/input-type';
    import {LabelOptions} from './label-options';
    import {onMount} from 'svelte';
    import {t} from '$i18n/i18n';

    import FaIcon from '$components/common/FaIcon.svelte';
    import LabeledInput from '$components/common/input/LabeledInput.svelte';

    export let addMarginTop = true;
    export let className = '';
    export let error = '';
    export let iconParentClass: string | undefined;
    export let labelOptions: LabelOptions | undefined;
    export let max = '';
    export let min = '';
    export let offerSmallerSteps = false;
    export let placeholder = '';
    export let step = 1;
    export let title = '';
    export let value = '';

    const DEFAULT_STEP = 1;
    const STEPS = [100, 10, DEFAULT_STEP, 0.1, 0.01, 0.001];
    const STEPS_MAX_INDEX = STEPS.length - 1;

    let currentStep = step;
    let decrementSteps = false;
    let stepTranslation = '';

    function getNextStepIndex(previousStep: number, decrement: boolean): number {
        const INDEX = STEPS.indexOf(previousStep);
        if (decrement && INDEX != STEPS_MAX_INDEX || INDEX === 0) {
            return INDEX + 1;
        }
        return INDEX - 1;
    }

    function goNextStep(): number {
        const RESULT_INDEX = getNextStepIndex(currentStep, decrementSteps);
        const RESULT_STEP = STEPS[RESULT_INDEX];
        if (RESULT_INDEX === 0 || RESULT_INDEX === STEPS_MAX_INDEX) {
            decrementSteps = currentStep < RESULT_STEP;
        }
        return RESULT_STEP;
    }

    function switchStepSize() {
        switchToNextStep();
        updateStepTranslation();
    }

    function switchToNextStep() {
        currentStep = goNextStep();
    }

    function updateStepTranslation() {
        // @ts-ignore
        stepTranslation = $t('general.step') + ': ' + currentStep;
    }

    onMount(() => {
        currentStep = step;
        updateStepTranslation();
    });
</script>

<LabeledInput {addMarginTop}
              {className}
              {error}
              {labelOptions}
              {max}
              {min}
              on:select
              on:change
              {placeholder}
              step={offerSmallerSteps ? currentStep : step}
              {title}
              type={InputType.Number}
              bind:value
>
    <div class="text-right w-full inline-flex justify-end"
         slot="inner">
        {#if offerSmallerSteps}
            <div class="cursor-pointer inline-flex font-sm"
                 title={$t('general.nextStep') + ': ' + STEPS[getNextStepIndex(currentStep, decrementSteps)]}
                 on:click={switchStepSize}
                 on:keydown={switchStepSize}
            >
                <div class="mb-1">
                    <FaIcon className="text-gray-500"
                            icon={decrementSteps? faArrowDown : faArrowUp}
                            parentClass={iconParentClass}/>
                </div>
                <span class="text-right text-gray-500">
                {$t('general.step') + ': ' + currentStep}
            </span>
            </div>
        {/if}
    </div>
</LabeledInput>

<style>

    @media only screen and (max-width: 767px) {
        span {
            display: none;
        }
    }

    @media only screen and (max-width: 1023px) {
        span {
            display: none;
        }
    }
</style>