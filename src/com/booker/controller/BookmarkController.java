package com.booker.controller;

import com.booker.constants.KidFriendlyStatusEnum;
import com.booker.entities.Bookmark;
import com.booker.entities.User;
import com.booker.managers.BookmarkManager;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/bookmark")
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
		Collection<Bookmark> list  = BookmarkManager.getInstance().getBooks(false, 5); //fetch bookmark from model 
		request.setAttribute("books", list);
		
		request.getRequestDispatcher("/browse.jsp").forward(request, response); //forward bookmark to the view
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

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
