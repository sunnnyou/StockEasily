<script lang="ts">
    import {goto} from "$app/navigation";
    import {onMount} from "svelte";
    import {page} from "$app/stores";
    import {SESSION_INFO} from '../../../../common/session-util';
    import {t} from "$i18n/i18n.js";

    import PageCard from "$components/common/PageCard.svelte";
    import PageContent from "$components/common/PageContent.svelte";

    let articles = [];
    let size = 0;
    let defaultImage = "/9j/4AAQSkZJRgABAQAAAQABAAD/4QAiRXhpZgAATU0AKgAAAAgAAQESAAMAAAABAAEAAAAAAAD/2wBDAAsICAoIBwsKCQoNDAsNERwSEQ8PESIZGhQcKSQrKigkJyctMkA3LTA9MCcnOEw5PUNFSElIKzZPVU5GVEBHSEX/2wBDAQwNDREPESESEiFFLicuRUVFRUVFRUVFRUVFRUVFRUVFRUVFRUVFRUVFRUVFRUVFRUVFRUVFRUVFRUVFRUVFRUX/wAARCACKAJgDASIAAhEBAxEB/8QAGgABAQEBAQEBAAAAAAAAAAAAAAQDAgEFB//EACsQAQABAQQIBwEBAAAAAAAAAAABAgMEEZESExQhMVFScTIzQUJigaFyYf/EABQBAQAAAAAAAAAAAAAAAAAAAAD/xAAUEQEAAAAAAAAAAAAAAAAAAAAA/9oADAMBAAIRAxEAPwD9PAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAc6yjrpzZXqqYimmJwiccXFN10qYmasMYx4Ao1lHXTmayjrpzYbJ8/w2T5/gN9ZR105mso66c2GyfP8ADZPn+A31lHXTmayjrpzYbJ8/w2T5/gN9ZR105mso66c2GyfP8Nk+f4CiKoq8MxPZ6jmKrvaxv3cd3rCwAAAAAAAAE979n23o8FPaGF79n23o8FPaAegVTFNMzPCN4GMY4YxjPoIK65qrmrhKyytNZRj6+sA7AAABJevMj+VaS9eZH8qwAAAAAAAAT3v2fbejwU9oYXv2fbejwU9oB6mvNpjOhHpvlvaV6uiasoQTOM4zxkB3ZWmrrx9J4uAH0YnGMY3wyrvFNFejhjz/AMc6UWFjhFWMzw3pZnGcQfRjfGMcBPdrTGNCfTgoBJevMj+VaS9eZH8qwAAAAAAAAT3v2fbejwU9oYXv2fbejy6e0AkvFpp14RwjcyV7LRzqzg2WjnVnAJBXstHOrODZaOdWcAkFey0c6s4Nlo51ZwCWJmJiY4wus64tKIqhnstHOrOHdnZxZxOjjOPME968yP5VpL15sdlYAAAAAAAAM7ay1tMYTvjgxii8UxhGMR3hUAl0bxznODRvHOc4VAJdG8c5zg0bxznOFQCXRvHOc4NG8c5zhUAl0bxznODRvHOc4VAJrOwrm00rTdhOPdSAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAP//Z"
    let limit = 10;
    let maxPage;
    let beforePage;
    let afterPage;
    let searchQuery;
    let hrefEndPaginator = "";

    async function getArticles(pageQuery) {

        try {
            if (pageQuery) {
                console.log($page.params.page);
                let response = await fetch(SESSION_INFO.API_ENDPOINT + '/api/v1/articles/search/' + pageQuery + '/' + $page.params.page);
                if (response.ok) {
                    articles = await response.json();
                } else {
                    console.log(response.status)
                }
            } else {
                let response = await fetch(SESSION_INFO.API_ENDPOINT + '/api/v1/articles/page/' + $page.params.page);
                if (response.ok) {
                    articles = await response.json();
                } else {
                    console.log(response.status)
                }
            }
        } catch (e) {
            console.log(e)
        }

        console.log(articles.length);
    }

    async function search() {
        try {
            await goto('/articles/page/1?' + searchQuery);
            location.reload();
        } catch (e) {
            console.log(e)
        }
    }

    async function getSize(pageQuery) {
        try {
            let response
            if (pageQuery) {
                response = await fetch(SESSION_INFO.API_ENDPOINT + '/api/v1/articles/size/' + pageQuery);
                hrefEndPaginator = "?" + pageQuery;
                if (response.ok) {
                    size = await response.json();
                    maxPage = Math.ceil(size / limit);
                    beforePage = Number($page.params.page) - 1;
                    beforePage = '/articles/page/' + beforePage + '?' + pageQuery;
                    afterPage = Number($page.params.page) + 1;
                    afterPage = '/articles/page/' + afterPage + '?' + pageQuery;
                    console.log(maxPage);
                } else {
                    console.log(response.status)
                }
            } else {
                response = await fetch(SESSION_INFO.API_ENDPOINT + '/api/v1/articles/size/');
                if (response.ok) {
                    size = await response.json();
                    maxPage = Math.ceil(size / limit);
                    beforePage = Number($page.params.page) - 1;
                    beforePage = '/articles/page/' + beforePage;
                    afterPage = Number($page.params.page) + 1;
                    afterPage = '/articles/page/' + afterPage;
                    console.log(maxPage);
                } else {
                    console.log(response.status)
                }
            }

        } catch (e) {
            console.log(e)
        }
    }

    onMount(() => {
        const PAGE_QUERY = window.location.search;
        if (PAGE_QUERY === undefined) {
            console.error('Could not get page query')
            return;
        }
        const ENCODED_PAGE_QUERY: string = encodeURIComponent(PAGE_QUERY.replace(/^\?/,''));
        getArticles(ENCODED_PAGE_QUERY);
        getSize(ENCODED_PAGE_QUERY);
    })

