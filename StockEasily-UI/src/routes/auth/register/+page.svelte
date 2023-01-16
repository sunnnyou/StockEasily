<script lang="ts">
    import type { ActionData} from '../../../../.svelte-kit/types/src/routes';

    import {enhance} from '$app/forms'
    export let form: ActionData
</script>

<h1>Register</h1>

<form action="?/register" method="POST" use:enhance>
    <div>
        <label for="username">Username</label>
        <input name="username" id="username" type="text" required>
    </div>

    <div>
        <label for="password">Password</label>
        <input name="username" id="password" type="text" required>
    </div>


    {#if form?.user}
        <p class="error">Username is taken.</p>
    {/if}
    <button type="submit">Register</button>
</form>