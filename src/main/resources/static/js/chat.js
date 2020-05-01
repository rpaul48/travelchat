let chat;
let roomId;
let curUser;

firebase.auth().onAuthStateChanged(function(user) {
    if (user) {
        // get a Firebase Database ref
        curUser = user;
        var chatRef = firebase.database().ref("chat");

        // create a FirechatUI instance, get the corresponding Firechat instance
        var chatUI = new FirechatUI(chatRef, document.getElementById("firechat-wrapper"));
        chat = chatUI._chat;

        // set the current chat user
        if (user.displayName != null) {
            chat.setUser(user.uid, user.displayName);
        } else if (user.emailVerified != null) {
            console.log("User displayName is null: using email instead.");
            chat.setUser(user.uid, user.email);
        } else {
            console.log("User displayName and emailVerified are null: using uid instead.");
            chat.setUser(user.uid, user.uid);
        }

        $.ajax({
            url: "/getUserRooms",
            type: "get",
            data: {"uid": user.uid},
            async: false,
            success: function (data) {
                var path = window.location.pathname;
                roomId = path.substring(path.lastIndexOf('/') + 1);
                var userRooms = JSON.parse(data);

                if (Object.keys(userRooms).includes(roomId)) {
                    // user is added to the room, enter the chat
                    chat.enterRoom(roomId);
                } else {
                    // user is not added to the room, redirect to manage-chats
                    window.location.href = "/manage-chats";
                }
            }});
    } else {
        // user is not logged in, redirect to login
        window.location.href = "/login";
    }
});

function leaveChat() {
    chat.leaveRoom(roomId);

    $.ajax({
        url: "/removeUserFromRoom",
        type: "post",
        data: {"auth": firebase.auth().currentUser.uid, "email": curUser.email, "roomId": roomId},
        async: false,
    });

    window.location.href = "/manage-chats";
}

function openSettings() {
    $("#settings-div").fadeIn();
}

function openPopup(id) {
    document.getElementById(id).style.display = "block";
}

function closePopup(id) {
    document.getElementById(id).style.display = "none";
}

function fillLocation(id) {
    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(showPosition);
    } else {
        document.getElementById(id).value = "Geolocation is not supported by this browser.";
    }

    function showPosition(position) {
        document.getElementById(id).value = "latitude: " + position.coords.latitude +
            ", longitude: " + position.coords.longitude;
    }
}

function editProfile() {
    var name = document.getElementById("update-display-name-field").value;

    firebase.auth().currentUser.updateProfile({displayName: name}).then(function () {
        console.log("set displayName to: " + firebase.auth().currentUser.displayName);
        closePopup('settings-div');
        location.reload();
    });
}

function inviteUser() {
    var email = document.getElementById("invite-search").value;
    var groupName = $("#groupName").text();

    $.ajax({
        url: "/addUserToRoom",
        type: "post",
        data: {"auth": firebase.auth().currentUser.uid, "email": email, "roomId": roomId, "groupName": groupName},
        async: false,
        });
}