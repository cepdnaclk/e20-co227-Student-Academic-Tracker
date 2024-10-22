// Get courseId from localStorage and display in the heading
const courseId = localStorage.getItem('selectedCourseId');
const courseCodeH1 = document.getElementById('courseCodeH1');
courseCodeH1.textContent += ` ${courseId}`;
courseCodeH1.style.color = 'white';

// Setup dynamic links based on user role
document.addEventListener('DOMContentLoaded', function() {
    let userRole = localStorage.getItem('userRole'); // 'Admin' or 'Student'

    let examMarksBtn = document.getElementById('examMarksBtn');
    let assignmentMarksBtn = document.getElementById('assignmentMarksBtn'); // corrected id name

    if (userRole === 'Admin') {
        examMarksBtn.href = 'ExamMarkSheet.html'; // Admin view
    } else {
        examMarksBtn.href = 'ExamMarkStudent.html'; // Student view
    }

    if (userRole === 'Admin') {
        assignmentMarksBtn.href = 'AssignmentMarks.html'; // Admin view
    } else {
        assignmentMarksBtn.href = 'AssignmentMarksStudent.html'; // Student view
    }
});

// Popup functions
let popup = document.getElementById("popup");

function openPopup() {
    popup.classList.add("open-popup");
}

function closePopup() {
    popup.classList.remove("open-popup");
}

// Function to submit form data (Exam, Assignments, Quizzes, and courseId) to backend
function submitWeights(event) {
    event.preventDefault();

    // Get values from the form
    let exam = document.getElementById('exam').value;
    let assignments = document.getElementById('assignments').value;
    let quizzes = document.getElementById('quizzes').value;
    
    // Fetch courseId from localStorage
    const courseId = localStorage.getItem('selectedCourseId');

    // Data to send to backend
    let formData = {
        examPercentage: parseFloat(exam),           // Adjusted name to match backend
        assignmentPercentage: parseFloat(assignments), // Adjusted name to match backend
        quizPercentage: parseFloat(quizzes),        // Adjusted name to match backend
        courseId: courseId
    };


    $.ajax({
        type: "POST", 
        url: "http://localhost:8090/api/v1/user/courseWeights", 
        contentType: "application/json", 
        data: JSON.stringify(formData), // Convert formData to JSON string
        success: function(response) {
            console.log('Data sent successfully:', response);
            // Optionally, show a success message or perform further actions
        },
        error: function(error) {
            console.error('Error sending data:', error);
            // Optionally, show an error message
        }
    });
}

// event listener to your form's submit button (trigger submitWeights function on click)
document.getElementById("loginForm").addEventListener("submit", submitWeights);
