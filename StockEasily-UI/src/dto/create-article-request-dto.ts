import {CategoryRequestDto, validateCategoryRequest} from './category-request-dto';
import {validatePropertyRequest} from './property-request-dto';
import type {PropertyRequestDto} from './property-request-dto';

export class CreateArticleRequestDto {
    name = '';
    quantity = 1;
    category: CategoryRequestDto = { name: ''};
    image: string | ArrayBuffer | undefined;
    properties: PropertyRequestDto[] = [];

}

export function validateCreateArticleRequest(article: CreateArticleRequestDto) {
    return article !== undefined
        && article.name !== undefined && article.name.length >= 1 && article.name.length <= 30
        // && article.image === undefined
        && article.quantity >= 0
        && validateCategoryRequest(article.category)
        && article.properties.every(property => validatePropertyRequest(property))
}