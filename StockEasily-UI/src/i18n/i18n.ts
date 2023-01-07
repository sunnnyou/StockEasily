import {writable} from 'svelte/store';
import {Locale, translations} from './translations';

export const INITIAL_LOCALE = Locale.en;

export const locale = writable(INITIAL_LOCALE);
// export const locales = Object.keys(translations);

const throwIfError = true;

function eventuallyThrowError(message: string) {
    if (throwIfError) {
        throw new Error(message);
    }
}

/**
 * Returns the translation for the desired i18n key
 * @param locale the locale to get the translation from
 * @param key the i18n key to find its value
 * @param vars optional parameters will be inserted into the translation
 */
// eslint-disable-next-line @typescript-eslint/no-explicit-any
function translate(key: string, vars: {[key: string]: string} = {}): string {
    if (!key) {
        eventuallyThrowError('No i18n key provided to $t()');
        return '';
    }
    if (!locale) {
        eventuallyThrowError(`No i18n locale provided for key "${key}"`);
        return key;
    }

    // Grab the translation from the translations object.
    const TRANSLATION_SHEET = translations[INITIAL_LOCALE];
    if (!TRANSLATION_SHEET) {
        eventuallyThrowError('Could not get TRANSLATION_SHEET from translations[' + locale + ']');
        return key;
    }

    let text: string = TRANSLATION_SHEET[key];
    if (!text) {
        eventuallyThrowError(`No translation found for${locale}.${key}`);
        return key;
    }

    // Replace any passed in variables in the translation string.
    Object.keys(vars).map((k) => {
        const regex = new RegExp(`{{${k}}}`, 'g');
        text = text.replace(regex, vars[k]);
    });

    return text;
}

/**
 * Returns the translation for the desired i18n key
 * @param key the i18n key to find its value
 * @param vars optional parameters will be inserted into the translation
 */
export function t(key: string, vars = {}): string {
    return translate(key, vars);
}
