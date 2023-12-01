$(document).ready(function () {
    getVideo();
});

function getVideo() {
    const videoId = window.location.pathname.match(/\/movies&tv\/(\d+)\/watch/)[1];

    $.ajax({
        url: `/api/boards/video/${videoId}`,  // 비디오 데이터를 가져오는 API 엔드포인트
        success: function (video) {
            displayVideo(video);
            console.log(video)
        },
        error: function (xhr, status, error) {
            console.error('Error:', error);
        }
    });
}

function displayVideo(video) {
    const videoElement = document.createElement('video');
    videoElement.controls = true;

    videoElement.style.maxWidth = '100%'; // 최대 너비를 100%로 지정
    videoElement.style.maxHeight = '100%'; // 최대 높이를 100%로 지정
    console.log(videoElement); // 확인을 위한 로그

    const sourceElement = document.createElement('source');
    sourceElement.src = `data:video/mp4;base64,${video.videoFileContent}`;
    sourceElement.type = 'video/mp4';

    console.log(sourceElement); // 확인을 위한 로그

    videoElement.appendChild(sourceElement);

    const watchArea = document.querySelector('.watch_area');
    watchArea.appendChild(videoElement);
}