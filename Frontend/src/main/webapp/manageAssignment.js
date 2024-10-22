function deleteFile(assignmentName, userID, courseID) {
    fetch('http://localhost:8090/api/v1/user/delete', {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
        },
        body: new URLSearchParams({
            assignmentName: assignmentName,
            adminId: userID,
            courseId: courseID,
        }),
    })
    .then(response => {
        if (response.ok) {
            alert('File deleted successfully');
        } else {
            alert('Failed to delete file');
        }
    })
    .catch(error => {
        console.error('Error:', error);
    });
}

function editFile(assignmentName, userID, courseID, deadLine) {
    let modal = document.getElementById("course-modal-edit");
    let span = document.getElementsByClassName("close-btn-addcourse-edit")[0];
    let updateBtn = document.getElementById("save_course-edit");

    // Open the modal
    modal.style.display = "block";

    // Fetch the assignment data from the server
    fetch('http://localhost:8090/api/v1/user/edit', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            assignmentName: assignmentName,
            deadLine: deadLine,
            adminId: userID,
            courseId: courseID
        })
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        return response.json();
    })
    .then(data => {
        if (data) {
            document.getElementById("assignment-name-edit").value = data.assignmentName;
            document.getElementById("deadline-edit").value = data.deadLine;
            document.getElementById("message-edit").value = data.message;
            document.getElementById("file-edit").value = '';
        } else {
            throw new Error('No data returned from server');
        }
    })
    .catch(error => {
        console.error('Error fetching assignment data:', error);
        alert('Failed to fetch assignment data.');
    });

    updateBtn.onclick = function() {
        const updatedAssignmentName = document.getElementById("assignment-name-edit").value;
        const updatedDeadLine = document.getElementById("deadline-edit").value;
        const updatedMessage = document.getElementById("message-edit").value;

        console.log('Updated Assignment Name:', updatedAssignmentName);
        console.log('Updated DeadLine:', updatedDeadLine);
        console.log('Updated Message:', updatedMessage);
        console.log('User ID:', userID);
        console.log('Course ID:', courseID);

        fetch('http://localhost:8090/api/v1/user/saveedit', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                assignmentName: updatedAssignmentName,
                deadLine: updatedDeadLine,
                adminId: userID,
                courseId: courseID,
                message: updatedMessage
            })
        })
        .then(response => {
            if (!response.ok) {
                return response.json().then(err => {throw err;}); // Convert non-OK responses to JSON to throw as error
            }
            return response.json();
        })
        .then(data => {
            console.log('Update successful:', data);
            alert('Assignment updated successfully!');
            modal.style.display = "none";
            // Reset the form fields
            document.getElementById("assignment-name-edit").value = '';
            document.getElementById("deadline-edit").value = '';
            document.getElementById("message-edit").value = '';
        })
        .catch(error => {
            console.error('Error updating assignment:', error);
            alert('Failed to update assignment.');
        });
    };


    // Close the modal when the x is clicked
    span.onclick = function() {
        modal.style.display = "none";
    };
}

document.addEventListener('click', function(event) {
    if (event.target && event.target.classList.contains('edit-btn')) {
        const assignmentName = event.target.getAttribute('data-assignment-name');
        const userID = event.target.getAttribute('data-user-id');
        const courseID = event.target.getAttribute('data-course-id');
        const deadLine = event.target.getAttribute('data-deadline');

        // Call the editFile function with the appropriate arguments
        editFile(assignmentName, userID, courseID, deadLine);
    }
});

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
                    <div class="course-item">r
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

    const AssignmentList = $('#AssignmentList');

    if (courseID) {
        $.ajax({
            url: `http://localhost:8090/api/v1/user/allAssignment/${userID}/${courseID}`,
            type: 'GET',
            dataType: 'json',
            success: function(assignments) {
                console.log('Assignments fetched:', assignments);

                assignments.forEach(assignment => {
                    const assignmentName = assignment.assignmentName;
                    const assignmentDeadline = assignment.deadLine;
                    const assignmentMessage = assignment.message;
                    const dataType = assignment.fileType;

                    console.log('Assignment:', assignment); // Log each assignment for debugging

                    const assignmentElement = `
                    <div class="assignment-item">
                        <h3>Assignment Name: ${assignmentName}</h3>
                        <p><strong>Assignment Deadline:</strong> ${assignmentDeadline}</p>
                        <p><strong>Assignment Message:</strong> ${assignmentMessage}</p>
                        <a href="//localhost:8090/api/v1/user/getfile/${userID}/${courseID}?fileName=${assignmentName}${dataType}" class="btn btn-primary">Download file</a>
                        <a href="#" class="btn btn-primary edit-btn" data-assignment-name="${assignmentName}" data-user-id="${userID}" data-course-id="${courseID}" data-deadline="${assignmentDeadline}">Edit file</a>
                        <a href="#" class="btn btn-danger" onclick="deleteFile('${assignmentName}', '${userID}', '${courseID}')">Delete file</a>
                    </div>
                `;

                    // Use html() to append HTML code
                    AssignmentList.append(assignmentElement);
                });
            },
            error: function(xhr, status, error) {
                courseDetails.html('<p>Unable to fetch assignment details.</p>');
            }
        });
    } else {
        courseDetails.html('<p>No course ID specified.</p>');
    }

    // Bind the uploadFiles function to form submission
    $('#course-form').on('submit', function(event) {
        event.preventDefault(); // Prevent default form submission
        uploadFiles(); // Call the uploadFiles function
    });

    function uploadFiles() {
        const fileInput = document.getElementById('file');
        const fileNameInput = document.getElementById('assignment-name');
        const deadlineInput = document.getElementById('deadline');
        const messageInput = document.getElementById('message');

        // Create a FormData object
        const formData = new FormData();

        // Append file(s)
        if (fileInput.files.length > 0) {
            formData.append('files', fileInput.files[0]);
        }

        // Append additional data
        formData.append('assignmentName', fileNameInput.value);
        formData.append('deadline', deadlineInput.value);
        formData.append('message', messageInput.value);
        formData.append('adminId', userID);
        formData.append('courseId', courseID);

        const xhr = new XMLHttpRequest();
        xhr.open('POST', 'http://localhost:8090/api/v1/user/saveAssignment', true);

        xhr.onload = function () {
            if (xhr.status === 200) {
                alert('Files uploaded successfully!');
                modal.style.display = "none";
                $('#course-form')[0].reset(); // Reset the form
            } else {
                alert('An error occurred!');
            }
        };

        xhr.send(formData);
    }
});
