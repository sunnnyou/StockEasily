<script lang="ts">
    import {faInbox, faServer, faTasks, faUserPlus, faUsers, faWallet} from '@fortawesome/free-solid-svg-icons';

    import MetricCardBoard from '$components/template/card/MetricCardBoard.svelte';
    import TableCard from '$components/template/card/TableCard.svelte';
    import TemplateCard from '$components/template/card/TemplateCard.svelte';
    import PageContent from '$components/common/PageContent.svelte';
    import A from '$components/html/a/A.svelte';
    import HorizontalRuler from '$components/html/HorizontalRuler.svelte';

    const metricCards: MetricCardBoard[] = [
        {color: 'green', icon: faWallet, title: 'Total Revenue', value: '$3249'},
        {color: 'pink', icon: faUsers, title: 'Total Users', value: '249'},
        {color: 'yellow', icon: faUserPlus, title: 'New Users', value: '2'},
        {color: 'blue', icon: faServer, title: 'Server Uptime', value: '162 days'},
        {color: 'indigo', icon: faTasks, title: 'To Do List', value: '7 tasks'},
        {color: 'red', icon: faInbox, title: 'Issues', value: '3'},
    ];

    const table: { headings: string[], rows: string[][] } = {
        headings: ['Name', 'Side', 'Role'],
        rows: [
            ['Obi Wan Kenobi', 'Light', 'Jedi'],
            ['Greedo', 'South', 'Scumbag'],
            ['Darth Vader', 'Dark', 'Sith'],
        ],
    };
</script>

<PageContent>
    <MetricCardBoard items={metricCards}></MetricCardBoard>

    <!--Divider-->
    <HorizontalRuler className="border-b-2 border-gray-400 my-8 mx-4"></HorizontalRuler>

    <div class="flex flex-row flex-wrap flex-grow mt-2 mx-auto">
        <div class="w-full md:w-1/2 p-3">
            <TemplateCard title="Template"/>
        </div>

        <div class="w-full p-3">
            <TableCard headings={table.headings}
                       rows={table.rows}
                       parentClass="bg-white border rounded shadow"
            >
                <p class="py-2">
                    <A href="/tacker/static">See More issues...</A>
                </p>
            </TableCard>
        </div>
    </div>
</PageContent>