// these variables will be updated as the user uses the site, and are global for easy reference throughout this .js file
let chat;
let roomId;
let curUser;
let coordinates;
let today;

firebase.auth().onAuthStateChanged(function (user) {
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
            }
        });

        // set the current coordinates
        if (navigator.geolocation) {
            navigator.geolocation.getCurrentPosition(showPosition);
        }

        function showPosition(position) {
            coordinates = position.coords.latitude.toString().concat(" ", position.coords.longitude.toString());
        }

        // set the current date
        var jsToday = new Date();
        var dd = String(jsToday.getDate()).padStart(2, '0');
        var mm = String(jsToday.getMonth() + 1).padStart(2, '0'); //January is 0!
        var yyyy = jsToday.getFullYear();

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
        data: {
            "auth": curUser.uid,
            "roomId": roomId
        },
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
        data: {
            "auth": firebase.auth().currentUser.uid,
            "email": email,
            "roomId": roomId,
            "groupName": groupName
        },
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
        data: {
            "auth": firebase.auth().currentUser.uid,
            "roomId": roomId
        },
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

// if the user selects to use the curr location, remove text from address & lock the field
$("#pmd-cur-loc").change(function () {
    if ($(this).is(':checked')) {
        $("#pmd-address").val("").prop("disabled", true);
    } else {
        $("#pmd-address").prop("disabled", false);
    }
});

// closes the schedule view
function closeSchedule() {
    document.getElementById("plan-my-day-results").style.display = "none";
    document.getElementById("close-schedule-button").style.display = "none";
    document.getElementById("pmd-button").style.display = "block";
    document.getElementById("plan-my-day-form-container").style.display = "block";
}

// returns a schedule for the day
function planMyDay() {
    // checks whether location information has been inputted properly
    var loc;
    var curLoc = document.getElementById("pmd-cur-loc").checked;
    if (curLoc) {
        if (coordinates == null) {
            window.alert("Please allow your browser to access your location or input an address.");
            return;
        } else {
            loc = coordinates;
        }
    } else {
        var address = document.getElementById("pmd-address").value;
        if (!(address === "")) {
            loc = getLatAndLongFromAddress(address);
        } else {
            window.alert("Please input an address or use your current location.");
            return;
        }
    }

    // checks whether valid date has been inputted
    var date = document.getElementById("date-to-plan").value;
    if ((Date.parse(date) < Date.parse(today)) || date === "") {
        window.alert("Please enter a valid date.");
        return;
    }

    var maxDist_sel = document.getElementById("max-distance");
    var maxDist = maxDist_sel.options[maxDist_sel.selectedIndex].text;
    var distanceRank = document.getElementById("distance-rank").value;
    var priceRank = document.getElementById("price-rank").value;


    // retrieves the selected cuisine options
    var cuisines = [];
    $("input:checkbox[name=pmd-cuisine]:checked").each(function () {
        cuisines.push($(this).val());
    });
    if (cuisines.length === 0) {
        window.alert("Please select at least one cuisine option.");
        return;
    }

    // retrieves the selected activities options
    var activities = [];
    $("input:checkbox[name=pmd-activity]:checked").each(function () {
        activities.push($(this).val());
    });
    if (activities.length === 0) {
        window.alert("Please select at least one activities option.");
        return;
    }

    document.getElementById("plan-my-day-form-container").style.display = "none";
    document.getElementById("pmd-button").style.display = "none";

    // returns an ordered schedule of events which satisfy the query parameters
    $.ajax({
        url: "/planMyDay",
        type: "get",
        data: {
            "location": loc,
            "date": date,
            "maxDist": maxDist,
            "distanceRank": distanceRank,
            "priceRank": priceRank,
            "cuisineTypes": cuisines.toString(),
            "activityTypes": activities.toString()
        },
        async: false,
        success: function (data) {
            var result = JSON.parse(data);
            console.log(result);

            document.getElementById("plan-my-day-results").innerHTML = "";
            if (result.length === 0) {
                document.getElementById("plan-my-day-results").innerHTML =
                    "Could not find sufficient items; please loosen input constraints";
            } else {
                var i;
                for (i = 0; i < result.length; i++) {
                    var html = [];
                    html.push(
                        "<p>",
                        (i + 1) + ". ",
                        result[i].name,
                        "<br>",
                        "location: ",
                        result[i].location_string);
                    var photo = result[i].photo_url;
                    if (photo === "") {
                        html.push(
                            "<br>",
                            "<hr>",
                            "</p>");
                    } else {
                        html.push(
                            "<br>",
                            "<img src=\"" + result[i].photo_url + "\" width = \"300\" height=\"200\"><br>",
                            "<hr>",
                            "</p>");
                    }
                    document.getElementById("plan-my-day-results").innerHTML += html.join("");
                }
            }
            document.getElementById("plan-my-day-results").style.display = "block";
            document.getElementById("close-schedule-button").style.display = "block";
        }
    });
}

