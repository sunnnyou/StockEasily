<script lang="ts">
    import {faArrowRightFromBracket, faUser} from '@fortawesome/free-solid-svg-icons';
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
            className: 'border-t mx-2 border-gray-400',
        },
        {
            href: '/logout',
            icon: faArrowRightFromBracket,
            i18n: 'menu.user.logout',
        },
    ];

    let isMenuOpen = false;

    function toggleMenu() {
        isMenuOpen = !isMenuOpen;
    }

</script>

<div class="relative text-sm">
    <UserButton {isMenuOpen}
                on:click={toggleMenu}
    />

    <div id="userMenu"
         class="bg-white rounded shadow-md mt-2 absolute mt-12 top-0 right-0 min-w-full overflow-auto z-30{isMenuOpen ? '' : ' invisible'}"
    >
        <ul class="list-reset">
            {#each buttons as button}
                <li>
                    <UserMenuButton {button}/>
                </li>
            {/each}
        </ul>
    </div>
</div>