const client = new StompJs.Client({
    brokerURL: 'ws://localhost:8080/websocket'
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
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#message").html("");
}

function connect() {
    client.activate();
}

function disconnect() {
    client.deactivate();
    setConnected(false);
    console.log("Disconnected");
}

function sendMessage() {
    client.publish({
        destination: "/app/chat",
        body: JSON.stringify({'chattingMessage': $("#chattingMessage").val()})
    });
}

function showChatting(message) {
    $("#message").append("<tr><td>" + message + "</td></tr>");
}

$(function () {
    $("form").on('submit', (e) => e.preventDefault());
    $("#chatConnect").click(() => connect());
    $("#chatDisconnect").click(() => disconnect());
    $("#messageSend").click(() => sendMessage());
});
