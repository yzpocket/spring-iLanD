$(document).ready(function () {
    getAllVideos();

    $("#btn_submit").on("click", createVideo);
    $("#btn_reset").on("click", resetForm);
});

function getAllVideos() {
    // 모든 비디오글 가져오기
    fetch(`/api/boards/video/all?page=${currentPage}`)
        .then(response => response.json())
        .then(data => {
            // 데이터를 받아와서 처리하는 로직 추가
            displayVideos(data.content, '.video_area');
        })
        .catch(error => console.error('Error:', error));
}

function displayVideos(videos, containerSelector) {
    const container = $(containerSelector);

    // 초기화
    container.empty();

    videos.forEach(video => {
        const videoElement = $("<div class='video-list'>" +
            "<div class='card' style='padding: 15px'>" +
            `<div class='card' style='padding: 10px; background-color: #f8f9fa' onclick='showVideoContent(${video.videoId})'>✅ ${video.videoTitle}</div>` +
            `<div class='date' style='padding: 10px' onclick='showVideoContent(${video.videoId})'>🕐 작성일 : ${video.formattedCreatedAt || video.createdAt}</div>` +
            `<div class='video-content' id='video-${video.videoId}' style='display: none;'></div>` +
            `<input type='hidden' id='video-id-${video.videoId}' value='${video.videoId}'>` +
            "</div></div>");

        container.append(videoElement);
    });
}

function showVideoContent(videoId) {
    const contentElement = $(`#video-${videoId}`);
    const videoIdField = $(`#video-id-${videoId}`);

    // 이미 내용이 로딩된 경우 숨기고, 아닌 경우 내용을 가져와서 보여줌
    if (contentElement.css('display') === 'block') {
        contentElement.css('display', 'none');
    } else {
        // Ajax를 이용해 비디오글 내용 가져오기
        $.ajax({
            url: `/api/boards/video/${videoIdField.val()}`,
            success: function (video) {
                const fileList = video.fileList;
                // 파일 리스트에서 마지막 파일의 파일명 가져오기
                const originFileName = fileList.length > 0 ? fileList[fileList.length - 1].fileName : '';
                const lastFileName = fileList.length > 0 ? fileList[fileList.length - 1].fileName.replace(/^[^_]+_/, '') : '';
                // 가져온 비디오글 정보를 화면에 표시
                contentElement.html(`
                    <div>
                        <div style="padding: 10px">👤 작성자 : ${video.videoWriter}</div>
                        ${fileList.length > 0 ? `<!--<div style="padding: 10px">📎 첨부파일 : ${lastFileName}</div>-->` : ''}
                    </div>
                     ${fileList.length > 0 ? `<div style="padding: 10px">미리보기: <img src="data:image/jpeg;base64,${video.fileContent}" alt="첨부 이미지" style="max-width: 200px; max-height: 200px;"></div>` : ''}

                    <form style="width: 100%" id="modifyForm">
                        <input type="hidden" name="boardId" id="boardId" value="1">
        
                        <div class="d-flex mt-3 gap-2 align-items-end">
                            <div class="flex-grow-1">
                                <label for="videoTitle" class="form-label">수정 할 제목</label>
                                <input type="text" name="modifyTitle" class="form-control" value='${video.videoTitle}' placeholder="제목 입력">
                            </div>
                        </div>
                        <div class="d-flex mt-3 gap-2 align-items-end">
                            <div class="flex-grow-1">
                                <label for="modifyType" class="form-label">수정 할 타입</label>
                                <select name="modifyType" class="form-select">
                                    <option value="MOVIE" ${video.videoType === 'MOVIE' ? 'selected' : ''}>영화</option>
                                    <option value="TV" ${video.videoType === 'TV' ? 'selected' : ''}>TV프로그램</option>
                                </select>
                            </div>
                        </div>
                        <div class="d-flex mt-3 gap-2 align-items-end">
                            <div class="flex-grow-1">
                                <label for="modifyContents" class="form-label">수정 할 내용</label>
                                <textarea name="modifyContents" class="form-control" rows="3">${video.videoContents}</textarea>
                            </div>
                        </div>
                        <div class="mt-3 d-flex gap-2">
                            <div>
                                <label for="f_photo" class="form-label">이미지</label>
                                <input type="file" name="photo" id="f_photo" class="form-control">
                            </div>
                        </div>
                        <div class="d-flex justify-content-center mt-3 d-flex gap-2">
                            <button type="button" class="btn btn-success w-25" onclick="modifyVideo(${videoId})">글수정</button>
                            <button type="button" class="btn btn-danger w-25" onclick="deleteVideo(${videoId})">글삭제</button>
                        </div>
                    </form>
                `);

                contentElement.css('display', 'block');
            },
            error: function (xhr, status, error) {
                console.error('Error:', error);
            }
        });
    }
}

