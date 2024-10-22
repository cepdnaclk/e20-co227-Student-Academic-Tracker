

// Function to show the modal
function uploadBtn() {
    const modal = document.getElementById('course-modal');
    modal.style.display = 'block'; // Show the modal
}








// Function to hide the modal
function closeModal() {
    const modal = document.getElementById('course-modal');
    modal.style.display = 'none'; // Hide the modal
}

// Event listener for the close button in the modal
document.querySelector('.close-btn-addsubmission').addEventListener('click', closeModal);

// Close the modal if the user clicks outside of it
window.addEventListener('click', (event) => {
    const modal = document.getElementById('course-modal');
    if (event.target === modal) {
        closeModal();
    }
});

const assignmentName = localStorage.getItem('assignmentName');
const studentId = localStorage.getItem('reg_no');
const deadline = localStorage.getItem('deadline');
const courseCode = localStorage.getItem('selectedCourseId');
const courseName = localStorage.getItem('selectedCourseName');
const assignmentId = localStorage.getItem('AssignmentId');



const assignmentNameH3 = document.getElementById("assignmentNameH3");
const assignmentDeadline = document.getElementById("deadLine");
const courseCodeId = document.getElementById("courseId");
const courseCodeIdName = document.getElementById("courseName");



assignmentNameH3.textContent = assignmentName;
assignmentDeadline.textContent += deadline;

courseCodeId.textContent = courseCode;
courseCodeIdName.textContent = courseName;

