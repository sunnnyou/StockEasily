import type {CategoryResponseDto} from './category-response-dto';
import type {PropertyResponseDto} from './property-response-dto';
import type {ValidatableArticle} from '../create-article-request-dto';

export class GetArticleResponseDto {
    id: number = 0;
    name: string = '';
    category: CategoryResponseDto = {id: 0, name: ''};
    image?: string;
    properties: PropertyResponseDto[] = [];
    quantity = -1;

    public constructor(response: GetArticleResponseDto) {
        this.id = response.id;
        this.name = response.name;
        this.category = response.category;
        this.image = response.image;
        this.properties = response.properties;
        this.quantity = response.quantity;
    }

    public toValidatable(): ValidatableArticle {
        return {
            image: {value: this.image},
            category: {value: this.name},
            name: {value: this.name},
            properties: this.properties.map((p: PropertyResponseDto) => {
                return {
                    error: '',
                    errors: {description: '', name: ''},
                    value: {id: p.id, description: p.description, name: p.name},
                };
            }),
            quantity: {value: this.quantity},
        };
    }
}