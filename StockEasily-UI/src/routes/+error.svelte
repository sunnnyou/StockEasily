<script lang="ts">
    import PageContent from "$components/common/PageContent.svelte";
    import {page} from "$app/stores";
    let imageSrc;
    let errorStatus = $page.status;
    if(errorStatus == 404) {
        imageSrc = "https://img.freepik.com/free-vector/isometric-error-background-hand-reaches-out-from-hole-with-sign-error-four-hundred-four-vector-illustration_1284-67441.jpg?w=900&t=st=1673817754~exp=1673818354~hmac=18951ecb79138606b0c127a40cda6e82ee3a9cb3d06be897be307c2a19212b60";
    } else if(errorStatus == 500) {
        imageSrc = "https://img.freepik.com/premium-vector/flat-illustration-500-internal-server-error-concept-can-t-connect-server-internet_258153-329.jpg?w=996";
    }

</script>

<PageContent>
    <div class="flex items-center justify-center">
        <img src={imageSrc} alt="">
    </div>
</PageContent>
