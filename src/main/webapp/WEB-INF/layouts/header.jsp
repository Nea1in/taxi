
<%@ page language="java"%>
       <%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
     <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
     <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
     <%@page import="java.util.ResourceBundle"%>
      <%@page import="java.util.Locale"%>
      <%@page import="java.util.Enumeration"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
 <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css" integrity="sha384-B0vP5xmATw1+K9KRQjQERJvTumQW0nPEzvF6L/Z6nronJ3oUOFUFpCjEUQouq2+l" crossorigin="anonymous">
	<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
	<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js" integrity="sha384-9/reFTGAW83EW2RDu2S0VKaIzap3H66lZH81PoYlFhbGU+6BZp6G7niu735Sk7lN" crossorigin="anonymous"></script>
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/js/bootstrap.min.js" integrity="sha384-+YQ4JLhjyBLPDQt//I+STsc9iw4uQqACwlvpslubQzn4u2UU2UFM80nGisd026JF" crossorigin="anonymous"></script>
	<script src="https://kit.fontawesome.com/957474d86b.js" crossorigin="anonymous"></script><script src="https://kit.fontawesome.com/957474d86b.js" crossorigin="anonymous"></script>
</head>
<body>









	<nav class="navbar navbar-expand-lg navbar-light bg-light">
		<div class="collapse navbar-collapse" id="navbarSupportedContent">
			<a class="navbar-brand" href="#">Taxi service</a>
			<ul class="navbar-nav mr-auto text-right">
	<%
	
		if (session.getAttribute("role") != null && session.getAttribute("role").equals(2)) {
	%>
			<li class="nav-item">
		    	<a class="nav-link" href="${base_url}/order">Booked Form</a>
		  	</li>
		  	<li class="nav-item">
		    	<a class="nav-link" href="OrderServlet/history">My History</a>
		  	</li>
	<%
		}
	%>
			</ul>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
 <fmt:setLocale value="${language}" />
 <fmt:setBundle basename="Messages" />
   <html lang="${language}">
 
 <form method="get"  >
             <select id="language" name="language" onchange="submit()">
                 <option value="en" ${language == 'en' ? 'selected' : ''}>Eng</option>
                 <option value="ua" ${language == 'ua' ? 'selected' : ''}>Ukr</option>
                
          
             </select>
         </form>
 
  <%

 Locale uk = null;
 
 if (session.getAttribute("language").equals("ua")){
	
  uk = new Locale("uk", "UA");
 }else{
	  
	 uk = new Locale("en", "US");
	  
	 
 }
 ResourceBundle bundle = ResourceBundle.getBundle("Messages", uk);  
 
 
 for (Enumeration e = bundle.getKeys(); e.hasMoreElements(); ) {
     String key = (String) e.nextElement();
     String s = bundle.getString(key);
     session.setAttribute( key, s);
     
 }
 %>
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
		<strong>SUCCESS!</strong> <% out.print(session.getAttribute("success")); %>
		<button type="button" class="close" data-dismiss="alert"
			aria-label="Close">
			<span aria-hidden="true">&times;</span>
		</button>
	</div>
	<%
			session.removeAttribute("success");
		}
	%>
