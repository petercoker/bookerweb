package com.booker;

import java.util.List;

import com.booker.entities.Bookmark;
import com.booker.entities.User;
import com.booker.managers.BookmarkManager;
import com.booker.managers.UserManager;
import com.booker.bgjobs.WebpageDownloaderTask;

public class Launch {

	private static List<User> users;
	private static List<List<Bookmark>> bookmarks;
	
	private static void loadData() {
		System.out.println("1. Loadings data...");
		DataStore.loadData();
		
		users = UserManager.getInstance().getUsers();
		bookmarks = BookmarkManager.getInstance().getBokmarks();
		
		System.out.println("\nPrinting data....");
		printUserData();
		printBookmarkData();
				
	}
	
	private static void printBookmarkData() {
		for(List<Bookmark> bookmarkList : bookmarks) {
			for (Bookmark bookmark : bookmarkList) {
				System.out.println(bookmark);
			}
		}
		
	}

	private static void printUserData() {
		for(User user : users) {
			System.out.println(user);
		}
	}
	
	
	private static void startBookmarking() {
		//System.out.println("\n2. Bookmarking...");
		for(User user : users) {
			View.browse(user, bookmarks);
		}
	}
	
	public static void main(String[] args) {
		loadData();
		startBookmarking();
//		runDownloaderJob(); //Backgroundd Jobs
	}

	private static void runDownloaderJob() {
		WebpageDownloaderTask task = new WebpageDownloaderTask(true);
		(new Thread(task)).start();
		
	}


}
