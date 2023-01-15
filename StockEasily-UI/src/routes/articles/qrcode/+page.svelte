<script lang="ts">

    import PageContent from "$components/common/PageContent.svelte";
    import PageCard from "$components/common/PageCard.svelte";
    import {t} from "$i18n/i18n.js";
    import {onMount} from "svelte";
    import QrScanner from "qr-scanner";
    import {goto} from "$app/navigation";

    onMount(() => {
        let camera_button = document.getElementById("start-camera");
        let video = document.getElementById("video");
        let canvas = document.getElementById("canvas");

        let stopScanning = true;

        camera_button.addEventListener('click', async function () {

            stopScanning = !stopScanning;

            video.srcObject = await navigator.mediaDevices.getUserMedia({video: true, audio: false})
                .catch(error => {
                    console.log(error);
                    alert("Can't access camera. " + error.message);
                });
            while (!stopScanning) {
                camera_button.innerText = "Stop Scanning";
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
                    })
                await sleep(200);
            }
            camera_button.innerText = "Start QR-Scan";

        });

        function sleep(ms) {
            return new Promise((resolve) => setTimeout(resolve, ms ?? 200));
        }
    });
</script>

<PageContent>
    <PageCard title={$t('menu.qrCode')}>
        <button id="start-camera">Start QR-Scan</button>
        <video id="video" width="full" height="full" autoplay>
            <track kind="captions" src="">
        </video>
        <canvas id="canvas" width="500" height="500" hidden="hidden"></canvas>
    </PageCard>
</PageContent>