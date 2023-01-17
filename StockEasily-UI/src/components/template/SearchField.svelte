<script lang="ts">
    import {goto} from '$app/navigation';
    import {browser} from '$app/environment';
    import {faMagnifyingGlass} from '@fortawesome/free-solid-svg-icons';
    import {InputType} from '$components/html/input/input-type.ts';

    import FaIcon from '$components/common/FaIcon.svelte';
    import Input from '$components/html/input/Input.svelte';

    export let maxLength = 240;
    export let placeholder = '';
    export let title = '';
    let value = '';
</script>

<div class="relative pull-right md:pr-0 my-2 px-4"
     {title}
>
    <form on:submit|preventDefault={() => {
        value = encodeURIComponent(value.replace(/^\?/,''));
        if (browser){
            goto(`/articles/page/1?${encodeURIComponent(value)}`)}}
        }
    >
        <Input className="w-full bg-gray-100 text-sm text-gray-800 transition border focFus:outline-none focus:border-gray-700 rounded py-1 px-2 pl-10 appearance-none leading-normal"
               {maxLength}
               {placeholder}
               type={InputType.Search}
               on:change={event => value = event.target.value}
        />
        <div class="absolute search-icon left-7 top-3">
            <FaIcon icon={faMagnifyingGlass}/>
        </div>
    </form>
</div>
