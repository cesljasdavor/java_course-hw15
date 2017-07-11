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
<title>${blogUser.nick}'s blog</title>
</head>
<body>
	<div class="jumbotron user" style="color: white">		
		<c:choose>
			<c:when test="${blogUser.id == currentUserId}">
					<h1 align="center" >Dobrodošli ${blogUser.nick}</h1>
					<br>
					<p style="text-align: center">Na ovoj stranici možete vidjeti Vaše dosadašnje objave, možete ih uređivati te stvarati nove</p>
			</c:when>
			<c:otherwise>
					<h1 align="center">Dobrodošli na blog korisnika ${blogUser.nick}</h1>
					<p style="text-align: center">Na ovoj stranici možete vidjeti sve objave koje je korisnik napisao.</p>	
			</c:otherwise>
		</c:choose>
		<a href="<%= request.getContextPath() %>" class="btn btn-warning btn-lg centered-container">Povratak na glavnu sranicu</a>
	</div>

	<h3 align="center" style="color: white">Do sada su napisane sljedeće objave	
		<c:if test="${blogUser.id == currentUserId}">
			<a class="btn btn-success"href="<%= request.getContextPath() %>/servleti/author/${blogUser.nick}/new"><span class="fa fa-plus"></span></a>
		</c:if>
	</h3><br>
	
	<ul style="list-style-type: none">
		<c:forEach var="blogEntry" items="${blogUser.entries}">
			<li>
			<div class="card card-width centered-container">
				<div class="card-block">
					<a href="<%= request.getContextPath() %>/servleti/author/${blogUser.nick}/${blogEntry.id}">
								<h4 align="center" class="card-title">${blogEntry.title}</h4>
					</a>
					<c:if test="${blogUser.id == currentUserId}">
						<a class="btn btn-primary"
							style="float: right;"
							href="<%= request.getContextPath() %>/servleti/author/${blogUser.nick}/edit?id=${blogEntry.id}">
							<span class="fa fa-pencil"></span>
						</a>
					</c:if>
				</div><br>
				<div class="card-footer text-muted">
  						<p class="centered-container">Objava kreirana: ${blogEntry.createdAt}</p>
  						<p class="centered-container">Zadnja izmjena: ${blogEntry.lastModifiedAt}</p>
				</div>
				</div>
			</li><br>
		</c:forEach>
	</ul>
</body>
</html>