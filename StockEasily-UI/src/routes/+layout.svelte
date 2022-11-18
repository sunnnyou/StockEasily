<script>
    import '../app.css';

    import {t} from "../i18n/i18n.js";

    const title = 'StockEasily';
</script>

<svelte:head>
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>{title}</title>
    <meta name="description" content={$t('app.description')}>
    <meta name="keywords" content={$t('app.keywords')}>
</svelte:head>

<slot/>

