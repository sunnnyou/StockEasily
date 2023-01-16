import type {Validatable} from './validatable';
import type {ValidatableImage} from './validatable-image';
import type {ValidatableProperty} from './validatable-property';

export type ValidatableArticle = {
    category: Validatable<string> & {},
    image: ValidatableImage & {},
    name: Validatable<string> & {},
    properties: ValidatableProperty[],
    quantity: Validatable<number> & {},
}