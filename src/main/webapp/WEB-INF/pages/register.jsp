<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
<title>Registracija</title>
</head>
<body>
<div class="container">
  <div class="card register-margin">
  	<a href="<%= request.getContextPath() %>"><span style="float: right; color: red;" class="fa fa-times"></span></a>
    <h1 align="center" class="labels-text">Registracija</h1>
    <h3 align="center" class="labels-text">Molimo Vas da ispunite sva polja</h3>
	<c:if test="${registerErrorMessage != null}">
		<h3 align="center" style="color: red">${registerErrorMessage}</h3>
	</c:if>
	
	<form action="<%= request.getContextPath() %>/servleti/register" method="post" name="login">
		<div class="form-group row">
			<label for="firstName" class="col-2 col-form-label labels-text">Ime</label>
			<div class="col-10">
				<input id="firstName" 
								class="form-control"
								type="text" 
								name="firstName" 
								placeholder="Unesite Vaše ime" 
								required>
			</div>
		</div>
		
		<div class="form-group row">
			<label for="lastName" class="col-2 col-form-label labels-text">Prezime</label>
			<div class="col-10">
				<input id="lastName" 
								class="form-control"
								type="text" 
								name="lastName" 
								placeholder="Unesite Vaše prezime"
								required>
			</div>
		</div>

		<div class="form-group row">
			<label for="nick" class="col-2 col-form-label labels-text">Nadimak</label>
			<div class="col-10">
				<input id="nick" 
								class="form-control"
								type="text" 
								name="nick" 
								placeholder="Unesite Vaš nadimak"
								required>
			</div>
		</div>
		
		<div class="form-group row">
			<label for="email" class="col-2 col-form-label labels-text">E-mail</label>
			<div class="col-10">
				<input id="email" 
								class="form-control"
								type="email" 
								name="email" 
								placeholder="Unesite Vašu e-mail adresu"
								required>
			</div>
		</div>
	
		<div class="form-group row">
			<label for="password" class="col-2 col-form-label labels-text">Lozinka</label>
			<div class="col-10">
				<input id="password" 
								class="form-control"
								type="password" 
								name="password" 
								pattern=".{6,}" 
								placeholder="Unesite Vašu lozinku"
								required>
			</div>
		</div>
	
		<input class="btn btn-lg btn-success centered-container" type="submit" value="Registriraj se">
	</form>
  </div>
</div>
</body>
</html>