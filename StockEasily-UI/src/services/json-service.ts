export class JsonService<T> {

    public static deserialize<T>(input: string): T | undefined {
        try {
            return JSON.parse(input) as T;
        } catch (e) {
            console.error('Could not parse string to object. Error:', e);
        }
        return undefined;
    }

    public static serialize<T>(input: T): string | undefined {
        try {
            return JSON.stringify(input);
        } catch (e) {
            console.error('Could not stringify object. Error:', e);
        }
        return undefined;
    }

}