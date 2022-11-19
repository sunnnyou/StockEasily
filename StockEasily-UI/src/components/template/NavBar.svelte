<script lang="ts">
    import {t} from '$i18n/i18n';
    import {faBars, faTableColumns} from '@fortawesome/free-solid-svg-icons';

    import SearchField from './SearchField.svelte';
    import FaIcon from '$common/FaIcon.svelte';
    import Button from '../html/button/Button.svelte';
    import Nav from '../html/Nav.svelte';
    import UserMenu from './menu/user/UserMenu.svelte';
    import MainMenu from './menu/main/MainMenu.svelte';

    export let title = 'App Name';

    let isMenuOpen = false;

    function toggleMenu() {
        isMenuOpen = !isMenuOpen;
    }
</script>

<Nav id="header">

    <div class="w-full container mx-auto flex flex-wrap items-center mt-0 py-3 md:pb-0">
        <!--<Logo> Name-->
        <div class="w-1/2 pl-2 md:pl-0">
            <a class="text-gray-900 text-base xl:text-xl no-underline hover:no-underline font-bold" href="/">
                <FaIcon icon={faTableColumns} className="text-blue-500 pr-3 py-1" slotClass="text-lg inline">{title}</FaIcon>
            </a>
        </div>
        <div class="w-1/2 pr-0">
            <div class="flex relative inline-block float-right">
                <UserMenu/>

                <div class="block lg:hidden pr-4 pl-4">
                    <Button id="nav-toggle"
                            className="flex items-center px-3 py-2 border rounded text-gray-500 border-gray-600 hover:text-gray-900 hover:border-gray-900 appearance-none focus:outline-none"
                            onClick={toggleMenu}
                    >
                        <FaIcon icon={faBars}/>
                    </Button>
                </div>
            </div>

        </div>

        <div class="w-full flex-grow lg:flex lg:items-center lg:w-auto lg:block mt-2 lg:mt-0 bg-white z-20{isMenuOpen ? '' : ' hidden'}"
             id="nav-content"
        >
            <MainMenu/>

            <SearchField placeholder={$t('general.search')} title={$t('menu.search.tooltip')}></SearchField>
        </div>

    </div>
</Nav>