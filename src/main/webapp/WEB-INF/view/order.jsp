<jsp:include page="/WEB-INF/layouts/header.jsp"/>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<form method="POST">
	
	<div class="row bg-light m-3 rounded-lg">
	<div class="col-md-12">
		<h1 class="text-center">Booked form</h1>
	</div>
		<div class="col-md-3">
			<div class="form-group">
				<label for="from">Departure address</label> <input type="text"
					class="form-control" name="from">
			</div>
		</div>
		<div class="col-md-3">
			<div class="form-group">
				<label for="to">Arrival address</label> <input type="text"
					class="form-control" name="to">
			</div>
		</div>
		<div class="col-md-3">
			<div class="form-group">
				<label for="to">Passengers</label> <input type="number"
					class="form-control" name="passenger" min="1">
			</div>
		</div>
		<div class="col-md-3">
			<label for="category_id">Category</label>
			<select class="form-control" name="categories">	
				<c:forEach items="${categories}" var="category">
					${category.id} <br>
					
					<option value="${ category.id }">${ category.name }</option>
				</c:forEach>
			</select>
		</div>
		<div class="col-md-12 p-3">
			<button type="submit" class="btn btn-primary w-100">Send</button>
		</div>
	</div>
</form>





 <jsp:include page="/WEB-INF/layouts/footer.jsp"/>