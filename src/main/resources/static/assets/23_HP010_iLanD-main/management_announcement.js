$(document).ready(function () {
    getAllNotices();

    $("#btn_submit").on("click", createNotice);
    $("#btn_reset").on("click", resetForm);
});

function getAllNotices() {
    // 모든 공지글 가져오기
    fetch(`/api/boards/notice/all?page=${currentPage}`)
        .then(response => response.json())
        .then(data => {
            // 데이터를 받아와서 처리하는 로직 추가
            displayNotices(data.content, '.announcement_area');
        })
        .catch(error => console.error('Error:', error));
}

function displayNotices(notices, containerSelector) {
    const container = $(containerSelector);

    // 초기화
    container.empty();

    notices.forEach(notice => {
        const noticeElement = $("<div class='announcement-list'>" +
            "<div class='card' style='padding: 15px'>" +
            `<div class='card' style='padding: 10px; background-color: #f8f9fa' onclick='showNoticeContent(${notice.noticeId})'>✅ ${notice.noticeTitle}</div>` +
            `<div class='date' style='padding: 10px' onclick='showNoticeContent(${notice.noticeId})'>🕐 공지일 : ${notice.formattedCreatedAt || notice.createdAt}</div>` +
            `<div class='notice-content' id='content-${notice.noticeId}' style='display: none;'></div>` +
            `<input type='hidden' id='notice-id-${notice.noticeId}' value='${notice.noticeId}'>` +
            "</div></div>");

        container.append(noticeElement);
    });
}

function showNoticeContent(noticeId) {
    const contentElement = $(`#content-${noticeId}`);
    const noticeIdField = $(`#notice-id-${noticeId}`);

    // 이미 내용이 로딩된 경우 숨기고, 아닌 경우 내용을 가져와서 보여줌
    if (contentElement.css('display') === 'block') {
        contentElement.css('display', 'none');
    } else {
        // Ajax를 이용해 공지글 내용 가져오기
        $.ajax({
            url: `/api/boards/notice/${noticeIdField.val()}`,
            success: function (notice) {
                const fileList = notice.fileList;

                // 가져온 공지글 정보를 화면에 표시
                contentElement.html(`
                    <div>
                        <div style="padding: 10px">👤 작성자 : ${notice.noticeWriter}</div>
                        ${fileList.length > 0 ? `<div style="padding: 10px">📎 첨부파일 : ${fileList[0].fileName.replace(/^[^_]+_/, '')}</div>` : ''}
                    </div>
                    <form style="width: 100%" id="modifyForm">
                        <input type="hidden" name="boardId" id="boardId" value="1">
        
                        <div class="d-flex mt-3 gap-2 align-items-end">
                            <div class="flex-grow-1">
                                <label for="noticeTitle" class="form-label">수정 할 제목</label>
                                <input type="text" name="modifyTitle" class="form-control" value='${notice.noticeTitle}' placeholder="제목 입력">
                            </div>
                        </div>
                        <div class="d-flex mt-3 gap-2 align-items-end">
                            <div class="flex-grow-1">
                                <label for="modifyType" class="form-label">수정 할 타입</label>
                                <select name="modifyType" class="form-select">
                                    <option value="NORMAL" ${notice.noticeType === 'NORMAL' ? 'selected' : ''}>일반</option>
                                    <option value="IMPORTANT" ${notice.noticeType === 'IMPORTANT' ? 'selected' : ''}>중요</option>
                                </select>
                            </div>
                        </div>
                        <div class="d-flex mt-3 gap-2 align-items-end">
                            <div class="flex-grow-1">
                                <label for="modifyContents" class="form-label">수정 할 내용</label>
                                <textarea name="modifyContents" class="form-control" rows="3">${notice.noticeContents}</textarea>
                            </div>
                        </div>
                        <div class="mt-3 d-flex gap-2">
                            <div>
                                <label for="f_photo" class="form-label">이미지</label>
                                <input type="file" name="photo" id="f_photo" class="form-control">
                            </div>
                        </div>
                        <div class="d-flex justify-content-center mt-3 d-flex gap-2">
                            <button type="button" class="btn btn-success w-25" onclick="modifyNotice(${noticeId})">글수정</button>
                            <button type="button" class="btn btn-danger w-25" onclick="deleteNotice(${noticeId})">글삭제</button>
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

function createNotice() {
    // 폼 필드에서 값을 읽어와서 변수에 저장
    const boardId = $('#boardId').val();
    const writer = "관리자";
    const title = $('#noticeTitle').val();
    const type = $('#noticeType').val();
    const contents = $('#noticeContents').val();

    const requestData = {
        boardId: boardId,
        noticeWriter: writer,
        noticeTitle: title,
        noticeType: type,
        noticeContents: contents
    };

    const formData = new FormData();

    // 파일이 선택된 경우에만 FormData에 추가
    const fileInput = document.getElementById('file');
    if (fileInput.files.length > 0) {
        formData.append('file', fileInput.files[0]);
    }

    formData.append('boardId', boardId);
    formData.append('noticeWriter', writer);
    formData.append('noticeTitle', title);
    formData.append('noticeType', type);
    formData.append('noticeContents', contents);

    $.ajax({
        type: 'POST',
        url: '/api/boards/notice/create',
        data: formData,
        contentType: false, // 필수
        processData: false, // 필수
        success: function (response) {
            alert('공지 추가 성공!');
            window.location.reload();
        },
        error: function (xhr, status, error) {
            alert('공지 추가 실패!');
            console.error('Error:', error);
        }
    });
}


function modifyNotice(noticeId) {
    const noticeElement = $(`#notice-id-${noticeId}`).closest('.announcement-list');
    const writer = "ADMIN";
    const type = noticeElement.find('select[name="modifyType"]').val();
    const title = noticeElement.find('input[name="modifyTitle"]').val();
    const contents = noticeElement.find('textarea[name="modifyContents"]').val();


    const requestData = {
        noticeType: type,
        noticeWriter: writer,
        noticeTitle: title,
        noticeContents: contents
    };

    $.ajax({
        type: 'PUT',
        url: '/api/boards/notice/update/' + noticeId,
        data: JSON.stringify(requestData),
        contentType: 'application/json;charset=UTF-8',
        dataType: 'json',
        success: function (response) {
            alert('공지 수정 성공!');
            window.location.reload();
        },
        error: function (xhr, status, error) {
            alert('공지 수정 실패!');
            console.error('Error:', error);
        }
    });
}

function deleteNotice(noticeId) {
    $.ajax({
        type: 'DELETE',
        url: `/api/boards/notice/delete/${noticeId}`,
        success: function (response) {
            alert('공지 삭제 성공!');
            window.location.reload();
        },
        error: function (xhr, status, error) {
            alert('공지 삭제 실패!');
            console.error('Error:', error);
        }
    });
}

function resetForm() {
    // Get the form element using jQuery and reset it
    $('#noticeForm')[0].reset();
}