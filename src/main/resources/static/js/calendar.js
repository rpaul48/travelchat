document.addEventListener('DOMContentLoaded', function() {


    let year = '2020';
    // convert to ISO. Create a function for this later!
    let startDate = year + '-' + '05-02';
    let endDate = year + '-' + '05-12';
    let minTime = startDate + 'T00:00';
    let maxTime = endDate + 'T23:59';


    /**
     * Set up the addEvent modal
     */

    const modal = document.getElementById('add-event-modal');
    const exitButton = document.getElementsByClassName('close')[0];
    // When the user clicks on (x), close the modal
    exitButton.onclick = function() {
        modal.style.display = "none";
    };

    const eventStartEl = document.getElementById('event-start-time');
    eventStartEl.min = minTime;
    eventStartEl.max = maxTime;

    const eventEndEl = document.getElementById('event-end-time');
    eventEndEl.min = minTime;
    eventEndEl.max = maxTime;

    function resetModal() {
        eventStartEl.value = startDate + 'T12:30';
        eventEndEl.value = startDate + 'T13:30';
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
                    modal.style.display = "block";
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


    /**
     * Set up the modal form to be able to add events to the calendar
     */


    const addEventForm = document.getElementById('add-event-form');
    addEventForm.onsubmit = function() {

        const startTime = document.getElementById('event-start-time').value;
        const endTime = document.getElementById('event-end-time').value;

        // Check validity of input
        if (moment(endTime).isBefore(startTime) || moment(endTime).isSame(startTime)){
            alert("Error: End time must be greater than start time.");
        } else {
            //TODO     alert('Great. Now, update your database...');
            const title = document.getElementById('event-title').value;
            addEventToCalendar(title, startTime, endTime)
            modal.style.display = "none";
        }


        // to prevent from reloading the page, return false.
        return false;

    };


    /**
     * Function to add events to the calendar. Outside functions will use this.
     */

    //TODO Function that takes in a JSON Event?

    function addEventToCalendar(title, startTimeISO, endTimeISO) {

        let alldayBoolean = false;
        // if the difference in days is >= 2, display as an "all day" event to save calendar space
        if ((moment(endTimeISO).date() - moment(startTimeISO).date()) > 1) {
            alldayBoolean = true;
            // append start time to title to be more informative
            const startTime = moment(startTimeISO).format("hh:mm:ss a");
            title += " @ " + startTime.substring(0, 5) + startTime.substring(9)
        }
        console.log(moment(startTimeISO).date());
        calendar.addEvent(
            {
                title: title ? title : "Untitled Event",
                start: startTimeISO,
                end: endTimeISO,
                editable: true,
                allDay: alldayBoolean
            }
        )


    }
});