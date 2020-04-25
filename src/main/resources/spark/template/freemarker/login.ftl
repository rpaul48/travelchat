<link rel="stylesheet" href="css/login.css">

<#assign content>

    <div id="login_div" class="main-div">
        <h2>Welcome to TravelChat</h2>
        <input type="email" placeholder="Email..." id="email_field"/>
        <input type="password" placeholder="Password..." id="password_field"/>

        <button onclick="login()">Login</button>

    </div>

    <div id="user_div" class="loggedin-div">
        <h3>Welcome User</h3>
        <p id="user_para">Welcome to TravelChat! You're currently logged in.</p>
        <button onclick="logout()">Log Out</button>
    </div>



</#assign>
<#include "main.ftl">