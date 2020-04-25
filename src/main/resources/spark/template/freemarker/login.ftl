<link rel="stylesheet" href="css/login.css">

<#assign content>
    <div id="login_div" class="main-div">
        <h3>Welcome to TravelChat</h3>
        <input type="email" placeholder="Email..." id="email_field"/>
        <input type="password" placeholder="Password..." id="password_field"/>

        <button onclick="login()">Login</button>
    </div>

    <div id="user_div" class="loggedin-div">
        <h3>Welcome User</h3>
        <p>Welcome to TravelChat! You're currently logged in.</p>
        <button>Log Out</button>
    </div>

    <script src="js/login-index.js"></script>
</#assign>
<#include "main.ftl">