document.addEventListener('DOMContentLoaded', function() {
    const form = document.getElementById('course-form');

    form.addEventListener('submit', function(event) {
        event.preventDefault(); // Prevent default form submission

        const files = document.getElementById('file').files;
        const assignmentName = files[0].name;
        localStorage.setItem('assignmentUploadedFileName', assignmentName);
        const studentID = localStorage.getItem('reg_no');
        const assignmentId = localStorage.getItem('AssignmentId');

        // Extract the text content from the span element, not the element itself
        const courseId = document.getElementById("courseId").textContent.trim();

        // Ensure files are selected
        if (files.length === 0) {
            alert("No files selected!");
            return;
        }

        const formData = new FormData();
        for (let i = 0; i < files.length; i++) {
            formData.append('files', files[i]);
        }
        formData.append('assignmentName', assignmentName);
        formData.append('studentId', studentID);
        formData.append('courseId', courseId);  // Use the text content instead of the DOM element
        formData.append('assignmentId', assignmentId);

        fetch('http://localhost:8090/api/v1/user/saveStdAssignment', {
            method: 'POST',
            body: formData
        })
            .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.text(); // Use .text() instead of .json() to handle non-JSON responses
        })
            .then(data => {
            console.log('Success:', data);
            alert('Files uploaded successfully!');
            // Handle successful response, e.g., clear the form or redirect the user

            // Fetch the updated assignment list after submission
            fetchAssignmentList(studentID, courseId);
        })
            .catch(error => {
            console.error('Error:', error);
            alert('Error uploading files!');
        });
    });

    // Function to fetch assignment list
    function fetchAssignmentList(studentID, courseId) {
        const AssignmentList = $('#AssignmentList');
        const assignmentUploadedFileName = localStorage.getItem('assignmentUploadedFileName');
        const assignmentId = localStorage.getItem('AssignmentId');

        $.ajax({
            url: `http://localhost:8090/api/v1/user/allStdAssignment/${studentID}/${courseId}/${assignmentId}`,
            type: 'GET',
            dataType: 'json',
            data: {
                fileName: assignmentUploadedFileName
            },
            success: function(assignmentsAnswers) {
                console.log('Assignments fetched:', assignmentsAnswers);

                AssignmentList.empty(); // Clear the current list

                if (assignmentsAnswers.length > 0) {
                    const uploadBtn = document.querySelector('.btn.btn-success');
                    uploadBtn.style.display = 'none';
                    assignmentsAnswers.forEach(assignmentAnswer => {
                        const assignmentName = assignmentAnswer.assignmentName;
                        const fileType = assignmentAnswer.fileType; // assuming you have fileType in response

                        console.log('Assignment:', assignmentAnswer); // Log each assignment for debugging

                        const assignmentAnswerElement = `
                        <div class="assignment-item">
                            <h3>Submited Assignment Name: <span class="assignment-name">${assignmentName}</span></h3>
                            <a href="//localhost:8090/api/v1/user/getStdfile/${studentID}/${courseId}?fileName=${assignmentName}${fileType}" class="btn btn-primary">Download Your submited File</a>
                            <a href="#" class="btn btn-danger" onclick="deleteFile('${assignmentName}', '${studentID}', '${courseId}')">Delete file</a>
                        </div>
                    `;

                        AssignmentList.append(assignmentAnswerElement); // Append the new element to the list
                    });
                } else {
                    AssignmentList.html('<p>No answer submited.</p>');
                }
            },
            error: function(xhr, status, error) {
                AssignmentList.html('<p>Unable to fetch assignment details.</p>');
            }
        });
    }

    // Initial load of the assignment list
    const studentID = localStorage.getItem('reg_no');
    const courseId = localStorage.getItem('selectedCourseId');


    fetchAssignmentList(studentID, courseId);



    const courseID = localStorage.getItem('selectedCourseId');
    const userID = localStorage.getItem('courseOfferedBy');
    const assignmentID = localStorage.getItem('AssignmentId');

    if (courseID) {
        $.ajax({
            url: `http://localhost:8090/api/v1/user/allAssignment/${userID}/${courseID}`,
            type: 'GET',
            dataType: 'json',
            success: function(assignments) {
                const filteredAssignments = assignments.filter(assignment => assignment.id == assignmentID);
                console.log('Assignment ID from localStorage:', assignmentID);
                console.log('Filtered Assignments:', filteredAssignments);
                if (filteredAssignments.length > 0) {
                    const assignment = filteredAssignments[0];
                    const assignmentName = assignment.assignmentName;
                    const assignmentDeadline = assignment.deadLine;
                    const assignmentMessage = assignment.message;
                    const dataType = assignment.fileType;
                    const assignmentIdInTable = assignment.id;
                    console.log('assignmentIdInTable:', assignmentIdInTable);
                    console.log('Assignment:', assignment); // Log each assignment for debugging

                    $('#assignmentNameH3').text(assignmentName); // Update the assignment name
                    $('#deadLine').text(`Due Date: ${assignmentDeadline}`); // Update the deadline

                    const assignmentListElement = $('#assignment-list'); // Select the element where you want to append the assignment list

                    // Loop over the filteredAssignments array (if it's intended to have multiple)
                    filteredAssignments.forEach(assignment => {
                        const assignmentElement = `
                            <div>
                                <a href="//localhost:8090/api/v1/user/getfile/${userID}/${courseID}?fileName=${assignment.assignmentName}${assignment.fileType}" class="btn btn-down">Download file</a>
                            </div>
                        `;
                        // Use append() to add the HTML code without replacing existing content
                        assignmentListElement.append(assignmentElement);
                    });
                } else {
                    const courseDetailsElement = $('#course-details'); // Select the element where you want to display the message
                    courseDetailsElement.html('<p>No assignments found.</p>');
                }
            },
            error: function(xhr, status, error) {
                console.log('Error occurred:', status, error);
                const courseDetailsElement = $('#course-details'); // Select the element where you want to display the error message
                courseDetailsElement.html('<p>Unable to fetch assignment details.</p>');
            }
        });
    } else {
        const courseDetailsElement = $('#course-details'); // Select the element where you want to display the message
        courseDetailsElement.html('<p>No course ID specified.</p>');
    }

});

function deleteFile(assignmentName, studentID, courseID, assignmentId) {

    fetch('http://localhost:8090/api/v1/user/studentDelete', {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
        },
        body: new URLSearchParams({
            assignmentName: assignmentName,
            studentId: studentID,
            courseId: courseID,
            assignmentId:assignmentId,
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

