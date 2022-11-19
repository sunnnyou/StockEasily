<script lang="ts">
    import {ATarget} from '$components/html/a/a-target';
    import {t} from '$i18n/i18n';

    import A from '$components/html/a/A.svelte';

    export let className = '';
    export let parentClassName = '';
    export let rows = [];

    function getClassName() {
        return className?.length > 0 ? '' + className : '';
    }
</script>

<div class="px-8 inline-block{parentClassName?.length > 0 ? ' ' + parentClassName : ''}">
    <ul class="list-reset items-center text-sm pt-3">
        {#each rows as row}
            <li>
                <A href={row.url}
                   className="inline-block text-gray-600 no-underline hover:text-gray-900 hover:underline py-1{getClassName()}"
                   target={ATarget.Blank}
                >
                    {$t(row.i18n)}
                </A>
            </li>
        {/each}
    </ul>
</div>