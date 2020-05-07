// these variables will be updated as the user uses the site, and are global for easy reference throughout this .js file
let chat;
let roomId;
let curUser;
let coordinates;
let today;

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

        // set the current date
        today = new Date();
        var dd = String(today.getDate()).padStart(2, '0');
        var mm = String(today.getMonth() + 1).padStart(2, '0'); //January is 0!
        var yyyy = today.getFullYear();

        today = yyyy + '-' + mm + '-' + dd;

        var dates = document.getElementsByClassName("date-class");

        // sets the value and min for all dates equal to today
        var i;
        for (i = 0; i < dates.length; i++) {
            dates[i].setAttribute("value", today);
            dates[i].setAttribute("min", today);
        }

    } else {
        // user is not logged in, redirect to login
        window.location.href = "/login";
    }
});

// removes the user from the chat room
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

// updates the user's profile with the chosen display name
function editProfile() {
    var name = document.getElementById("update-display-name-field").value;

    firebase.auth().currentUser.updateProfile({displayName: name}).then(function () {
        console.log("set displayName to: " + firebase.auth().currentUser.displayName);
        closePopup('settings-div');
        location.reload();
    });
}

// invites a user to the chatroom
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

// displays the user's budget after getting it from the Firebase database
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

// updates the users budget based on the input value and if they click "log" or "add"
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
        var address = document.getElementById("pmd-address").value;
        var loc = getLatAndLongFromAddress(address);

        // returns an ordered schedule of events which satisfy the query parameters
        $.ajax({
            url: "/planMyDay",
            type: "get",
            data: {"location": loc,
                "date": date,
                "maxDist": maxDist,
                "cuisineTypes": cuisines,
                "activityTypes": activities},
            async: false,
            success: function (data) {
                var recs = JSON.parse(data);
            }});
}

// returns search results for restaurants
function browseRestaurants() {
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

        var address = document.getElementById("restaurant-address").value;
        var loc = getLatAndLongFromAddress(address);

        // returns a list of restaurant options which match the query parameters
        $.ajax({
            url: "/browseRestaurants",
            type: "get",
            data: {"miles": miles,
                "location": loc,
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

// returns search results for activities
function browseActivities() {
        var miles_sel = document.getElementById("activities-miles-sel");
        var miles = miles_sel.options[miles_sel.selectedIndex].text;

        var activities = [];
        $("input:checkbox[name=browse-activity]:checked").each(function(){
            activities.push($(this).val());
        });

        var address = document.getElementById("activities-address").value;
        var loc = getLatAndLongFromAddress(address);

        // returns a list of activities options which match the query parameters
        $.ajax({
            url: "/browseActivities",
            type: "get",
            data: {"location": loc,
                "miles": miles,
                "activityTypes": activities.toString()},
            async: false,
            success: function (data) {
                var result = JSON.parse(data);
                var recs = Object.values(result)[1];
                document.getElementById("activities-results").innerHTML = recs;
            }});
}

// returns search results for lodging
function browseLodging() {
        var type_sel = document.getElementById("hotel-type-sel");
        var type = type_sel.options[type_sel.selectedIndex].text;
        var checkin = document.getElementById("check-in").value;
        var checkout = document.getElementById("check-out").value;
        var rating_sel = document.getElementById("hotel-rating-sel");
        var rating = rating_sel.options[rating_sel.selectedIndex].text;
        var num_rooms = document.getElementById("num-rooms").value;

        var address = document.getElementById("lodging-address").value;
        var loc = getLatAndLongFromAddress(address);

        // returns a list of lodging options which match the query parameters
        $.ajax({
            url: "/browseLodging",
            type: "get",
            data: {"location": loc,
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

// returns search results for flights
function browseFlights() {
    if ((coordinates === "Geolocation is not supported by this browser.") || (coordinates == null)) {
        window.alert("Please allow your browser to access your location.");
    } else {
        var departure_date = document.getElementById("departure-date").value;
        var depart = document.getElementById("depart").value;
        var destination = document.getElementById("destination").value;
        var adults = document.getElementById("num-adults").value;
        var children = document.getElementById("num-children").value;
        var seniors = document.getElementById("num-seniors").value;
        var numStops_sel = document.getElementById("max-stops-sel");
        var numStops = numStops_sel.options[numStops_sel.selectedIndex].text;
        var flightClass_sel = document.getElementById("flight-class-sel");
        var flightClass = flightClass_sel.options[flightClass_sel.selectedIndex].text;
        document.getElementById("flights-results").innerHTML = "Loading...";

        // returns a list of flight options which match the query parameters
        $.ajax({
            url: "/browseFlights",
            type: "get",
            data: {"location": coordinates,
                "departure_date": departure_date,
                "origin": depart,
                "destination": destination,
                "adults": adults,
                "children": children,
                "seniors": seniors,
                "numStops": numStops,
                "flightClass": flightClass},
            async: false,
            success: function (data) {
                var result = JSON.parse(data);
                document.getElementById("flights-results").innerHTML = "";
                if (result.length === 0) {
                    document.getElementById("flights-results").innerHTML = "No results found.";
                } else {
                    var i;
                    for (i = 0; i < result.length; i++) {
                        var html = [];
                        html.push(
                            "<p>",
                            "carrier: ",
                            result[i].carrier,
                            "<br>",
                            "price: ",
                            result[i].price,
                            "<br>",
                            "book your flight: ",
                            "<a href=",
                            result[i].booking_url,
                            ">here</a>",
                            "<hr>",
                            "</p>",
                        );
                        document.getElementById("flights-results").innerHTML += html.join("");
                    }
                }
            }});
    }
}

function updateCalendarLink() {
    const calendarLinkEl = $("#calendar-link");
    calendarLinkEl.attr("href", "/calendar/" + roomId + "/" + firebase.auth().currentUser.uid);
}

// gets the latitude and longitude for a given address
function getLatAndLongFromAddress(address) {
    var ret = "";
    $.ajax({url: "https://www.mapquestapi.com/geocoding/v1/address",
        type: "get",
        data: {"key": "b7pNYRJpdr0LJw2A7pupccBhMGHHa0fE", "location": address},
        async: false,
        success: function(data) {
        var latLng = data.results[0].locations[0].latLng;
        ret = String(latLng.lat) + " " + String(latLng.lng);
    }});
    return ret;
}