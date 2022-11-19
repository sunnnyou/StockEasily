<script lang="ts">
    import '../app.css';

    import {t} from '$i18n/i18n';
    import Body from '$components/adminlte/Body.svelte';
    import NavBar from '$components/adminlte/NavBar.svelte';
    import Footer from '$components/adminlte/menu/footer/Footer.svelte';
    import HtmlHead from '$components/html/HtmlHead.svelte';

    const title = 'StockEasily';
</script>

<HtmlHead description={$t('app.description')}
          keywords={$t('app.keywords')}
          {title}
/>



<slot/>

