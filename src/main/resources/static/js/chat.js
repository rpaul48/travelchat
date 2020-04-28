firebase.auth().onAuthStateChanged(function(user) {
    if (user) {
        // get a Firebase Database ref
        var chatRef = firebase.database().ref("chat");

        // create a FirechatUI instance, get the corresponding Firechat instance
        var chatUI = new FirechatUI(chatRef, document.getElementById("firechat-wrapper"));
        var chat = chatUI._chat;

        // set the current chat user
        chat.setUser(user.uid, user.uid);

        // enter the desired room, specified by the url's path
        var path = window.location.pathname;
        var roomId = path.substring(path.lastIndexOf('/') + 1);
        chat.enterRoom(roomId);
    } else {
        // user is not logged in, redirect to login
        this.window.href = "/login";
    }
});