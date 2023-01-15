import type {ValidatableProperty} from '../dto/property-request-dto';
import type {Validatable} from './validatable';
import type {ValidatableImage} from './validatable-image';

export type ValidatableArticle = {
    category: Validatable<string> & {},
    image: ValidatableImage & {},
    name: Validatable<string> & {},
    properties: ValidatableProperty[],
    quantity: Validatable<number> & {},
}