<script lang="ts">

    import PageContent from "$components/common/PageContent.svelte";
    import PageCard from "$components/common/PageCard.svelte";
    import {t} from "$i18n/i18n.js";
    import {onMount} from "svelte";
    import QrScanner from "qr-scanner";
    import {goto} from "$app/navigation";

    onMount(() => {
        // ask for camera permission

        let camera_button = document.getElementById("start-camera");
        let video = document.getElementById("video");
        let click_button = document.getElementById("click_photo");
        let canvas = document.getElementById("canvas");

        camera_button.addEventListener('click', async function () {
            video.srcObject = await navigator.mediaDevices.getUserMedia({video: true, audio: false})
                .catch(error => {
                    console.log(error);
                    alert("Can't access camera. " + error.message);
                });
        });

        click_button.addEventListener('click', function () {
            canvas.getContext('2d').drawImage(video, 0, 0, canvas.width, canvas.height);
            let image_data_url = canvas.toDataURL('image/png');

            // data url of the image
            console.log(image_data_url);

            QrScanner.scanImage(image_data_url)
                .then(result => {
                    console.log(result);
                    goto("/articles/" + result);
                })
                .catch(error => {
                    console.log(error || 'No QR code found.');
                    alert('No QR code found.');
                });
        });
    });
</script>

<PageContent>
    <PageCard title={$t('menu.qrCode')}>
        <button id="start-camera">Start Camera</button>
        <video id="video" width="320" height="240" autoplay></video>
        <button id="click_photo">Click Photo</button>
        <canvas id="canvas" width="320" height="240" hidden="hidden"></canvas>
    </PageCard>
</PageContent>