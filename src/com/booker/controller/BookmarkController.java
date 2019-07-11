package com.booker.controller;

import com.booker.constants.KidFriendlyStatusEnum;
import com.booker.entities.Bookmark;
import com.booker.entities.User;
import com.booker.managers.BookmarkManager;
import com.booker.managers.UserManager;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = {"/bookmark", "/boomark/save", "/bookmark/mybooks"})
//This will be a singleton
public class BookmarkController extends HttpServlet {
	// Don't because tomecat will create a singleton of the servlet
	// Which tomecat does thie
	/*
	 * //variable that holds the instance private static BookmarkController instance
	 * = new BookmarkController();
	 * 
	 * 
	 * private BookmarkController() {}
	 * 
	 * public static BookmarkController getInstance(){ return instance; }
	 */

	public BookmarkController() {
	    // TODO Auto-generated constructor stub
    }
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		RequestDispatcher dispatcher = null;
		System.out.println("Servlet path: " + request.getServletPath());
	
		if(request.getServletPath().contains("save")) { //If the users click on save
			// save
			dispatcher = request.getRequestDispatcher("/mybooks.jsp");
			
			String bid = request.getParameter("bid"); //book id
			
			// Get data from Database
			User user = UserManager.getInstance().getUser(5);
			Bookmark bookmark = BookmarkManager.getInstance().getBook(Long.parseLong(bid));
			
			// Save user bookmark
			BookmarkManager.getInstance().saveUserBookmark(user, bookmark);
			
			// Get all the bookmark the user bookmark
			Collection<Bookmark> list  = BookmarkManager.getInstance().getBooks(true, 5); //fetch all bookmark from model 
			request.setAttribute("books", list);
			
			
		} else if (request.getServletPath().contains("mybooks")) { //If the users click on mybooks
			// mybooks
			dispatcher = request.getRequestDispatcher("/mybooks.jsp");
			
			Collection<Bookmark> list  = BookmarkManager.getInstance().getBooks(true, 5); //fetch all bookmark from model 
			request.setAttribute("books", list);
			
		} else { //If the users click on browse
			dispatcher = request.getRequestDispatcher("/browse.jsp");
			
			Collection<Bookmark> list  = BookmarkManager.getInstance().getBooks(false, 5); //fetch only bookmarks bookmarked by user  from model 
			request.setAttribute("books", list);
			
		}
		
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
//	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		// TODO Auto-generated method stub
//		doGet(request, response);
//	}

	// From Controller, Bookmark is invoked through the method manager class
	public void saveUserBookMark(User user, Bookmark bookmark) {
		// From Controller to DAO
		BookmarkManager.getInstance().saveUserBookmark(user, bookmark); // then bookmarkmanager invoke DAO
	}

	// Is kidfriendly request is received
	public void setKidFriendlyStatus(User user, KidFriendlyStatusEnum kidFriendlyStatus, Bookmark bookmark) {
		BookmarkManager.getInstance().setKidFriendlyStatus(user, kidFriendlyStatus, bookmark);

	}

	// Is share by request is received
	public void share(User user, Bookmark bookmark) {

		// implementing get instance and implement share()
		BookmarkManager.getInstance().share(user, bookmark);

	}

}
