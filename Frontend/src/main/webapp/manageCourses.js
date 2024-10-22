let body = document.body;
let profile = document.querySelector('.header .flex .profile');
let sideBar = document.querySelector('.side-bar');
let notify = document.querySelector('.header .flex .notification');
let search = document.querySelector('.header .flex .search-form');
let dashboard = document.querySelector('.dashboard');
let courseAdd = document.getElementById('course-modal'); 

document.querySelector('#user-btn').onclick = () => {
    // Toggle profile and remove active state from others
    notify.classList.remove('active');
    profile.classList.toggle('active');
    search.classList.remove('active');
    courseAdd.classList.remove('active');
}

window.onscroll = () => {
    // Remove active states on scroll
    profile.classList.remove('active');
    search.classList.remove('active');
    notify.classList.remove('active');

    if (window.innerWidth < 1200) {
        sideBar.classList.remove('active');
        body.classList.remove('active');
        courseAdd.classList.remove('active');
    }
}

document.querySelector('#menu-btn').onclick = () => {
    // Toggle sidebar and body active states, remove others
    sideBar.classList.toggle('active');
    body.classList.toggle('active');
    dashboard.classList.toggle('active');
    notify.classList.remove('active');
    search.classList.remove('active');
    profile.classList.remove('active');
    courseAdd.classList.remove('active');
}

document.querySelector('.close-btn').onclick = () => {
    // Toggle sidebar and body active states
    sideBar.classList.toggle('active');
    body.classList.toggle('active');
    courseAdd.classList.remove('active');
    courseAdd.classList.remove('active');
}

document.querySelector('#notify').onclick = () => {
    // Toggle notify and remove active state from others
    profile.classList.remove('active');
    notify.classList.toggle('active');
    search.classList.remove('active');
    courseAdd.classList.remove('active');
}

document.querySelector('#add-course-btn').onclick = () => {
    // Toggle course modal and remove active state from others
    courseAdd.classList.toggle('active');
    profile.classList.remove('active');
    notify.classList.remove('active');
    search.classList.remove('active');
}

document.querySelector('.close-btn-addcourse').onclick = () => {
    // Toggle course modal and remove active state from others
    courseAdd.classList.remove('active');

}


$(document).ready(function() {
    const courseForm = $('#course-form');
    const courseList = $('#course-list');

    const userID = localStorage.getItem('userID');

    // Fetch and display courses using jQuery AJAX
    $.ajax({
        url: `http://localhost:8090/api/v1/user/coursesAdmin/${userID}`,
        type: 'GET',
        dataType: 'json',
        success: function(courses) {
            console.log(courses); // Log the fetched data for debugging

            courses.forEach(course => {
                const courseID = course.course_id;
                const courseDES = course.course_description;
                const courseNAME = course.course_name;

                console.log(courseID);
                console.log(courseDES);
                console.log(courseNAME);

                const courseElement = `
                    <div class="course-item">
                        <h3>${courseNAME}</h3>
                        <p><strong>Course Code:</strong> ${courseID}</p>
                        <p><strong>Description:</strong> ${courseDES}</p>
                        <a href="manageLectureNote.html?courseID=${courseID}" class="btn btn-primary">Lecture Notes</a>
                        <a href="manageAssigment.html?courseID=${courseID}" class="btn btn-primary">Assignment</a>
                        <a href="manageQuiz.html?courseID=${courseID}" class="btn btn-primary">Quiz</a>
                    </div>
                `;

                // Append course element to course list
                courseList.append(courseElement);
            });
        },
        error: function(xhr, status, error) {
            console.error('Error fetching courses:', error);
        }
    });

    // Event listener for form submission
    courseForm.on('submit', function(event) {
        event.preventDefault(); // Prevent default form submission

        // Get form values
        const courseName = $('#course-name').val();
        const courseCode = $('#course-code').val();
        const courseDescription = $('#course-description').val();

        // Send course data to the backend using jQuery AJAX
        const courseData = {
            course_name: courseName,
            course_id: courseCode,
            course_description: courseDescription,
            course_offered_by: userID
        };

        console.log(courseData);
        $.ajax({
            url: 'http://localhost:8090/api/v1/user/savecourse',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(courseData),
            success: function(data) {
                console.log('Success:', data);
                // Optionally, you can update the course list or display a success message here
            },
            error: function(xhr, status, error) {
                console.error('Error:', error);
            }
        });
    });
});
