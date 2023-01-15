import type {ValidatableArticle} from '$validation/validatable-article';
import type {ValidatableImage} from '../validation/validatable-image';

import {formatBytesAsKilobytes} from './number-util';
import {SESSION_INFO} from './session-util';
import {writable} from 'svelte/store';

export const selectedFiles = writable<File[]>([]);
export const selectedFileName = writable<string | undefined>();
export let imageSelected: File | undefined = undefined;

export function getImageResponseMessage(article: ValidatableArticle, t: any): string | undefined {
    if (imageSelected === undefined) {
        console.debug('No image was selected, returning empty response message');
        return t('articles.image.deleted');
    }

    const IMAGE_ERROR = article.image.errors[0];
    console.debug('Error:', IMAGE_ERROR);
    return `${t('general.error')}: ${IMAGE_ERROR}`;
}

export function onImageSelected(files: File[], article: ValidatableArticle, t: any): ValidatableImage | undefined {
    if (files === undefined) {
        console.error('Could not get selected image, files is undefined');
        selectedFiles.set([]);
        return {
            value: undefined,
            errors: [t('validation.unkown')],
        };
    }
    if (files.length === 0) {
        console.warn('No file selected');
        selectedFiles.set([]);
        imageSelected = undefined;
        selectedFileName.set(undefined);
        article.image.value = undefined;
        return {
            value: undefined,
            errors: [t('articles.image.deleted')],
        };
    }
    imageSelected = files[0];
    console.log('Selected image size:', imageSelected.size);
    if (imageSelected.size > SESSION_INFO.IMAGE_MAX_SIZE) {
        const EXPECTED = formatBytesAsKilobytes(SESSION_INFO.IMAGE_MAX_SIZE);
        console.warn(`Image selected is too big ( ${formatBytesAsKilobytes(imageSelected.size)} ). Maximum size allowed: ${EXPECTED}`);

        const INSERT_VALUE: File[] = imageSelected === undefined ? [] : [imageSelected];
        selectedFiles.set(INSERT_VALUE);
        return {
            value: undefined,
            errors: [t('validation.image', {expected: EXPECTED})],
        };
    }

    let reader = new FileReader();
    reader.readAsDataURL(imageSelected);
    reader.onload = e => {
        if (e.target?.result === undefined) {
            console.error('Could not get base64 encoded image, result is undefined');
            selectedFiles.set([]);
            return;
        }
        article.image.value = e.target.result as string;
        selectedFileName.set(imageSelected!.name);
        console.log('Selected file:', imageSelected!.name);
    };
    return article.image;
}