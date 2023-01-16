import type {PropertyRequestDto} from '$dto/property-request-dto';
import type {ValidatableArticle} from '$validation/validatable-article';
import type {ValidatableProperty} from '$validation/validatable-property';

function isEditingExistingProperty(article: ValidatableArticle, index: number): boolean {
    return index !== Number.NaN && article.properties.length > index;
}

function isDuplicateProperty(properties: ValidatableProperty[], property: PropertyRequestDto) {
    return properties.some(p => p.value.name === property.name && p.value.description === property.description);
}

export function onSaveProperty(article: ValidatableArticle, property: PropertyRequestDto, index: number = Number.NaN): ValidatableProperty[] {
    property.name = property.name?.trim() || '';
    property.description = property.description?.trim() || '';

    if (isEditingExistingProperty(article, index)) {
        article.properties[index].value = property;
        return article.properties;
    }

    if (isDuplicateProperty(article.properties, property)) {
        console.debug('Property already exists');
        return article.properties;
    }

    const VALIDATABLE: ValidatableProperty = {errors: {description: '', name: ''}, value: property};
    article.properties = [...article.properties, VALIDATABLE];

    return article.properties;
}