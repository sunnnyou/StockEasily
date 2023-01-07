import type {Validatable} from '../common/validatable';

export type ValidatableProperty = Validatable<PropertyRequestDto> & {
    errors: {
        name: string;
        description: string;
    };
};

export const PROPERTY_LIMITS = {
    MAX_LENGTH: {
        DESCRIPTION: 50,
        NAME: 30,
    },
    MIN_LENGTH: {
        NAME: 1,
    },
};

export class PropertyRequestDto {
    name: string | undefined;
    description: string | undefined;
}

export function isPropertyNameValid(validatable: ValidatableProperty): boolean {
    const VALUE = validatable?.value?.name;
    return VALUE !== undefined && VALUE.length >= PROPERTY_LIMITS.MIN_LENGTH.NAME && VALUE.length <= PROPERTY_LIMITS.MAX_LENGTH.NAME;
}

export function isPropertyDescriptionValid(validatable: ValidatableProperty): boolean {
    const VALUE = validatable?.value?.description;
    return VALUE === undefined || VALUE.length < PROPERTY_LIMITS.MAX_LENGTH.DESCRIPTION;
}

export function isPropertyRequestValid(request: PropertyRequestDto): boolean {
    const VALIDATABLE: ValidatableProperty =
        <Validatable<PropertyRequestDto> & { errors: { name: string; description: string } }>{
            errors: {
                description: '',
                name: '',
            }, value: request,
        };
    return isPropertyNameValid(VALIDATABLE) && isPropertyDescriptionValid(VALIDATABLE);
}