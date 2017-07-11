<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
 <!-- Required meta tags -->
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<!-- Bootstrap CSS -->
<link rel="stylesheet" href="<%= request.getContextPath()%>/styles.css">
<link rel="icon" type="image/x-icon" href="<%= request.getContextPath() %>/logo.png">
<title>Prikaz objave</title>
</head>
<body>
<div class="container">
  <div class="card register-margin">
 	 	<a href="./"><span style="float: right; color: red;" class="fa fa-times"></span></a>
		<div class="card-block">
			<h1 align="center" class="labels-text">${toDisplay.title}</h1>
			<hr>
			<h4 align="center">${toDisplay.text}</h4>
			<hr>		
			<h4 align="center">Komentari:</h4>		
		</div>
		
		<c:forEach var="comment" items="${toDisplay.comments}">
				<div class="card">
					<h3 class="card-header">${comment.usersEMail}</h3>
					<div class="card-block">
					<h5 align="center">${comment.message}</h5>
					</div>
					<div class="card-footer text-muted">
	   					<p style="text-align: center;">Vrijeme postavljanja komentara: ${comment.postedOn}</p>
					</div>	  
				</div><br>
		</c:forEach>

		<form action="" method="post" name="comment">
			<textarea class="form-control"
								rows="4" 
								name="message"
								placeholder="Postavite Vaš komentar." 
								required></textarea><br>
			<input type="submit" value="Komentiraj" class="btn btn-lg btn-primary centered-container">
		</form>	
	</div>
</div>
	
	
</body>
</html>