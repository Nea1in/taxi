package controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import dao.impl.CarDaoImpl;
import dao.impl.CategoryDaoImpl;
import dao.impl.OrderDaoImpl;
import dao.impl.UsersDaoImpl;
import entity.AltOrder;

import entity.Categories;
import entity.Orders;
import entity.OrdersForAdmin;
import entity.Users;

/**
 * Servlet implementation class OrderServlet
 */

@WebServlet("/order")

public class OrderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public OrderServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    protected static  boolean getAuth(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {
    	HttpSession session = request.getSession();
    	String path_proj = request.getContextPath();
    	if (session.getAttribute("user") == null  || !session.getAttribute("role").equals(2)) {
			 response.sendRedirect(path_proj);
			 return false;
		}
    	return true;
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		if (getAuth(request, response)) {
			request.setAttribute("base_url", request.getContextPath());
			HttpSession session = request.getSession();
			String[] action = request.getRequestURL().toString().split("/");
			
			
			
			String path_proj = request.getContextPath();
			request.setAttribute("url_base", request.getContextPath());
			if (action[action.length - 1].equals("reject")) {
				
				OrderDaoImpl order =new OrderDaoImpl();
				order.deleteOrder( Integer.parseInt(request.getParameter("order_id")));
				session.setAttribute("success", "Order reject.");
				response.sendRedirect(path_proj);
			} else if (action[action.length - 1].equals("approval")) {
				OrderDaoImpl order =new OrderDaoImpl();
				BigDecimal price= new BigDecimal(request.getParameter("price"));
				
				order.updateOrder( Integer.parseInt(request.getParameter("order_id")),  Integer.parseInt(request.getParameter("category_id")), 1, price);
				session.setAttribute("success", "Order confirmed. Weating 15 minutes");
				CarDaoImpl car = new CarDaoImpl();
				int count =Integer.parseInt(request.getParameter("count"));
				car.updateCar(Integer.parseInt(request.getParameter("category_id")), count);
				response.sendRedirect(path_proj);
			} else if (action[action.length - 1].equals("history")){
				
				if (request.getParameter("page") == null) {
					session.setAttribute("page", 0);
				} else {
					session.setAttribute("page", request.getParameter("page"));
				}
				if (request.getParameter("sort_date") != null) {
					session.setAttribute("sort_date", request.getParameter("sort_date"));
				} else {
					session.removeAttribute("sort_date");
				}
				if (request.getParameter("sort_price") != null) {
					session.setAttribute("sort_price", request.getParameter("sort_price"));
				} else {
					session.removeAttribute("sort_price");
				}
				session.setAttribute("user_id", session.getAttribute("user"));
				if (request.getParameter("date") != null  && !request.getParameter("date").equals("")) {
					session.setAttribute("date", request.getParameter("date"));
				} else {
					session.removeAttribute("date");
				}
				OrderDaoImpl order = new OrderDaoImpl();
				int count = order.getAllOrdersCount(request);
				List<OrdersForAdmin> listOrders = new ArrayList<OrdersForAdmin>();
				
				UsersDaoImpl user = new UsersDaoImpl();
				
				List<Users> listUsers = new ArrayList<Users>();
				listUsers.addAll(user.getAllUsers());
				listOrders.addAll(order.getOrdersAdmin(request));
				request.setAttribute("listOrders",listOrders);
				request.setAttribute("count",count);
				request.setAttribute("users",listUsers);
				request.setAttribute("url", request.getContextPath() + "/OrderServlet/history");
				request.getRequestDispatcher("/WEB-INF/view/history-user.jsp").forward(request, response);
			} else {
				CategoryDaoImpl category = new CategoryDaoImpl();
				List<Categories> categories = new ArrayList<Categories>();
				categories = category.getAllCategories();
				request.setAttribute("categories", categories);
				
				request.getRequestDispatcher("/WEB-INF/view/order.jsp").forward(request, response);
			}
			
			
		}
		 
		//response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// TODO Auto-generated method stub
		
		String[] action = request.getRequestURL().toString().split("/");
		
		
		if (action[action.length - 1].equals("create")) {
			HttpSession session = request.getSession();
		
	    	int userId = (int) session.getAttribute("user");
	    	String from = request.getParameter("from");
	    	
	    	String to = request.getParameter("to");
	    	Integer passenger = Integer.parseInt(request.getParameter("passenger"));
	    	Integer categoryId = Integer.parseInt(request.getParameter("categoryId"));
	    	int status = 0;
	    	OrderDaoImpl neworder = new OrderDaoImpl();
	    	
	    	Orders order = new Orders(userId, from, to, passenger, categoryId, status);
	    	neworder.createOrder(order);
	    	Integer id = neworder.getLastOrderId();
	    	String category_name = neworder.checkCategoryOrder(order.getCategoryId(), order.getPassenger());
	    	
	    	HashMap<String, String> order_det = new HashMap<String, String>();
	    	HashMap<String, String> order_alt = new HashMap<String, String>();
	    	
	    	
	    	
	    	if (category_name != null) {
				
				order_det.put("from", from);
				order_det.put("to", to);
				order_det.put("passengers", passenger.toString());
				order_det.put("category", category_name);
				order_det.put("price", order.getPrice().toString());
				order_det.put("category_id",categoryId.toString());
				order_det.put("order_id", id.toString());
				order_det.put("url", request.getContextPath());
			
				request.setAttribute("order_det", order_det);
	    	} else {
	    		
	    	
	    		
	    		List<AltOrder> categories = new ArrayList<AltOrder>();
	    		categories =  neworder.findCar(order.getCategoryId(), order.getPassenger());
	    		
	    		if (categories.equals(null)) {
	    			// Error
	    		} else {
	    			order_alt.put("from", from);
		    		order_alt.put("to", to);
		    		order_alt.put("passengers", passenger.toString());
		    		order_alt.put("price", order.getPrice().toString());
		    		order_alt.put("category_id",categoryId.toString());
		    		order_alt.put("order_id", id.toString());
		    		order_alt.put("url", request.getContextPath());
		    		request.setAttribute("order_alt", order_alt);
		    		request.setAttribute("categories", categories);
	    		}				
	    	}
	    	
	    	request.getRequestDispatcher("/WEB-INF/view/order-result.jsp").forward(request, response);
		}
		
		    	
    	doGet(request, response);
	}
	
	protected void create(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
	}

}
