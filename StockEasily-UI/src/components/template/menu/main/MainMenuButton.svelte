<script lang="ts">
    import type {MenuButton} from '$common/menu-button';
    import {t} from '$i18n/i18n';
    import {page} from '$app/stores';

    import A from '$components/html/a/A.svelte';
    import FaIcon from '$components/common/FaIcon.svelte';

    export let button: MenuButton | undefined;

    function isSelected() {
        return $page.url.pathname === button?.href;
    }

</script>

<A href={button?.href}
   className="block py-1 md:py-3 pl-1 align-middle no-underline hover:text-gray-900 border-b-2 hover:border-blue-500 {isSelected() ? 'text-blue-500 border-blue-500' : 'text-gray-500'}">
    <FaIcon className="mr-3" icon={button?.icon} fw={true}>
        {#if button.i18n}
            {$t(button.i18n)}
        {/if}
    </FaIcon>
</A>