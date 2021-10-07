package controller;


import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import dao.impl.UsersDaoImpl;
import entity.Users;

/**
 * Servlet implementation class HomeServlet
 */

@WebServlet("/user")

public class UserServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private String path_proj;
    
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserServlet() {
        super();
        
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
        
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
    	 String path = request.getServletPath();
    	 
    	 path_proj = request.getContextPath();
    	 
    	 String[] action = request.getRequestURL().toString().split("/");
    	 HttpSession session = request.getSession();
    	 
    	 
    	

    	 if (action[action.length - 1].equals("log_out")) {
    		 session.removeAttribute("user");
    		 session.removeAttribute("role");
    		 response.sendRedirect(path_proj);
    	 }
    	 
    	 
   
    	 if (session.getAttribute("user") != null && path.equals("/")) {
    		 if (session.getAttribute("role").equals(1)) {
    			 response.sendRedirect(path_proj + "/admin");
    		 }
    		 if (session.getAttribute("role").equals(2)) {
    			 response.sendRedirect(path_proj + "/order");
    		 }
    	 } else if (path.equals("/")) {
            request.getRequestDispatcher("/WEB-INF/view/index.jsp").forward(request, response);
    	 }
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
    	 
    	//ResourceBundle bundleDefault = ResourceBundle.getBundle("	");
    	
    	
    	
    	String[] action = request.getRequestURL().toString().split("/");
    	HttpSession session = request.getSession();
    	
    	
    	
    	
    	if (action[action.length - 1].equals("sign_up")) {
    		// Registration
    		String login =null;
        	String password= null;
        	String email=null;
            login = request.getParameter("login");
            password = request.getParameter("password");
            email = request.getParameter("email");
            Users user = new Users(login,password,email, 2);
            UsersDaoImpl userService = new UsersDaoImpl();
                        
            
            
            if (!userService.checkEmail(user)) {
            	session.setAttribute("error", "User with this email has already exists.");
            	response.sendRedirect(path_proj);
//            	response.sendRedirect("/");
            }
            if (userService.createUser(user)) {
            	session.setAttribute("success", "User was registered. Sign in use yuors email and password");
            	response.sendRedirect(path_proj);
            } else {
            	session.setAttribute("error", "Internal server error.");
            	response.sendRedirect(path_proj);
            }
            
            user.toString();
            response.getWriter().append(login).append(" ").append(password).append( user.toString());
            // сторінка замовлень
            doGet(request, response);
    	} else if(action[action.length - 1].equals("sign_in")) {
    		UsersDaoImpl userService = new UsersDaoImpl();
    		String password= null;
        	String email=null;
    		password = request.getParameter("password");
            email = request.getParameter("email");
    		Users user = userService.getUserByPasswordAndLogin(password, email);
    		if (user == null) {
    			session.setAttribute("error", "Incorrect user or password");
    			response.sendRedirect(path_proj);
    		}else {
    		session.setAttribute("user", user.getId());
    		session.setAttribute("role", user.getRoleId());
    		response.sendRedirect(path_proj);
    		}
    		
//    		request.getRequestDispatcher("/WEB-INF/view/index.jsp").forward(request, response);
    	} else {
        	response.sendError(404);
    	}
    	   	
    	
    }

}
