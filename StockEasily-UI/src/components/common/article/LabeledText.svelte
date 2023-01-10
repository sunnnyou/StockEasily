<script lang="ts">
    export let labelClass = '';
    export let labelText = '';
    export let textClass = '';
    export let text = '';
</script>


<div class="h-full">
    <h5 class="font-bold text-gray-600{labelClass?.length > 0 ? ` ${labelClass}` : ''}">
        {labelText}
    </h5>
    <p class="pb-2 my-4{textClass?.length > 0 ? ` ${textClass}`:''}">
        {text}
    </p>
</div>