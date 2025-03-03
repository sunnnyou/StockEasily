import type {CategoryRequestDto} from './category-request-dto';
import type {PropertyRequestDto} from './property-request-dto';
import type {ValidatableArticle} from '$validation/validatable-article';

export class CreateArticleRequestDto {
    name = '';
    category: CategoryRequestDto = {name: ''};
    properties: PropertyRequestDto[] = [];
    quantity = 1;
    image?: string | undefined;

    constructor(validatable: ValidatableArticle) {
        this.name = validatable.name.value;
        this.image = validatable.image.value;
        this.category = {name: validatable.category.value!};
        this.properties = validatable.properties.map(p => p.value!);
        this.quantity = validatable.quantity.value;
    }
}