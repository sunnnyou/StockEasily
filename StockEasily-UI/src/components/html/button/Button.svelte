<script lang="ts">
    import {ButtonPriority} from './button-priority';
    import {ButtonType} from './button-type';

    export let className = '';
    export let id = '';
    export let name = '';
    export let priority: ButtonPriority = ButtonPriority.Secondary;
    export let title = '';
    export let type = ButtonType.Button;
    export let value = '';

    function getButtonClass() {
        return priority === ButtonPriority.Transparent ? '' : 'btn h-10 px-5 m-2 ';
    }

    function getPriorityClass() {
        switch (priority) {
            case ButtonPriority.Primary:
                return 'btn-blue';
            case ButtonPriority.Secondary:
                return 'btn-light-gray';
            case ButtonPriority.Dangerous:
                return 'text-red-100 transition-colors duration-150 bg-red-700 rounded-lg focus:shadow-outline hover:bg-red-800';
            case ButtonPriority.TransparentHover:
                return 'btn-hover';
            case ButtonPriority.Transparent:
            default:
                return '';
        }
    }
</script>

<button class="{getButtonClass()} {getPriorityClass() + (className?.length > 0 ? ' ' + className : '')}"
        {id}
        {name}
        {title}
        {type}
        on:click
>
    <slot>
        {value}
    </slot>
</button>
