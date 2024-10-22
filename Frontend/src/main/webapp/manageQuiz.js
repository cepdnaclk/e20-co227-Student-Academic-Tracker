$(document).ready(function() {

    var modal = document.getElementById("course-modal");
    var btn = document.getElementById("add-course-btn");
    var span = document.getElementsByClassName("close-btn-addcourse")[0];

    // Open the modal when the button is clicked
    btn.onclick = function() {
        modal.style.display = "block";
    }

    // Close the modal when the <span> (x) is clicked
    span.onclick = function() {
        modal.style.display = "none";
    }

    // Close the modal when clicking outside of it
    window.onclick = function(event) {
        if (event.target === modal) {
            modal.style.display = "none";
        }
    }

    // Fetch course details if courseID is provided
    const urlParams = new URLSearchParams(window.location.search);
    const courseID = urlParams.get('courseID');
    localStorage.setItem('courseID', courseID);
    const courseDetails = $('#courseDetails');
    const userID = localStorage.getItem('userID');

    if (courseID) {
        $.ajax({
            url: `http://localhost:8090/api/v1/user/course/${userID}/${courseID}`,
            type: 'GET',
            dataType: 'json',
            success: function(course) {
                const courseNAME = course[0].course_name;

                const courseDetailHTML = `
                    <div class="course-item">
                        <h1 class="Head">${courseNAME}</h1>
                        <p class="text" id="code"><strong>Course Code:</strong> ${courseID}</p>
                    </div>
                `;

                courseDetails.html(courseDetailHTML);
            },
            error: function(xhr, status, error) {
                console.error('Error fetching course details:', error);
                courseDetails.html('<p>Unable to fetch course details.</p>');
            }
        });
    } else {
        courseDetails.html('<p>No course ID specified.</p>');
    }




    const QuizList = $('#QuizList');

    if (courseID) {
        $.ajax({
            url: `http://localhost:8090/api/v1/user/getquiz/${userID}/${courseID}`,
            type: 'GET',
            dataType: 'json',
            success: function(Quizes) {
                console.log('Assignments fetched:', Quizes);

                Quizes.forEach(Quiz => {
                    const quizName = Quiz.quizName;
                    const quizDeadline = Quiz.deadLine;
                    const quizMessage = Quiz.message;
                    const quizCode = Quiz.quizCode;

                    console.log('Assignment:', Quiz); // Log each assignment for debugging

                    const quizElement = `
                    <div class="quiz-item">
                        <h3>Quiz Name: ${quizName}</h3>
                        <p><strong>Quiz Deadline:</strong> ${quizDeadline}</p>
                        <p><strong>Quiz Message:</strong> ${quizMessage}</p>
                        <p><strong>Quiz Code:</strong> ${quizCode}</p>
                        <button type="submit" id="edit" class="btn last">Edit Quiz</button>
                        <button type="submit" id="delete" class="btn last" data="${quizName}" >Delete Quiz</button>
                    </div>
                `;

                    // Use html() to append HTML code
                    QuizList.append(quizElement);
                });
            },
            error: function(xhr, status, error) {
                courseDetails.html('<p>Unable to fetch assignment details.</p>');
            }
        });
    } else {
        courseDetails.html('<p>No course ID specified.</p>');
    }




});


function uploadFiles(courseID,userID) {
    const quizName = document.getElementById('quizName').value;
    const quizCode = parseInt(document.getElementById('quizCode').value, 10); 
    const deadline = document.getElementById('deadline').value;
    const message = document.getElementById('message').value;


    $.ajax({
        url: 'http://localhost:8090/api/v1/user/saveQuiz',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify({
            "quizName":  quizName,
            "course_id": courseID,
            "admin_id": userID,
            "deadLine": deadline,
            "message": message,
            "quizCode": quizCode
        }),

        success: function(data) {
            console.log('Success:', data);
            alert("Success")
        },
        error: function(xhr, status, error) {
            
            console.error('Error:', error);
        }
    });
}