</script>

<PageContent>
    <PageCard title={$t('menu.articles')}>

        <section class="text-gray-600 px-4">
            <div class="flex flex-col h-full">
                <div class="w-full max-w-2xl mx-auto shadow rounded-md bg-white rounded-sm border border-gray-200">

                    <!-- Search Bar -->
                    <div class="p-4">
                        <form on:submit|preventDefault={search}
                              class="flex items-center">
                            <label for="simple-search"
                                   class="sr-only">
                                Search
                            </label>
                            <div class="relative w-full">
                                <div class="absolute inset-y-0 left-0 flex items-center pl-3 pointer-events-none">
                                    <svg aria-hidden="true"
                                         class="w-5 h-5 text-gray-500 dark:text-gray-400"
                                         fill="currentColor"
                                         viewBox="0 0 20 20"
                                         xmlns="http://www.w3.org/2000/svg">
                                        <path fill-rule="evenodd"
                                              d="M8 4a4 4 0 100 8 4 4 0 000-8zM2 8a6 6 0 1110.89 3.476l4.817 4.817a1 1 0 01-1.414 1.414l-4.816-4.816A6 6 0 012 8z"
                                              clip-rule="evenodd">
                                        </path>
                                    </svg>
                                </div>
                                <input bind:value={searchQuery}
                                       type="text"
                                       id="simple-search"
                                       class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full pl-10 p-2.5  dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
                                       placeholder="Search"
                                       required>
                            </div>
                            <button type="submit"
                                    class="p-2.5 ml-2 text-sm font-medium text-white bg-gray-700 rounded-lg border border-gray-700 hover:bg-gray-800 focus:ring-4 focus:outline-none focus:ring-blue-300 dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800">
                                <svg class="w-5 h-5"
                                     fill="none" stroke="currentColor"
                                     viewBox="0 0 24 24"
                                     xmlns="http://www.w3.org/2000/svg">
                                    <path stroke-linecap="round"
                                          stroke-linejoin="round"
                                          stroke-width="2"
                                          d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z">
                                    </path>
                                </svg>
                                <span class="sr-only">Search</span>
                            </button>
                        </form>
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
                                                <div class="w-10 h-10 flex-shrink-0 mr-2 sm:mr-3">
                                                    {#if article.image}
                                                        <img class="rounded-full"
                                                             src={article.image}
                                                             width="40"
                                                             height="40"
                                                             alt="">
                                                    {:else}
                                                        <img class="rounded-full"
                                                             src="{`data:image/jpeg;base64,${defaultImage}`}"
                                                             width="40"
                                                             height="40"
                                                             alt="">
                                                    {/if}
                                                </div>
                                                <div class="font-medium text-gray-800">{article.name}</div>
                                            </div>
                                        </td>
                                        <td class="p-2 whitespace-nowrap">
                                            <div class="text-left">{article.category.name}</div>
                                        </td>
                                        <td class="p-2 whitespace-nowrap">
                                            <div class="text-left font-medium text-gray-500">{article.id}</div>
                                        </td>
                                    </tr>
                                {:else}
                                    <div class="text-gray-800 font-semibold py-4 px-2 flex m-auto">{$t('articles.notFound')}</div>
                                {/each}
                                </tbody>
                            </table>
                        </div>
                    </div>

                    <!-- Paginator -->
                    {#if size}
                        <div class="flex">
                            <div class="m-auto">
                                <div class="inline-block font-medium text-gray-800">
                                    {#if $page.params.page > 1}
                                        <a class="ui-button border-black float-left p-2 no-underline"
                                           rel="external"
                                           href={beforePage}>&laquo;
                                        </a>
                                    {:else}
                                        <div class="border-black float-left p-2 no-underline">&laquo;</div>
                                    {/if}

                                    {#each Array.from({length: maxPage}) as i, pageNumber}
                                        {#if Number($page.params.page) === Number(pageNumber + 1)}
                                            <div class="border-black float-left p-2 underline">{(pageNumber + 1)}</div>
                                        {:else}
                                            <a class="border-black float-left p-2 no-underline"
                                               rel="external"
                                               href={'/articles/page/' + (pageNumber + 1) + hrefEndPaginator}>{(pageNumber + 1)}
                                            </a>
                                        {/if}
                                    {/each}

                                    {#if $page.params.page < maxPage}
                                        <a class="ui-button border-black float-left p-2 no-underline"
                                           rel="external"
                                           href={afterPage}>&raquo;
                                        </a>
                                    {:else}
                                        <div class="border-black float-left p-2 no-underline">&raquo;</div>
                                    {/if}
                                </div>
                            </div>
                        </div>
                    {/if}
                </div>
            </div>
        </section>

    </PageCard>
</PageContent>
<style>
    .article:hover {
        cursor: pointer;
        background: #ccc;
        color: #fff;
    }
</style>
