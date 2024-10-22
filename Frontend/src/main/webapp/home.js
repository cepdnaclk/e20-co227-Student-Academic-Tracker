let body = document.body;
let profile = document.querySelector('.header .flex .profile');
let sideBar = document.querySelector('.side-bar');
let notify = document.querySelector('.header .flex .notification');
let search = document.querySelector('.header .flex .search-form');
localStorage.setItem('userRole', 'Student');

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

function fetchNotifications() {
    const userId = localStorage.getItem('reg_no');
    fetch(`http://localhost:8090/api/v1/user/unread/${userId}`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        }
    })
        .then(response => {
        if (!response.ok) {
            throw new Error(`Error: ${response.status}`); // Handle non-2xx status codes
        }
        return response.json(); // Parse the JSON response
    })
        .then(data => {
        console.log(data);
        displayNotifications(data); // Display notifications with the fetched data
    })
        .catch(error => {
        console.error('Error fetching notifications:', error); // Log any errors that occur
    });
}
fetchNotifications();

const notificationDot = document.getElementById('notification-dot');

function displayNotifications(notifications) {
    const notificationList = document.querySelector('#notification-list');
    console.log('notifications list:', notificationList);
    notificationList.innerHTML = ''; // Clear previous notifications

    if (notifications.length > 0) {
        notificationDot.style.display = 'block';
        notifications.forEach(notification => {
            const li = document.createElement('li');
            li.textContent = "New Assignment added in " + notification.course_id;
            li.onclick = () => {
                markNotificationAsRead(notification.assignment.id);
                location.reload(); // Add this line to refresh the page
            };

            if (!notification.read) {
                li.classList.add('unread');
            }
            console.log(li.textContent);
            notificationList.appendChild(li);
        });
    } else {
        // If no notifications are available, show a message
        notificationList.innerHTML = '<li>No new notifications</li>';
    }

}

function markNotificationAsRead(notificationId) {
    const student_id = localStorage.getItem('reg_no'); // Get the actual logged-in student's ID
    fetch(`http://localhost:8090/api/v1/user/read/${student_id}/${notificationId}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
        }
    })
        .then(response => {
        console.log(response.body);
        if (!response.ok) {
            throw new Error('Failed to mark notification as read');
        }
        return response.json();
    })
        .then(data => {
        console.log('Notification marked as read:', data);
        // Optionally, update the UI here to reflect the read status
        fetchNotifications(); // Re-fetch notifications to update the display
    })
        .catch(error => {
        console.error('Error:', error);
    });
}