package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.impl.OrderDaoImpl;
import dao.impl.UsersDaoImpl;
import entity.OrdersForAdmin;
import entity.Users;

/**
 * Servlet implementation class AdminServlet
 */

@WebServlet("/admin")

public class AdminServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @throws IOException 
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected static  boolean getAuth(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {
    	HttpSession session = request.getSession();
    	String path_proj = request.getContextPath();
    	if (session.getAttribute("user") == null || !session.getAttribute("role").equals(1)) {
			 response.sendRedirect(path_proj);
			 return false;
		}
    	return true;
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		if (getAuth(request, response)) {
			
			HttpSession session = request.getSession();
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
			if (request.getParameter("user_id") != null && !request.getParameter("user_id").equals("null")) {
				session.setAttribute("user_id", request.getParameter("user_id"));
			} else {
				session.removeAttribute("user_id");
			}
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
			request.setAttribute("url", request.getContextPath() + "/admin");
			request.getRequestDispatcher("/WEB-INF/view/admin.jsp").forward(request, response);
		}		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
			
		
		doGet(request, response);
	}

}
