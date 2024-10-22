// Fetch the courseId and reg_no from localStorage
const courseId = localStorage.getItem('selectedCourseId');
const reg_no = localStorage.getItem('reg_no');

// Update the HTML elements with the courseId and reg_no
const courseCodeH1 = document.getElementById('courseCodeH1');
const stu_id = document.getElementById('stu_id');
let day = document.getElementById('day');

courseCodeH1.textContent += ` ${courseId}`;
stu_id.textContent += ` ${reg_no}`;

courseCodeH1.style.color = 'white';
stu_id.style.color = 'white';

// DOM manipulation for profile, sidebar, and notifications
let body = document.body;
let profile = document.querySelector('.header .flex .profile');
let sideBar = document.querySelector('.side-bar');
let notify = document.querySelector('.header .flex .notification');
let search = document.querySelector('.header .flex .search-form');
let dashboard = document.querySelector('.dashboard');

// Toggle user profile
document.querySelector('#user-btn').onclick = () => {
    notify.classList.remove('active');
    profile.classList.toggle('active');
    search.classList.remove('active');
}

// Hide elements on scroll
window.onscroll = () => {
    profile.classList.remove('active');
    search.classList.remove('active');
    notify.classList.remove('active');
    if (window.innerWidth < 1200) {
        sideBar.classList.remove('active');
        body.classList.remove('active');
    }
}

// Toggle sidebar
document.querySelector('#menu-btn').onclick = () => {
    sideBar.classList.toggle('active');
    body.classList.toggle('active');
    dashboard.classList.toggle('active');
    notify.classList.remove('active');
    search.classList.remove('active');
    profile.classList.remove('active');
}

// Close sidebar
document.querySelector('.close-btn').onclick = () => {
    sideBar.classList.toggle('active');
    body.classList.toggle('active');
}

// Toggle notifications
document.querySelector('#notify').onclick = () => {
    profile.classList.remove('active');
    notify.classList.toggle('active');
    search.classList.remove('active');
}

// Function to fetch and store attendance data
function getAttendance() {
    // AJAX call to fetch attendance
    $.ajax({
        method: "GET",
        contentType: "application/json",
        url: `http://localhost:8090/api/v1/user/getAttendance/${courseId}/${reg_no}`,
        async: true,
        success: function(data) {
            let days = data.days; // Assuming `data.days` contains attendance info
            // Store the attendance data in localStorage
            localStorage.setItem('days',JSON.stringify(days));

            let dys = localStorage.getItem('days');
            day.textContent += ` ${days}`;
            day.style.color = 'white';
        },
        error: function(xhr, exception) {
            alert("Failed to fetch attendance data!");
        }
    });
}