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
                        <label for="event-title">Event Title:</label>
                        <input type="text" id="event-title" name="event-title" class="event-input">
                        <br>
                        <label for="event-location">Location:</label>
                        <input type="text" id="event-location" name="event-location" class="event-input">
                        <br>
                        <label for="event-start-time">Start time:</label>
                        <input type="datetime-local" id="event-start-time" class="event-input"
                           name="start-time">
                        <br>
                        <label for="event-end-time">End time:</label>
                        <input type="datetime-local" id="event-end-time" class="event-input"
                           name="end-time">
                        <br>
                        <label for="event-description">Description:</label>
                        <textarea name="event-description" id="event-description" class="event-input" cols="35" rows="5" ></textarea>
                        <br>
                        <label for="event-price">Cost: </label>
                        $ <input aria-label="Event Price" type="number" id="event-price" class="event-input" min="0.00" max="10000.00" step="0.01" value="0.00" />
                        <button class="submit large-green-button" type="submit"> Add Event </button>
                    </form>
            </div>

        </div>

        <div id="event-popup" class="modal">
            <!-- event pop-up-->
            <div class="modal-content">
                <span class="close" id="event-popup-close">&times;</span>
            </div>
        </div>

        <div id='calendar'></div>

    </body>

</html>
