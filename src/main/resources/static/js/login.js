
firebase.auth().onAuthStateChanged(function(user) {
    if (window.location.pathname === "/login") {
        if (user) {
            window.location.href = "/manage-chats"
        } else {
            document.getElementById("login-div").style.display = "block";
        }
    } else if (window.location.pathname === "/manage-chats") {
        if (!user) {
            window.location.href = "/login"
        } else {
            document.getElementById("chats-div").style.display = "block";
        }
    }
});

function login(){
    var userEmail = document.getElementById("email-field").value;
    var userPass = document.getElementById("password-field").value;

    firebase.auth().signInWithEmailAndPassword(userEmail, userPass).catch(function(error) {
        // Handle Errors here.
        var errorCode = error.code;
        var errorMessage = error.message;

        window.alert("Error: " + errorMessage);
    });
}

function createAccount(){
    var userEmail = document.getElementById("email-field").value;
    var userPass = document.getElementById("password-field").value;

    firebase.auth().createUserWithEmailAndPassword(userEmail, userPass).catch(function(error) {
        // Handle Errors here.
        var errorCode = error.code;
        var errorMessage = error.message;

        window.alert("Error: " + errorMessage);
    });
}

function logout() {
    firebase.auth().signOut();
    window.location.href = "/login"
}