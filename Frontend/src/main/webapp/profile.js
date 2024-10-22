function saveUser(){
    let reg_no = localStorage.getItem('reg_no')
    let first_name = $('#exampleFormControlInput2').val();
    let email = $('#exampleFormControlInput3').val();
    let country = $('#exampleFormControlInput4').val();
    let last_name = $('#exampleFormControlInput5').val();
    let city = $('#exampleFormControlInput6').val();
    let mobile = $('#exampleFormControlInput7').val();

    $.ajax({
        method:"POST",
        contentType:"application/json",
        url:`http://localhost:8090/api/v1/user/saveUser`,
        async:true,
        data:JSON.stringify({
            "reg_no":reg_no,
            "first_name":first_name,
            "last_name":last_name,
            "email":email,
            "country":country,
            "city":city,
            "mobile":mobile
        }),
        success: function(data){
            alert("saved")
        },
        error: function(xhr, exception){
            alert("Error")
        }
    })
}

function updateUser(){
    let reg_no = localStorage.getItem('reg_no')
    let first_name = $('#exampleFormControlInput2').val();
    let email = $('#exampleFormControlInput3').val();
    let country = $('#exampleFormControlInput4').val();
    let last_name = $('#exampleFormControlInput5').val();
    let city = $('#exampleFormControlInput6').val();
    let mobile = $('#exampleFormControlInput7').val();

    $.ajax({
        method:"PUT",
        contentType:"application/json",
        url:`http://localhost:8090/api/v1/user/updateUser`,
        async:true,
        data:JSON.stringify({
            "reg_no":reg_no,
            "first_name":first_name,
            "last_name":last_name,
            "email":email,
            "country":country,
            "city":city,
            "mobile":mobile
        }),
        success: function(data){
            alert("updated")
        },
        error: function(xhr, exception){
            alert("Error")
        }
    })
}