function createVideo() {
    // 폼 필드에서 값을 읽어와서 변수에 저장
    const boardId = $('#boardId').val();
    const writer = "관리자";
    const title = $('#videoTitle').val();
    const type = $('#videoType').val();
    const contents = $('#videoContents').val();

    const requestData = {
        boardId: boardId,
        videoWriter: writer,
        videoTitle: title,
        videoType: type,
        videoContents: contents
    };

    const formData = new FormData();

    // 파일이 선택된 경우에만 FormData에 추가
    const fileInput = document.getElementById('file');
    if (fileInput.files.length > 0) {
        formData.append('file', fileInput.files[0]);
    }

    formData.append('boardId', boardId);
    formData.append('videoWriter', writer);
    formData.append('videoTitle', title);
    formData.append('videoType', type);
    formData.append('videoContents', contents);

    $.ajax({
        type: 'POST',
        url: '/api/boards/video/create',
        data: formData,
        contentType: false, // 필수
        processData: false, // 필수
        success: function (response) {
            alert('비디오 추가 성공!');
            window.location.reload();
        },
        error: function (xhr, status, error) {
            alert('비디오 추가 실패!');
            console.error('Error:', error);
        }
    });
}


function modifyVideo(videoId) {
    const videoElement = $(`#video-id-${videoId}`).closest('.video-list');
    const writer = "관리자";
    const type = videoElement.find('select[name="modifyType"]').val();
    const title = videoElement.find('input[name="modifyTitle"]').val();
    const contents = videoElement.find('textarea[name="modifyContents"]').val();

    const requestData = {
        videoType: type,
        videoWriter: writer,
        videoTitle: title,
        videoContents: contents
    };

    const formData = new FormData();

    // 파일이 선택된 경우에만 FormData에 추가
    const fileInput = videoElement.find('input[name="photo"]');
    if (fileInput.length > 0 && fileInput[0].files.length > 0) {
        formData.append('file', fileInput[0].files[0]);
    }

    // 기존 파일이 있는 경우에는 fileId도 전송
    const existingFileId = videoElement.find('input[name="existingFileId"]').val();
    if (existingFileId) {
        formData.append('fileId', existingFileId);
    }

    formData.append('videoType', type);
    formData.append('videoWriter', writer);
    formData.append('videoTitle', title);
    formData.append('videoContents', contents);

    $.ajax({
        type: 'PUT',
        url: '/api/boards/video/update/' + videoId,
        data: formData,
        contentType: false, // 필수
        processData: false, // 필수
        success: function (response) {
            alert('비디오 수정 성공!');
            window.location.reload();
        },
        error: function (xhr, status, error) {
            alert('비디오 수정 실패!');
            console.error('Error:', error);
        }
    });
}

function deleteVideo(videoId) {
    $.ajax({
        type: 'DELETE',
        url: `/api/boards/video/delete/${videoId}`,
        success: function (response) {
            alert('비디오 삭제 성공!');
            window.location.reload();
        },
        error: function (xhr, status, error) {
            alert('비디오 삭제 실패!');
            console.error('Error:', error);
        }
    });
}

function resetForm() {
    // Get the form element using jQuery and reset it
    $('#videoForm')[0].reset();
}