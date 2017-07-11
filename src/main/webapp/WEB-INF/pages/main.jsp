<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
 <!-- Required meta tags -->
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<!-- Bootstrap CSS -->
<link rel="stylesheet" href="<%= request.getContextPath()%>/styles.css">
<link rel="icon" type="image/x-icon" href="<%= request.getContextPath() %>/logo.png">
<title>Dobrodošli na Blog</title>
</head>
<body>
	<div class="jumbotron main">
		<h1 align="center" style="color: #0275d8">Dobrodošli na blog ${currentUserFn}</h1>
		<c:choose>
			<c:when test="${currentUserId != null}">
				<a class="btn btn-primary btn-lg centered-container"  href="<%= request.getContextPath() %>/servleti/author/${currentUserNick}">Moj blog</a>	
				<br>
				<a class="btn btn-danger btn-lg centered-container"  href="<%= request.getContextPath() %>/servleti/logout">Odjava</a>
			
			</c:when>
			
			<c:otherwise>
				<hr>
				<div class="centered-container">
					<p align="center">Ovdje se možete prijaviti ili registrirati. </p>
					<p align="center">Ukoliko niste zainteresirani za prijavu nastavite pregledavati pritiskom na blog nekog od naših korisnika</p>			
				</div>
				
				<c:if test="${registerSuccess != null}">
					<h3 align="center" style="color: green">${registerSuccess}</h3>
				</c:if>
				
				<c:if test="${loginErrorMessage != null}">
					<h3 align="center" style="color: red">${loginErrorMessage}</h3>
				</c:if>
				
			<form class="form-inline centered-container" action="<%= request.getContextPath() %>/servleti/main" method="post" name="login">
				<label class="sr-only"  for="nick">Nadimak</label>
				<input  id="nick" 
								class="form-control"
								type="text" 
								name="nick" 
								placeholder="Unesite Vaš nadimak"
								value="${providedNick}"
								required>
				
				<label class="sr-only" for="password">Lozinka</label>
				<input  id="password" 
								class="form-control"
								type="password" 
								name="password" 
								placeholder="Unesite Vašu lozinku"
								required>
				
				<input class="btn btn-success"  type="submit" value="Prijava">
			</form>
				<br><br>
				<a class="btn btn-primary btn-lg centered-container" href="<%= request.getContextPath() %>/servleti/register">Želite li se registrirati?</a>
			</c:otherwise>
		</c:choose>
	</div>

	<h2 align="center" style="color: white">Postojeći blogeri</h2><br>
	<ul style="list-style-type: none">
		<c:forEach var="user" items="${blogUsers}">
			<li>
				<a href="<%= request.getContextPath() %>/servleti/author/${user.nick}">
					<div class="card card-width centered-container">
						<div class="card-block">
							<h4 align="center" class="card-title">${user.nick}</h4>
							<h6 align="center" class="card-subtitle mb-2 text-muted">${user.firstName} ${user.lastName }</h6>
							<h6 align="center" class="card-subtitle mb-2 text-muted">e-mail: ${user.email}</h6>
						</div>
						 <div class="card-footer text-muted">
	    						<p class="centered-container">Broj objava: ${fn:length(user.entries)}</p>
	  					</div>
					</div>
				</a>
			</li><br>
		</c:forEach>
	</ul>

</body>
</html>