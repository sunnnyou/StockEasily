<script lang="ts">
    import type { ActionData} from './$types'
    import {applyAction, enhance} from "$app/forms";
    import {invalidateAll} from "$app/navigation";
    export let form: ActionData
</script>

<h1>Login</h1>

<form action="?/login" method="POST" use:enhance={() => {
    return async ({result}) => {
        invalidateAll()
        await applyAction(result)
    }
}}>
    <div>
        <label for="username">Username</label>
        <input type="text" name="username" id="username" required />
    </div>

    <div>
        <label for="password">Password</label>
        <input type="text" name="password" id="password" required />
    </div>

    {#if form?.invalid}
        <p class="error">Username or password required.</p>
    {/if}

    {#if form?.credentials}
        <p class="error">Wrong username or password credentials.</p>
    {/if}

    <button type="submit">Login</button>
</form>