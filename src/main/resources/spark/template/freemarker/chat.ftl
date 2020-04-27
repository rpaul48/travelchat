<#assign content>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Chat</title>
    <link rel="stylesheet" href="css/chat.css">
</head>
<body>
    <div id="chatControls">
        <input id="message" placeholder="Type your message">
        <button id="send">Send</button>
    </div>
    <ul id="userlist"> <!-- Built by JS --> </ul>
    <div id="chat">    <!-- Built by JS --> </div>
    <script src="js/chat.js"></script>
</body>
</#assign>
<#include "main.ftl">