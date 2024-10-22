const courseCode = localStorage.getItem('selectedCourseId');
const assignmentsContainer = document.querySelector('.table-box');
const assignmentsRowsContainer = document.createElement('div');
assignmentsRowsContainer.classList.add('assignments-rows');
assignmentsContainer.appendChild(assignmentsRowsContainer);

if (courseCode) {
  // Fetch assignments associated with the course ID
  fetch(`http://localhost:8090/api/v1/user/assignments/${courseCode}`) 
    .then(response => response.json())
    .then(assignments => {
      console.log('Assignments:', assignments);

      // Create table rows for each assignment
      assignments.forEach(assignment => {
        const assignmentName = assignment.assignmentName;
        const deadline = assignment.deadLine;
        const assignmentId = assignment.id;
        const courseOfferedBy = assignment.adminId;
        localStorage.setItem('courseOfferedBy', courseOfferedBy);
        // Create the HTML for each assignment
        const assignmentRow = `
          <div class="table-row">
            <div class="table-cell first-cell">
              <a href="assignmentDetail.html" onclick="saveDetails('${assignmentName}', '${deadline}' ,'${assignmentId}')">${assignmentName}</a>    <!-- Link to the PDF or download PDF -->
            </div>
            <div class="table-cell dDate">
              <p>${deadline}</p>
            </div>
            <div class="table-cell status">
              <a href="assignmentDetail.html" class="upload" onclick="saveDetails('${assignmentName}', '${deadline}','${assignmentId}')">Upload here</a>    <!-- Link to upload answers -->
            </div>
          </div>
        `;

        // Append the assignment row to the container
        assignmentsRowsContainer.innerHTML += assignmentRow;
      });
    })
    .catch(error => console.error('Error fetching assignments:', error));
} else {
  console.log('No course ID found in local storage.');
}


function saveDetails(assignmentName,deadline,assignmentId) {
    // Save the assignment details to local storage
    localStorage.setItem('assignmentName', assignmentName);
    localStorage.setItem('deadline', deadline);
    localStorage.setItem('AssignmentId', assignmentId);
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
