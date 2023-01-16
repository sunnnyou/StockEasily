import {derived, writable} from 'svelte/store';
import translations from './translations';

export const locale = writable("en");
export const locales = Object.keys(translations);

/**
 * Returns the translation found by current locale and key with parameters added
 * @param {string} locale the locale selected
 * @param {string} key the key to search for
 * @param {Object} vars the parameters to add
 * @returns {string} the final translation
 */
function translate(locale, key, vars) {
    // Let's throw some errors if we're trying to use keys/locales that don't exist.
    // We could improve this by using Typescript and/or fallback values.
    if (!key) throw new Error("no key provided to $t()");
    if (!locale) throw new Error(`no translation for key "${key}"`);

    // Grab the translation from the translations object.
    let text = translations[locale][key];

    if (!text) throw new Error(`no translation found for ${locale}.${key}`);

    // Replace any passed in variables in the translation string.
    Object.keys(vars).map((k) => {
        const regex = new RegExp(`{{${k}}}`, "g");
        text = text.replace(regex, vars[k]);
    });

    return text;
}

export const t = derived(locale,
    ($locale) =>
        /**
         * Returns the translation found by current locale and key with parameters added
         * @param {string} key the key to search for
         * @param {Object} vars the parameters to add
         * @returns {string} the final translation
         */
            (key, vars = {}) => translate($locale, key, vars)
);