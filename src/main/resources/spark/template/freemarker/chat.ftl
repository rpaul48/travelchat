<link rel="stylesheet" href="/css/chat.css">
<link rel="stylesheet" href="/css/sidenav.css">

<#assign content>

    <div class="container">
        <div class="sidewrapper">
            <div class="sidenav">
                <!-- <h2><a id="back" href="/manage-chats">TravelChat</a></h2> -->
                <a id="back" href="/manage-chats"> <img src="/images/TravelChat-Banner.png" alt="TravelChat"> </a>
                <ul>
                    <li><a onclick="openPopup('settings-div')"><i class="fas fa-cog"></i>Settings</a></li>
                    <li><a onclick="openPopup('trip-details-div')"><i class="fas fa-route"></i>Trip Details</a></li>
                    <li><a href="/calendar"><i class="fas fa-calendar-alt"></i>Calendar</a></li>
                    <li><a onclick="openPopup('plan-my-day-div')"><i class="fas fa-list"></i>Plan My Day</a></li>
                    <li><a onclick="openPopup('restaurants-div')"><i class="fas fa-utensils"></i>Browse Restaurants</a></li>
                    <li><a onclick="openPopup('activities-div')"><i class="fas fa-hiking"></i>Browse Activities</a></li>
                    <li><a onclick="openPopup('lodging-div')"><i class="fas fa-hotel"></i>Browse Lodging</a></li>
                    <li><a onclick="openPopup('flights-div')"><i class="fas fa-plane"></i>Browse Flights</a></li>
                </ul>
            </div>
        </div>
        <div class="main">
            <div id="firechat-wrapper">
            </div>
        </div>
    </div>

    <div id="settings-div" class="popup-div">
        <h2>Settings</h2>
        <input type="text" placeholder="Display Name" id="update-display-name-field"/>
        <button onclick="editProfile()" class="large-green-button">Save Changes</button>
        <button onclick="leaveChat()" id="leave-chat" class="large-red-button">Leave Chat</button>
        <button onclick="closePopup('settings-div')" class="large-black-button">Back</button>
    </div>

    <div id="trip-details-div" class="big-popup-div">
        <h2>Trip Details</h2>
        <button onclick="closePopup('trip-details-div')" class="large-black-button">Back</button>
    </div>

    <div id="plan-my-day-div" class="big-popup-div">
        <h2>Plan My Day</h2>
        <button onclick="closePopup('plan-my-day-div')" class="large-black-button">Back</button>
    </div>

    <div id="restaurants-div" class="big-popup-div">
        <h2>Browse Restaurants</h2>
        <button onclick="closePopup('restaurants-div')" class="large-black-button">Back</button>
    </div>

    <div id="activities-div" class="big-popup-div">
        <h2>Browse Activities</h2>
        <button onclick="closePopup('activities-div')" class="large-black-button">Back</button>
    </div>

    <div id="lodging-div" class="big-popup-div">
        <h2>Browse Lodging</h2>
        <button onclick="closePopup('lodging-div')" class="large-black-button">Back</button>
    </div>

    <div id="flights-div" class="big-popup-div">
        <h2>Browse Flights</h2>
        <button onclick="closePopup('flights-div')" class="large-black-button">Back</button>
    </div>

    <script src="/js/login.js"></script>
    <script src="/js/chat.js"></script>
</#assign>
<#include "main.ftl">