// if the user selects to use the curr location, remove text from address & lock the field
$("#restaurant-cur-loc").change(function () {
    if ($(this).is(':checked')) {
        $("#restaurant-address").val("").prop("disabled", true);
    } else {
        $("#restaurant-address").prop("disabled", false);
    }
});

// returns search results for restaurants
function browseRestaurants() {
    var loc;
    var curLoc = document.getElementById("restaurant-cur-loc").checked;

    if (curLoc) {
        if (coordinates == null) {
            window.alert("Please allow your browser to access your location or input an address.");
            return;
        } else {
            loc = coordinates;
        }
    } else {
        var address = document.getElementById("restaurant-address").value;
        if (!(address === "")) {
            loc = getLatAndLongFromAddress(address);
        } else {
            window.alert("Please input an address or use your current location.");
            return;
        }
    }

    var miles_sel = document.getElementById("restaurant-miles-sel");
    var miles = miles_sel.options[miles_sel.selectedIndex].text;
    var price_sel = document.getElementById("restaurant-price-sel");
    var price = price_sel.options[price_sel.selectedIndex].text;
    var rating_sel = document.getElementById("restaurant-rating-sel");
    var rating = rating_sel.options[rating_sel.selectedIndex].text;
    var diet_sel = document.getElementById("diet-sel");
    var diet = diet_sel.options[diet_sel.selectedIndex].text;

    var cuisines = [];
    $("input:checkbox[name=browse-cuisine]:checked").each(function () {
        cuisines.push($(this).val());
    });
    if (cuisines.length === 0) {
        window.alert("Please select at least one cuisine option.");
        return;
    }

    // returns a list of restaurant options which match the query parameters
    $.ajax({
        url: "/browseRestaurants",
        type: "get",
        data: {
            "miles": miles,
            "location": loc,
            "cuisines": cuisines.toString(),
            "rating": rating,
            "price": price,
            "diet": diet
        },
        async: false,
        success: function (data) {
            var result = JSON.parse(data);
            var recs = Object.values(result)[0];
            document.getElementById("restaurants-results").innerHTML = recs;
        }
    });
}

// if the user selects to use the curr location, remove text from address & lock the field
$("#activities-cur-loc").change(function () {
    if ($(this).is(':checked')) {
        $("#activities-address").val("").prop("disabled", true);
    } else {
        $("#activities-address").prop("disabled", false);
    }
});

// returns search results for activities
function browseActivities() {
    var loc;
    var curLoc = document.getElementById("activities-cur-loc").checked;

    if (curLoc) {
        if (coordinates == null) {
            window.alert("Please allow your browser to access your location or input an address.");
            return;
        } else {
            loc = coordinates;
        }
    } else {
        var address = document.getElementById("activities-address").value;
        if (!(address === "")) {
            loc = getLatAndLongFromAddress(address);
        } else {
            window.alert("Please input an address or use your current location.");
            return;
        }
    }

    var miles_sel = document.getElementById("activities-miles-sel");
    var miles = miles_sel.options[miles_sel.selectedIndex].text;

    var activities = [];
    $("input:checkbox[name=browse-activity]:checked").each(function () {
        activities.push($(this).val());
    });
    if (activities.length === 0) {
        window.alert("Please select at least one activities option.");
        return;
    }

    // returns a list of activities options which match the query parameters
    $.ajax({
        url: "/browseActivities",
        type: "get",
        data: {
            "location": loc,
            "miles": miles,
            "activityTypes": activities.toString()
        },
        async: false,
        success: function (data) {
            var result = JSON.parse(data);
            var recs = Object.values(result)[1];
            document.getElementById("activities-results").innerHTML = recs;
        }
    });
}

// if the user selects to use the curr location, remove text from address & lock the field
$("#lodging-cur-loc").change(function () {
    if ($(this).is(':checked')) {
        $("#lodging-address").val("").prop("disabled", true);
    } else {
        $("#lodging-address").prop("disabled", false);
    }
});

