<script lang="ts">
    import {ButtonPriority} from '$components/html/button/button-priority.js';
    import {faArrowDown, faArrowUp} from '@fortawesome/free-solid-svg-icons';
    import {InputType} from '$components/html/input/input-type';
    import {onMount} from 'svelte';
    import {t} from '$i18n/i18n';

    import Button from '$components/html/button/Button.svelte';
    import FaIcon from '$components/common/FaIcon.svelte';
    import LabeledInput from '$components/common/input/LabeledInput.svelte';

    export let addMarginTop = true;
    export let className = '';
    export let iconParentClass: string | undefined;
    export let id: string;
    export let label: string;
    export let max = '';
    export let min = '';
    export let offerSmallerSteps = false;
    export let placeholder = '';
    export let step = 1;
    export let title = '';

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
              {id}
              {label}
              {max}
              {min}
              on:select
              on:change
              {placeholder}
              step={offerSmallerSteps ? currentStep : step}
              {title}
              type={InputType.Number}
>
    {#if offerSmallerSteps}
        <Button className="cursor-pointer inline-flex font-sm"
                title={$t('general.nextStep') + ': ' + STEPS[getNextStepIndex(currentStep, decrementSteps)]}
                priority={ButtonPriority.Transparent}
                on:click={switchStepSize}
                on:keydown={switchStepSize}
                slot="inner"
        >
            <div class="mb-1">
                <FaIcon className="text-gray-500"
                        icon={decrementSteps? faArrowDown : faArrowUp}
                        parentClass={iconParentClass}/>
            </div>
            <span class="text-right text-gray-500">
                {$t('general.step') + ': ' + currentStep}
            </span>
        </Button>
    {/if}
</LabeledInput>

<style>

    @media only screen and (max-width: 767px) {
        span {
            display: none;
        }
    }
</style>