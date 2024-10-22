// Add item
/*
const publicPages = ['/src/main/webapp/calender.html', '/src/main/webapp/home.html','/src/main/webapp/about.html','/src/main/webapp/calculator.html','/src/main/webapp/Contact.html','/src/main/webapp/Ranking.html','/src/main/webapp/selectCourse.html','/src/main/webapp/my_courses.html'];
sessionStorage.setItem('navigation_source', 'calendar');
sessionStorage.setItem('navigation_source', 'about');
sessionStorage.setItem('navigation_source', 'calculator');
sessionStorage.setItem('navigation_source', 'contact');
sessionStorage.setItem('navigation_source', 'Ranking');
sessionStorage.setItem('navigation_source', 'selectCourse');
sessionStorage.setItem('navigation_source', 'my_courses');

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
  window.location.href = 'Stdlogin.html';
}

let sessionToken = getToken();
let navigationSource = sessionStorage.getItem('navigation_source');

if (!sessionToken ) {
  window.location.href = 'Stdlogin.html';
}

// Listen for visibility change events
document.addEventListener('visibilitychange', function() {
  if (document.visibilityState === 'hidden') {
    sessionStorage.setItem('session_inactive', 'true');
  } 
  else if (document.visibilityState === 'visible' && sessionStorage.getItem('session_inactive') === 'true') {

    if (window.location.pathname == '/src/main/webapp/home.html' &&  navigationSource != 'calendar' &&
      navigationSource != 'about' && navigationSource != 'calculator' && navigationSource != 'contact'
      && navigationSource != 'Ranking' && navigationSource != 'selectCourse' && navigationSource != 'my_courses') 
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
