import {CategoryRequestDto} from './category-request-dto';
import type {PropertyRequestDto} from './property-request-dto';

export class CreateArticleRequestDto {
    name = '';
    quantity = 1;
    category = new CategoryRequestDto();
    properties: PropertyRequestDto[] = [];
}