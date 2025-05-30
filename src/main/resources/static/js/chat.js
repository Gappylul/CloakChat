let stompClient = null;

function connect() {
    const socket = new SockJS('/ws');
    stompClient = Stomp.over(socket);

    stompClient.connect({}, function () {
        console.log("Connected");

        stompClient.subscribe('/topic/messages', function (msg) {
            showMessage(msg.body);
        });

        stompClient.subscribe('/user/queue/messages', function (msg) {
            showMessage("[Private] " + msg.body);
        });
    });
}

function sendMessage() {
    const msg = document.getElementById("message").value;
    stompClient.send("/app/chat", {}, msg);
    document.getElementById("message").value = '';
}

function showMessage(message) {
    const box = document.getElementById("chat-box");
    box.innerHTML += `<div>${message}</div>`;
    box.scrollTop = box.scrollHeight;
}

window.onload = connect;
