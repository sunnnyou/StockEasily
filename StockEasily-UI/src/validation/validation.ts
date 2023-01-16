import type {Validatable} from './validatable';
import type {ValidatableArticle} from './validatable-article';
import type {ValidatableProperty} from './validatable-property';

import {isPropertyDescriptionValid, isPropertyNameValid, PROPERTY_LIMITS} from '../dto/property-request-dto';

export function validateForm(validatableArticle: ValidatableArticle, t: any) {
    let isValid = true;

    if (!validateTextLengthBetween1And30(validatableArticle.name, 'Name', t)) {
        isValid = false;
    }
    if (!validateTextLengthBetween1And30(validatableArticle.category, 'Category', t)) {
        isValid = false;
    }
    if (!validateProperties(validatableArticle, t)) {
        isValid = false;
    }
    return isValid;
}

export function validatePropertyDescription(prop: ValidatableProperty, t: any): boolean {
    let isValid = true;
    if (!isPropertyDescriptionValid(prop)) {
        isValid = false;
        prop.errors.description = t('validation.max', {
            entity: 'Description',
            max: PROPERTY_LIMITS.MAX_LENGTH.DESCRIPTION,
        });
    } else {
        prop.errors.description = '';
    }
    return isValid;
}

export function validatePropertyName(prop: ValidatableProperty, t: any): boolean {
    let isValid = true;
    if (!isPropertyNameValid(prop)) {
        isValid = false;
        prop.errors.name = t('validation.between', {
            entity: 'Name',
            min: PROPERTY_LIMITS.MIN_LENGTH.NAME,
            max: PROPERTY_LIMITS.MAX_LENGTH.NAME,
        });
    } else {
        prop.errors.name = '';
    }
    return isValid;
}

export function validateProperty(property: ValidatableProperty, t: any): boolean {
    let isValid = true;

    if (!property.value) {
        isValid = false;
    } else {
        isValid = isValid ? validatePropertyName(property, t) : false;
        isValid = isValid ? validatePropertyDescription(property, t) : false;
    }

    return isValid;
}

export function validateProperties(validatableArticle: ValidatableArticle, t: any): boolean {
    let isValid = true;
    for (const PROPERTY of validatableArticle.properties) {
        isValid = isValid ? validateProperty(PROPERTY, t) : false;

    }
    return isValid;
}

export function validateTextLengthBetween(field: Validatable<string>, entity: string, min: number, max: number, t: any, nullable: boolean = false): boolean {
    const VALUE = field.value;
    const isValid = VALUE ? VALUE.length >= min || VALUE.length <= max : nullable;
    if (!isValid) {
        field.error = t('validation.between', {entity: entity, min: min, max: max});// `${entity} length must be between ${min} and ${max}`;
        console.warn(`Invalid input ${entity}:`, field.error);
    } else {
        field.error = '';
    }
    return isValid;
}

export function validateTextLengthBetween1And30(field: Validatable<string>, entity: string, t: any, nullable: boolean = false): boolean {
    return validateTextLengthBetween(field, entity, 1, 30, t, nullable);
}