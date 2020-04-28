// Get a Firebase Database ref
var chatRef = firebase.database().ref("chat");

// Create a Firechat instance
var chatUI = new FirechatUI(chatRef, document.getElementById("firechat-wrapper"));
var chat = chatUI._chat;

var currentUser = firebase.auth().currentUser;
chat.setUser(currentUser.uid, currentUser.displayName);

var roomId = window.location.pathname.substring(window.location.pathname.lastIndexOf('/') + 1);
chat.enterRoom(roomId);

/*
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
        chat.sendMessage(roomId, message);
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
*/