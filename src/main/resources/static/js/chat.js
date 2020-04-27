// establish WebSocket connection & event listeners
// const webSocket = new WebSocket("ws://" + location.hostname + ":" + location.port + "/chat");
// webSocket.onmessage = function (msg) { updateChat(msg); };
// webSocket.onclose = function () { alert("WebSocket connection closed") };

chat.on("message-add", function (msg) { updateChat(msg); });

// click "send" --> send message
id("send").addEventListener("click", function () {
    sendMessage(id("message").value);
});

// enter is pressed --> send message
id("message").addEventListener("keypress", function (e) {
    if (e.keyCode === 13) { sendMessage(e.target.value); }
});

// sends the message if not empty, clears input field
function sendMessage(message) {
    if (message !== "") {
        webSocket.send(message);
        id("message").value = "";
    }
}

// updates chat window and list of connected users
function updateChat(msg) {
    var data = JSON.parse(msg.data);
    insert("chat", data.userMessage);
    id("userlist").innerHTML = "";
    data.userlist.forEach(function (user) {
        insert("userlist", "<li>" + user + "</li>");
    });
}

// helper --> inserts HTML as first child of an element
function insert(targetId, message) {
    id(targetId).insertAdjacentHTML("afterbegin", message);
}

// helper --> gets an element by an id
function id(id) {
    return document.getElementById(id);
}
