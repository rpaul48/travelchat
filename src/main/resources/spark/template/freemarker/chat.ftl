<link rel="stylesheet" href="/css/chat.css">
<link rel="stylesheet" href="/css/travelchat.css">

<#assign content>
    <img onclick="logout()" src="/images/TravelChat-Banner.png" alt="TravelChat">

    <div id="firechat-wrapper">
    <div id="chat-div">
    <div id="chatControls">
        <input id="message" placeholder="Type your message">
        <button id="send">Send</button>
    </div>
    <ul id="userlist"> <!-- Built by JS --> </ul>
    <div id="chat">    <!-- Built by JS --> </div>
    </div>
    </div>

    <button onclick="leaveChat()" id="leave-chat" class="large-red-button">Leave Chat</button>

    <script src="/js/login.js"></script>
    <script src="/js/chat.js"></script>
</#assign>
<#include "main.ftl">