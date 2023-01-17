<script>
    import { onMount } from 'svelte';
    let email = "";
    let password = "";
    let error = "";

    async function checkLogin() {
        const response = await fetch('/api/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                email,
                password
            })
        });

        if (response.ok) {
            const { token } = await response.json();
            localStorage.setItem('token', token);
            location.href = '/';
        } else {
            error = await response.text();
        }
    }

    onMount(async () => {
        // Check if the user is already logged in
        const isLoggedIn = await checkLogin();
        if (isLoggedIn) {
            // Redirect the user to the home page
            window.location.replace('/');
        }
    });

    async function handleSubmit(event) {
        event.preventDefault();

        try {
            const response = await fetch('/api/register', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ email, password })
            });

            if (response.ok) {
                // Redirect the user to the login page
                window.location.replace('/login');
            } else {
                const data = await response.json();
                error = data.message;
            }
        } catch (err) {
            error = err.message;
        }
    }
</script>

<form on:submit|preventDefault={handleSubmit}>
    <label for="email">Email</label>
    <input type="email" bind:value={email} id="email" required />

    <label for="password">Password</label>
    <input type="password" bind:value={password} id="password" required />

    <button type="submit">Register</button>
</form>

{#if error}
    <p>{error}</p>
{/if}
