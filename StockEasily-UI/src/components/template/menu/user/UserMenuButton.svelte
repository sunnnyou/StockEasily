<script lang="ts">
    import type {MenuButton} from '$common/menu-button';
    import {t} from '$i18n/i18n';

    import A from '$components/html/a/A.svelte';
    import FaIcon from '$components/common/FaIcon.svelte';
    import HorizontalRuler from '$components/html/HorizontalRuler.svelte';

    export let button: MenuButton | undefined;
</script>

{#if button}
    {#if button.href}
        <A href={button.href} className="px-4 py-2 block no-underline hover:bg-gray-400 hover:no-underline">
            <FaIcon icon={button.icon} className="mr-2">
                {#if button.i18n}
                    {$t(button.i18n)}
                {/if}
            </FaIcon>
        </A>
    {:else }
        <HorizontalRuler className={button.className}/>
    {/if}
{/if}