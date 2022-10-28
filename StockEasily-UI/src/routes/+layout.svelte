<script>
    import {t} from "svelte-intl-precompile";

    const title = 'StockEasily';
</script>

<svelte:head>
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>{title}</title>
    <meta name="description" content={$t('app.description')}>
    <meta name="keywords" content={$t('app.keywords')}>
</svelte:head>

<slot/>

