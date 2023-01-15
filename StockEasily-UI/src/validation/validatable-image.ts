import type {Validatable} from '../common/validatable';

export type ValidatableImage = Validatable<string | undefined> & {
    errors: string[];
};