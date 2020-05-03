document.addEventListener('DOMContentLoaded', function() {


    //TODO: Take into account timezone. Calendar should be able to switch between time-zones.

    const chatID = 'chat_id1';

    let year = '2020';
    // convert to ISO. Create a function for this later!
    let startDate = year + '-' + '05-02';
    let endDate = year + '-' + '05-12';
    let minTime = startDate + 'T00:00';
    let maxTime = endDate + 'T23:59';


    /**
     * Set up the addEvent modal
     */

    const modal = $("#add-event-modal");
    const exitButton = $(".close").first();
    // When the user clicks on (x), close the modal
    exitButton.click(function() {
        modal.hide();
    });

    const eventStartEl = $("#event-start-time");
    eventStartEl.attr({
        "min" : minTime,
        "max" : maxTime
    });

    const eventEndEl = $("#event-end-time");
    eventEndEl.attr({
        "min" : minTime,
        "max" : maxTime
    });

    function resetModal() {
        $("#event-title").val("");
        eventStartEl.val(startDate + 'T12:30');
        eventEndEl.val(startDate + 'T13:30');
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
                    window.history.back();
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
        defaultDate: startDate
    });

    calendar.render();
    $.get("/getCalendarEvents", { chatID: chatID }, function( data ) {

        console.log("AYE");
        for (const event of data) {
            console.log(event);
            const eventObject = generateEventObject(event.title, event.startTimeISO, event.endTimeISO);
            calendar.addEvent(eventObject);
        }

    }, 'json');


    /**
     * Set up "add event" functionality to the modal form.
     */
    const addEventForm = $("#add-event-form");
    addEventForm.submit(function() {

        const startTime = $("#event-start-time").val();
        const endTime = $("#event-end-time").val();

        // Check validity of input
        if (moment(endTime).isBefore(startTime) || moment(endTime).isSame(startTime)){
            alert("Error: End time must be greater than start time.");
        } else {
            const title = $("#event-title").val();
            const eventObject = generateEventObject(title, startTime, endTime);
            // Add event to database
            $.post("/postCalendarEvent", eventObject, null, 'json');
            // Load visually
            calendar.addEvent(eventObject);
            modal.hide();
        }

        // to prevent from reloading the page, return false.
        return false;

    });

    function generateEventObject(title, startTimeISO, endTimeISO) {

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
            title: title,
            start: startTimeISO,
            end: endTimeISO,
            editable: true,
            allDay: alldayBoolean,
            chatID: chatID
        };
    }


});