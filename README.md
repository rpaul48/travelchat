# TravelChat: CSCI 0320 Term Project 2020
https://travelchatapp.herokuapp.com/login

#### Team Members
- **Front-end:** Nathaniel Nguyen, Raj Paul, Colton Rusch
- **Back-end:** Nathan Mugerwa, Suhye Park

## The Concept
Traveling with groups can be a mess. From coordinating schedules to managing spending to sorting through attractions, planning a trip with multiple people in the picture is no small task. TravelChat is a web-based application designed to streamline this process. Through this platform, users can chat with their groups, keep track of spending, manage calendars, use intelligently-generated daily schedules, and so much more!

## Features
- **Secure authentication**: With Firebase Authentication, users can log in to their account to gain secure access to their chats and personal information.
- **Chat**: Users can easily create and participate in multiple groups. Our chat feature is hosted through the Firebase Firechat API.
- **Beautiful, accessible design**: Our interface is modern, intuitive, and accessible. We used the WAVE Web Accessibility Evaluation Tool while developing and simulated the user end-to-end user experience using only keyboard navigation.
- **Budgeting**: Users can keep track of their spending and update it as they please from the main chat interface.
- **Calendars**: Users can create, customize, and share events through our calendar feature.
- **PlanMyDay**: We use the A* Search Algorithm to design a day-long schedule from user preferences. This feature takes in several user inputs and produces an ordered sequence of three restaurants and two activities, using distance and cost as variably-weighted heuristics.
- **Browse Menus**: Users can search for restaurants, activities, lodging, and flights from the chat interface with just a few clicks! These menus contain several options for users to filter search results, which are queried from the TripAdvisor API.

## Technologies Used
- Figma
- Firebase
- Heroku
- HTML/CSS
- Java
- Javascript
- Python
- Spark

## How to Build and Run
To build: \
run `mvn package` from root directory.

To run the Spark server: \
`./run` will start the server at `http://localhost:4567/login`
