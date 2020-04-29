<link rel="stylesheet" href="/css/chat.css">

<#assign content>
<img onclick="logout()" src="/images/TravelChat-Banner.png" alt="TravelChat">

    <div class="sidenav">
        <a id="manage-chats" href="/manage-chats">< Go Home</a>
        <a href="#">Chat</a>
        <a href="/calendar">Calendar</a>
        <a href="#">Specify Time/Budget</a>
        <a href="#">Choose Location</a>
        <a href="#">Choose Flights</a>
        <a href="#">Choose Housing</a>
        <a href="#">Choose Activities</a>
    </div>

    <div class="main">
        <div id="firechat-wrapper">
        </div>
    </div>

    <button onclick="leaveChat()" id="leave-chat" class="large-red-button">Leave Chat</button>

    <script src="/js/login.js"></script>
    <script src="/js/chat.js"></script>
</#assign>
<#include "main.ftl">