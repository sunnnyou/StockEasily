export type Validatable<T> = {
    value: T | undefined,
    error: string | undefined,
}