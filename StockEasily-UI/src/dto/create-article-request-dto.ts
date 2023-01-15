import type {CategoryRequestDto} from './category-request-dto';
import type {PropertyRequestDto} from './property-request-dto';
import type {Validatable} from '../common/validatable';
import type {ValidatableImage} from '../validation/validatable-image';
import type {ValidatableProperty} from './property-request-dto';

export class ValidatableArticle {
    category: Validatable<string> & {} = {error: '', value: ''};
    image: ValidatableImage & {} = {errors: [], value: ''};
    name: Validatable<string> & {} = {error: '', value: ''};
    properties: ValidatableProperty[] = [];
    quantity: Validatable<number> & {} = {error: '', value: 0};
}

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