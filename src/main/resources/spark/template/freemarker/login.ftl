<link rel="stylesheet" href="/css/travelchat.css">
<#assign content>
    <img class="logo" onclick="logout()" src="images/TravelChat-Banner.png" alt="TravelChat Banner Logo">

    <main>
    <div id="login-div" class="main-div">
        <h2>Welcome to TravelChat</h2>
        <input type="email" aria-label="Email" placeholder="Email" id="email-field"/>
        <input type="password" aria-label="Password" placeholder="Password" id="password-field"/>

        <button onclick="login()" class="large-blue-button">
            Login
        </button>
        <button onclick="openCreateAccount()" class="large-green-button">
            Create Account
        </button>
        <div id="create-account-div" class="popup-div">
            <h2>Create Account</h2>
            <input type="text" aria-label="Display Name" placeholder="Display Name" id="display-name-field"/>
            <input type="email" aria-label="Email" placeholder="Email" id="create-email-field"/>
            <input type="password" aria-label="Password" placeholder="Password" id="create-password-field"/>
            <button onclick="createAccount()" class="large-green-button">Create</button>
            <button onclick="closeCreateAccount()" class="large-black-button">Back</button>
        </div>
    </div>

    <script src="/js/login.js"></script>
    </main>
</#assign>
<#include "main.ftl">