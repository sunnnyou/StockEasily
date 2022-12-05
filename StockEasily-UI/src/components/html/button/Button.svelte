<script lang="ts">
    import {ButtonPriority} from './button-priority';
    import {ButtonType} from './button-type';

    export let className = '';
    export let id = '';
    export let name = '';
    export let priority: ButtonPriority = ButtonPriority.Secondary;
    export let type = ButtonType.Button;
    export let value = '';

    function getButtonClass() {
        return priority === ButtonPriority.Transparent ? '' : 'btn';
    }

    function getPriorityClass() {
        switch (priority) {
            case ButtonPriority.Primary:
                return 'btn-blue';
            case ButtonPriority.Secondary:
                return 'btn-light-gray';
            case ButtonPriority.Transparent:
                return '';
        }
    }
</script>

<button class="{getButtonClass()} {getPriorityClass() + (className?.length > 0 ? ' ' + className : '')}"
        {id}
        {name}
        {type}
        on:click
>
    <slot>
        {value}
    </slot>
</button>
