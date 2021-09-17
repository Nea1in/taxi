package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.impl.CategoryDaoImpl;
import entity.Categories;

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
			CategoryDaoImpl category = new CategoryDaoImpl();
			List<Categories> categories = new ArrayList<Categories>();
			categories = category.getAllCategories();
			request.setAttribute("categories", categories);
			request.getRequestDispatcher("/WEB-INF/view/order.jsp").forward(request, response);
		}
		 
		//response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
