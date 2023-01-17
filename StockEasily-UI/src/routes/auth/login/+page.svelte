<script>
    export let error;

    let email;
    let password;

    const handleSubmit = async (e) => {
        e.preventDefault();
        // Send login request to the backend
        try {
            const res = await fetch('/api/login', {
                method: 'POST',
                body: JSON.stringify({ email, password }),
                headers: { 'Content-Type': 'application/json' },
            });
            const data = await res.json();
            if (data.token) {
                localStorage.setItem('token', data.token);
                // Redirect to the home page
                window.location.href = '/';
            } else {
                error = data.message;
            }
        } catch (err) {
            console.error(err);
            error = 'An error occurred. Please try again later.';
        }
    };
</script>

<form on:submit={handleSubmit}>
    <label for="email">Email:</label>
    <input type="email" id="email" bind:value={email} required />
    <label for="password">Password:</label>
    <input type="password" id="password" bind:value={password} required />
    <button type="submit">Log in</button>
    {#if error}
        <p style="color: red">{error}</p>
    {/if}
</form>
