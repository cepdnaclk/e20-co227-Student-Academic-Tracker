$(document).ready(function() {
    // Add event listeners
    $('#refreshButton').on('click', fetchStudents);
    // Initialize table
    initializeTable();
});


let students = [];

// empty table
function initializeTable() {
    const tableBody = document.getElementById('studentTableBody');
    tableBody.innerHTML = '';
    for (let i = 0; i < 10; i++) {
        const row = document.createElement('tr');
        row.innerHTML = `
            <td>${i + 1}</td>
            <td>-</td>
            <td>-</td>
        `;
        tableBody.appendChild(row);
    }
}


// Get data
function fetchStudents() {
    $.ajax({
        method: "GET",
        contentType: "application/json",
        url: "http://localhost:8090/api/v1/user/print_rank",
        async: true,
        success: function(response) {

            console.log(response);

            students = response;

            // Sort the students by average GPA in descending order
            students.sort(function(a, b) {
                return b.average_result - a.average_result;
            });

            // add to table
            renderStudents();
        },
        error: function(xhr, status, error) {
            alert("Data not saved: " + error);
        }
    });
}

function renderStudents() {
    const tableBody = document.getElementById('studentTableBody');
    tableBody.innerHTML = '';

    // Sort students by average_result in descending order
    students.sort((a, b) => b.average_result - a.average_result);

    let currentIndex = 1;  // Start with index 1
    let previousResult = null;  // To keep track of the previous student's GPA
    let sameRankCount = 0;  // To count how many students have the same rank

    students.forEach((student, index) => {
        // Determine the rank index
        if (previousResult !== null && student.average_result === previousResult) {
            sameRankCount++;
        } else {
            currentIndex += sameRankCount;
            sameRankCount = 1;
        }
        previousResult = student.average_result;

        // Create a table row element
        const row = document.createElement('tr');

        // Apply special colors based on rank
        if (currentIndex === 1) {
            row.style.backgroundColor = 'gold'; // 1st place
        } else if (currentIndex === 2) {
            row.style.backgroundColor = 'silver'; // 2nd place
        } else if (currentIndex === 3) {
            row.style.backgroundColor = '#cd7f32'; // 3rd place (bronze)
        }

        row.innerHTML = `
            <td>${currentIndex}</td>
            <td>${student.student_id}</td>
            <td>${student.average_result}</td>
        `;
        tableBody.appendChild(row);
    });

    // Fill the table to have at least 10 rows
    while (students.length < 10) {
        const row = document.createElement('tr');
        row.innerHTML = `
            <td>${students.length + 1}</td>
            <td>-</td>
            <td>-</td>
        `;
        tableBody.appendChild(row);
        students.push({ student_id: '-', average_result: "-" });
    }
}






