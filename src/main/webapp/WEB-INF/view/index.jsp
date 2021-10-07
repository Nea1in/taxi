     <jsp:include page="/WEB-INF/layouts/header.jsp"/>
     
    <script src='https://www.google.com/recaptcha/api.js?hl=en'></script>
     <script src="https://www.google.com/recaptcha/api.js"></script>
     <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
     <%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <div class="row">
        <div class="col-md-2"></div>
        <div class="col-md-4 bg-light m-3 rounded-lg">
            <form method="POST" action="UserServlet/sign_in">
                <h1 class="text-center">${Sign_in} </h1>
                <div class="form-group">
                    <label for="email">${Email_address}</label>
                    <input type="email" class="form-control" aria-describedby="emailHelp" name="email">
                 </div>
                <div class="form-group">
                    <label for="password">${Password}</label>
                    <input type="password" class="form-control" name="password">
                </div>
                <button type="submit" class="btn btn-primary">${Sign_in}</button>
            </form>
        </div>
        <div class="col-md-4 bg-light m-3 rounded-lg">
            <form method="POST" action="UserServlet/sign_up">
                <h1 class="text-center">${Sign_up}</h1>
                <div class="form-group">
                    <label for="login">${Login}</label>
                    <input type="text" class="form-control" id="login" name="login">
                </div>
                <div class="form-group">
                    <label for="email">${Email_address}</label>
                    <input type="email" class="form-control" aria-describedby="emailHelp" name="email">
                </div>
                <div class="form-group">
                    <label for="password">${Password}</label>
                    <input type="password" class="form-control" name="password">
                </div>
                <button type="submit" class="btn btn-primary">${Sign_up}</button>
            </form>
        </div>
    </div>

      <jsp:include page="/WEB-INF/layouts/footer.jsp"/>