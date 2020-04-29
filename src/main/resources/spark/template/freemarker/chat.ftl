<link rel="stylesheet" href="/css/chat.css">

<#assign content>

    <div class="sidenav">
        <a id="manage-chats" href="/manage-chats">< Go Home</a>
        <a id="edit-profile" onclick="openEditProfile()">Edit Profile</a>
        <a href="#">Chat</a>
        <a href="/calendar">Calendar</a>
        <a href="#">Specify Time/Budget</a>
        <a href="#">Choose Location</a>
        <a href="#">Choose Flights</a>
        <a href="#">Choose Housing</a>
        <a href="#">Choose Activities</a>
        <button onclick="leaveChat()" id="leave-chat" class="large-red-button">Leave Chat</button>
    </div>

    <div id="edit-profile-div" class="popup-div">
        <h2>Edit Profile</h2>
        <input type="text" placeholder="Display Name" id="update-display-name-field"/>
        <button onclick="editProfile()" class="large-green-button">Save Changes</button>
        <button onclick="closeEditProfile()" class="large-black-button">Back</button>
    </div>

    <div class="main">
        <div id="firechat-wrapper">
        </div>
    </div>


    <script src="/js/login.js"></script>
    <script src="/js/chat.js"></script>
</#assign>
<#include "main.ftl">