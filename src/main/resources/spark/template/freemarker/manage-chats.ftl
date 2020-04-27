<link rel="stylesheet" href="css/travelchat.css">
<#assign content>
    <img onclick="logout()" src="images/TravelChat-Banner.png" alt="TravelChat">

    <div id="chats-div" class="main-div">
        <h2>Manage Chats</h2>

        <button onclick="openCreateChat()" class="large-green-button">Create new chat</button>
        <button onclick="openJoinChat()" class="large-green-button">Join existing chat</button>
        <button onclick="logout()" class="large-black-button">Back</button>
    </div>

    <div id="create-chat-div" class="popup-div">
        <h2>Create New Chat</h2>
        <input type="text" placeholder="Group Name" id="group-name-field"/>
        <p id="room-id">Your unique room ID will appear below. Your friends can use this to
        join your group!</p>
        <button onclick="createChat()" class="large-green-button">Create</button>
        <button onclick="closeCreateChat()" class="large-black-button">Back</button>
    </div>

    <div id="join-chat-div" class="popup-div">
        <h2>Join Existing Chat</h2>
        <input type="text" placeholder="Group Id" id="group-id-field"/>
        <button onclick="joinChat()" class="large-green-button">Join</button>
        <button onclick="closeJoinChat()" class="large-black-button">Back</button>
    </div>


    <script src="js/login.js"></script>
    <script src="js/manage-chats.js"></script>
</#assign>
<#include "main.ftl">