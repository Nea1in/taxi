
   
    <jsp:include page="/WEB-INF/layouts/header.jsp"/>

    <div class="row">
        <div class="col-md-2"></div>
        <div class="col-md-4 bg-light m-3 rounded-lg">
            <form method="POST" action="UserServlet/sign_in">
                <h1 class="text-center">Sign In</h1>
                <div class="form-group">
                    <label for="email">Email address</label>
                    <input type="email" class="form-control" aria-describedby="emailHelp" name="email">
                 </div>
                <div class="form-group">
                    <label for="password">Password</label>
                    <input type="password" class="form-control" name="password">
                </div>
                <button type="submit" class="btn btn-primary">Sign in</button>
            </form>
        </div>
        <div class="col-md-4 bg-light m-3 rounded-lg">
            <form method="POST" action="UserServlet/sign_up">
                <h1 class="text-center">Sign Up</h1>
                <div class="form-group">
                    <label for="login">Login</label>
                    <input type="text" class="form-control" id="login" name="login">
                </div>
                <div class="form-group">
                    <label for="email">Email address</label>
                    <input type="email" class="form-control" aria-describedby="emailHelp" name="email">
                </div>
                <div class="form-group">
                    <label for="password">Password</label>
                    <input type="password" class="form-control" name="password">
                </div>
                <button type="submit" class="btn btn-primary">Sign up</button>
            </form>
        </div>
    </div>

      <jsp:include page="/WEB-INF/layouts/footer.jsp"/>