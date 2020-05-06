let chat;
let roomId;
let curUser;
let coordinates;

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

        // set the current coordinates
        if (navigator.geolocation) {
            navigator.geolocation.getCurrentPosition(showPosition);
        } else {
            coordinates = "Geolocation is not supported by this browser.";
        }
        function showPosition(position) {
            coordinates = position.coords.latitude.toString().concat(" ", position.coords.longitude.toString());
        }

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
        data: {"auth": curUser.uid,
            "roomId": roomId},
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
        data: {"auth": firebase.auth().currentUser.uid,
            "email": email,
            "roomId": roomId,
            "groupName": groupName},
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

function displayBudget() {
    $.ajax({
        url: "/getUserBudgetInRoom",
        type: "post",
        data: {"auth": firebase.auth().currentUser.uid,
            "roomId": roomId},
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

// returns a schedule for the day
function planMyDay() {
    if (coordinates === "Geolocation is not supported by this browser.") {
        window.alert("Please allow your browser to access your location.");
    } else {
        var date = document.getElementById("date-to-plan").value;
        var maxDist = document.getElementById("max-distance").value;
        var cuisines = [];
        $("input:checkbox[name=pmd-cuisine]:checked").each(function(){
            cuisines.push($(this).val());
        });
        var activities = [];
        $("input:checkbox[name=pmd-activity]:checked").each(function(){
            activities.push($(this).val());
        });

        console.log(date);

        // returns an ordered schedule of events which satisfy the query parameters
        $.ajax({
            url: "/planMyDay",
            type: "get",
            data: {"location": coordinates,
                "date": date,
                "maxDist": maxDist,
                "cuisineTypes": cuisines,
                "activityTypes": activities},
            async: false,
            success: function (data) {
                var recs = JSON.parse(data);
            }});
    }
}

// returns search results for restaurants
function browseRestaurants() {
    if ((coordinates === "Geolocation is not supported by this browser.") || (coordinates == null)) {
        window.alert("Please allow your browser to access your location.");
    } else {
        var miles_sel = document.getElementById("restaurant-miles-sel");
        var miles = miles_sel.options[miles_sel.selectedIndex].text;
        var price_sel = document.getElementById("restaurant-price-sel");
        var price = price_sel.options[price_sel.selectedIndex].text;
        var rating_sel = document.getElementById("restaurant-rating-sel");
        var rating = rating_sel.options[rating_sel.selectedIndex].text;
        var diet_sel = document.getElementById("diet-sel");
        var diet = diet_sel.options[diet_sel.selectedIndex].text;

        var cuisines = [];
        $("input:checkbox[name=browse-cuisine]:checked").each(function(){
            cuisines.push($(this).val());
        });

        // returns a list of restaurant options which match the query parameters
        $.ajax({
            url: "/browseRestaurants",
            type: "get",
            data: {"miles": miles,
                "location": coordinates,
                "cuisines": cuisines.toString(),
                "rating": rating,
                "price": price,
                "diet": diet},
            async: false,
            success: function (data) {
                var result = JSON.parse(data);
                var recs = Object.values(result)[0];
                document.getElementById("restaurants-results").innerHTML = recs;
            }});
    }
}

// returns search results for activities
function browseActivities() {
    if ((coordinates === "Geolocation is not supported by this browser.") || (coordinates == null)) {
        window.alert("Please allow your browser to access your location.");
    } else {
        var miles_sel = document.getElementById("activities-miles-sel");
        var miles = miles_sel.options[miles_sel.selectedIndex].text;

        var activities = [];
        $("input:checkbox[name=browse-activity]:checked").each(function(){
            activities.push($(this).val());
        });

        // returns a list of activities options which match the query parameters
        $.ajax({
            url: "/browseActivities",
            type: "get",
            data: {"location": coordinates,
                "miles": miles,
                "activityTypes": activities.toString()},
            async: false,
            success: function (data) {
                var result = JSON.parse(data);
                var recs = Object.values(result)[1];
                document.getElementById("activities-results").innerHTML = recs;
            }});
    }
}

// returns search results for lodging
function browseLodging() {
    if ((coordinates === "Geolocation is not supported by this browser.") || (coordinates == null)) {
        window.alert("Please allow your browser to access your location.");
    } else {
        var type_sel = document.getElementById("hotel-type-sel");
        var type = type_sel.options[type_sel.selectedIndex].text;
        var checkin = document.getElementById("check-in").value;
        var checkout = document.getElementById("check-out").value;
        var rating_sel = document.getElementById("hotel-rating-sel");
        var rating = rating_sel.options[rating_sel.selectedIndex].text;
        var num_rooms = document.getElementById("num-rooms").value;

        console.log(coordinates);
        console.log(type);
        console.log(checkin);
        console.log(checkout);
        console.log(rating);
        console.log(num_rooms);

        // returns a list of lodging options which match the query parameters
        $.ajax({
            url: "/browseLodging",
            type: "get",
            data: {"location": coordinates,
                "type": type,
                "check-in": checkin,
                "check-out": checkout,
                "rating": rating,
                "num-rooms": num_rooms},
            async: false,
            success: function (data) {
                var result = JSON.parse(data);
                var recs = Object.values(result)[1];
                document.getElementById("lodging-results").innerHTML = recs;
            }});
    }
}

// returns search results for flights
function browseFlights() {
    if ((coordinates === "Geolocation is not supported by this browser.") || (coordinates == null)) {
        window.alert("Please allow your browser to access your location.");
    } else {
        var depart = document.getElementById("depart").value;
        var destination = document.getElementById("destination").value;
        var adults = document.getElementById("num-adults").value;
        var children = document.getElementById("num-children").value;
        var seniors = document.getElementById("num-seniors").value;
        var numStops_sel = document.getElementById("max-stops-sel");
        var numStops = numStops_sel.options[numStops_sel.selectedIndex].text;
        var flightClass_sel = document.getElementById("flight-class-sel");
        var flightClass = flightClass_sel.options[flightClass_sel.selectedIndex].text;

        // returns a list of flight options which match the query parameters
        $.ajax({
            url: "/browseFlights",
            type: "get",
            data: {"location": coordinates,
                "depart": depart,
                "destination": destination,
                "adults": adults,
                "children": children,
                "seniors": seniors,
                "numStops": numStops,
                "flightClass": flightClass},
            async: false,
            success: function (data) {
                var result = JSON.parse(data);
                var recs = Object.values(result)[1];
                document.getElementById("flights-results").innerHTML = recs;
            }});
    }
}

function updateCalendarLink() {
    const calendarLinkEl = $("#calendar-link");
    calendarLinkEl.attr("href", "/calendar/" + roomId + "/" + firebase.auth().currentUser.uid);
}