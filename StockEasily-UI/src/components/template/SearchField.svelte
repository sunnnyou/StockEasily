<script lang="ts">
    import {faMagnifyingGlass} from '@fortawesome/free-solid-svg-icons';
    import {InputType} from '$components/html/input/input-type.ts';

    import FaIcon from '$components/common/FaIcon.svelte';
    import Input from '$components/html/input/Input.svelte';

    export let placeholder = '';
    export let title = '';
</script>

<div class="relative pull-right md:pr-0 my-2 px-4"
     {title}
>
    <Input {placeholder}
           type={InputType.Search}
           className="w-full bg-gray-100 text-sm text-gray-800 transition border focus:outline-none focus:border-gray-700 rounded py-1 px-2 pl-10 appearance-none leading-normal"
    />
    <div class="absolute search-icon left-7 top-3">
        <FaIcon icon={faMagnifyingGlass}/>
    </div>
</div>
