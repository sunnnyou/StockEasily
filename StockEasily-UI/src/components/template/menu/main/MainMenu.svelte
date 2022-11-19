<script lang="ts">
    import {faChartArea, faEnvelope, faHome, faTasks, faWallet} from '@fortawesome/free-solid-svg-icons';
    import MainMenuButton from './MainMenuButton.svelte';

    export let buttons = [
        {
            href: '/',
            i18n: 'menu.home',
            icon: faHome,
        },
        {
            href: '/tasks',
            i18n: 'menu.tasks',
            icon: faTasks,
        },
        {
            href: '/messages',
            i18n: 'menu.messages',
            icon: faEnvelope,
        },
        {
            href: '/analytics',
            i18n: 'menu.analytics',
            icon: faChartArea,
        },
        {
            href: '/payments',
            i18n: 'menu.payments',
            icon: faWallet,
        },
    ];
</script>

<ul class="list-reset lg:flex flex-1 items-center px-4 md:px-0">
    {#each buttons as button}
        <li class="mr-6 my-2 md:my-0">
            <MainMenuButton {button}/>
        </li>
    {/each}
</ul>