function createChat() {
    // Get a Firebase Database ref
    var chatRef = firebase.database().ref("chat");
    // Create a Firechat instance
    var chat = new Firechat(chatRef);
    var currentUser = firebase.auth().currentUser;
    chat.setUser(currentUser.uid, currentUser.uid);

    var groupName = document.getElementById("group-name-field").value;

    chat.createRoom(groupName, "public", function(roomId) {
        document.getElementById("room-id").innerHTML +=
            "<br><b> " + groupName + " group ID: </b> " + roomId;
    })
}

function addChat() {
    // Get a Firebase Database ref
    var chatRef = firebase.database().ref("chat");
    // Create a Firechat instance
    var chat = new Firechat(chatRef);
    var currentUser = firebase.auth().currentUser;

    // both userid and display name set to uid
    chat.setUser(currentUser.uid, currentUser.uid, function() {
        chat.resumeSession();
    });

    var groupId = document.getElementById("group-id-field").value;
    chat.enterRoom(groupId);

    // this send message call doesn't seem to be working - it doesn't update in firebase?
    chat.sendMessage(groupId, "ENTERED ROOM", 'default', function(){
        console.log("sent");
    });

    window.location.pathname = "/chat/" + groupId;
}

function openCreateChat() {
    $("#create-chat-div").fadeIn();
}

function closeCreateChat() {
    document.getElementById("create-chat-div").style.display = "none";
}

function openAddChat() {
    $("#add-chat-div").fadeIn();
}

function closeAddChat() {
    document.getElementById("add-chat-div").style.display = "none";
}