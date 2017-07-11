<%@page import="hr.fer.zemris.java.hw15.model.BlogEntry"%>
<%@page import="java.util.List"%>
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
<title>Upravljanje objavama</title>
</head>
<%
	boolean isNew = ((List<String>)request.getAttribute("router.parameters")).get(1).equals("new");
%>
<body>
<div class="container">
  <div class="card register-margin">
 	 <a href="javascript:history.back()"><span style="float: right; color: red;" class="fa fa-times"></span></a>
		<c:choose>
			<c:when test="<%= isNew %>">
				<h1 align="center" class="labels-text">Stvaranje nove objave</h1>
			</c:when>
			<c:otherwise>
				<h1 align="center" class="labels-text">Izmjena objave</h1>
			</c:otherwise>
		</c:choose>	
  	<form action="" method="post" name="manage">
		<div class="form-group">
		<label for="title" class="labels-text">Naslov objave:</label>
		<input id="title" 
						class="form-control"						
						type="text" 
						name="title" 
						placeholder="Unesite naslov objave" 
						value="${entryToUpdate.title}"
						required> <br><br>
		
		</div>
		
		 <div class="form-group">
    		<label for="exampleTextarea" class="labels-text">Tijelo objave:</label>
			<textarea rows="5" 
							class="form-control"
							id="text"
							name="text" 
							placeholder=""
							required>${entryToUpdate.text}</textarea><br><br>
  		</div>
		
		<input type="submit" class="btn btn-lg btn-success centered-container" value="<%= isNew ? "Stvori" : "Izmjeni"%>">
	</form>
  </div>
 </div>	
</body>
</html>