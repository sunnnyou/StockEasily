import type {CategoryResponseDto} from './category-response-dto';
import type {PropertyResponseDto} from './property-response-dto';

export type GetArticleResponseDto = {
    id: number,
    name: string,
    category: CategoryResponseDto,
    image?: string,
    properties: PropertyResponseDto[];
    quantity: -1;
}