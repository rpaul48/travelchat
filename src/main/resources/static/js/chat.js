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
                const path = window.location.pathname;
                roomId = path.substring(path.lastIndexOf('/') + 1);
                updateCalendarLink();
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

    // removes the room from the user's room list and the user from the room's user list
    $.ajax({
        url: "/removeUserFromRoom",
        type: "post",
        data: {"auth": curUser.uid, "roomId": roomId},
        async: false,
    });

    // redirects to manage chats page
    window.location.href = "/manage-chats";
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

// displays the div with the input id
function openPopup(id) {
    document.getElementById(id).style.display = "block";
}

// stops displaying the div with the input id
function closePopup(id) {
    document.getElementById(id).style.display = "none";
}

// returns the user's current coordinates
function getLocation() {
    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(showPosition);
    } else {
        return "Geolocation is not supported by this browser.";
    }
    function showPosition(position) {
        return position.coords.latitude + " " + position.coords.longitude;
    }
}

// returns search results for restaurants
function browseRestaurants() {
    var location = getLocation();
    if (location === "Geolocation is not supported by this browser.") {
        window.alert("Please allow your browser to access your location.");
    } else {
        var miles_sel = document.getElementById("restaurant-miles-sel");
        var miles = miles_sel.options[miles_sel.selectedIndex].text;
        var cuisine_sel = document.getElementById("cuisine-sel");
        var cuisine = cuisine_sel.options[cuisine_sel.selectedIndex].text;
        var price_sel = document.getElementById("restaurant-price-sel");
        var price = price_sel.options[price_sel.selectedIndex].text;
        var rating_sel = document.getElementById("restaurant-rating-sel");
        var rating = rating_sel.options[rating_sel.selectedIndex].text;
        var diet_sel = document.getElementById("diet-sel");
        var diet = diet_sel.options[diet_sel.selectedIndex].text;

        // returns a list of restaurant options which match the query parameters
        $.ajax({
            url: "/browseRestaurants",
            type: "get",
            data: {"miles": miles, "location": location, "cuisine": cuisine, "rating": rating, "price": price, "diet": diet},
            async: false,
            success: function (data) {
                var recs = JSON.parse(data);
            }});
    }
}

function displayBudget() {
    $.ajax({
        url: "/getUserBudgetInRoom",
        type: "post",
        data: {"auth": firebase.auth().currentUser.uid, "roomId": roomId},
        async: false,
        success: function (data) {
            var yourBudget = document.getElementById("your-budget");
            yourBudget.innerHTML = "<h3>Your budget: $" + data + "</h3>"
        }
    });
}

function updateBudget(logOrAdd) {
    var amount = document.getElementById("update-budget-field").value.replace(/[^0-9.]/g, '');
    if (amount != null && amount !== "") {
        $.ajax({
            url: "/updateUserBudgetInRoom",
            type: "post",
            data: {
                "auth": firebase.auth().currentUser.uid,
                "roomId": roomId,
                "type": logOrAdd,
                "amount": amount
            },
            async: false,
            success: function (data) {
                document.getElementById("update-budget-field").value = null;
                var yourBudget = document.getElementById("your-budget");
                yourBudget.innerHTML = "<h3>Your budget: $" + data + "</h3>"
            }
        });
    }
}

// returns search results for activities
function browseActivities() {
    var location = getLocation();
    if (location === "Geolocation is not supported by this browser.") {
        window.alert("Please allow your browser to access your location.");
    } else {
        var miles_sel = document.getElementById("activities-miles-sel");
        var miles = miles_sel.options[miles_sel.selectedIndex].text;

        // returns a list of activities options which match the query parameters
        $.ajax({
            url: "/browseActivities",
            type: "get",
            data: {"miles": miles, "location": location},
            async: false,
            success: function (data) {
                var recs = JSON.parse(data);
            }});
    }
}

// returns search results for lodging
function browseLodging() {
    var location = getLocation();
    if (location === "Geolocation is not supported by this browser.") {
        window.alert("Please allow your browser to access your location.");
    } else {
        var type_sel = document.getElementById("hotel-type-sel");
        var type = type_sel.options[type_sel.selectedIndex].text;
        var checkin_sel = document.getElementById("check-in");
        var checkin = checkin_sel.options[checkin_sel.selectedIndex].text;
        var checkout_sel = document.getElementById("check-out");
        var checkout = checkout_sel.options[checkout_sel.selectedIndex].text;
        var price_sel = document.getElementById("hotel-price-sel");
        var price = price_sel.options[price_sel.selectedIndex].text;
        var rating_sel = document.getElementById("hotel-rating-sel");
        var rating = rating_sel.options[rating_sel.selectedIndex].text;
        var num_rooms_sel = document.getElementById("num-rooms");
        var num_rooms = num_rooms_sel.options[num_rooms_sel.selectedIndex].text;

        // returns a list of lodging options which match the query parameters
        $.ajax({
            url: "/browseLodging",
            type: "get",
            data: {"location": location, "type": type, "check-in": checkin, "check-out": checkout,
            "price": price, "rating": rating, "num-rooms": num_rooms},
            async: false,
            success: function (data) {
                var recs = JSON.parse(data);
            }});
    }

}

function updateCalendarLink() {

    const calendarLinkEl = $("#calendar-link");
    calendarLinkEl.attr("href", "/calendar/" + roomId + "/" + firebase.auth().currentUser.uid);

}


