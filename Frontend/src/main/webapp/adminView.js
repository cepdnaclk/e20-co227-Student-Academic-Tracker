function getAdmin(){
    let adminID =localStorage.getItem('userID');
    $.ajax({
        method: "GET",
        contentType: "application/json",
        url: `http://localhost:8090/api/v1/user/getAdminByUserId/${adminID}`,
        async: true,
        success: function(data){
            $('#datatable').empty();

            let adminID = data.adminID;
            let first_name = data.first_name;
            let last_name = data.last_name;
            let email = data.email;
            let country = data.country;
            let city = data.city;
            let mobile = data.mobile;

            var rows = `
                <tr><th scope="row">ADMIN ID</th><td>${adminID}</td></tr>
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

function deleteAdmin(){
    let adminID = localStorage.getItem('userID');

    $.ajax({
        method:"DELETE",
        contentType:"application/json",
        url:`http://localhost:8090/api/v1/user/deleteAdmin/${adminID}`,
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

