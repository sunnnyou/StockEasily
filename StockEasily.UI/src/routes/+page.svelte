<script>
    import {t} from "svelte-intl-precompile";
</script>

<h1>{$t('home.heading')}</h1>
<p>{$t('home.caption', {values: { url: 'kit.svelte.dev'}})}</p>
