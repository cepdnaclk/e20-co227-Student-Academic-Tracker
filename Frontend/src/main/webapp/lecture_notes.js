const courseCode = localStorage.getItem('selectedCourseId');
const lectureNotesContainer = document.querySelector('.table-box');
const lectureNotesRowsContainer = document.createElement('div');
lectureNotesRowsContainer.classList.add('assignments-rows');
lectureNotesContainer.appendChild(lectureNotesRowsContainer);
const studentId = localStorage.getItem('reg_no');

if (courseCode) {
  // Fetch lecture notes associated with the course ID
  fetch(`http://localhost:8090/api/v1/user/lectureNotes/${courseCode}`)
    .then(response => response.json())
    .then(lectureNotes => {
      console.log('LectureNotes:', lectureNotes);

      // Create table rows for each lecture note
      lectureNotes.forEach(lectureNote => {
        const lectureNoteName = lectureNote.lectureNoteName;
        const publishDate = lectureNote.publishDate;
        const lectureNoteId = lectureNote.id;
        const courseOfferedBy = lectureNote.adminId;
        localStorage.setItem('courseOfferedBy', courseOfferedBy);
        const dataType = lectureNote.fileType;
        // Create the HTML for each lecture note
        const lectureNoteRow = `
          <div class="table-row">
            <div class="table-cell first-cell">
              <a href="//localhost:8090/api/v1/user/getLectureNoteFile/${studentId}/${courseCode}?fileName=${lectureNoteName}${dataType}" class="btn btn-primary">${lectureNoteName}</a>
            </div>
            <div class="table-cell dDate">
              <p>${publishDate}</p>
            </div>
          </div>
        `;

        lectureNotesRowsContainer.innerHTML += lectureNoteRow;
      });
    })
    .catch(error => console.error('Error fetching lecture notes:', error));
} else {
  console.log('No student id found in local storage.');
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
