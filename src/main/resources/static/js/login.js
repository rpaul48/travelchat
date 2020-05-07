// this piece of code regulates what part of the site the user can visit based on if they are authenticated
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
    } else if (window.location.pathname.startsWith("/chat/")) {
        if (!user) {
            window.location.href = "/login"
        } else {
            document.getElementById("chat-div").style.display = "block";
        }
    }
});

// logs user in with their inputted email and password combination
function login() {
    var userEmail = document.getElementById("email-field").value;
    var userPass = document.getElementById("password-field").value;

    firebase.auth().signInWithEmailAndPassword(userEmail, userPass).catch(function(error) {
        // Handle Errors here.
        var errorCode = error.code;
        var errorMessage = error.message;

        window.alert("Error: " + errorMessage);
    });
}

function openCreateAccount() {
    $("#create-account-div").fadeIn();
}

function closeCreateAccount() {
    document.getElementById("create-account-div").style.display = "none";
}

// creates a new account with the given display name, email, and password
function createAccount() {
    var name = document.getElementById("display-name-field").value;
    var userEmail = document.getElementById("create-email-field").value;
    var userPass = document.getElementById("create-password-field").value;

    firebase.auth().createUserWithEmailAndPassword(userEmail, userPass).catch(function(error) {
        // Handle Errors here.
        var errorCode = error.code;
        var errorMessage = error.message;

        window.alert("Error: " + errorMessage);
    });

    firebase.auth().currentUser.updateProfile({displayName: name}).then(function () {
        console.log("set displayName to: " + firebase.auth().currentUser.displayName);
        closeCreateAccount();
    });
}

// logs the user out, returns them to login page
function logout() {
    firebase.auth().signOut().then(r =>
        window.location.href = "/login")
        .catch(function(error) {
            window.location.href = "/login"
    });
}