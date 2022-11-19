<script lang="ts">
    import {AType} from '$components/html/a/a-type';
    import {ATarget} from '$components/html/a/a-target';
    import type {TableColumn} from './table-column';
    import {t} from '$i18n/i18n';

    import A from '$components/html/a/A.svelte';
    import TemplateCard from './TemplateCard.svelte';

    export let parentClass = '';
    export let headings: string[] = [];
    export let rows: TableColumn[][] = [];
    export let title = 'Table';
    export let aType = AType.TextHtml;

    function getColumnText(column: TableColumn): string {
        return column.i18n ? $t(column.i18n) : (column?.text || '');
    }
</script>

<div class={parentClass}>
            <TemplateCard {title}>
            <table class="w-full p-5 text-gray-700">
                <thead>
                {#if headings}
                    <tr>
                        {#each headings as heading}
                            <th class="text-left text-blue-900">{heading}</th>
                        {/each}
                    </tr>
                {/if}
                </thead>
                <tbody>
                {#if rows}
                    {#each rows as row}
                        <tr>
                            {#each row as column}
                                <td class={column?.right ? 'text-right' : ''}>

                                    {#if column.i18n || column.text}
                                        {#if column.url && column.url.length > 0}
                                            <A href={column.url} target={ATarget.Blank} type={aType}>
                                                {getColumnText(column)}
                                            </A>
                                        {:else}
                                            {getColumnText(column)}
                                        {/if}
                                    {:else}
                                        {column}
                                    {/if}
                                </td>
                            {/each}
                        </tr>
                    {/each}
                {/if}
                </tbody>
            </table>

            <slot/>
    </TemplateCard>
</div>
