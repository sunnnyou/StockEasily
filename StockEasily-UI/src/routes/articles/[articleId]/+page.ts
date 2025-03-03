import type {GetArticleResponseDto} from '$dto/response/get-article-response-dto';

import {SESSION_INFO} from '$common/session-util';
import {isNumeric} from '$common/string-util';
import {JsonService} from '../../../services/json-service';

// noinspection JSUnusedGlobalSymbols
export async function load({fetch, params}: { fetch: any, params: any }) {
    let {articleId} = params;
    if (articleId === undefined || !isNumeric(articleId)) {
        return {
            result: undefined,
        };
    }

    try {
        let response = await fetch(SESSION_INFO.API_ENDPOINT + '/api/v1/articles/' + articleId);
        return {
            result: JsonService.deserialize<GetArticleResponseDto>(await response.text()),
        };
    } catch (e) {
        return {
            result: undefined,
        };
    }
}
