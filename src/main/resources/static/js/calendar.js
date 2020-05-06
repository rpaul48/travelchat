document.addEventListener('DOMContentLoaded', function() {


    // Calendar properties.
    const pathSplit = window.location.pathname.split("/");
    const userID = pathSplit[pathSplit.length - 1];
    const chatID = pathSplit[pathSplit.length - 2];

    let year = '2020';
    // convert to ISO. Create a function for this later!
    let startDate = year + '-' + '05-02';
    let endDate = year + '-' + '05-12';
    let minTime = startDate + 'T00:00';
    let maxTime = endDate + 'T23:59';


    // Elements of the add event modal.
    const modal = $("#add-event-modal");
    const modalExitButton = $("#modal-close")
    const eventTitleEl = $("#event-title");
    const eventLocationEl = $("#event-location");
    const eventStartEl = $("#event-start-time");
    const eventEndEl = $("#event-end-time");
    const eventDescriptionEl = $("#event-description");
    const eventPriceEl = $("#event-price");

    // Set up the modal.
    modalExitButton.click(function() {
        modal.hide();
    });
    eventStartEl.attr({
        "min" : minTime,
        "max" : maxTime
    });
    eventEndEl.attr({
        "min" : minTime,
        "max" : maxTime
    });
    function resetModal() {
        eventTitleEl.val("");
        eventStartEl.val(startDate + 'T12:30');
        eventEndEl.val(startDate + 'T13:30');
        eventPriceEl.val(0.00);
    }


    // Elements of the event info pop-up.
    const eventPopup = $("#event-popup");
    const eventPopupExitButton = $("#event-popup-close");

    // Set up the pop-up.
    eventPopupExitButton.click(function() {
        eventPopup.hide();
    });

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
            eventPopup.fadeIn();
            info.el.style.borderColor = 'red';
        }
    });

    calendar.render();
    $.get("/getCalendarEvents", { chatID: chatID }, function( data ) {

        for (const event of data) {
            const eventObject = generateEventObject(
                event.id, event.title, event.location,
                event.startTimeISO, event.endTimeISO,
                event.description, event.price);
            calendar.addEvent(eventObject);
        }

    }, 'json');


    /**
     * Set up "add event" functionality to the modal form.
     */
    const addEventForm = $("#add-event-form");
    addEventForm.submit(function() {

        const startTime = eventStartEl.val();
        const endTime = eventEndEl.val();


        // Check validity of input
        if (moment(endTime).isBefore(startTime) || moment(endTime).isSame(startTime)){
            alert("Error: End time must be greater than start time.");
        } else {
            const title = eventTitleEl.val();
            const location = eventLocationEl.val();
            const description = eventDescriptionEl.val();
            const price = eventPriceEl.val();
            const eventObject = generateEventObject(getUUID(), title, location, startTime, endTime, description, price);
            // Add event to database
            $.post("/postCalendarEvent", eventObject, null, 'json');
            // Update the budget of the adding user
            updateBudget(price, "log");
            // Load event visually
            calendar.addEvent(eventObject);
            modal.hide();
        }

        // to prevent from reloading the page, return false.
        return false;

    });

    function generateEventObject(id, title, location, startTimeISO, endTimeISO, description, price) {


        title = title ? title : "Untitled Event";

        let alldayBoolean = false;
        // if the difference in days is >= 2, display as an "all day" event to save calendar space
        if ((moment(endTimeISO).date() - moment(startTimeISO).date()) > 1) {
            alldayBoolean = true;
            // append start time to title to be more informative
            const startTime = moment(startTimeISO).format("hh:mm:ss a");
            title += " @ " + startTime.substring(0, 5) + startTime.substring(9)
        }

        return {
            id: id,
            title: title,
            location: location,
            start: startTimeISO,
            end: endTimeISO,
            description: description,
            price: price,
            editable: true,
            allDay: alldayBoolean,
            chatID: chatID
        };
    }

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

    function getUUID() {
        return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
            var r = Math.random() * 16 | 0, v = c === 'x' ? r : (r & 0x3 | 0x8);
            return v.toString(16);
        });

    }


});