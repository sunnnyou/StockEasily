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


<!--    <li class="mr-6 my-2 md:my-0">-->
<!--        <a href="#"-->
<!--           class="block py-1 md:py-3 pl-1 align-middle text-pink-600 no-underline hover:text-gray-900 border-b-2 border-orange-600 hover:border-orange-600">-->
<!--            <i class="fas fa-home fa-fw mr-3 text-pink-600"></i><span-->
<!--                class="pb-1 md:pb-0 text-sm">Home</span>-->
<!--        </a>-->
<!--    </li>-->
<!--    <li class="mr-6 my-2 md:my-0">-->
<!--        <a href="#"-->
<!--           class="block py-1 md:py-3 pl-1 align-middle text-gray-500 no-underline hover:text-gray-900 border-b-2 border-white hover:border-pink-500">-->
<!--            <i class="fas fa-tasks fa-fw mr-3"></i><span class="pb-1 md:pb-0 text-sm">Tasks</span>-->
<!--        </a>-->
<!--    </li>-->
<!--    <li class="mr-6 my-2 md:my-0">-->
<!--        <a href="#"-->
<!--           class="block py-1 md:py-3 pl-1 align-middle text-gray-500 no-underline hover:text-gray-900 border-b-2 border-white hover:border-purple-500">-->
<!--            <i class="fa fa-envelope fa-fw mr-3"></i><span class="pb-1 md:pb-0 text-sm">Messages</span>-->
<!--        </a>-->
<!--    </li>-->
<!--    <li class="mr-6 my-2 md:my-0">-->
<!--        <a href="#"-->
<!--           class="block py-1 md:py-3 pl-1 align-middle text-gray-500 no-underline hover:text-gray-900 border-b-2 border-white hover:border-green-500">-->
<!--            <i class="fas fa-chart-area fa-fw mr-3"></i><span class="pb-1 md:pb-0 text-sm">Analytics</span>-->
<!--        </a>-->
<!--    </li>-->
<!--    <li class="mr-6 my-2 md:my-0">-->
<!--        <a href="#"-->
<!--           class="block py-1 md:py-3 pl-1 align-middle text-gray-500 no-underline hover:text-gray-900 border-b-2 border-white hover:border-red-500">-->
<!--            <i class="fa fa-wallet fa-fw mr-3"></i><span class="pb-1 md:pb-0 text-sm">Payments</span>-->
<!--        </a>-->
<!--    </li>-->
</ul>