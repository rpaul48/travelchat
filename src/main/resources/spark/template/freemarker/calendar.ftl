<html lang='en'>
    <head>
        <meta charset='utf-8' />

        <link href="http://unpkg.com/@fullcalendar/core/main.css" rel='stylesheet' />
        <link href="http://unpkg.com/@fullcalendar/daygrid/main.css" rel='stylesheet' />
        <link href="http://unpkg.com/@fullcalendar/timegrid/main.css" rel='stylesheet' />

        <script src="http://unpkg.com/@fullcalendar/core/main.js"></script>
        <script src="http://unpkg.com/@fullcalendar/daygrid/main.js"></script>
        <script src="http://unpkg.com/@fullcalendar/timegrid/main.js"></script>
        <script src="http://unpkg.com/@fullcalendar/interaction/main.js"></script>
        <script src="http://unpkg.com/@fullcalendar/moment/main.js"></script>

        <script>

            document.addEventListener('DOMContentLoaded', function() {

                let startDate = '2020-05-01';
                let endDate = '2020-06-01';
                const calendarEl = document.getElementById('calendar');
                const calendar = new FullCalendar.Calendar(calendarEl, {
                    plugins: [ 'timeGrid', 'interaction' ],
                    defaultView: 'timeGridWeek',
                    customButtons: {
                        addEvent: {
                            text: 'Add Event',
                            click: function() {
                                // var dateStr = prompt('Enter a date in YYYY-MM-DD format');
                                // var date = moment(dateStr);
                                //
                                // if (date.isValid()) {
                                //     $('#calendar').fullCalendar('renderEvent', {
                                //         title: 'dynamic event',
                                //         start: date,
                                //         allDay: true
                                //     });
                                //     alert('Great. Now, update your database...');
                                // } else {
                                //     alert('Invalid date.');
                                // }
                                calendar.addEvent(
                                    {
                                        title: 'Event Name',
                                        start: '2020-05-02T08:30:00',
                                        end: '2020-05-02T16:30:00',
                                        editable: true
                                    }
                                )
                            }
                        }
                    },
                    header: {
                        left:   'title',
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
            });
        </script>

    </head>

    <body>

        <div id='calendar'></div>

    </body>

</html>