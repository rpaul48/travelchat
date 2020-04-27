function initChat(user) {
    // Get a Firebase Database ref
    var chatRef = firebase.database().ref("chat");

    // Create a Firechat instance
    var chat = new FirechatUI(chatRef, document.getElementById("firechat-wrapper"));

    // Set the Firechat user
    chat.setUser(user.uid, user.displayName);
}

function addUser() {
    var email = document.getElementById("add-user-field").value;
    admin.auth().getUserByEmail(email).then(function(userRecord) {
        // See the UserRecord reference doc for the contents of userRecord.
        //console.log('Successfully fetched user data:', userRecord.toJSON());
        document.getElementById("added-users").innerHTML += userRecord.email + "<br/>";
    })
        .catch(function(error) {
            document.getElementById("add-user-field").innerHTML = "";
            console.log('Error fetching user data:', error);
        });
}

function createChat() {

}

function openCreateChat() {
    document.getElementById("create-chat-div").style.display = "block";
}

function closeCreateChat() {
    document.getElementById("create-chat-div").style.display = "none";
}