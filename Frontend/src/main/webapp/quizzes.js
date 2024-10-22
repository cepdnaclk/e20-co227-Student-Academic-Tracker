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

document.addEventListener('DOMContentLoaded', function() {

    const courseId = localStorage.getItem('selectedCourseId');
    
    fetch(`http://localhost:8090/api/v1/user/getquiz/${courseId}`)
        .then(response => response.json())
        .then(data => {
            const tableBody = document.getElementById('quizTableBody');

            console.log(data);
            

            data.forEach(quiz => {
                // Create a new row for each quiz
                const row = document.createElement('div');
                row.className = 'table-row';

                // Create the Quiz Title with a link
                const titleCell = document.createElement('div');
                titleCell.className = 'table-cell first-cell';
                const link = document.createElement('a');
                link.href = 'https://worksheetzone.org/'; 
                link.textContent = quiz.quizName;
                titleCell.appendChild(link);
                row.appendChild(titleCell);

                // Create the Date cell
                const dateCell = document.createElement('div');
                dateCell.className = 'table-cell';
                dateCell.innerHTML = `<p>${quiz.deadLine}</p>`;
                row.appendChild(dateCell);

                // Create the Time cell
                const messageCell = document.createElement('div');
                messageCell.className = 'table-cell';
                messageCell.innerHTML = `<p>${quiz.message}</p>`;
                row.appendChild(messageCell);

                const CodeCell = document.createElement('div');
                CodeCell.className = 'table-cell';
                CodeCell.innerHTML = `<p>${quiz.quizCode}</p>`;
                row.appendChild(CodeCell);

                // Append the row to the table body
                tableBody.appendChild(row);
            });
        })
        .catch(error => {
            console.error('Error fetching quiz data:', error);
        });
});

