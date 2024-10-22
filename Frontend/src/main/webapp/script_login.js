function signup() {

    let reg_no = $('#reg_no').val();
    let password = $('#password').val();
    if (!reg_no || !password ) {
        alert("All fields are required");
        return;
    }

    $.ajax({
        method: "GET",
        contentType: "application/json",
        url: `http://localhost:8090/api/v1/user/login_student/${reg_no}/${password}`,
        async: true,
        success: function(response) {

            if (response=="Password is wrong"){
                alert("Incorrect Password");
                return;
            }
            if (response=="Student not found"){
                alert("Student not found");
                return;
            }

            else{
                alert("successfully login");
                $('#loginForm')[0].reset();
                localStorage.setItem('reg_no', reg_no);
                let name = response;
                localStorage.setItem('name', name);
                window.location.href = 'home.html';
                
                // After successful login give a token
                let token = response.token; 
                setToken(token);

            }
        },
        error: function(xhr, status, error) {
            alert("Data not saved: " + error);
        }
    });
}

$(document).ready(function() {
    $('#login').on('click', signup);
    let reg_no = localStorage.getItem('reg_no');
    let name = localStorage.getItem('name');

   
    if (reg_no) {
        let nameUppaer = name.toUpperCase();

        $('.regNoDisplay').text(reg_no);
        $('.nameDisplay').text(nameUppaer);

    } else {
        // Optionally handle the case where reg_no is not found in local storage
        $('#regNoDisplay').text('Register number not found');
        $('.nameDisplay').text("No name");
    }


});

function setToken(token) {
    const expirationTime = new Date().getTime() + (60 * 100000); // 1 minutes
    document.cookie = `session_token=${token}; expires=${new Date(expirationTime).toUTCString()}; path=/;`;
    sessionStorage.setItem('token_expiration', expirationTime);
}
