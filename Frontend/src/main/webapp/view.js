function getUser(){
    let reg_no = localStorage.getItem('reg_no');
    $.ajax({
        method: "GET",
        contentType: "application/json",
        url: `http://localhost:8090/api/v1/user/getUserByUserId/${reg_no}`,
        async: true,
        success: function(data){
            $('#datatable').empty();

            let reg_no = data.reg_no;
            let first_name = data.first_name;
            let last_name = data.last_name;
            let email = data.email;
            let country = data.country;
            let city = data.city;
            let mobile = data.mobile;

            var rows = `
                <tr><th scope="row">REGISTRATION NUMBER</th><td>${reg_no}</td></tr>
                <tr><th scope="row">FIRST NAME</th><td>${first_name}</td></tr>
                <tr><th scope="row">LAST NAME</th><td>${last_name}</td></tr>
                <tr><th scope="row">EMAIL</th><td>${email}</td></tr>
                <tr><th scope="row">COUNTRY</th><td>${country}</td></tr>
                <tr><th scope="row">CITY</th><td>${city}</td></tr>
                <tr><th scope="row">MOBILE</th><td>${mobile}</td></tr>
            `;
            $('#datatable').append(rows);
        },
        error: function(xhr, exception){
            alert("Profile not created");
        }
    });
}

function deleteUser(){
    let reg_no = localStorage.getItem('reg_no');

    $.ajax({
        method:"DELETE",
        contentType:"application/json",
        url:`http://localhost:8090/api/v1/user/deleteUser/${reg_no}`,
        async:true,
        success: function(data){
            $('#datatable').empty();
            alert("deleted")
        },
        error: function(xhr, exception){
            alert("Error")
        }
    })
}
