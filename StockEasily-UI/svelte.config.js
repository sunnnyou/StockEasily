import adapter from '@sveltejs/adapter-auto';
import preprocess from 'svelte-preprocess';

/** @type {import('@sveltejs/kit').Config} */
const config = {
    env: {
        development: {
            dotenv: '.env',
        },
        staging: {
            dotenv: '.env',
        },
        production: {
            dotenv: '.env',
        },
    },

    // Consult https://github.com/sveltejs/svelte-preprocess
    // for more information about preprocessors
    preprocess: [
        preprocess({
            postcss: true,
        }),
    ],

    kit: {
        adapter: adapter(),
        alias: {
            $components: 'src/components',
            $common: 'src/common',
            $dto: 'src/dto',
            $i18n: 'src/i18n',
            $services: 'src/services',
            $validation: 'src/validation',
        },
    }
};

export default config;
