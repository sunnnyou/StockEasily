<script lang="ts">
    import PageContent from "$components/common/PageContent.svelte";
    import PageCard from "$components/common/PageCard.svelte";
    import {t} from "$i18n/i18n.js";
    import {goto} from "$app/navigation";
    import SearchField from "$components/template/SearchField.svelte";
    import {onMount} from "svelte";

    let articles = [];

    async function getArticles() {
        try{
            let response = await fetch('http://localhost:8080/api/v1/articles/');
            if(response.ok) {
                articles = await response.json();
            } else {
                console.log(response.status)
            }
        } catch (e) {
            console.log(e)
        }
    }
    onMount(() => {
        getArticles();
    })
</script>

<PageContent>
    <PageCard title={$t('menu.articles')}>

        {#if articles}
            <section class="text-gray-600 px-4">
                <div class="flex flex-col h-full">

                    <!-- Search Bar -->
                    <div class="w-full max-w-2xl mx-auto shadow rounded-md bg-white rounded-sm border border-gray-200">
                        <div class="pr-4">
                            <SearchField placeholder={$t('general.search')} title={$t('menu.search.tooltip')}></SearchField>
                        </div>
                        <!-- Table -->
                        <div class="p-3">
                            <div class="overflow-x-auto">
                                <table class="table-auto w-full">

                                    <thead class="text-xs font-semibold uppercase text-gray-400 bg-gray-50">
                                        <tr>
                                            <th class="p-2 whitespace-nowrap">
                                                <div class="font-semibold text-left">{$t('articles.name')}</div>
                                            </th>
                                            <th class="p-2 whitespace-nowrap">
                                                <div class="font-semibold text-left">{$t('category')}</div>
                                            </th>
                                            <th class="p-2 whitespace-nowrap">
                                                <div class="font-semibold text-left">{$t('articles.id')}</div>
                                            </th>
                                        </tr>
                                    </thead>
                                    <tbody class="text-sm divide-y divide-gray-100">
                                    {#each articles as article}
                                        <tr class="article" on:click={() => goto('/articles/'+article.id)}>
                                            <td class="p-2 whitespace-nowrap">
                                                <div class="flex items-center">
                                                    <div class="w-10 h-10 flex-shrink-0 mr-2 sm:mr-3"><img class="rounded-full" src="https://raw.githubusercontent.com/cruip/vuejs-admin-dashboard-template/main/src/images/user-36-05.jpg" width="40" height="40" alt="Alex Shatov"></div>
                                                    <div class="font-medium text-gray-800">{article.name}</div>
                                                </div>
                                            </td>
                                            <td class="p-2 whitespace-nowrap">
                                                <div class="text-left">{article.category.name}</div>
                                            </td>
                                            <td class="p-2 whitespace-nowrap">
                                                <div class="text-left font-medium text-green-500">{article.id}</div>
                                            </td>
                                        </tr>
                                    {:else}
                                        <p>loading...</p>
                                        {/each}
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
            </section>

        {:else}
            <div>{$t('articles.notFound')}</div>
        {/if}
    </PageCard>
</PageContent>
<style>
    .article:hover{
        cursor: pointer;
        background:#ccc;
        color:#fff;
    }
</style>
