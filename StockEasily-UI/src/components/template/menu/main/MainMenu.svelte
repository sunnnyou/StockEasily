<script lang="ts">
    import {faChartArea, faHome, faList, faPlus, faQrcode, faWallet} from '@fortawesome/free-solid-svg-icons';
    import MainMenuButton from './MainMenuButton.svelte';

    export let buttons = [
        {
            href: '/',
            i18n: 'menu.home',
            icon: faHome,
        },
        {
            href: '/articles/page/1',
            i18n: 'menu.articles',
            icon: faList,
        },
        {
            href: '/articles/new',
            i18n: 'menu.addArticle',
            icon: faPlus,
        },
        {
            href: '/articles/qrcode',
            i18n: 'qrcode.title',
            icon: faQrcode,
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

<style>
    ul {
        position: relative;
        top: 5px;
    }
</style>