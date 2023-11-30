$(document).ready(function () {
    getAllContents();

    $("#btn_submit").on("click", createContent);
    $("#btn_reset").on("click", resetForm);
});

function getAllContents() {
    // 모든 비디오글 가져오기
    fetch(`/api/boards/content/all?page=${currentPage}`)
        .then(response => response.json())
        .then(data => {
            // 데이터를 받아와서 처리하는 로직 추가
            displayContents(data.content, '.announcement_area');
        })
        .catch(error => console.error('Error:', error));
}

function displayContents(contents, containerSelector) {
    const container = $(containerSelector);

    // 초기화
    container.empty();

    contents.forEach(content => {
        const contentElement = $("<div class='announcement-list'>" +
            "<div class='card' style='padding: 15px'>" +
            `<div class='card' style='padding: 10px; background-color: #f8f9fa' onclick='showContentContent(${content.contentId})'>✅ ${content.contentTitle}</div>` +
            `<div class='date' style='padding: 10px' onclick='showContentContent(${content.contentId})'>🕐 작성일 : ${content.formattedCreatedAt || content.createdAt}</div>` +
            `<div class='content-content' id='content-${content.contentId}' style='display: none;'></div>` +
            `<input type='hidden' id='content-id-${content.contentId}' value='${content.contentId}'>` +
            "</div></div>");

        container.append(contentElement);
    });
}

function showContentContent(contentId) {
    const contentElement = $(`#content-${contentId}`);
    const contentIdField = $(`#content-id-${contentId}`);

    // 이미 내용이 로딩된 경우 숨기고, 아닌 경우 내용을 가져와서 보여줌
    if (contentElement.css('display') === 'block') {
        contentElement.css('display', 'none');
    } else {
        // Ajax를 이용해 비디오글 내용 가져오기
        $.ajax({
            url: `/api/boards/content/${contentIdField.val()}`,
            success: function (content) {
                const fileList = content.fileList;
                // 파일 리스트에서 마지막 파일의 파일명 가져오기
                const originFileName = fileList.length > 0 ? fileList[fileList.length - 1].fileName : '';
                const lastFileName = fileList.length > 0 ? fileList[fileList.length - 1].fileName.replace(/^[^_]+_/, '') : '';
                // 가져온 비디오글 정보를 화면에 표시
                contentElement.html(`
                    <div>
                        <div style="padding: 10px">👤 작성자 : ${content.contentWriter}</div>
                        ${fileList.length > 0 ? `<div style="padding: 10px">📎 첨부파일 : ${lastFileName}</div>` : ''}
                    </div>
                     ${fileList.length > 0 ? `<div style="padding: 10px">미리보기: <img src="/uploads/${originFileName}" alt="첨부 이미지" style="max-width: 200px; max-height: 200px;"></div>` : ''}

                    <form style="width: 100%" id="modifyForm">
                        <input type="hidden" name="boardId" id="boardId" value="1">
        
                        <div class="d-flex mt-3 gap-2 align-items-end">
                            <div class="flex-grow-1">
                                <label for="contentTitle" class="form-label">수정 할 제목</label>
                                <input type="text" name="modifyTitle" class="form-control" value='${content.contentTitle}' placeholder="제목 입력">
                            </div>
                        </div>
                        <div class="d-flex mt-3 gap-2 align-items-end">
                            <div class="flex-grow-1">
                                <label for="modifyType" class="form-label">수정 할 타입</label>
                                <select name="modifyType" class="form-select">
                                    <option value="NORMAL" ${content.contentType === 'NORMAL' ? 'selected' : ''}>일반</option>
                                    <option value="IMPORTANT" ${content.contentType === 'IMPORTANT' ? 'selected' : ''}>중요</option>
                                </select>
                            </div>
                        </div>
                        <div class="d-flex mt-3 gap-2 align-items-end">
                            <div class="flex-grow-1">
                                <label for="modifyContents" class="form-label">수정 할 내용</label>
                                <textarea name="modifyContents" class="form-control" rows="3">${content.contentContents}</textarea>
                            </div>
                        </div>
                        <div class="mt-3 d-flex gap-2">
                            <div>
                                <label for="f_photo" class="form-label">이미지</label>
                                <input type="file" name="photo" id="f_photo" class="form-control">
                            </div>
                        </div>
                        <div class="d-flex justify-content-center mt-3 d-flex gap-2">
                            <button type="button" class="btn btn-success w-25" onclick="modifyContent(${contentId})">글수정</button>
                            <button type="button" class="btn btn-danger w-25" onclick="deleteContent(${contentId})">글삭제</button>
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

function createContent() {
    // 폼 필드에서 값을 읽어와서 변수에 저장
    const boardId = $('#boardId').val();
    const writer = "관리자";
    const title = $('#contentTitle').val();
    const type = $('#contentType').val();
    const contents = $('#contentContents').val();

    const requestData = {
        boardId: boardId,
        contentWriter: writer,
        contentTitle: title,
        contentType: type,
        contentContents: contents
    };

    const formData = new FormData();

    // 파일이 선택된 경우에만 FormData에 추가
    const fileInput = document.getElementById('file');
    if (fileInput.files.length > 0) {
        formData.append('file', fileInput.files[0]);
    }

    formData.append('boardId', boardId);
    formData.append('contentWriter', writer);
    formData.append('contentTitle', title);
    formData.append('contentType', type);
    formData.append('contentContents', contents);

    $.ajax({
        type: 'POST',
        url: '/api/boards/content/create',
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


function modifyContent(contentId) {
    const contentElement = $(`#content-id-${contentId}`).closest('.announcement-list');
    const writer = "관리자";
    const type = contentElement.find('select[name="modifyType"]').val();
    const title = contentElement.find('input[name="modifyTitle"]').val();
    const contents = contentElement.find('textarea[name="modifyContents"]').val();

    const requestData = {
        contentType: type,
        contentWriter: writer,
        contentTitle: title,
        contentContents: contents
    };

    const formData = new FormData();

    // 파일이 선택된 경우에만 FormData에 추가
    const fileInput = contentElement.find('input[name="photo"]');
    if (fileInput.length > 0 && fileInput[0].files.length > 0) {
        formData.append('file', fileInput[0].files[0]);
    }

    // 기존 파일이 있는 경우에는 fileId도 전송
    const existingFileId = contentElement.find('input[name="existingFileId"]').val();
    if (existingFileId) {
        formData.append('fileId', existingFileId);
    }

    formData.append('contentType', type);
    formData.append('contentWriter', writer);
    formData.append('contentTitle', title);
    formData.append('contentContents', contents);

    $.ajax({
        type: 'PUT',
        url: '/api/boards/content/update/' + contentId,
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

function deleteContent(contentId) {
    $.ajax({
        type: 'DELETE',
        url: `/api/boards/content/delete/${contentId}`,
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
    $('#contentForm')[0].reset();
}