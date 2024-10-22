const today = new Date();

function deleteFile(lectureNoteName, userID, courseID) {
    fetch('http://localhost:8090/api/v1/user/deleteLectureNote', {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
        },
        body: new URLSearchParams({
            lectureNoteName: lectureNoteName,
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


function editFile(lectureNoteName, userID, courseID, publishDate) {               
    let modal = document.getElementById("course-modal-edit");
    let span = document.getElementsByClassName("close-btn-addcourse-edit")[0];
    let updateBtn = document.getElementById("save_course-edit");

    // Open the modal
    modal.style.display = "block";

    // Fetch the assignment data from the server
    fetch('http://localhost:8090/api/v1/user/editLectureNote', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            lectureNoteName: lectureNoteName,
            publishDate: publishDate,
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
            document.getElementById("lectureNote-name-edit").value = data.lectureNoteName;
            document.getElementById("publishDate-edit").value = data.publishDate;
            document.getElementById("message-edit").value = data.message;
            document.getElementById("file-edit").value = '';
        } else {
            throw new Error('No data returned from server');
        }
    })
    .catch(error => {
        console.error('Error fetching lecture note data:', error);
        alert('Failed to fetch lecture note data.');
    });

    updateBtn.onclick = function() {
        const updatedLectureNoteName = document.getElementById("lectureNote-name-edit").value;
        const updatedPublishDate = document.getElementById("publishDate-edit").value;
        const updatedMessage = document.getElementById("message-edit").value;

        console.log('Updated LectureNote Name:', updatedLectureNoteName);
        console.log('Updated PublishDate:', updatedPublishDate);
        console.log('Updated Message:', updatedMessage);
        console.log('User ID:', userID);
        console.log('Course ID:', courseID);

        fetch('http://localhost:8090/api/v1/user/saveLectureNoteEdit', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                lectureNoteName: updatedLectureNoteName,
                publishDate: updatedPublishDate,
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
            alert('Lecture Note updated successfully!');
            modal.style.display = "none";
            // Reset the form fields
            document.getElementById("lectureNote-name-edit").value = '';
            document.getElementById("publishDate-edit").value = '';
            document.getElementById("message-edit").value = '';
        })
        .catch(error => {
            console.error('Error updating lecture note:', error);
            alert('Failed to update lecture note.');
        });
    };


    // Close the modal when the x is clicked
    span.onclick = function() {
        modal.style.display = "none";
    };
}

document.addEventListener('click', function(event) {
    if (event.target && event.target.classList.contains('edit-btn')) {
        const lectureNoteName = event.target.getAttribute('data-lectureNote-name');
        const userID = event.target.getAttribute('data-user-id');
        const courseID = event.target.getAttribute('data-course-id');
        const publishDate = event.target.getAttribute('data-publishDate');

        // Call the editFile function with the appropriate arguments
        editFile(lectureNoteName, userID, courseID, publishDate);
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
                //////////////////////////////////////////////////
                console.log('courseNAME:', courseNAME);
                console.log('courseID:', courseID);
                
            },
            error: function(xhr, status, error) {
                console.error('Error fetching course details:', error);
                courseDetails.html('<p>Unable to fetch course details.</p>');
            }
        });
    } else {
        courseDetails.html('<p>No course ID specified.</p>');
    }

    const LectureNoteList = $('#LectureNoteList');

    if (courseID) {
        $.ajax({
            url: `http://localhost:8090/api/v1/user/allLectureNote/${userID}/${courseID}`,
            type: 'GET',
            dataType: 'json',
            success: function(lectureNote) {
                console.log('LectureNotes fetched:', lectureNote);

                lectureNote.forEach(lectureNote => {
                    const lectureNoteName = lectureNote.lectureNoteName;
                    const lectureNotePublishDate = lectureNote.publishDate;
                    const lectureNoteMessage = lectureNote.message;
                    const dataType = lectureNote.fileType;

                    console.log('LectureNote:', lectureNote); // Log each assignment for debugging

                    const lectureNoteElement = `
                    <div class="lectureNote-item">
                        <h3>Lecture Note: ${lectureNoteName}</h3>
                        <p><strong>Lecture Note Published Date:</strong> ${lectureNotePublishDate}</p>
                        <p><strong>Message:</strong> ${lectureNoteMessage}</p>
                        <a href="//localhost:8090/api/v1/user/getLectureNoteFile/${userID}/${courseID}?fileName=${lectureNoteName}${dataType}" class="btn btn-primary">Download file</a>
                        <a href="#" class="btn btn-primary edit-btn" data-lectureNote-name="${lectureNoteName}" data-user-id="${userID}" data-course-id="${courseID}" data-deadline="${lectureNotePublishDate}">Edit file</a>
                        <a href="#" class="btn btn-danger" onclick="deleteFile('${lectureNoteName}', '${userID}', '${courseID}')">Delete file</a>
                    </div>
                `;

                    // Use html() to append HTML code
                    LectureNoteList.append(lectureNoteElement);
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
        const fileNameInput = document.getElementById('lectureNote-name');
        const publishDateInput = document.getElementById('publishDate');
        const messageInput = document.getElementById('message');

        // Create a FormData object
        const formData = new FormData();

        // Append file(s)
        if (fileInput.files.length > 0) {
            formData.append('files', fileInput.files[0]);
        }

        // Append additional data
        formData.append('lectureNoteName', fileNameInput.value);
        formData.append('publishDate', publishDateInput.value);
        formData.append('message', messageInput.value);
        formData.append('adminId', userID);
        formData.append('courseId', courseID);

        const xhr = new XMLHttpRequest();
        xhr.open('POST', 'http://localhost:8090/api/v1/user/saveLectureNote', true);

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
