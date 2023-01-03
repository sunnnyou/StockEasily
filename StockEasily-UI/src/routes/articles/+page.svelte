<script lang="ts">
    import PageContent from "$components/common/PageContent.svelte";
    import PageCard from "$components/common/PageCard.svelte";
    import {t} from "$i18n/i18n.js";
    import {goto} from "$app/navigation";
    import SearchField from "$components/template/SearchField.svelte";
    import {onMount} from "svelte";

    let articles = [];
    let defaultImage = "/9j/4AAQSkZJRgABAQAAAQABAAD/4QAiRXhpZgAATU0AKgAAAAgAAQESAAMAAAABAAEAAAAAAAD/2wBDAAsICAoIBwsKCQoNDAsNERwSEQ8PESIZGhQcKSQrKigkJyctMkA3LTA9MCcnOEw5PUNFSElIKzZPVU5GVEBHSEX/2wBDAQwNDREPESESEiFFLicuRUVFRUVFRUVFRUVFRUVFRUVFRUVFRUVFRUVFRUVFRUVFRUVFRUVFRUVFRUVFRUVFRUX/wAARCACKAJgDASIAAhEBAxEB/8QAGgABAQEBAQEBAAAAAAAAAAAAAAQDAgEFB//EACsQAQABAQQIBwEBAAAAAAAAAAABAgMEEZESExQhMVFScTIzQUJigaFyYf/EABQBAQAAAAAAAAAAAAAAAAAAAAD/xAAUEQEAAAAAAAAAAAAAAAAAAAAA/9oADAMBAAIRAxEAPwD9PAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAc6yjrpzZXqqYimmJwiccXFN10qYmasMYx4Ao1lHXTmayjrpzYbJ8/w2T5/gN9ZR105mso66c2GyfP8ADZPn+A31lHXTmayjrpzYbJ8/w2T5/gN9ZR105mso66c2GyfP8Nk+f4CiKoq8MxPZ6jmKrvaxv3cd3rCwAAAAAAAAE979n23o8FPaGF79n23o8FPaAegVTFNMzPCN4GMY4YxjPoIK65qrmrhKyytNZRj6+sA7AAABJevMj+VaS9eZH8qwAAAAAAAAT3v2fbejwU9oYXv2fbejwU9oB6mvNpjOhHpvlvaV6uiasoQTOM4zxkB3ZWmrrx9J4uAH0YnGMY3wyrvFNFejhjz/AMc6UWFjhFWMzw3pZnGcQfRjfGMcBPdrTGNCfTgoBJevMj+VaS9eZH8qwAAAAAAAAT3v2fbejwU9oYXv2fbejy6e0AkvFpp14RwjcyV7LRzqzg2WjnVnAJBXstHOrODZaOdWcAkFey0c6s4Nlo51ZwCWJmJiY4wus64tKIqhnstHOrOHdnZxZxOjjOPME968yP5VpL15sdlYAAAAAAAAM7ay1tMYTvjgxii8UxhGMR3hUAl0bxznODRvHOc4VAJdG8c5zg0bxznOFQCXRvHOc4NG8c5zhUAl0bxznODRvHOc4VAJrOwrm00rTdhOPdSAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAP//Z"

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
                                                    <div class="w-10 h-10 flex-shrink-0 mr-2 sm:mr-3">
                                                        {#if article.image}
                                                            <img class="rounded-full" src="{`data:image/png;base64,${article.image}`}" width="40" height="40" alt="">
                                                        {:else}
                                                            <img class="rounded-full" src="{`data:image/jpeg;base64,${defaultImage}`}" width="40" height="40" alt="">
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
                                        <p>loading...</p>
                                        {/each}
                                    </tbody>
                                </table>
                            </div>
                        </div>

                        <div class="flex">
                            <div class="m-auto">
                                <div class="inline-block font-medium text-gray-800">
                                    <a class="border-black float-left p-2 no-underline" href="#">&laquo;</a>

                                    <a class="border-black float-left p-2 no-underline" href="#">1</a>
                                    <a class="border-black float-left p-2 no-underline" href="#">2</a>
                                    <a class="border-black float-left p-2 no-underline" href="#">3</a>
                                    <a class="border-black float-left p-2 no-underline" href="#">4</a>
                                    <a class="border-black float-left p-2 no-underline" href="#">5</a>
                                    <a class="border-black float-left p-2 no-underline" href="#">6</a>

                                    <a class="border-black float-left p-2 no-underline" href="#">&raquo;</a>
                                </div>
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
