<jsp:include page="/WEB-INF/layouts/header.jsp"/>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<h1>MY HISTORY</h1>
<form method="GET" action="${url}/order/history">
<div class="row m-3">
	<div>
	<%
	    if (session.getAttribute("sort_price") != null) {
	%>
		<input name="sort_price" value="<%out.print(session.getAttribute("sort_price"));%>" class="d-none">
	<%
	    }
	    if (session.getAttribute("sort_date") != null) {
	 %>
			<input name="sort_date" value="<%out.print(session.getAttribute("sort_date"));%>"  class="d-none">
	<%    	
	    }
	%>
	</div>
	<div class="col-md-4">
		<div class="form-group">
			<label for="to">Date</label> <input type="date"
				class="form-control" name="date">
		</div>
				
	</div>
	<div class="col-md-12">
		<button class="float-left btn btn-success" type="submit"> Filter
		</button>
		<a class="float-right btn btn-light" href="${url}/order/history"> Reset
		</a>
	</div>
</div>
</form>
<jsp:include page="/WEB-INF/parts/orders_table.jsp"/>


 <jsp:include page="/WEB-INF/layouts/footer.jsp"/>