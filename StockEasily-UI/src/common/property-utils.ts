import type {PropertyRequestDto, ValidatableProperty} from '../dto/property-request-dto';
import type {ValidatableArticle} from '../dto/create-article-request-dto';

function isEditingExistingProperty(article: ValidatableArticle, index: number): boolean {
    return index !== Number.NaN && article.properties.length > index;
}

export function onSaveProperty(article: ValidatableArticle, property: PropertyRequestDto, index: number = Number.NaN): ValidatableProperty[] {
    property.name = property.name?.trim() || '';
    property.description = property.description?.trim() || '';

    if (isEditingExistingProperty(article, index)) {
        article.properties[index].value = property;
        return article.properties;
    }
    const VALIDATABLE: ValidatableProperty = {errors: {description: '', name: ''}, value: property};
    article.properties = [...article.properties, VALIDATABLE];

    return article.properties;
}