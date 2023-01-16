import type {PropertyRequestDto} from '$dto/property-request-dto';
import type {Validatable} from './validatable';

export type ValidatableProperty = Validatable<PropertyRequestDto> & {
    errors: {
        name: string;
        description: string;
    };
};
