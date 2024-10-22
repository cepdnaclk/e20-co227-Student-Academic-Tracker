function signup() {
    let lecturer_ID = $('#userID').val();
    let password = $('#password').val();

    if (!lecturer_ID || !password) {
        alert("All fields are required");
        return;
    }

    $.ajax({
        method: "GET",
        contentType: "application/json",
        url: `http://localhost:8090/api/v1/user/login_lecturer/${lecturer_ID}/${password}`,
        async: true,
        success: function(response) {
            if (response === "Password is wrong") {
                alert("Incorrect Password");
                return;
            }
            if (response === "Admin not found") {
                alert("Admin not found");
                return;
            }

            alert("Successfully logged in");
            $('#loginForm')[0].reset();

            // Ensure response has the correct data
            localStorage.setItem('userID', lecturer_ID);
            localStorage.setItem('name_admin', response); 
            window.location.href = 'homelec.html';

            // After successful login give a token (if token is part of response)
            let token = response.token;
            setToken(token);
        },
        error: function(xhr, status, error) {
            alert("Data not saved: " + error);
        }
    });
}

$(document).ready(function() {
    $('#adlogin').on('click', function(event) {
        event.preventDefault();
        signup();
    });

    // Retrieve values from localStorage
    let lecturer_ID = localStorage.getItem('userID');
    let name = localStorage.getItem('name_admin');

    // Check if values exist in localStorage
    if (lecturer_ID) {

        $('.userIDDisplay').text(lecturer_ID);
        if (name) {
            $('.nameD').text(name.toUpperCase());
        }
    } else {
        // Handle case where values are not found in localStorage
        $('.userIDDisplay').text('User ID not found');
        $('.nameD').text('No name');
    }
});

function setToken(token) {
    const expirationTime = new Date().getTime() + (60 * 60 * 1000); // 1 hour
    document.cookie = `session_token=${token}; expires=${new Date(expirationTime).toUTCString()}; path=/;`;
    sessionStorage.setItem('token_expiration', expirationTime);
}

