<script lang="ts">
    import {t} from '$i18n/i18n';

    import FooterColumn from './FooterColumn.svelte';

    const leftRows = [
        {
            i18n: 'about.reportIssue',
            url: 'https://inf-git.fh-rosenheim.de/sINFdahent/stockeasily/-/issues/new',
        },
        {
            i18n: 'about.contribute',
            url: 'https://inf-git.fh-rosenheim.de/sINFdahent/stockeasily',
        },
        {
            i18n: 'about.sourceCode',
            url: 'https://inf-git.fh-rosenheim.de/sINFdahent/stockeasily/-/tree/develop',
        },
    ];

    const rightRows = [
        {
            i18n: 'about.contact',
            url: 'https://inf-git.fh-rosenheim.de/sINFdahent/stockeasily/-/wikis/Team',
        },
        {
            i18n: 'about.donate',
            url: '#1',
        },
    ];
</script>

<footer class="bg-white border-t border-gray-400 shadow">
    <div class="container max-w-md mx-auto flex py-8 h-52">
        <div class="w-full mx-auto flex flex-wrap justify-between">
            <h5 class="font-bold text-gray-900 mx-auto">
                {$t('about')}
            </h5>

            <div class="flex w-full justify-between">
                <FooterColumn rows={leftRows} parentClassName="justify-between"/>
                <FooterColumn rows={rightRows}/>
            </div>
        </div>
    </div>
</footer>