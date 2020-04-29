<link rel="stylesheet" href="/css/chat.css">
<link rel="stylesheet" href="/css/sidenav.css">

<#assign content>

    <div class="sidewrapper">
        <div class="sidenav">
            <!-- <h2><a id="back" href="/manage-chats">TravelChat</a></h2> -->
            <a id="back" href="/manage-chats"> <img src="/images/TravelChat-Banner.png" alt="TravelChat"> </a>
            <ul>
                <li><a onclick="openSettings()"><i class="fas fa-cog"></i>Settings</a></li>
                <li><a href="/calendar"><i class="fas fa-calendar-alt"></i>Calendar</a></li>
                <li><a href="#"><i class="fas fa-route"></i>Trip Details</a></li>
                <li><a href="#"><i class="fas fa-globe-americas"></i>Choose Location</a></li>
                <li><a href="#"><i class="fas fa-plane"></i>Choose Flights</a></li>
                <li><a href="#"><i class="fas fa-hotel"></i>Choose Housing</a></li>
                <li><a href="#"><i class="fas fa-hiking"></i>Choose Activities</a></li>
            </ul>
        </div>
    </div>

    <div id="settings-div" class="popup-div">
        <h2>Settings</h2>
        <input type="text" placeholder="Display Name" id="update-display-name-field"/>
        <button onclick="editProfile()" class="large-green-button">Save Changes</button>
        <button onclick="leaveChat()" id="leave-chat" class="large-red-button">Leave Chat</button>
        <button onclick="closeEditProfile()" class="large-black-button">Back</button>
    </div>

    <div id="settings-div" class="popup-div">
        <h2>Settings</h2>
    </div>

    <div class="main">
        <div id="firechat-wrapper">
        </div>
    </div>


    <script src="/js/login.js"></script>
    <script src="/js/chat.js"></script>
</#assign>
<#include "main.ftl">