import { sveltekit } from '@sveltejs/kit/vite';
import type { UserConfig } from 'vite';
import precompileIntl from 'svelte-intl-precompile/sveltekit-plugin'

const config: UserConfig = {
	plugins: [
		sveltekit(),
		precompileIntl('static/locales')
	]
};

export default config;
