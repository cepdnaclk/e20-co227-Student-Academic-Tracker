document.addEventListener('DOMContentLoaded', () => {
    const userID = localStorage.getItem('userID');


    fetch(`http://localhost:8090/api/v1/user/coursesAdmin/${userID}`)
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
                const imageUrl = imageMap[course.course_id];

                // Only generate HTML if the image URL is found
                if (imageUrl) {
                    const flexBoxHtml = `
                        <div class="flex-box">
                            <img src="${imageUrl}" alt="Image">
                            <h2>${course.course_id}</h2>
                            <h3>${course.course_name}</h3>
                            <a href="catagories.html" class="btn" onclick="storeCourseId('${course.course_id}', '${course.course_name}')">Go To Course</a>
                        </div>
                    `;
                    container.innerHTML += flexBoxHtml;
                }
                // If imageUrl is not found, do nothing (course will not be shown)
            });
        })
        .catch(error => console.error('Error fetching course IDs:', error));
});

// Function to store course ID in local storage
function storeCourseId(courseId,courseName) {
    localStorage.setItem('selectedCourseId', courseId);
    localStorage.setItem('selectedCourseName', courseName);
}





