function createChat() {
    // get a Firebase Database ref
    var chatRef = firebase.database().ref("chat");
    // create a Firechat instance
    var chat = new Firechat(chatRef);

    // get current authenticated user, set the chat's user.
    var currentUser = firebase.auth().currentUser;
    chat.setUser(currentUser.uid, currentUser.uid);

    // get desired name for the group
    var groupName = document.getElementById("group-name-field").value;

    // get all emails of desired invitees
    var emails = document.getElementById("add-user-field").value;
    var allEmails = emails.split(",");
    var uids = [];

    for (const email of allEmails) {
        // for every invitee, make a GET request to our Spark server to get their UID
        $.ajax({
            url: "/getUID",
            type: "get",
            // authenticated with the current user's UID
            data: {"auth": currentUser.uid, "email": email},
            // MUST be a synchronous request, or else uids will not update correctly
            async: false,
            success: function (data) {
                uids.push(data);
            }});
        }

    // create a private room, invite all invitees who have an account
    chat.createRoom(groupName, "private", function(roomId) {
        for (const uid of uids) {
            if (uid !== "") {
                chat.inviteUser(uid, roomId);
            }
        }

        document.getElementById("room-id").innerHTML =
            "<br><b> " + groupName + " group ID: </b> " + roomId;

        closeCreateChat();
        window.location.href = "/chat/" + roomId;
    })
}

function addChat() {
    // get a Firebase Database ref
    var chatRef = firebase.database().ref("chat");
    // create a Firechat instance
    var chat = new Firechat(chatRef);

    // get current authenticated user, set the chat's user.
    var currentUser = firebase.auth().currentUser;
    // both userid and display name set to uid
    chat.setUser(currentUser.uid, currentUser.uid, function() {
        chat.resumeSession();
    });

    // enter the desired room
    var groupId = document.getElementById("group-id-field").value;
    chat.enterRoom(groupId);

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