document.addEventListener("click", function(event) {
    var modal_1 = document.getElementById("quizupdate");
    const urlParams = new URLSearchParams(window.location.search);
    const courseID = urlParams.get('courseID');
    localStorage.setItem('courseID', courseID);
    const courseDetails = $('#courseDetails');
    const userID = localStorage.getItem('userID');

    if (event.target && event.target.id === "save_course"){
        uploadFiles(courseID,userID);
    }

    // Check if the clicked element is the edit button
    if (event.target && event.target.id === "edit") {
        modal_1.style.display = "block";
    }

    // Check if the clicked element is the close button (span)
    if (event.target && event.target.classList.contains("close-btn-addcourse")) {
        modal_1.style.display = "none";
    }

    // Close the modal when clicking outside of it
    if (event.target === modal_1) {
        modal_1.style.display = "none";
    }
    
    var quizName = event.target.closest('.quiz-item').querySelector('h3').innerText.replace('Quiz Name: ', '');
    localStorage.setItem('quizName', quizName);

    if (courseID) {
        $.ajax({
            url: `http://localhost:8090/api/v1/user/findQuiz/${userID}/${courseID}/${quizName}`,
            type: 'GET',
            dataType: 'json',
            success: function(quiz) {
                
                document.getElementById('quizName-edit').value = quiz.quizName;
                document.getElementById('quizCode-edit').value = quiz.quizCode;
                document.getElementById('deadline-edit').value = quiz.deadLine;
                document.getElementById('message-edit').value = quiz.message;
            },
            error: function(xhr, status, error) {
                console.error('Error fetching course details:', error);
                courseDetails.html('<p>Unable to fetch course details.</p>');
            }
        });
    } else {
        courseDetails.html('<p>No course ID specified.</p>');
    }


});



// Event listener for the update button
document.getElementById('update-quiz').addEventListener('click', function(event) {
    event.preventDefault(); // Prevent form submission

    const courseID = localStorage.getItem('courseID');
    const userID = localStorage.getItem('userID');
    var quizName = localStorage.getItem('quizName');

    
    updateQuiz(courseID, userID, quizName);
});

function updateQuiz(courseID, userID, quizName) {
    // Get updated quiz data from input fields
    const quizNameEdit = document.getElementById('quizName-edit').value;
    const quizCodeEdit = parseInt(document.getElementById('quizCode-edit').value, 10);
    const deadlineEdit = document.getElementById('deadline-edit').value;
    const messageEdit = document.getElementById('message-edit').value;

    const formData = new FormData();
    formData.append('value1', quizNameEdit);
    formData.append('value2', deadlineEdit);
    formData.append('value3', messageEdit);
    formData.append('value4', quizCodeEdit);
    formData.append('adminId', userID);
    formData.append('courseId', courseID);
    formData.append('quizName', quizName);
    console.log(formData);
    

    const xhr = new XMLHttpRequest();
    xhr.open('PUT', 'http://localhost:8090/api/v1/user/updateQuiz', true);
    xhr.onload = function () {
        if (xhr.status === 200) {
            alert('Quiz updated successfully!');
            modal_1.style.display = 'none'; // Close the modal
            document.getElementById('course-form').reset(); // Reset the form
        } else {
            alert('An error occurred!');
        }
    };
    xhr.send(formData);
}

document.addEventListener('click', function(event) {
    event.preventDefault(); // Prevent default action

    if (event.target && event.target.id === 'delete') {


        const courseID = localStorage.getItem('courseID');
        const userID = localStorage.getItem('userID');
        const quizName = event.target.getAttribute('data'); // Retrieve quizName

        if (courseID && userID && quizName) {
            deleteQuiz(courseID, userID, quizName);
        } else {
            console.error('Missing parameters: courseID, userID, or quizName');
        }
    }
});

function deleteQuiz(courseID, userID, quizName) {


    if (courseID) {
        $.ajax({
            url: `http://localhost:8090/api/v1/user/deleteQuiz/${userID}/${courseID}/${quizName}`,
            type: 'DELETE',
            dataType: 'json',
            success: function(r) {
                
            },
            error: function(xhr, status, error) {
                
                if(xhr.status === 200){
                    alert('File deleted successfully');
                }
                else{
                    alert('Failed to delete file');
                }
                
            }
        });
    } else {
        courseDetails.html('<p>No course ID specified.</p>');
    }

}


