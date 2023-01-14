import type {ValidatableArticle} from '$dto/create-article-request-dto';
import type {Writable} from 'svelte/store';

import {writable} from 'svelte/store';

export const validatableArticleStore: Writable<ValidatableArticle> = writable<ValidatableArticle>({
    category: {value: '', error: ''},
    image: {value: '', error: ''},
    name: {value: '', error: ''},
    properties: [],
    quantity: {value: 1, error: ''},
});
