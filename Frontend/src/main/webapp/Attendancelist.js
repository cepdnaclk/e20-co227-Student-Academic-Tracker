$(document).ready(function() {
    const studentist = $('#student-list');

    const urlParams = new URLSearchParams(window.location.search);
    const courseID = urlParams.get('courseID');
    console.log(courseID);
    

    // Fetch and display courses using jQuery AJAX
    $.ajax({
        url: `http://localhost:8090/api/v1/user/getstudentlist/${courseID}`,
        type: 'GET',
        dataType: 'json',
        success: function(studentlist) {

            studentlist.forEach(student => {
                const studentId = student.student_id;
                const days = student.days;

                console.log(studentId);
                console.log(days);

                const studentElement = `
                    <div class="course-item">
                        <h3>${studentId}</h3>
                        <p><strong>Current Attendance:</strong> ${days}</p>
                        <p class="btn btn-primary increase-attendance" data-student-id="${studentId}">Increase Attendance By One</p>
                    </div>
                `;

                // Append course element to course list
                studentist.append(studentElement);
            });
        },
        error: function(xhr, status, error) {
            console.error('Error fetching courses:', error);
        }

    });

    studentist.on('click', '.increase-attendance', function(event) {
        event.preventDefault();

        const studentId = $(this).data('student-id');

        // Make an AJAX request to increase attendance for this student
        $.ajax({
            url: `http://localhost:8090/api/v1/user/attend/${studentId}/${courseID}`,
            type: 'PUT',
            contentType: 'application/json',
            success: function(response) {
                console.log(`Attendance increased for student ${studentId}`);
            },
            error: function(xhr, status, error) {
                console.error('Error increasing attendance:', error);
            }
        });
    });
    

});
