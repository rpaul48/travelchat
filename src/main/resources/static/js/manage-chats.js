// creates a new chat
function createChat() {
    // get a Firebase Database ref
    var chatRef = firebase.database().ref("chat");
    // create a Firechat instance
    var chat = new Firechat(chatRef);

    // get current authenticated user, set the chat's user.
    var currentUser = firebase.auth().currentUser;
    if (currentUser.displayName != null) {
        chat.setUser(currentUser.uid, currentUser.displayName);
    } else if (currentUser.emailVerified != null) {
        console.log("User displayName is null: using emailVerified instead.");
        chat.setUser(currentUser.uid, currentUser.emailVerified);
    } else {
        console.log("User displayName and emailVerified are null: using uid instead.");
        chat.setUser(currentUser.uid, currentUser.uid);
    }

    // get desired name for the group
    var groupName = document.getElementById("group-name-field").value;

    // get all emails of desired invitees
    var emails = document.getElementById("add-user-field").value;
    var regex = /^([\w+-.%]+@[\w-.]+\.[A-Za-z]{2,4},?)+$/;
    if (!regex.test(emails)) {
        alert("Invitee emails are improperly formatted. Please enter emails only separated by commas.");
        document.getElementById("add-user-field").focus();
    } else {
        // create a room, invite all invitees who have an account
        chat.createRoom(groupName, "public", function (roomId) {
            // for every invitee, make a POST request to our Spark server to get their UID
            $.ajax({
                url: "/createRoom",
                type: "post",
                // authenticated with the current user's UID
                data: {
                    "auth": currentUser.uid,
                    "emails": emails,
                    "groupId": roomId,
                    "groupName": groupName
                },
                // MUST be a synchronous request
                async: false,
                success: function (data) {
                    document.getElementById("room-id").innerHTML =
                        "<br><b> " + groupName + " group ID: </b> " + roomId;

                    chat.enterRoom(roomId);
                    closeCreateChat();
                    window.location.href = "/chat/" + roomId;
                }
            });
        })
    }
}

function openCreateChat() {
    $("#create-chat-div").fadeIn();
}

function closeCreateChat() {
    document.getElementById("create-chat-div").style.display = "none";
}

// displays the chats that the user has already created / been invited to
function showUserChats() {
    firebase.auth().onAuthStateChanged(function(user) {
        if (user) {
            // get a Firebase Database ref
            var chatRef = firebase.database().ref("chat");
            // create a Firechat instance
            var chat = new Firechat(chatRef);

            if (user.displayName != null) {
                chat.setUser(user.uid, user.displayName);
            } else {
                chat.setUser(user.uid, user.uid);
            }

            // gets the user's rooms from the Firebase database
            $.ajax({
                url: "/getUserRooms",
                type: "get",
                data: {"uid": user.uid},
                async: false,
                success: function (data) {
                    var userRooms = JSON.parse(data);
                    for (let id of Object.keys(userRooms)) {
                        var groupName = userRooms[id];
                        var idString = "\'" + String(id) + "\'";
                        $("#user-rooms").append("<button onclick=\"goToRoom(" + idString + ")\"" +
                            "class=\"large-blue-button\">" + groupName + "</button>");
                    }
                }});
        }
    })
}

// sends the user to a particular room
function goToRoom(groupId) {
    window.location.pathname = "/chat/" + groupId;
}

showUserChats();