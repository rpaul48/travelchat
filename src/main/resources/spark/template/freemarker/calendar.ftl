<html lang='en'>
    <head>
        <meta charset='utf-8' />

        <link href="http://unpkg.com/@fullcalendar/core/main.css" rel='stylesheet' />
        <link href="http://unpkg.com/@fullcalendar/daygrid/main.css" rel='stylesheet' />
        <link href="http://unpkg.com/@fullcalendar/timegrid/main.css" rel='stylesheet' />
        <link rel="stylesheet" href="css/calendar.css">

        <script src="js/jquery-2.1.1.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.24.0/moment.min.js"></script>
        <script src="http://unpkg.com/@fullcalendar/core/main.js"></script>
        <script src="http://unpkg.com/@fullcalendar/daygrid/main.js"></script>
        <script src="http://unpkg.com/@fullcalendar/timegrid/main.js"></script>
        <script src="http://unpkg.com/@fullcalendar/interaction/main.js"></script>
        <script src="js/calendar.js"></script>

    </head>

    <body>

        <div id="add-event-modal" class="modal">

            <!-- Modal content -->
            <div class="modal-content">
                <span class="close">&times;</span>
                    <form id="add-event-form" action="" >
                        <label for="event-title">Event Title:</label>
                        <input type="text" id="event-title" name="event-title" class="event-input">
                        <br>
                        <label for="start-time">Start time:</label>
                        <input type="datetime-local" id="event-start-time" class="event-input"
                           name="start-time">
                        <br>
                        <label for="end-time">End time:</label>
                        <input type="datetime-local" id="event-end-time" class="event-input"
                           name="end-time">
                        <br>
                        <button class="submit" type="submit"> Add Event </button>
                    </form>
            </div>

        </div>
        <div id='calendar'></div>

    </body>

</html>