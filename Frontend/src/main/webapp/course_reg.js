$(document).ready(function() {
    const courseForm = $('#course-form');
    const courseList = $('#course-list');

    const userID = localStorage.getItem('reg_no');

    //get the year as a Integer
    const currentYear = new Date().getFullYear().toString();
    const subCurrentYear = currentYear.substring(2,4);
    const subCorrentYearInt = parseInt(subCurrentYear,10);
    console.log(subCorrentYearInt);

    //get the batch number as a Integer
    const regNoSubstri = userID.substring(2, 4);
    const regNoInt = parseInt(regNoSubstri,10);
    console.log(regNoInt);

    //get what year the student currently studies at (assumption:-students' currently studeying year changes with the change of the year)
    const studentCurrentYear = subCorrentYearInt - regNoInt - 2 ;
    console.log(studentCurrentYear);

    function findTheStudentYear(studentCurrentYear, courseID) {
        //get the course year(1st,2nd,3rd or 4th), when the students have to select the course(as an integer)
        const courseIDSubstr = courseID.substring(2, 3);
        const courseIDSubstrInt = parseInt(courseIDSubstr,10);

        return studentCurrentYear === courseIDSubstrInt;
    }

    // Fetch and display courses using jQuery AJAX
    $.ajax({
        url: `http://localhost:8090/api/v1/user/regCourse`,
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

                if (findTheStudentYear(studentCurrentYear,courseID)){
                    const courseElement = `
                    <table>
                        <tr class = "table-row">
                            <div class="course-item">
                                <td class="courseID">
                                    <p class="courseID">${courseID}</p>
                                </td>
                                <td>
                                    <p class="courseName">${courseNAME}</p>
                                </td>
                                <td class="checkbox-cell">
                                    <input type = "checkbox" class="checkbox">
                                </td>
                            </div>
                        </tr>
                    </table>
                    `;

                    // Append course element to course list
                    courseList.append(courseElement);
                }
            });
        },
        error: function(xhr, status, error) {
            console.error('Error fetching courses:', error);
        }
    });

});





document.querySelector('.upload-button').addEventListener('click', function (event) {
    event.preventDefault(); 

    const regNo = document.querySelector('.regNoDisplay').textContent.trim();

    const selectedCourses = [];
    document.querySelectorAll('input[type="checkbox"]').forEach((checkbox) => {
        if (checkbox.checked) {
            const courseID = checkbox.closest('tr').querySelector('td:first-child').textContent.trim();
            selectedCourses.push(courseID);
        }
    });

    if (selectedCourses.length === 0) {
        alert('Please select at least one course to enroll.');
        return;
    }

    // Loop through selected courses and register each one
    selectedCourses.forEach(courseID => {
        const data = {
            studentId: regNo,
            courseId: courseID
        };

        // Enroll course via first AJAX call
        $.ajax({
            url: 'http://localhost:8090/api/v1/user/enroll', 
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(data), 
            success: function (response) {
                console.log(`Course ${courseID} registered successfully for student ${regNo}.`);

                // After successful enrollment, run the second AJAX call
                $.ajax({
                    method: "POST",
                    contentType: "application/json",
                    url: 'http://localhost:8090/api/v1/user/saves_attend',
                    async: true,
                    data: JSON.stringify({
                        "student_id": regNo,
                        "course_id": courseID,  // Use individual courseID for each enrollment
                    }),
                    success: function(response) {
                        console.log(`Saved student ${regNo} for course ${courseID} successfully.`);
                    },
                    error: function(xhr, status, error) {
                        console.log(`Error saving student ${regNo} for course ${courseID}.`);
                        console.log(error);
                    }
                });
            },
            error: function (error) {
                console.error(`Error registering course ${courseID} for student ${regNo}.`);
                console.error(error);
            }
        });
    });

    alert('Selected courses are being registered.');
});
