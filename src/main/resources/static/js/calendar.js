document.addEventListener('DOMContentLoaded', function() {


    // Calendar properties.
    const pathSplit = window.location.pathname.split("/");
    const userID = pathSplit[pathSplit.length - 3];
    const chatID = pathSplit[pathSplit.length - 4];
    const currentTimeZone = "EDT";
    const tripTimeZone = "PST";

    // convert to ISO. Create a function for this later!
    let startDate = pathSplit[pathSplit.length - 2];
    let endDate = pathSplit[pathSplit.length - 1];
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
       closeEventPopup();
    });

    function closeEventPopup() {
        clickedEventEl.style.borderColor = "white";
        eventPopup.hide();
    }



    function populateEventPopup(event) {

        eventTitleEl.text(event.title);
        eventTimeEl.text(getNeatTimeDetails(event.start, event.end));
        const eventExtendedProps = event.extendedProps;
        eventLocationEl.text(eventExtendedProps.location);
        eventPriceEl.text("$ " + eventExtendedProps.price);
        eventDescriptionEl.text(eventExtendedProps.description);


        removeEventButtonEl.click(function () {
            removeEventFromDatabase(event.id);
            event.remove();
            eventPopup.hide();
        });

        joinEventButtonEl.click(function () {
            addRemoveSelfToEvent(event.id, "add");
            eventPopup.hide();
        });

        leaveEventButtonEl.click(function () {
            addRemoveSelfToEvent(event.id, "remove");
            eventPopup.hide();
        });


        if (userID === eventExtendedProps.ownerID) {
            removeEventButtonEl.show();
            joinEventButtonEl.hide();
            leaveEventButtonEl.hide();

        } else if (eventExtendedProps.participants && userID in eventExtendedProps.participants) {
            removeEventButtonEl.hide();
            joinEventButtonEl.hide();
            leaveEventButtonEl.show();
        } else {
            removeEventButtonEl.hide();
            joinEventButtonEl.show();
            leaveEventButtonEl.hide();


        }

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
        },
        eventRender: function(info) {
            if (!isUserInEvent(info.event)) {
                info.el.style.background = "grey";
                info.el.style.border = "grey";
            }

            // displayUserBudget();
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
        const price = eventPriceInputEl.val();

        // Check validity of input
        if (moment(endTime).isBefore(startTime) || moment(endTime).isSame(startTime)){
            alert("Error: End time must be greater than start time.");
        } else if (price === "") {
            alert("Error: Price must be an number (i.e. 20.50");
        } else {
            const title = eventTitleInputEl.val();
            const location = eventLocationInputEl.val();
            const description = eventDescriptionInputEl.val();
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

        $.post("/addRemoveUserFromEvent",
            {
                chatID: chatID,
                userID: userID,
                eventID: eventID,
                addRemove: addRemove
            },

            function() {
                const eventPrice = calendar.getEventById(eventID).extendedProps.price;
                if (addRemove === "add") {
                    updateBudget(eventPrice, 'log');
                    console.log("subtracting " + eventPrice);
                } else if (addRemove === "remove") {
                    updateBudget(eventPrice, 'add');
                    console.log("adding " + eventPrice);
                } else {
                    alert("addRemove must be \"add\" or \"remove\"");
                }
                reloadEvent(eventID);
            },
            'text');

    }

    function reloadEvent(eventID) {

        $.get("/getSingleCalendarEvent", { chatID: chatID, eventID: eventID}, function( data ) {
            calendar.getEventById(eventID).remove()
            calendar.addEvent(data);
        }, 'json');
    }

    function removeEventFromDatabase(eventID) {
        $.post("/removeCalendarEvent",
            {
                chatID: chatID,
                eventID: eventID
            },

            function() {
            },
            'text');

    }

    function displayUserBudget() {
        $.post( "/getUserBudgetInRoom",
            {
                roomId: chatID,
                auth: userID
            },

            function(budget) {
                const budgetString = "<h3 id='budget'> Budget: $" + budget + "</h3>";

                if ($("#budget").length === 0) {
                    $('.fc-button-group').before(budgetString);
                } else {
                    $("#budget").replaceWith(budgetString);
                }

            },
            'text');
    }


    function isUserInEvent(eventObject) {

        const props = eventObject.extendedProps;
        if (userID === props.ownerID) {
            return true;
        }

        return !!(props.participants && userID in props.participants);


    }



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