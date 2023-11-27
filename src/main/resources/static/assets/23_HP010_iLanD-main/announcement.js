$(document).ready(function () {
    // 페이지 로딩 시 실행할 코드
    getAllNotices();
});

function getAllNotices() {
    // 일반 공지글 가져오기
    fetch(`/api/boards/notice/normal?page=${currentPage}`)
        .then(response => response.json())
        .then(data => {
            // 데이터를 받아와서 처리하는 로직 추가
            displayNotices(data.content, '.announcement_area');
        })
        .catch(error => console.error('Error:', error));

    // 중요 공지글 가져오기
    fetchAllImportantNotices();
}

function fetchAllImportantNotices() {
    $.ajax({
        type: 'GET',
        url: `/api/boards/notice/important`,
        dataType: 'json',
        success: function (data) {
            // 데이터를 받아와서 처리하는 로직 추가
            displayNotices(data.content, '.announcement_important');
        },
        error: function (error) {
            console.error('Error:', error);
        }
    });
}

function displayNotices(notices, containerSelector) {
    const container = $(containerSelector);

    // 초기화
    container.html('');

    notices.forEach(notice => {
        const noticeElement = $('<div class="announcement-list"></div>');

        noticeElement.html(`
            <div class="card" style="padding: 15px" >
                <div class="card" style="padding: 10px; background-color: #f8f9fa" onclick="showNoticeContent(${notice.noticeId})">✅ ${notice.noticeTitle}</div>
                <div class="date" style="padding: 10px" onclick="showNoticeContent(${notice.noticeId})">🕐 공지일 : ${notice.formattedCreatedAt || notice.createdAt}</div>
                <div class="notice-content" id="content-${notice.noticeId}" style=" display: none;"></div>
                <input type="hidden" id="notice-id-${notice.noticeId}" value=" ${notice.noticeId}">
            </div>
        `);

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
            type: 'GET',
            url: `/api/boards/notice/${noticeIdField.val()}`,
            dataType: 'json',
            success: function (notice) {
                // 가져온 공지글 정보를 화면에 표시
                contentElement.html(`
                    <div>
                        <div style="padding: 10px">👤 작성자 : ${notice.noticeWriter}</div>
                        <div style="padding: 10px">🗒️ 내&nbsp&nbsp&nbsp용 : </div>
                        <div style="padding-left: 20px">${notice.noticeContents}</div>
                    </div>
                `);

                contentElement.css('display', 'block');
            },
            error: function (error) {
                console.error('Error:', error);
            }
        });
    }
}
