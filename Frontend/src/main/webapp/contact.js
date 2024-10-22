function contact() {
    let name = $('#name').val();
    let email = $('#email').val();
    let phone = $('#phone').val();
    let message = $('#message').val();

    if (!name || !email || !phone || !message) {
        alert("All fields are required");
        return;
    }

    $.ajax({
        method: "POST",
        contentType: "application/json",
        url: 'http://localhost:8090/api/v1/user/sendEmail',
        async: true,
        data: JSON.stringify({
            "name": name,
            "from": email,
            "phone": phone,
            "message": message
        }),
        success: function(response) {
            console.log("Email sent successfully: ", response); 
            alert("Message sent successfully!");
        },
        error: function(xhr, status, error) {
            console.error("Error occurred: ", error);
            alert("Failed to send: " + error);
        }
    });
}

$(document).ready(function() {
    $('#Submit').on('click', contact);
});