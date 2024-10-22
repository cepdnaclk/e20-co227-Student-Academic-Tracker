

document.addEventListener("DOMContentLoaded", () => {
    const addEventWrapper = document.querySelector(".add-event-wrapper");
    const closeBtn = document.querySelector(".close");
    const eventForm = document.getElementById("event-form");
    const addEventBtn = document.querySelector(".add-event");
    const eventsDiv = document.querySelector(".events");
    const eventsList = document.querySelector(".events-list");
    let selectedDate = null;
    const events = {};

    let date = new Date();

    const renderCalendar = () => {
        date.setDate(1);

        const monthDays = document.querySelector(".days");

        const lastDay = new Date(date.getFullYear(), date.getMonth() + 1, 0).getDate();
        const prevLastDay = new Date(date.getFullYear(), date.getMonth(), 0).getDate();
        const firstDayIndex = date.getDay();
        const lastDayIndex = new Date(date.getFullYear(), date.getMonth() + 1, 0).getDay();
        const nextDays = 7 - lastDayIndex - 1;

        const months = [
            "January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December"
        ];

        document.querySelector(".date h1").innerHTML = months[date.getMonth()];
        document.querySelector(".year").innerHTML = date.getFullYear();

        let days = "";

        for (let x = firstDayIndex; x > 0; x--) {
            days += `<div class="prev-date">${prevLastDay - x + 1}</div>`;
        }

        for (let i = 1; i <= lastDay; i++) {
            const dateKey = `${i}-${date.getMonth() + 1}-${date.getFullYear()}`;
            const todayClass = (i === new Date().getDate() && date.getMonth() === new Date().getMonth() && date.getFullYear() === new Date().getFullYear()) ? 'today' : '';
            const hasEventClass = events[dateKey] && events[dateKey].length > 0 ? 'has-event' : '';

            days += `<div class="date-div ${todayClass} ${hasEventClass}" data-date="${dateKey}">${i}</div>`;
        }

        for (let j = 1; j <= nextDays; j++) {
            days += `<div class="next-date">${j}</div>`;
        }

        monthDays.innerHTML = days;

        document.querySelectorAll('.date-div').forEach(dateDiv => {
            dateDiv.addEventListener('click', (e) => {
                if (selectedDate) {
                    document.querySelector(`[data-date="${selectedDate}"]`).classList.remove("selected");
                }
                selectedDate = e.target.getAttribute('data-date');
                e.target.classList.add("selected");
                const dateEvents = events[selectedDate] || [];
                const eventList = dateEvents.length > 0 ?
                    dateEvents.map(event => `<div class="event"><h3>${event.title}</h3><p>${event.description}</p></div>`).join("") :
                    '<p class="no-events-message">No events added</p>';
                eventsList.innerHTML = eventList;
                eventsDiv.style.display = "block";
            });
        });
    };

    document.querySelector(".prev").addEventListener("click", () => {
        date.setMonth(date.getMonth() - 1);
        renderCalendar();
    });

    document.querySelector(".next").addEventListener("click", () => {
        date.setMonth(date.getMonth() + 1);
        renderCalendar();
    });

    closeBtn.addEventListener("click", () => {
        addEventWrapper.style.display = "none";
    });

    addEventBtn.addEventListener("click", () => {
        addEventWrapper.style.display = "block";
        document.querySelector(".event-date").value = selectedDate;
    });

    // Get regno form the local store
    const ID = localStorage.getItem('userID');

    fetch(`http://localhost:8090/api/v1/user/events/${ID}`)
        .then(res => res.json())
        .then(data => {
            console.log(data); // Log the fetched data for debugging

            // Update events object with fetched data
            data.forEach(event => {
                const eventDateParts = event.eventDate.split('/');
                const eventKey = `${eventDateParts[2]}-${eventDateParts[1]}-${eventDateParts[0]}`;
                console.log(eventKey);
                if (!events[eventKey]) {
                    events[eventKey] = [];
                }
                events[eventKey].push({ title: event.eventName, description: event.eventDescription });
            });

            renderCalendar(); // Render calendar after fetching events
        })
        .catch(error => console.error('Error fetching events:', error));

    eventForm.addEventListener("submit", (e) => {
        e.preventDefault();
        const eventTitle = document.querySelector(".event-title").value;
        const eventDescription = document.querySelector(".event-description").value;
        const eventDate = document.querySelector(".event-date").value;

        if (eventTitle) {
            //const eventDate = document.querySelector(".event-date").value;
            console.log(eventDate);

            if (!events[eventDate]) {
                events[eventDate] = [];
            }

            events[eventDate].push({ title: eventTitle, description: eventDescription });

            renderCalendar(); // Re-render the calendar to show the updated red dots
            addEventWrapper.style.display = "none";
            eventForm.reset();
        }

        if (selectedDate) {
            document.querySelector(`[data-date="${selectedDate}"]`).classList.remove("selected");
            selectedDate = null;
        }

        // Send event data to the backend
        const eventData = {

            eventName: eventTitle,
            eventDescription: eventDescription,
            eventDate: eventDate,
            student_id: regno
        };

        fetch('http://localhost:8090/api/v1/user/saveevents', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(eventData)
        })
            .then(response => response.json())
            .then(data => {
                console.log('Success:', data);
                renderCalendar(); // Re-render the calendar to show the updated red dots
                addEventWrapper.style.display = "none";
                eventForm.reset();
            })
            .catch((error) => {
                console.error('Error:', error);
            });

        if (selectedDate) {
            document.querySelector(`[data-date="${selectedDate}"]`).classList.remove("selected");
            selectedDate = null;
        }

    });

    renderCalendar();
});
