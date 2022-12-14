import {CategoryRequestDto} from './category-request-dto';
import type {PropertyRequestDto} from './property-request-dto';

export class CreateArticleRequestDto {
    name = '';
    quantity = 1;
    category: CategoryRequestDto = { name: ''};
    image: string | ArrayBuffer | undefined;
    properties: PropertyRequestDto[] = [];
}