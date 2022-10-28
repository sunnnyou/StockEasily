import {getLocaleFromNavigator, init, waitLocale} from 'svelte-intl-precompile';
import {registerAll} from '$locales';

const defaultLocale = 'de-DE';

registerAll();

// noinspection JSUnusedGlobalSymbols
export async function load() {
    init({
        fallbackLocale: defaultLocale,
        initialLocale: getLocaleFromNavigator() || defaultLocale,
    });
    await waitLocale(); // awaits for initialLocale language pack to finish loading;
    return {};
}