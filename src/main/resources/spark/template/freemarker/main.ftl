<!DOCTYPE html>
<head>
    <meta charset="utf-8">
    <title>${title}</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
</head>
<body>
${content}
<!-- Again, we're serving up the unminified source for clarity. -->
<script src="js/jquery-2.1.1.js"></script>
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
<link rel="stylesheet" href="https://cdn.firebase.com/libs/firechat/3.0.1/firechat.min.css" />
<script src="https://cdn.firebase.com/libs/firechat/3.0.1/firechat.min.js"></script>

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
<script src="js/login-index.js"></script>
</body>
<!-- See http://html5boilerplate.com/ for a good place to start
     dealing with real world issues like old browsers.  -->
</html>