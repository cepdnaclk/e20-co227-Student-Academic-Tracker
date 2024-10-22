
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
                        <a href="manageAssigment.html?courseID=${courseID}" class="btn btn-primary">Assignment</a>
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

});
