    const courseId = localStorage.getItem('selectedCourseId'); // Retrieve selected course ID from localStorage
    const regNo = localStorage.getItem('reg_no'); // Retrieve student registration number from localStorage
    const courseCodeH1 = document.getElementById('courseCodeH1'); // Get the heading element to display course code

    // Display the course ID in the heading
    courseCodeH1.textContent += ` ${courseId}`;
    courseCodeH1.style.color = 'white'; // Change the text color to white

    /**
     * This function fetches the course weights (e.g., exam, assignment, quiz percentages)
     * and calculates the final score for a student based on their marks.
     */
    document.addEventListener('DOMContentLoaded', function() {


        let userRole = localStorage.getItem('userRole'); // 'Admin' or 'Student'
    
    
        let examMarksBtn = document.getElementById('examMarksBtn');
        let assignmentMarksBtn = document.getElementById('assignmentMarksBtn');
    
        if (userRole === 'Admin') {
            examMarksBtn.href = 'ExamMarkSheet.html'; // Admin view
        } else {
            examMarksBtn.href = 'ExamMarksStudent.html'; // Student view
        }
    
        if (userRole === 'Admin') {
            assignmentMarksBtn.href = 'AssignmentMarks.html'; // Admin view
        } else {
            assignmentMarksBtn.href = 'AssignmentMarksStudent.html'; // Student view
        }
    function fetchCourseWeightsAndCalculateFinalValue(courseId, regNo) {
        // First AJAX request to get the weights of the exam, assignment, and quiz
        $.ajax({
            url: `http://localhost:8090/api/v1/user/courseWeights/${courseId}`, // Backend API to get course weights
            type: 'GET',
            dataType: 'json', // Expect a JSON response
            success: function(weights) {
                console.log('Course Weights:', weights); // Log the course weights for debugging

                // Extract the weights, defaulting to 0 if any are missing
                const examWeight = weights.examPercentage || 0;
                const assignmentWeight = weights.assignmentPercentage || 0;
                const quizWeight = weights.quizPercentage || 0;

                // Second AJAX request to get the combined marks for the student in this course
                $.ajax({
                    url: `http://localhost:8090/api/v1/user/combinedMarks?studentId=${regNo}&courseId=${courseId}`, // Backend API to get student marks
                    type: 'GET',
                    dataType: 'json', // Expect a JSON response
                    success: function(data) {
                        console.log('Combined Marks:', data); // Log the marks for debugging

                        let finalScore = 0; // Initialize the final score accumulator
                        let totalWeight = examWeight + assignmentWeight + quizWeight; // Sum the total weight

                        // Iterate over each mark received for the student
                        data.forEach(mark => {
                            const source = mark.source; // Mark source (exam, assignment, or quiz)
                            const percentage = mark.percentage; // The percentage value of the mark

                            // Add the weighted contribution to the final score
                            if (source === 'exam') {
                                finalScore += (percentage * examWeight) / 100;
                            } else if (source === 'assignment') {
                                finalScore += (percentage * assignmentWeight) / 100;
                            } else if (source === 'quiz') {
                                finalScore += (percentage * quizWeight) / 100;
                            }
                        });

                        // Calculate the final percentage based on the weighted score
                        const finalPercentage = (finalScore / totalWeight) * 100;

                        // Calculate the grade based on finalPercentage
                        const grade = calculateGrade(finalPercentage);

                        // Display the final score and grade in the DOM
                        console.log(`Final Score for student ID ${regNo} in course ${courseId}: ${finalPercentage.toFixed(2)}%, Grade: ${grade}`);
                        $('#finalScoreContainer').append(`<p>Final Score: ${finalPercentage.toFixed(2)}%, Grade: ${grade}</p>`);

                        // After calculating the final percentage and grade, save it to the backend
                        saveFinalPercentage(courseId, regNo, finalPercentage.toFixed(2), grade);
                    },
                    error: function(xhr, status, error) {
                        console.error('Error fetching combined marks:', error); // Log the error if fetching marks fails
                        if (xhr.status === 403) {
                            console.error('Access denied: You do not have permission to access this resource.');
                        } else if (xhr.status === 204) {
                            console.log('No content: No marks found for the given student and course.');
                        } else {
                            console.error('Error status:', xhr.status); // Log the status code
                        }
                    }
                });
            },
            error: function(xhr, status, error) {
                console.error('Error fetching course weights:', error); // Log the error if fetching weights fails
                if (xhr.status === 403) {
                    console.error('Access denied: You do not have permission to access this resource.');
                } else {
                    console.error('Error status:', xhr.status); // Log the status code
                }
            }
        });
    }

    /**
     * This function calculates the grade based on the final percentage.
     */
    function calculateGrade(finalPercentage) {
        if (finalPercentage >= 85) {
            return 'A+';
        } else if (finalPercentage >= 80) {
            return 'A';
        } else if (finalPercentage >= 75) {
            return 'A-';
        } else if (finalPercentage >= 70) {
            return 'B+';
        }else if (finalPercentage >= 65) {
            return 'B';
        }else if (finalPercentage >= 60) {
            return 'B-';
        }else if (finalPercentage >= 55) {
            return 'C+';
        }else if (finalPercentage >= 50) {
            return 'C';
        }else if (finalPercentage >= 45) {
            return 'D+';
        }else if (finalPercentage >= 40) {
            return 'D';
        }else {
            return 'E';
        }
    }

    /**
     * This function sends the calculated final percentage and grade to the backend for saving.
     */
    function saveFinalPercentage(courseId, regNo, finalPercentage, grade) {
        $.ajax({
            url: `http://localhost:8090/api/v1/user/saveFinalScore`,
            type: 'POST', // POST method for sending data
            contentType: 'application/json', // Send the data as JSON
            data: JSON.stringify({
                student_id: regNo, // Include the student ID
                course_id: courseId, // Include the course ID
                grade: grade, // Send the calculated grade
                value: finalPercentage // Send the calculated final percentage
            }),
            success: function(response) {
                console.log('Final percentage and grade successfully saved:', response); // Log the success response
            },
            error: function(xhr, status, error) {
                console.error('Error saving final percentage and grade:', error); // Log any error that occurs during the save operation
                if (xhr.status === 500) {
                    console.error('Internal server error. Please check your backend logs.'); // Handle server errors
                } else {
                    console.error('Error status:', xhr.status); // Log other error statuses
                }
            }
        });
    }

    // Call the function to fetch weights, calculate the final score and grade, and save it
    fetchCourseWeightsAndCalculateFinalValue(courseId, regNo);
});
