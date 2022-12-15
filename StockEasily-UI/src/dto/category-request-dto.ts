export class CategoryRequestDto {
    name: string = '';
}

export function validateCategoryRequest(category: CategoryRequestDto) {
    return category !== undefined && category.name !== undefined
        && category.name.length >= 1 && category.name.length <= 30;
}