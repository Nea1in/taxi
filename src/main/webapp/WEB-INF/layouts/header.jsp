<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
 <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css" integrity="sha384-B0vP5xmATw1+K9KRQjQERJvTumQW0nPEzvF6L/Z6nronJ3oUOFUFpCjEUQouq2+l" crossorigin="anonymous">
	<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
	<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js" integrity="sha384-9/reFTGAW83EW2RDu2S0VKaIzap3H66lZH81PoYlFhbGU+6BZp6G7niu735Sk7lN" crossorigin="anonymous"></script>
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/js/bootstrap.min.js" integrity="sha384-+YQ4JLhjyBLPDQt//I+STsc9iw4uQqACwlvpslubQzn4u2UU2UFM80nGisd026JF" crossorigin="anonymous"></script>

</head>
<body>

	<nav class="navbar navbar-expand-lg navbar-light bg-light">
		<div class="collapse navbar-collapse" id="navbarSupportedContent">
			<a class="navbar-brand" href="#">Taxi service</a>
			<ul class="navbar-nav mr-auto text-right"></ul>
	<%
		if (session.getAttribute("user") != null) {
	%>
		<a href="UserServlet/log_out" id="logOut"> Log Out </a>	
	<%
		}
	%>
		</div>
	</nav>


	
	<%
		if (session.getAttribute("error") != null) {
	%>
	<div class="alert alert-danger alert-dismissible fade show"
		role="alert">
		<strong>ERROR!</strong> <% out.print(session.getAttribute("error")); %>
		<button type="button" class="close" data-dismiss="alert"
			aria-label="Close">
			<span aria-hidden="true">&times;</span>
		</button>
	</div>
	<%
			session.removeAttribute("error");
		}
	%>
	
		<%
		if (session.getAttribute("success") != null) {
	%>
	<div class="alert alert-success alert-dismissible fade show"
		role="alert">
		<strong>ERROR!</strong> <% out.print(session.getAttribute("success")); %>
		<button type="button" class="close" data-dismiss="alert"
			aria-label="Close">
			<span aria-hidden="true">&times;</span>
		</button>
	</div>
	<%
			session.removeAttribute("success");
		}
	%>
