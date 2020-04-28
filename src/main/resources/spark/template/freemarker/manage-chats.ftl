<link rel="stylesheet" href="/css/travelchat.css">
<#assign content>
    <img onclick="logout()" src="/images/TravelChat-Banner.png" alt="TravelChat">

    <div id="chats-div" class="main-div">
        <h2><b>Manage Chats</b></h2>

        <div id="user-rooms"></div>
        <button onclick="openCreateChat()" class="large-green-button">+ Create new chat</button>
        <button onclick="logout()" class="large-black-button">Back</button>
    </div>

    <div id="create-chat-div" class="popup-div">
        <h2>Create New Chat</h2>
        <input type="text" placeholder="Group Name" id="group-name-field"/>
        <input placeholder="Comma-separated invitee emails" id="add-user-field"/>
        <p id="room-id"></p>
        <button onclick="createChat()" class="large-green-button">Create</button>
        <button onclick="closeCreateChat()" class="large-black-button">Back</button>
    </div>

    <div id="add-chat-div" class="popup-div">
        <h2>Add Existing Chat</h2>
        <input type="text" placeholder="Group Id" id="group-id-field"/>
        <button onclick="addChat()" class="large-green-button">Add</button>
        <button onclick="closeAddChat()" class="large-black-button">Back</button>
    </div>


    <script src="/js/login.js"></script>
    <script src="/js/manage-chats.js"></script>
</#assign>
<#include "main.ftl">