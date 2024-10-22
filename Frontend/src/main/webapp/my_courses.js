document.addEventListener('DOMContentLoaded', () => {
    const userID = localStorage.getItem('reg_no');

    $(document).ready(function() {
        $('#calculate-gpa-btn').click(function() {
            // Get the student ID (you can replace this with actual logic to get the ID)
            
    
            // Make AJAX GET request to the Spring Boot controller
            $.ajax({
                url: `http://localhost:8090/api/v1/user/average_result/${userID}`, // Endpoint URL
                type: 'GET', // Request type
                success: function(response) {
                    // Handle the successful response here
                    // You might want to display the result in the popup
                    $('.content').text(`Your Current GPA = ${response}`);
                    openPopup(); // Call your function to open the popup
                },
                error: function(xhr, status, error) {
                    // Handle errors here
                    alert('Error: ' + error);
                }
            });
        });
    });
    

    // Get the year as an Integer
    const currentYear = new Date().getFullYear().toString();
    const subCurrentYear = currentYear.substring(2, 4);
    const subCorrentYearInt = parseInt(subCurrentYear, 10);
    console.log(subCorrentYearInt);

    const regNoSubstri = userID.substring(2, 4);
    const regNoInt = parseInt(regNoSubstri, 10);
    console.log(regNoInt);

    const studentCurrentYear = subCorrentYearInt - regNoInt - 2;
    console.log(studentCurrentYear);

    function findTheStudentYear(studentCurrentYear, courseID) {
        // Get the course year (1st, 2nd, 3rd, or 4th)
        const courseIDSubstr = courseID.substring(2, 3);
        const courseIDSubstrInt = parseInt(courseIDSubstr, 10);

        return studentCurrentYear === courseIDSubstrInt;
    }

    fetch(`http://localhost:8090/api/v1/user/courses/${userID}`)
        .then(response => response.json())
        .then(courses => {
            console.log('Courses:', courses);
            const container = document.querySelector('.flex-container');
            container.innerHTML = ''; // Clear existing content

            // Define a mapping for course IDs to image URLs
            const imageMap = {
                'CO200': 'img/course_bg02.jpg',
                'EE282': 'img/course_bg04.jpg',
                'CO224': 'img/course_bg03.jpeg',
                'GP114': 'img/course_bg01.jpg'
                // Add more course mappings as needed
            };

            // Process each course
            courses.forEach(course => {
                const courseID = course.course_id;
                const imageUrl = imageMap[courseID]; // Get image URL for the course

                // Only generate HTML if the image URL is found and the year matches
                if (imageUrl && findTheStudentYear(studentCurrentYear, courseID)) {
                    const flexBoxHtml = `
                        <div class="flex-box">
                            <img src="${imageUrl}" alt="Image">
                            <h2>${course.course_id}</h2>
                            <h3>${course.course_name}</h3>
                            <a href="selectCourse.html" class="btn" onclick="storeCourseId('${course.course_id}', '${course.course_name}')">Go To Course</a>
                        </div>
                    `;
                    container.innerHTML += flexBoxHtml;
                }
            });
        })
        .catch(error => console.error('Error fetching course IDs:', error));


});



// Function to store course ID in local storage
function storeCourseId(courseId,courseName) {
    localStorage.setItem('selectedCourseId', courseId);
    localStorage.setItem('selectedCourseName', courseName);
}




let body = document.body;
let profile = document.querySelector('.header .flex .profile');
let sideBar = document.querySelector('.side-bar');
let notify = document.querySelector('.header .flex .notification');
let search = document.querySelector('.header .flex .search-form');

document.querySelector('#user-btn').onclick = () => {
    // Toggle profile and remove active state from others
    notify.classList.remove('active');
    profile.classList.toggle('active');
    search.classList.remove('active');
}

window.onscroll = () => {
    // Remove active states on scroll
    profile.classList.remove('active');
    search.classList.remove('active');
    notify.classList.remove('active');

    if (window.innerWidth < 1200) {
        sideBar.classList.remove('active');
        body.classList.remove('active');
    }
}

document.querySelector('#menu-btn').onclick = () => {
    // Toggle sidebar and body active states, remove others
    sideBar.classList.toggle('active');
    body.classList.toggle('active');
    notify.classList.remove('active');
    search.classList.remove('active');
    profile.classList.remove('active');
}

document.querySelector('.close-btn').onclick = () => {
    // Toggle sidebar and body active states
    sideBar.classList.toggle('active');
    body.classList.toggle('active');
}

document.querySelector('#notify').onclick = () => {
    // Toggle notify and remove active state from others
    profile.classList.remove('active');
    notify.classList.toggle('active');
    search.classList.remove('active');
}
