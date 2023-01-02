import {expect, test} from '@playwright/test';

test('Displays page /articles/new', async ({page}) => {
    await page.goto('/articles/new');
    console.warn('content: ', page.textContent('h5'))
    expect(await page.textContent('h5')).toBe('Add Article');
});

// Test example
// test('index page has expected h1', async ({ page }) => {
// 	await page.goto('/');
// 	expect(await page.textContent('h1')).toBe('Welcome to SvelteKit');
// });

