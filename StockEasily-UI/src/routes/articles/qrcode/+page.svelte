<script lang="ts">

    import PageContent from "$components/common/PageContent.svelte";
    import PageCard from "$components/common/PageCard.svelte";
    import {t} from "$i18n/i18n.js";
    import {onMount} from "svelte";
    import QrScanner from "qr-scanner";
    import {goto} from "$app/navigation";

    let testQR5 = "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAHQAAAB0CAYAAABUmhYnAAAAAXNSR0IArs4c6QAABKxJREFUeF7tnFGu5DAIBCf3P/SstH9xpJRaDWSe0/trB3AXYDuTt8f3+/1+8m8bBY4A3Ybl/4UE6F48A3QzngEaoLspsNl6socG6GYKbLacVGiAbqbAZstJhQboZgpstpxUaIBupsBmy0mFBuhmCmy2HLtCj+MYlUT9+ZbiI3v0fPXiKR7yF6Dw+36AUgqZ42oGExCyR8+by7k8TvGQv1RoKvScI2sGuxm2ZqBrX62wNX7XP1bUcgZx9SuvUDegAPW+2QvQJYNSocUtIxX64xXavYep9teEoS2C9lDVf3cHaG+53QtW7QcoHNOezuAAXW4d7pfzAaq9+kzLhUMXVagr4NMJS/fYyyFy9woNUDElns7gVOjL9tBU6GYVStcUtcNQgpB89Dzdi8n+6+6hqqAqcBJc9U/2XncoSoWKKfF0BtOhKECLgYrmLtPVhKGWRoCrfxyg9dP66PnxlqsGpApKglAF0yGE7HevT7XffihSAwrQH//5LEDvFajuAOUV6gKk52mP7B6n+Nxx2gLIfoCaL/9JYHU8QEUgbgWrgNT5ARqgp5yxW66agdXz3WtJdTxP2wvQpwkU+w/QYkGfNhegTxMo9m8DpYsx7XHqetRTIPkne/R8d/yq/QAd/uszSiAV4OVV6a99JEYLUgWhCiN79DzFu46TP9VegC4KkMCvA0oZ5e6xJPglQ8UXDRS/WmG0XtWfOt/eQ8khLZAqIEBJ4fN4gGp6fSjBKIFFd/L0ABUl2x5odcukPZH0J8HJPv0ao+6pFG/1uF2hAep9MhKgoEAqVFVgETQVulmFqi2DEoD2KDpFqvbV+Cn/KT7Vnzrf3kNlh+J/9kiHFBpX46P5ASq26FQopdT9eCpU1O/1FaruKeoeSAITLzc+8k/roecp/su92v35jBy6gpF9VxA3PvIfoOahiRJAfVNUPZ/OCGr8qVA4pKkVp87/80DdjKPn3ZaWlksKD48H6Fnw8WtLNe8ADdCTAmm55psft0LVQwi9GlTH6dSrro/Wo9qzWy61PDUgmk8CUMW54wFKhMTxAL0XLBW6fDlPFfy6CqUKEgvyQwK742489KKA4lP90/zyCg3Q8xcMASq+miPBaJwyXm2x7qlZjaf8XW61YCSgKlh1fHSqV+NzAY4DJQHcPYjsqwKrCUD+aX0BCqdSEpCA0Th1EAJUfcZIhYp/nUYAUqGQwm6LTIVSCgIAalFuBrv2KUGoRVKCUAs25VXD+7TfQwN09sv6ABVrgCqOOoroTp4eoKJkAWp+xUcZ/2stXcyP8unbVygdatyEoYotJ0aHVPdDa1eQbsG77Qfo8D00QMUeQRUqmrtMd+2reyzFSxVJ8dI4+afx9j2UAqBxV4AAJYWXcVdwcufaD1BSOEBvFaIEpHFR/usWVX3KdQOi59V3s+6et8ajAlHn0/ppvHwPJYfueIDeKxig8PtoKtQtQfOeSvfOaUB/ruU284t5UQG75Yr+Mr1ZgQBtFnjafIBOK97sL0CbBZ42H6DTijf7C9BmgafNB+i04s3+ArRZ4GnzATqteLO/AG0WeNp8gE4r3uwvQJsFnjYfoNOKN/sL0GaBp80H6LTizf7+AXg/6v+W5SlWAAAAAElFTkSuQmCC";
    onMount(() => {
        // ask for camera permission

        let camera_button = document.getElementById("start-camera");
        let video = document.getElementById("video");
        let click_button = document.getElementById("click_photo");
        let canvas = document.getElementById("canvas");

        camera_button.addEventListener('click', async function () {
            video.srcObject = await navigator.mediaDevices.getUserMedia({video: true, audio: false});
        });

        click_button.addEventListener('click', function () {
            canvas.getContext('2d').drawImage(video, 0, 0, canvas.width, canvas.height);
            let image_data_url = canvas.toDataURL('image/png');

            // data url of the image
            console.log(image_data_url);

            QrScanner.scanImage(testQR5)
                .then(result => {
                    console.log(result);
                    goto("/articles/" + result);
                }
                )
                .catch(error => console.log(error || 'No QR code found.'));
        });
    });
</script>

<PageContent>
    <PageCard title={$t('menu.qrCode')}>
        <button id="start-camera">Start Camera</button>
        <video id="video" width="320" height="240" autoplay></video>
        <button id="click_photo">Click Photo</button>
        <canvas id="canvas" width="320" height="240"></canvas>
    </PageCard>
</PageContent>