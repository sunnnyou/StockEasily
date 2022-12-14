export class PropertyRequestDto {
    name: string | undefined;
    description: string | undefined;

}
export function validatePropertyRequest(property: PropertyRequestDto) {
    return property !== undefined
        && (property.name !== undefined && property.name.length >= 1 && property.name.length <= 30)
        && (!property.description || (property.description.length <= 50))
}