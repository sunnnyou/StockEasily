export function formatBytesAsKilobytes(bytes: number): string {
    const kilobytes = (bytes / 1000).toFixed(2);
    return `${kilobytes} KB`;
}