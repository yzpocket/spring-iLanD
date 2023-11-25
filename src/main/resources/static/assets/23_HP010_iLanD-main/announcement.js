document.addEventListener("DOMContentLoaded", function () {
    // 페이지 로딩 시 실행할 코드
    getAllNotices();
});

function getAllNotices() {
    // Ajax로 서버에 공지글 데이터 요청
    fetch('/api/boards/notice/all?page=1')
        .then(response => response.json())
        .then(data => {
            // 데이터를 받아와서 처리하는 로직 추가
            displayNotices(data.content);
        })
        .catch(error => console.error('Error:', error));
}

function displayNotices(notices) {
    const announcementArea = document.querySelector('.announcement_area');
    const importantArea = document.querySelector('.announcement_important');

    // 초기화
    announcementArea.innerHTML = '';
    importantArea.innerHTML = '';

    notices.forEach(notice => {
        const noticeElement = document.createElement('div');
        noticeElement.classList.add('announcement-list');

        noticeElement.innerHTML = `
            <div class="h5 fw-bolder" style="padding-top: 15px" onclick="showNoticeContent(${notice.noticeId})">✅ ${notice.noticeTitle}</div>
            <div class="date" style="padding-bottom: 15px" onclick="showNoticeContent(${notice.noticeId})">🕐 공지일 : ${notice.formattedCreatedAt}</div>
            <div class="notice-content" id="content-${notice.noticeId}" style=" display: none;"></div>
            <input type="hidden" id="notice-id-${notice.noticeId}" value=" ${notice.noticeId}">
        `;

        if (notice.noticeType === 'IMPORTANT') {
            importantArea.appendChild(noticeElement);
        } else {
            announcementArea.appendChild(noticeElement);
        }
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
            <p>👤 작성자: ${notice.noticeWriter}</p>
            <p>🗒️ ${notice.noticeContents}</p>
        `;

        contentElement.style.display = 'block';
    }
}

