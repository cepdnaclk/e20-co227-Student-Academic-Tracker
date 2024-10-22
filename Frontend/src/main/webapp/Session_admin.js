// Add item

const publicPages = ['/src/main/webapp/calnder_admin.html', '/src/main/webapp/homelec.html','/src/main/webapp/manageCourses.html'];
sessionStorage.setItem('navigation_source', 'calnder_admin');
sessionStorage.setItem('navigation_source', 'manageCourses');


function getToken() {
    let value = "; " + document.cookie;
    let parts = value.split("; session_token=");
    if (parts.length == 2) {
        let token = parts.pop().split(";").shift();
        return token;
    }
    return null;
}


// Function to handle user logout
function logoutUser() {

    document.cookie = "session_token=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;";
    sessionStorage.removeItem('session_inactive');
    sessionStorage.removeItem('navigation_source');
    // Redirect to login page
    window.location.href = 'Adlogin.html';
}

let sessionToken = getToken();
let navigationSource = sessionStorage.getItem('navigation_source');

if (!sessionToken ) {
    window.location.href = 'Adlogin.html';
}

// Listen for visibility change events
document.addEventListener('visibilitychange', function() {
    if (document.visibilityState === 'hidden') {
        sessionStorage.setItem('session_inactive', 'true');
    }
    else if (document.visibilityState === 'visible' && sessionStorage.getItem('session_inactive') === 'true') {

        if (window.location.pathname == '/src/main/webapp/homelec.html' &&  navigationSource != 'calnder_admin' 
            &&  navigationSource != 'manageCourses'
        )
        {
            alert('Your session has expired. Please log in again.');
            logoutUser();
        }
        else {
            if (!publicPages.includes(window.location.pathname))
            {
                alert('Your session has expired.');
                logoutUser();
            }
        }
    }
});

document.addEventListener("DOMContentLoaded", function() {
    // Add event listener to the logout button
    const logoutButton = document.getElementById("logout");
    if (logoutButton) {
        logoutButton.addEventListener("click", logoutUser);
    }
});
