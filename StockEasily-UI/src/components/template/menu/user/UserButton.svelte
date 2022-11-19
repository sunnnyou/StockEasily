<script lang="ts">
    import {faAngleDown, faAngleUp} from '@fortawesome/free-solid-svg-icons';
    import {t} from '$i18n/i18n';

    import FaIcon from '$components/common/FaIcon.svelte';
    import Button from '$components/html/button/Button.svelte';

    export let isMenuOpen = false;
    export let onClick: Function | undefined;

    function onClickCallback() {
        if (onClick) {
            onClick();
        }
    }
</script>

<Button id="userButton" className="flex items-center focus:outline-none mr-3" onClick={onClickCallback}>
    <img class="w-8 h-8 rounded-full mr-4" src="https://i.pravatar.cc/300" alt="Avatar of User">
    <span class="hidden md:inline-block">
        {$t('menu.user', {username: $t('general.guest')})}
    </span>
    <FaIcon className="pl-2" icon={isMenuOpen ? faAngleUp : faAngleDown}></FaIcon>
</Button>