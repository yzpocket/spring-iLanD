$(document).ready(function () {
    getMovies();
    getTvs();
});
function getMovies() {
    $.ajax({
        type: 'GET',
        url: `/api/boards/video/movie`,
        dataType: 'json',
        success: function (data) {
            // 데이터를 받아와서 처리하는 로직 추가
            displayVideos(data.content, '.movie_area');
        },
        error: function (error) {
            console.error('Error:', error);
        }
    });
}

function getTvs() {
    $.ajax({
        type: 'GET',
        url: `/api/boards/video/tv`,
        dataType: 'json',
        success: function (data) {
            // 데이터를 받아와서 처리하는 로직 추가
            displayVideos(data.content, '.tv_area');
        },
        error: function (error) {
            console.error('Error:', error);
        }
    });
}

function displayVideos(videos, containerSelector) {
    const container = $(containerSelector);

    // 초기화
    container.html('');

    const listElement = $('<div class="container" style="width: auto; text-align: center"></>');

    videos.forEach(video => {
        const listItem = $('<li style="display: inline-block; margin: 10px;"></li>');

        // 이미지를 클릭했을 때 해당 영화를 보는 페이지로 이동하는 링크 추가
        const linkElement = $(`<a href="movies&tv/${video.videoId}/watch"></a>`);
        const imageElement = $(`<img src="data:image/jpeg;base64,${video.imgFileContent}" alt="첨부 이미지" style="width: 100px; height: 150px;">`);

        linkElement.append(imageElement);
        listItem.append(linkElement);

        listElement.append(listItem);
    });

    container.append(listElement);
}


