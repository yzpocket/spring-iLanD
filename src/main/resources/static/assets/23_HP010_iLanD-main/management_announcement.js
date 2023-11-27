document.addEventListener("DOMContentLoaded", function () {
    // 페이지 로딩 시 실행할 코드
    getAllNotices();
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
    const container = document.querySelector(containerSelector);

    // 초기화
    container.innerHTML = '';

    notices.forEach(notice => {
        const noticeElement = document.createElement('div');
        noticeElement.classList.add('announcement-list');

        noticeElement.innerHTML = `
            <div class="card" style="padding: 15px" >
                <div class="card" style="padding: 10px; background-color: #f8f9fa" onclick="showNoticeContent(${notice.noticeId})">✅ ${notice.noticeTitle}</div>
                <div class="date" style="padding: 10px" onclick="showNoticeContent(${notice.noticeId})">🕐 공지일 : ${notice.formattedCreatedAt || notice.createdAt}</div>
                <div class="notice-content" id="content-${notice.noticeId}" style=" display: none;"></div>
                <input type="hidden" id="notice-id-${notice.noticeId}" value=" ${notice.noticeId}">
            </div>
        `;

        container.appendChild(noticeElement);
    });
}

async function showNoticeContent(noticeId) {
    const contentElement = document.getElementById(`content-${noticeId}`);
    const noticeIdField = document.getElementById(`notice-id-${noticeId}`);

    // 이미 내용이 로딩된 경우 숨기고, 아닌 경우 내용을 가져와서 보여줌
    if (contentElement.style.display === 'block') {
        contentElement.style.display = 'none';
    } else {
        // Ajax를 이용해 공지글 내용 가져오기
        const response = await fetch(`/api/boards/notice/${noticeIdField.value}`);
        const notice = await response.json();

        // 가져온 공지글 정보를 화면에 표시
        contentElement.innerHTML = `
            <div>
                <div style="padding: 10px">👤 작성자 : ${notice.noticeWriter}</div>
                <div style="padding: 10px">🗒️ 내&nbsp&nbsp&nbsp용 : </div>
                <div style="padding-left: 20px">${notice.noticeContents}</div>
            </div>
        `;

        contentElement.style.display = 'block';
    }
}
$(document).ready(function() {
    window.createNotice = function () {
        const boardId = $('#boardId').val();
        const writer = "ADMIN";
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

        $.ajax({
            type: 'POST',
            url: '/api/boards/notice/create',
            data: JSON.stringify(requestData),
            contentType: 'application/json;charset=UTF-8',
            dataType: 'json',
            success: function (response) {
                alert('공지 추가 성공!');
                window.location.reload();
            },
            error: function (xhr, status, error) {
                alert('공지 추가 실패!');
                console.error('Error:', error);
            }
        });
    };
})