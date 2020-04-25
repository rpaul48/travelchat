<link rel="stylesheet" href="css/login.css">

<#assign content>
    <!-- The core Firebase JS SDK is always required and must be listed first -->
    <script src="https://www.gstatic.com/firebasejs/7.14.2/firebase-app.js"></script>

    <!-- TODO: Add SDKs for Firebase products that you want to use
         https://firebase.google.com/docs/web/setup#available-libraries -->
    <script src="https://www.gstatic.com/firebasejs/7.14.2/firebase-analytics.js"></script>

    <script>
        // Your web app's Firebase configuration
        var firebaseConfig = {
            apiKey: "AIzaSyBKJa_Wm0x40UsemNVuvTCx7-C_uwND1yo",
            authDomain: "travelchat-3120c.firebaseapp.com",
            databaseURL: "https://travelchat-3120c.firebaseio.com",
            projectId: "travelchat-3120c",
            storageBucket: "travelchat-3120c.appspot.com",
            messagingSenderId: "61871334260",
            appId: "1:61871334260:web:dcd8520d9162ed61904221",
            measurementId: "G-9R264YXTTQ"
        };
        // Initialize Firebase
        firebase.initializeApp(firebaseConfig);
        firebase.analytics();
    </script>

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