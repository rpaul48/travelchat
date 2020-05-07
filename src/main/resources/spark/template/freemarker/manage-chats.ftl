<link rel="stylesheet" href="/css/travelchat.css">
<#assign content>
    <img class="logo" onclick="logout()" src="/images/TravelChat-Banner.png" alt="TravelChat">

    <main>
    <div id="chats-div" class="main-div">
        <h2>Manage Chats</h2>
        <div id="user-rooms"></div>
        <button onclick="openCreateChat()" class="large-green-button">+ Create new chat</button>
        <button onclick="logout()" class="large-black-button">Back</button>
    </div>

    <div id="create-chat-div" class="popup-div">
        <h2>Create New Chat</h2>
        <input type="text" aria-label="Group Name" placeholder="Group Name" id="group-name-field"/>
        <input aria-label="Comma-separated invitee emails" placeholder="Comma-separated invitee emails" id="add-user-field"/>
        <p id="room-id"></p>
        <button onclick="createChat()" class="large-green-button">Create</button>
        <button onclick="closeCreateChat()" class="large-black-button">Back</button>
    </div>


    <script src="/js/login.js"></script>
    <script src="/js/manage-chats.js"></script>
    </main>
</#assign>
<#include "main.ftl">