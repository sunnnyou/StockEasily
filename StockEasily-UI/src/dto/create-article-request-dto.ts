import type {CategoryRequestDto} from './category-request-dto';
import type {PropertyRequestDto} from './property-request-dto';
import type {Validatable} from '../common/validatable';
import type {ValidatableProperty} from './property-request-dto';

export type ValidatableArticle = {
    category: Validatable<string> & {},
    image: Validatable<string> & {},
    name: Validatable<string> & {},
    properties: ValidatableProperty[],
    quantity: Validatable<number> & {},
};

export class CreateArticleRequestDto {
    name = '';
    quantity = 1;
    category: CategoryRequestDto = {name: ''};
    image: string | undefined;
    properties: PropertyRequestDto[] = [];

    constructor(validatable: ValidatableArticle) {
        this.name = validatable.name.value;
        this.image = validatable.image.value;
        this.category = {name: validatable.category.value};
        this.properties = validatable.properties.map(p => p.value);
        this.quantity = validatable.quantity.value;
    }
}