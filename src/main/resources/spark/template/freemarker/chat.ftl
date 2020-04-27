<link rel="stylesheet" href="/css/chat.css">
<#assign content>
    <div id="chat-div">
    <div id="chatControls">
        <input id="message" placeholder="Type your message">
        <button id="send">Send</button>
    </div>
    <ul id="userlist"> <!-- Built by JS --> </ul>
    <div id="chat">    <!-- Built by JS --> </div>
    </div>

    <script src="/js/login.js"></script>
    <script src="/js/chat.js"></script>
</#assign>
<#include "main.ftl">