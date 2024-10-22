function signup() {
    let name = $('#Name').val();
    let regNo = $('#Reg_no').val();
    let email = $('#Email').val();
    let password = $('#password').val();
    let password2 = $('#password_2').val();

    if (!name || !regNo || !email || !password || !password2) {
        alert("All fields are required");
        return;
    }

    if (password !== password2) {
        alert("Passwords do not match");
        return;
    }

    if (!/^[a-zA-Z0-9._%+-]+@gmail\.com$/.test(email)) {
        alert("Please enter a valid Gmail address");
        return;
    }
    

    $.ajax({
        method: "POST",
        contentType: "application/json",
        url: 'http://localhost:8090/api/v1/user/savestudent',
        async: true,
        data: JSON.stringify({
            "student_id": regNo,
            "student_email": email,
            "student_name": name,
            "password": password
        }),
        success: function(response) {
            alert("Data Saved");
            $('#signupForm')[0].reset();
            window.location.href = 'Stdlogin.html';
        },
        error: function(xhr, status, error) {
            alert("Data not saved: " + error);
        }
    });
}

$(document).ready(function() {
    $('#SignUp').on('click', signup);
});

