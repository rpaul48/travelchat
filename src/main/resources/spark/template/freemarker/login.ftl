<link rel="stylesheet" href="css/login.css">

<#assign content>

    <img onclick="home()" src="images/TravelChat-Banner.png" alt="TravelChat">

    <div id="login_div" class="main-div">
        <h2>Welcome to TravelChat</h2>
        <input type="email" placeholder="Email..." id="email_field"/>
        <input type="password" placeholder="Password..." id="password_field"/>

        <button onclick="login()">Login</button>
        <button id="createAccount" onclick="createAccount()">Create Account</button>
    </div>

    <div id="user_div" class="loggedin-div">
        <h2 id="user_para">Welcome User</h2>
        <button onclick="logout()">Log Out</button>
    </div>



</#assign>
<#include "main.ftl">