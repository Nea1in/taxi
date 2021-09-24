<jsp:include page="/WEB-INF/layouts/header.jsp"/>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<form method="POST" action="OrderServlet/create">
	
	<div class="row ">
	<div class="col-md-12">
		<h1 class="text-center">Booked form</h1>
	</div>
		<div class="col-md-2"></div>
		<div class="col-md-8 bg-light m-3 rounded-lg">
		<div class="col-md-12">
			<div class="form-group">
				<label for="from">Departure address</label> <input type="text"
					class="form-control" name="from" required>
			</div>
		</div>
		<div class="col-md-12">
			<div class="form-group">
				<label for="to">Arrival address</label> <input type="text"
					class="form-control" name="to" required >
			</div>
		</div>
		<div class="col-md-12">
			<div class="form-group">
				<label for="to">Passengers</label> <input type="number"
					class="form-control" name="passenger" min="1" required>
			</div>
		</div>
		<div class="col-md-12">
			<label for="category_id">Category</label>
			<select class="form-control" name="categoryId">	
				<c:forEach items="${categories}" var="category">
									
					<option value=${ category.id }>${ category.name }</option>
				</c:forEach>
			</select>
		</div>
		<div class="col-md-12 p-12 mt-3 mb-3">
			<button type="submit" class="btn btn-primary w-100">Send</button>
		</div>
		</div>
	</div>
</form>





 <jsp:include page="/WEB-INF/layouts/footer.jsp"/>