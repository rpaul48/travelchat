<link rel="stylesheet" href="/css/travelchat.css">
<#assign content>
    <img onclick="logout()" src="images/TravelChat-Banner.png" alt="TravelChat">

    <div id="login-div" class="main-div">
        <h2><b>Welcome to TravelChat!</b></h2>
        <input type="email" placeholder="Email" id="email-field"/>
        <input type="password" placeholder="Password" id="password-field"/>

        <button onclick="login()" class="large-blue-button">
            Login
        </button>
        <button onclick="createAccount()" class="large-green-button">
            Create Account
        </button>
    </div>

    <script src="/js/login.js"></script>
</#assign>
<#include "main.ftl">