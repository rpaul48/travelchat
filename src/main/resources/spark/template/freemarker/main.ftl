<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>TravelChat</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- In real-world webapps, css is usually minified and
         concatenated. Here, separate normalize from our code, and
         avoid minification for clarity. -->
    <!-- Place your kit's code here -->
    <script src="https://kit.fontawesome.com/b6b689005b.js" crossorigin="anonymous"></script>
    <!--favicon for TravelChat-->
    <link rel="icon" href="/images/TravelChat.png">
</head>
<body>
<!-- Again, we're serving up the unminified source for clarity. -->
<script src="/js/jquery-2.1.1.js"></script>
<!-- The core Firebase JS SDK is always required and must be listed first -->
<script src="https://www.gstatic.com/firebasejs/7.14.2/firebase-app.js"></script>

<!-- TODO: Add SDKs for Firebase products that you want to use
     https://firebase.google.com/docs/web/setup#available-libraries -->
<script src="https://www.gstatic.com/firebasejs/7.14.2/firebase-analytics.js"></script>

<!-- Add Firebase products that you want to use -->
<script src="https://www.gstatic.com/firebasejs/7.14.2/firebase-auth.js"></script>
<script src="https://www.gstatic.com/firebasejs/7.14.2/firebase-firestore.js"></script>

<!-- jQuery -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.1.0/jquery.min.js"></script>

<!-- Firebase -->
<script src="https://www.gstatic.com/firebasejs/3.3.0/firebase.js"></script>

<!-- Firechat -->
<link rel="stylesheet" href="/css/firechat.css" />
<script src="/js/firechat.js"></script>

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
</script>

<!-- buttons -->
<link rel="stylesheet" href="/css/buttons.css">
<link rel="stylesheet" href="/css/div-types.css">

${content}
</body>
<!-- See http://html5boilerplate.com/ for a good place to start
     dealing with real world issues like old browsers.  -->
</html>