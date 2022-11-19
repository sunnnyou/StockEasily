<script lang="ts">
    import {faBell, faUser} from '@fortawesome/free-solid-svg-icons';
    import type {MenuButton} from '$common/menu-button';

    import UserButton from './UserButton.svelte';
    import UserMenuButton from './UserMenuButton.svelte';

    const buttons: MenuButton[] = [
        {
            href: '/account',
            icon: faUser,
            i18n: 'menu.user.account',
        },
        {
            href: '/notifications',
            icon: faBell,
            i18n: 'menu.user.notifications',
        },
        {
            className: 'border-t mx-2 border-gray-400',
        },
        {
            href: '/logout',
            icon: faBell,
            i18n: 'menu.user.notifications',
        },
    ];

    let isMenuOpen = false;

    function toggleMenu() {
        isMenuOpen = !isMenuOpen;
    }

</script>

<div class="relative text-sm">
    <UserButton {isMenuOpen} onClick={toggleMenu}/>

    <div id="userMenu"
         class="bg-white rounded shadow-md mt-2 absolute mt-12 top-0 right-0 min-w-full overflow-auto z-30{isMenuOpen ? '' : ' invisible'}"
    >
        <ul class="list-reset">
            {#each buttons as button}
                <li>
                    <UserMenuButton {button}/>
                </li>
            {/each}

            <!--<li><a href="#"-->
            <!--       class="px-4 py-2 block text-gray-900 hover:bg-gray-400 no-underline hover:no-underline">My-->
            <!--    account</a></li>-->
            <!--<li><a href="#"-->
            <!--       class="px-4 py-2 block text-gray-900 hover:bg-gray-400 no-underline hover:no-underline">Notifications</a>-->
            <!--</li>-->
            <!--<li>-->
            <!--    <hr class="border-t mx-2 border-gray-400">-->
            <!--</li>-->
            <!--<li><a href="#"-->
            <!--       class="px-4 py-2 block text-gray-900 hover:bg-gray-400 no-underline hover:no-underline">Logout</a>-->
            <!--</li>-->
        </ul>
    </div>
</div>