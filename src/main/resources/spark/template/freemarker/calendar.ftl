<html lang='en'>
<title>TravelChat Calendar</title>
<link rel="icon" href="/images/TravelChat.png">
    <head>
        <meta charset='utf-8' />

        <link href="http://unpkg.com/@fullcalendar/core/main.css" rel='stylesheet' />
        <link href="http://unpkg.com/@fullcalendar/daygrid/main.css" rel='stylesheet' />
        <link href="http://unpkg.com/@fullcalendar/timegrid/main.css" rel='stylesheet' />
        <link rel="stylesheet" href="../../css/calendar.css">

        <link rel="stylesheet" href="../../css/buttons.css">

        <script src="../../js/jquery-2.1.1.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.24.0/moment.min.js"></script>
        <script src="http://unpkg.com/@fullcalendar/core/main.js"></script>
        <script src="http://unpkg.com/@fullcalendar/daygrid/main.js"></script>
        <script src="http://unpkg.com/@fullcalendar/timegrid/main.js"></script>
        <script src="http://unpkg.com/@fullcalendar/interaction/main.js"></script>
        <script src="../../js/calendar.js"></script>


    </head>

<body>

<div id="add-event-modal" class="modal">

    <!-- Modal form -->
    <div class="modal-content">
        <span class="close" id="modal-close">&times;</span>
        <form id="add-event-form" action="" >
            <label for="event-title-input">Event Title:</label>
            <input type="text" id="event-title-input" name="event-title-input" class="event-input">
            <br>
            <label for="event-location-input">Location:</label>
            <input type="text" id="event-location-input" name="event-location" class="event-input">
            <br>
            <label for="event-start-time-input">Start time:</label>
            <input type="datetime-local" id="event-start-time-input" class="event-input"
                   name="start-time-input">
            <br>
            <label for="event-end-time-input">End time:</label>
            <input type="datetime-local" id="event-end-time-input" class="event-input"
                   name="end-time-input">
            <br>
            <label for="event-price-input">$</label>
            <input type="number" id="event-price-input" class="event-input" min="0.00" max="10000.00" step="0.01" value="0.00" />
            <br>
            <label for="event-description-input">Description:</label>
            <textarea name="event-description-input" id="event-description-input" class="event-input" cols="35" rows="5" ></textarea>
            <br>
            <button class="submit large-green-button" type="submit"> Add Event </button>
        </form>
    </div>

</div>

<div id="event-popup" class="modal">
    <!-- event pop-up-->
    <div class="modal-content">
        <span class="close" id="event-popup-close">&times;</span>
        <h1 id="event-title"> Title </h1>
        <h3 id="event-time"> Time </h3>
        <h3 id="event-location"> Location </h3>
        <h3 id="event-price"> Price </h3>
        <p id="event-description"> Description </p>
        <div id="event-buttons">
            <button id="join-event" class="submit small-green-button" type="submit"> Join Event </button>
            <button id="remove-event" class="submit small-red-button" type="submit"> Remove Event </button>
            <button id="leave-event" class="submit small-red-button" type="submit"> Leave Event </button>
        </div>
    </div>
</div>

<div id='calendar'></div>

</body>

</html>
