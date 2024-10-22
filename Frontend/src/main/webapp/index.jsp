<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Index Page</title>
<%@include file= "allcss.jsp" %>
<link rel="stylesheet" href="style.css">

</head>
<body>
<%@include file= "navigation_bar.jsp" %>

<div id="carouselExampleSlidesOnly" class="carousel slide" data-bs-ride="carousel" data-bs-interval="5000">
  <div class="carousel-inner">
    <div class="carousel-item active">
      <img src="img/image 1.jpg" class="d-block w-100" alt="slide 1" width=100% height=100%>
    </div>
    <div class="carousel-item">
      <img src="img/image 2.jpg" class="d-block w-100" alt="slide 2" width=100% height=100%>
    </div>
    <div class="carousel-item">
      <img src="img/image 3.jpg" class="d-block w-100" alt="slide 3" width=100% height=100%>
    </div>
  </div>
  <header class="about_webpage">
        <h1>Welcome to the Academic Tracker</h1>
        <p>Click this <a href="#">link</a> to learn more about this</p>
  </header>
</div>


  





</body>
</html>