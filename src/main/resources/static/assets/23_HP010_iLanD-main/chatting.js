const client = new StompJs.Client({
    brokerURL: 'ws://192.168.0.2:8080/websocket'
    // 브로커주소를 서버 IP로 수정해야한다.
    // brokerURL: 'ws://localhost:8080/websocket'
});

client.onConnect = (frame) => {
    setConnected(true);
    console.log('Connected: ' + frame);
    client.subscribe('/chatMessage', (chatting) => {
        showChatting(JSON.parse(chatting.body).content);
    });
};

client.onWebSocketError = (error) => {
    console.error('Error with websocket', error);
};

client.onStompError = (frame) => {
    console.error('Broker reported error: ' + frame.headers['message']);
    console.error('Additional details: ' + frame.body);
};

function setConnected(connected) {
    $("#chatConnect").prop("disabled", connected);
    $("#chatDisconnect").prop("disabled", !connected);
    $("#message").html("");

    // 연결 상태에 따라 메시지 추가
    var message = connected ? "[System] 채팅방에 연결되었습니다." : "[System] 채팅방에서 나갔습니다.";
    showChatting(message);
}

function connect() {
    client.activate();
}

function disconnect() {
    client.deactivate();
    setConnected(false);
    console.log("Disconnected");
}

// 클라이언트에서 토큰을 추출하는 함수 정의
function extractUsernameFromToken(token) {
    try {
        const base64Url = token.split('.')[1];
        const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
        const jsonPayload = decodeURIComponent(atob(base64).split('').map(function(c) {
            return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
        }).join(''));

        const payload = JSON.parse(jsonPayload);
        return payload.sub; // 토큰에서 유저 이름 추출
    } catch (error) {
        console.error('Error extracting username from token:', error);
        return null;
    }
}

// 토큰 가져오기 함수 정의
function getJwtToken() {
    // 여기에서 실제로 토큰을 가져오는 로직을 작성해야 함
    // 예시로 쿠키에서 토큰을 가져오는 방식 사용
    const name = "Authorization=";
    const decodedCookie = decodeURIComponent(document.cookie);
    const cookieArray = decodedCookie.split(';');

    for (let i = 0; i < cookieArray.length; i++) {
        let cookie = cookieArray[i].trim();
        if (cookie.indexOf(name) === 0) {
            return cookie.substring(name.length, cookie.length);
        }
    }

    return null;
}

// 토큰이 없을 경우 "[Unknown]" 반환하는 함수
function getUsernameOrUnknown() {
    const token = getJwtToken();
    return token ? extractUsernameFromToken(token) : "Unknown";
}

// 채팅 메시지 전송 시 유저 이름을 함께 전송
function sendMessage() {
    const chattingMessage = $("#chattingMessage").val();
    const username = getUsernameOrUnknown(); // 수정: getUsernameOrUnknown 사용

    if (username) {
        const messageToSend = `[${username}] ${chattingMessage}`;

        // 전송할 메시지 구성
        client.publish({
            destination: "/app/chat",
            body: JSON.stringify({'chattingMessage': messageToSend})
        });

        // 텍스트 입력 부분으로 커서 이동 및 내용 비우기
        $("#chattingMessage").val('').focus();
    } else {
        console.error('Username not available.');
    }
}

function showChatting(message) {
    // 메시지를 추가할 div 요소 선택
    var conversationDiv = $("#conversation");

    // 메시지를 담을 새로운 div 생성
    var messageDiv = $("<div>").addClass("chat-message").text(message);

    // 현재 유저인지 확인
    const isCurrentUser = message.includes(`[${getUsernameOrUnknown()}]`);

    // 오른쪽 정렬이면 클래스 추가
    if (isCurrentUser) {
        messageDiv.addClass("current-user");
    }

    // conversationDiv에 messageDiv 추가
    conversationDiv.append(messageDiv);

    // 스크롤을 최하단으로 이동 (새 메시지가 보이도록)
    conversationDiv.scrollTop(conversationDiv.prop("scrollHeight"));
}

$(function () {
    $("#chatConnect").click(() => connect());
    $("#chatDisconnect").click(() => disconnect());
    $("#messageSend").click(() => sendMessage());

    // textarea에서 엔터 키 감지
    $("#chattingMessage").keydown(function (e) {
        if (e.which === 13 && !e.shiftKey) {
            e.preventDefault(); // 엔터 키 기본 동작 방지
            $("#messageSend").click(); // 전송 버튼 클릭

            // 텍스트 입력 부분으로 커서 이동 및 내용 비우기
            $(this).val('').focus();
        }
    });
});
