import type {Validatable} from './validatable';

export type ValidatableImage = Validatable<string | undefined> & {
    errors: string[];
};