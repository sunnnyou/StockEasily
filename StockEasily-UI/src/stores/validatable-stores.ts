import type {ValidatableArticle} from '$validation/validatable-article';
import type {Writable} from 'svelte/store';

import {writable} from 'svelte/store';

export const validatableArticleStore: Writable<ValidatableArticle> = writable<ValidatableArticle>({
    category: {value: '', error: ''},
    image: {value: '', errors: []},
    name: {value: '', error: ''},
    properties: [],
    quantity: {value: 1, error: ''},
});
