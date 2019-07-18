package com.booker.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.booker.managers.UserManager;

/**
 * Servlet implementation class AuthController
 */
@WebServlet(urlPatterns = {"/auth", "/auth/logout"})
public class AuthController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AuthController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Servlet path: " + request.getServletPath());
		
		if (!request.getServletPath().contains("logout")) {
			// log out
			
			String email = request.getParameter("email");
			String password = request.getParameter("password");
			
			System.out.println("Get parameter email: " + email); // check get email
		    System.out.println("Get parameter password: " + password); // check get passsword
		
			long userId = UserManager.getInstance().authenticate(email, password);
			if(userId != -1) {
				// if the user click on the user Id
				
				HttpSession session = request.getSession();
				session.setAttribute("userId", userId);
				
				request.getRequestDispatcher("bookmark/mybooks").forward(request, response);;
				
			} else {
				// Send the user back to login page
				request.getRequestDispatcher("/login.jsp").forward(request, response);;
			
			}
		
		} else {
			// if the user click on log out
			request.getSession().invalidate();
			//invaliate the sessison not to allow user to click on the back button to still login
			request.getRequestDispatcher("/login.jsp").forward(request, response);;
			
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
