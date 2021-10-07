<jsp:include page="/WEB-INF/layouts/header.jsp"/>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	
	<div class="row ">
		<div class="col-md-12">
			<h1 class="text-center">Result page</h1>
		</div>
		<div class="col-md-2"></div>
		<div class="col-md-8 bg-light m-3 rounded-lg">
			<%
				if (request.getAttribute("order_det") != null) {
			%>
			<p> <b> Departure address: </b> ${ order_det.from} </p>
			<p> <b> Arrival address: </b> ${ order_det.to}</p>
			<p> <b> Passengers: </b> ${ order_det.passengers} </p>
			<p> <b> Car's category: </b> ${ order_det.category} </p>
			<p> <b> Price: </b> ${ order_det.price} </p>
			<a class="btn btn-success w-100 mt-3 mb-3" href="${order_det.url}/OrderServlet/approval?order_id=${order_det.order_id}&category_id=${order_det.category_id}&price=${order_det.price}&count=1">Apply</a>
			<a class="btn btn-danger w-100 mt-3 mb-3" href="${order_det.url}/OrderServlet/reject?order_id=${order_det.order_id}">Reject</a>
			<%
				} else {
			
			%>
			<h4> Alternative option </h4>
			<p> <b> Departure address: </b> ${ order_alt.from} </p>
			<p> <b> Arrival address: </b> ${ order_alt.to}</p>
			<p> <b> Passengers: </b> ${ order_alt.passengers} </p>
			<table border="1" class="w-100">
			    <thead>
			        <tr>
			            <th class="w-25">
			                Category
			            </th>
			            <th  class="w-25">
			                Count
			            </th>
			            <th  class="w-25">
			                Price
			            </th>
			            <th  class="w-25">
			                
			            </th>
			        </tr>
			    </thead>
			    
			    <tbody>
			    	<c:forEach items="${categories}" var="category">
						<tr>
				            <td>${category.name}</td>
				            <td>${category.count}</td>
				            <td>${category.count*order_alt.price}</td>
				            <td>
				            	<a class="btn btn-success w-100 mt-3 mb-3" href="${order_alt.url}/OrderServlet/approval?order_id=${order_alt.order_id}&category_id=${category.id}&price=${category.count*order_alt.price}&count=${category.count}">Apply</a>
				            </td>
				        </tr>
					</c:forEach>
				</tbody>
			</table>
			<a class="btn btn-danger w-100 mt-3 mb-3" href="${order_alt.url}/OrderServlet/reject?order_id=${order_alt.order_id}">Reject</a>
			<%
				}
			%>
			
		</div>
	</div>




 <jsp:include page="/WEB-INF/layouts/footer.jsp"/>