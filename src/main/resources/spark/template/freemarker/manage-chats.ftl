<link rel="stylesheet" href="css/travelchat.css">
<#assign content>
    <img onclick="logout()" src="images/TravelChat-Banner.png" alt="TravelChat">

    <div id="chats-div" class="main-div">
        <h2>Manage Chats</h2>

        <button onclick="openCreateChat()" class="large-green-button">Create new chat</button>
        <button onclick="logout()" class="large-black-button">Back</button>
    </div>

    <div id="create-chat-div" class="popup-div">
        <h2>Create New Chat</h2>
        <input type="text" placeholder="Group Name" id="group-name-field"/>
        <input placeholder="User email" id="add-user-field"/>
        <button onclick="addUser()">Add</button>
        <p id="added-users"> The people you add will appear here.</p>
        <button onclick="createChat()" class="large-green-button">Create</button>
        <button onclick="closeCreateChat()" class="large-black-button">Back</button>
    </div>


    <script src="js/login.js"></script>
    <script src="js/manage-chats.js"></script>
</#assign>
<#include "main.ftl">