// returns search results for lodging
function browseLodging() {
    var loc;
    var curLoc = document.getElementById("lodging-cur-loc").checked;

    if (curLoc) {
        if (coordinates == null) {
            window.alert("Please allow your browser to access your location or input an address.");
            return;
        } else {
            loc = coordinates;
        }
    } else {
        var address = document.getElementById("lodging-address").value;
        if (!(address === "")) {
            loc = getLatAndLongFromAddress(address);
        } else {
            window.alert("Please input an address or use your current location.");
            return;
        }
    }

    var type_sel = document.getElementById("hotel-type-sel");
    var type = type_sel.options[type_sel.selectedIndex].text;

    var checkin = document.getElementById("check-in").value;
    if ((Date.parse(checkin) < Date.parse(today)) || checkin === "") {
        window.alert("Please enter a valid check-in date.");
        return;
    }
    var checkout = document.getElementById("check-out").value;
    if ((Date.parse(checkout) <= (Date.parse(checkin))) || (checkout === "")) {
        window.alert("Please enter a valid check-out date later than your check-in date.");
        return;
    }

    var rating_sel = document.getElementById("hotel-rating-sel");
    var rating = rating_sel.options[rating_sel.selectedIndex].text;
    var num_rooms = document.getElementById("num-rooms").value;
    if (num_rooms <= 0) {
        window.alert("Please select a positive number of rooms.");
        return;
    } else if (!(num_rooms == parseInt(num_rooms, 10))) {
        window.alert("Please select a whole number of rooms.");
        return;
    }

    // returns a list of lodging options which match the query parameters
    $.ajax({
        url: "/browseLodging",
        type: "get",
        data: {
            "location": loc,
            "type": type,
            "check-in": checkin,
            "check-out": checkout,
            "rating": rating,
            "num-rooms": parseInt(num_rooms, 10)
        },
        async: false,
        success: function (data) {
            var result = JSON.parse(data);
            var recs = Object.values(result)[1];
            document.getElementById("lodging-results").innerHTML = recs;
        }
    });
}

// returns search results for flights
function browseFlights() {
    var departure_date = document.getElementById("departure-date").value;
    if ((Date.parse(departure_date) < Date.parse(today)) || departure_date === "") {
        window.alert("Please enter a valid departure date.");
        return;
    }
    var depart = document.getElementById("depart").value;
    if (!(/^[a-zA-Z]+$/.test(depart))) {
        window.alert("Please enter only letters in the departure airport code.");
        return;
    }
    var destination = document.getElementById("destination").value;
    if (!(/^[a-zA-Z]+$/.test(destination))) {
        window.alert("Please enter only letters in the destination airport code.");
        return;
    }
    var adults = document.getElementById("num-adults").value;
    if (adults < 0) {
        window.alert("Please enter a non-negative number of adult tickets.");
        return;
    } else if (!(adults == parseInt(adults, 10))) {
        window.alert("Please select a whole number of adult tickets.");
        return;
    }
    var children = document.getElementById("num-children").value;
    if (children < 0) {
        window.alert("Please enter a non-negative number of child tickets.");
        return;
    } else if (!(children == parseInt(children, 10))) {
        window.alert("Please select a whole number of child tickets.");
        return;
    }
    var seniors = document.getElementById("num-seniors").value;
    if (seniors < 0) {
        window.alert("Please enter a non-negative number of senior tickets.");
        return;
    } else if (!(adults == parseInt(adults, 10))) {
        window.alert("Please select a whole number of senior tickets.");
        return;
    }
    if ((adults + children + seniors) === 0) {
        window.alert("You must order at least one ticket.");
        return;
    }
    var numStops_sel = document.getElementById("max-stops-sel");
    var numStops = numStops_sel.options[numStops_sel.selectedIndex].text;
    var flightClass_sel = document.getElementById("flight-class-sel");
    var flightClass = flightClass_sel.options[flightClass_sel.selectedIndex].text;
    document.getElementById("flights-results").innerHTML = "Loading...";

    // returns a list of flight options which match the query parameters
    $.ajax({
        url: "/browseFlights",
        type: "get",
        data: {
            "departure_date": departure_date,
            "origin": depart.toUpperCase(),
            "destination": destination.toUpperCase(),
            "adults": adults,
            "children": children,
            "seniors": seniors,
            "numStops": numStops,
            "flightClass": flightClass
        },
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
                        "</p>"
                    );
                    document.getElementById("flights-results").innerHTML += html.join("");
                }
            }
        }
    });
}

function updateCalendarLink() {
    const calendarLinkEl = $("#calendar-link");
    calendarLinkEl.attr("href", "/calendar/" + roomId + "/" + firebase.auth().currentUser.uid);
}

// gets the latitude and longitude for a given address
function getLatAndLongFromAddress(address) {
    var ret = "";
    $.ajax({
        url: "https://www.mapquestapi.com/geocoding/v1/address",
        type: "get",
        data: {"key": "b7pNYRJpdr0LJw2A7pupccBhMGHHa0fE", "location": address},
        async: false,
        success: function (data) {
            var latLng = data.results[0].locations[0].latLng;
            ret = String(latLng.lat) + " " + String(latLng.lng);
        }
    });
    return ret;
}

// converts UTC date to local date
function convertUTCDateToLocalDate(date) {
    var newDate = new Date(date.getTime() + date.getTimezoneOffset() * 60 * 1000);

    var offset = date.getTimezoneOffset() / 60;
    var hours = date.getHours();

    newDate.setHours(hours - offset);

    return newDate;
}

// converts a date into a yyyy-mm-dd string
function formatDate(date) {
    var d = new Date(date),
        month = '' + (d.getMonth() + 1),
        day = '' + d.getDate(),
        year = d.getFullYear();

    if (month.length < 2)
        month = '0' + month;
    if (day.length < 2)
        day = '0' + day;

    return [year, month, day].join('-');
}


