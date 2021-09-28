<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<table border="1" class="w-100">
			    <thead>
			        <tr>
			            <th class="w-10">
			                Login
			            </th>
			            <th  class="w-10">
			                Email
			            </th>
			            <th  class="w-10">
			                From
			            </th>
			            <th  class="w-10">
			                To
			            </th>
			            <th  class="w-10">
			                Passenger
			            </th>
			            <th  class="w-10">
			                Category
			            </th>
			            <th  class="w-10">
			                Date
			                <a href="${url}?page=<%
			                	out.print(session.getAttribute("page"));
				                if (session.getAttribute("sort_price") != null) {
							    	out.print("&sort_price="+session.getAttribute("sort_price"));
							    }
				                if (session.getAttribute("user_id") != null) {
							    	out.print("&user_id="+session.getAttribute("user_id"));
							    }
				                if (session.getAttribute("date") != null) {
							    	out.print("&date="+session.getAttribute("date"));
							    }
			                	%>&sort_date=DESC">
			                	<i class="far fa-arrow-alt-circle-down"></i>
			                </a>
			                
			                 <a href="${url}?page=<%out.print(session.getAttribute("page"));
			                 	if (session.getAttribute("sort_price") != null) {
							    	out.print("&sort_price="+session.getAttribute("sort_price"));
							    }
			                 	if (session.getAttribute("user_id") != null) {
							    	out.print("&user_id="+session.getAttribute("user_id"));
							    }
				                if (session.getAttribute("date") != null) {
							    	out.print("&date="+session.getAttribute("date"));
							    }
							    %>&sort_date=ASC">
			                	<i class="far fa-arrow-alt-circle-up"></i>
			                </a>
			                
			            </th>
			            <th  class="w-10">
			                Price
			                <a href="${url}?page=<%out.print(session.getAttribute("page"));
			                if (session.getAttribute("sort_date") != null) {
							    	out.print("&sort_date="+session.getAttribute("sort_date"));
							    }
		                 	if (session.getAttribute("user_id") != null) {
						    	out.print("&user_id="+session.getAttribute("user_id"));
						    }
			                if (session.getAttribute("date") != null) {
						    	out.print("&date="+session.getAttribute("date"));
						    }
							%>&sort_price=DESC">
			                	<i class="far fa-arrow-alt-circle-down"></i>
			                </a>
			                
			                 <a href="${url}?page=<%out.print(session.getAttribute("page"));
			                 	if (session.getAttribute("sort_date") != null) {
							    	out.print("&sort_date="+session.getAttribute("sort_date"));
							    }
			                 	if (session.getAttribute("user_id") != null) {
							    	out.print("&user_id="+session.getAttribute("user_id"));
							    }
				                if (session.getAttribute("date") != null) {
							    	out.print("&date="+session.getAttribute("date"));
							    }%>&sort_price=ASC">
			                	<i class="far fa-arrow-alt-circle-up"></i>
			                </a>
			                
			            </th>
			            <th  class="w-10">
			                Status
			            </th>
			            
			        </tr>
			    </thead>
			    
			    <tbody>
			    	<c:forEach items="${listOrders}" var="listOrder">
						<tr>
				            <td>${listOrder.login}</td>
				            <td>${listOrder.email}</td>
				            <td>${listOrder.from}</td>
				            <td>${listOrder.to}</td>
				            <td>${listOrder.passenger}</td>
				            <td>${listOrder.nameCategory}</td>
				            <td>${listOrder.date}</td>
				            <td>${listOrder.price}</td>
				            <td>${listOrder.status}</td>
				            
				        </tr>
					</c:forEach>
				</tbody>
			</table>
			<nav aria-label="Page navigation example m-3">
			
			  <ul class="pagination justify-content-center">
			  <%
			  	int page_count = (int) request.getAttribute("count")/5;
			  for (int i = 0; i < page_count+1; i++) {
				  String active = "";
				  if (Integer.parseInt(session.getAttribute("page").toString()) == i) {
					  active = "active";
				  }
			  %>
			    <li class="page-item <% out.print(active);%>"><a class="page-link" href="${url}?page=<%
			    	out.print(i);
			    if (session.getAttribute("sort_price") != null) {
			    	out.print("&sort_price="+session.getAttribute("sort_price"));
			    }
			    if (session.getAttribute("sort_date") != null) {
			    	out.print("&sort_date="+session.getAttribute("sort_date"));
			    }
			    if (session.getAttribute("user_id") != null) {
			    	out.print("&user_id="+session.getAttribute("user_id"));
			    }
                if (session.getAttribute("date") != null) {
			    	out.print("&date="+session.getAttribute("date"));
			    }
			    %>">
			    <%
			    	out.print(i + 1);
			    %>
			    </a></li>
			  <%
			  }
			  %>
			  </ul>
		</nav>