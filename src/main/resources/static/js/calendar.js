document.addEventListener('DOMContentLoaded', function() {


    // Calendar properties.
    const pathSplit = window.location.pathname.split("/");
    const userID = pathSplit[pathSplit.length - 1];
    const chatID = pathSplit[pathSplit.length - 2];
    const currentTimeZone = "EDT";
    const tripTimeZone = "PST";

    let year = '2020';
    // convert to ISO. Create a function for this later!
    let startDate = year + '-' + '05-02';
    let endDate = year + '-' + '05-12';
    let minTime = startDate + 'T00:00';
    let maxTime = endDate + 'T23:59';


    // Elements of the add event modal.
    const modal = $("#add-event-modal");
    const modalExitButton = $("#modal-close");
    const eventTitleInputEl = $("#event-title-input");
    const eventLocationInputEl = $("#event-location-input");
    const eventStartInputEl = $("#event-start-time-input");
    const eventEndInputEl = $("#event-end-time-input");
    const eventPriceInputEl = $("#event-price-input");
    const eventDescriptionInputEl = $("#event-description-input");

    // Set up the modal.
    modalExitButton.click(function() {
        modal.hide();
    });
    eventStartInputEl.attr({
        "min" : minTime,
        "max" : maxTime
    });
    eventEndInputEl.attr({
        "min" : minTime,
        "max" : maxTime
    });
    function resetModal() {
        eventTitleInputEl.val("");
        eventStartInputEl.val(startDate + 'T12:30');
        eventEndInputEl.val(startDate + 'T13:30');
        eventPriceInputEl.val(0.00);
    }


    // Elements of the event info pop-up.
    const eventPopup = $("#event-popup");
    const eventPopupExitButton = $("#event-popup-close");
    const eventTitleEl = $("#event-title");
    const eventTimeEl = $("#event-time");
    const eventLocationEl = $("#event-location");
    const eventPriceEl = $("#event-price");
    const eventDescriptionEl = $("#event-description");
    const joinEventButtonEl = $("#join-event");
    const removeEventButtonEl = $("#remove-event");
    const leaveEventButtonEl = $("#leave-event");
    let clickedEventEl;

    // Set up the pop-up.
    eventPopupExitButton.click(function() {
        clickedEventEl.style.borderColor = "white";
        eventPopup.hide();
    });

    function populateEventPopup(event) {
        eventTitleEl.text(event.title);
        eventTimeEl.text(getNeatTimeDetails(event.start, event.end));
        eventLocationEl.text(event.extendedProps.location);
        eventPriceEl.text("$ " + event.extendedProps.price);
        eventDescriptionEl.text(event.extendedProps.description);


        //
        // if (userID === event.extendedProps.ownerID) {
        //     removeEventButtonEl.show();
        //     joinEventButtonEl.hide();
        //     leaveEventButtonEl.hide();
        //
        // } else {
        //
        // }
        addRemoveSelfToEvent(event.id, "add");
        // console.log(checkIfUserInEvent("d9f9a9db-f610-4a67-893b-166adb4e5438"));
        // console.log(userID === event.extendedProps.ownerID);

    }

    function getNeatTimeDetails(startISO, endISO) {
        return moment(startISO).format("dddd, MMMM Do, h:mma") + "- " + moment(endISO).format("h:mma")
    }

    /**
     * Set up calendar
     */

    const calendarEl = document.getElementById('calendar');
    const calendar = new FullCalendar.Calendar(calendarEl, {
        plugins: [ 'timeGrid', 'interaction' ],
        defaultView: 'timeGridWeek',
        customButtons: {
            addEvent: {
                text: 'Add Event',
                click: function() {
                    // When the user clicks the button, open the modal
                    resetModal();
                    modal.fadeIn();
                }
            },
            goBack: {
                text: 'Go Back',
                click: function() {
                    window.location.href = '/chat/' + chatID;
                }
            }
        },
        header: {
            left:   'goBack,title',
            center: 'addEvent',
            right:  'prev,next'
        },
        validRange: {
            start: startDate,
            end: endDate
        },
        defaultDate: startDate,
        eventClick: function(info) {
            clickedEventEl = info.el;
            // console.log(info.event);
            clickedEventEl.style.borderColor = 'red';
            populateEventPopup(info.event);
            eventPopup.fadeIn();
        }
    });

    calendar.render();

    $.get("/getCalendarEvents", { chatID: chatID }, function( data ) {

        for (const event of data) {
            calendar.addEvent(event);
        }

    }, 'json');


    /**
     * Set up "add event" functionality to the modal form.
     */
    const addEventForm = $("#add-event-form");
    addEventForm.submit(function() {

        const startTime = eventStartInputEl.val();
        const endTime = eventEndInputEl.val();


        // Check validity of input
        if (moment(endTime).isBefore(startTime) || moment(endTime).isSame(startTime)){
            alert("Error: End time must be greater than start time.");
        } else {
            const title = eventTitleInputEl.val();
            const location = eventLocationInputEl.val();
            const description = eventDescriptionInputEl.val();
            const price = eventPriceInputEl.val();
            // Add event to database
            $.post("/postCalendarEvent",
                {
                    chatID: chatID,
                    ownerID: userID,
                    title: title ? title : "Untitled Event",
                    start: startTime,
                    end: endTime,
                    location: location,
                    price: price,
                    description: description
                },

                function(event) {
                    // Update the budget of the adding user
                    updateBudget(price, "log");
                    // Load event visually
                    calendar.addEvent(event);
                },
                'json');

            modal.hide();
        }

        // to prevent from reloading the page, return false.
        return false;

    });

    function updateBudget(price, type) {

        $.ajax({
            url: "/updateUserBudgetInRoom",
            type: "post",
            data: {
                "auth": userID,
                "roomId": chatID,
                "type": type,
                "amount": price
            },
            async: false,
            success: function () {
            }
        });
    }

    function addRemoveSelfToEvent(eventID, addRemove) {

        $.post("/addRemoveUserFromEventHandler",
            {
                chatID: chatID,
                userID: userID,
                eventID: eventID,
                addRemove: addRemove
            },

            function() {
                reloadEvent(eventID);
            },
            'text');

    }

    function reloadEvent(eventID) {

        $.get("/getSingleCalendarEvent", { chatID: chatID, eventID: eventID}, function( data ) {
            calendar.getEventById(eventID).remove();
            calendar.addEvent(data);
            // console.log(calendar.getEventById(eventID));
        }, 'json');

    }


    // function checkIfUserInEvent(eventID) {
    //
    //     $.post("/getUsersFromEventHandler",
    //         {
    //             chatID: chatID,
    //             eventID: eventID
    //         },
    //
    //         function(data) {
    //             if (userID in data) {
    //                 console.log("AY");
    //                 return true;
    //             }
    //         },
    //         'json');
    //
    //
    //
    //     return false;
    //
    // }


    //
    // function generateEventObject(id, title, startTimeISO, endTimeISO, location, price, description) {
    //
    //     title = title ? title : "Untitled Event";
    //
    //     let alldayBoolean = false;
    //     // if the difference in days is >= 2, display as an "all day" event to save calendar space
    //     if ((moment(endTimeISO).date() - moment(startTimeISO).date()) > 1) {
    //         alldayBoolean = true;
    //         // append start time to title to be more informative
    //         const startTime = moment(startTimeISO).format("hh:mm:ss a");
    //         title += " @ " + startTime.substring(0, 5) + startTime.substring(9)
    //     }
    //
    //     return {
    //         id: id,
    //         chatID: chatID,
    //         ownerID: userID,
    //         title: title,
    //         start: startTimeISO,
    //         end: endTimeISO,
    //         location: location,
    //         price: price,
    //         description: description,
    //         editable: false,
    //         allDay: alldayBoolean
    //     };
    // }


});