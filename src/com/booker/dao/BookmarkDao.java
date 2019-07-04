package com.booker.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;

import com.booker.entities.WebLink;
import com.booker.managers.BookmarkManager;

import java.util.List;

import com.booker.DataStore;
import com.booker.constants.BookGenreEnum;
import com.booker.entities.Album;
import com.booker.entities.Book;
import com.booker.entities.Bookmark;
import com.booker.entities.Movie;
import com.booker.entities.UserBookmark;

public class BookmarkDao {

	// This fetches data from the data source
	public List<List<Bookmark>> getBookmarks() {
		return DataStore.getBookmarks();
	}

	// Save user bookmark to database
	public void saveUserBookmark(UserBookmark userBookmark) {
		// Use 3 data
//		DataStore.add(userBookmark); 

		// Store in userBookmark into Database
		// Created table to capture bookmarking actions for saving Bookmarking data
		try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/booker?useSSL=false", "root",
				"vCEyuK6md6UuoM"); /*
									 * a) Database User Profile: root is who the user is b) Database user password
									 */
				Statement stmt = conn.createStatement();) /* execute mysql queries */ {
			if (userBookmark.getBookmark() instanceof Book) {
				saveUserBook(userBookmark, stmt);
			} else if (userBookmark.getBookmark() instanceof Movie) {
				saveUserMovie(userBookmark, stmt);
			} else if (userBookmark.getBookmark() instanceof WebLink) {
				saveWebLink(userBookmark, stmt);
			} else {
				saveUserAlbum(userBookmark, stmt);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void saveUserBook(UserBookmark userBookmark, Statement stmt) throws SQLException {
		String query = "INSERT INTO User_Book (user_id, book_id)" + "values (" + userBookmark.getUser().getId() + ", "
				+ userBookmark.getBookmark().getId() + ")";
		stmt.executeUpdate(query); // executeUpdate used into Update
	}

	private void saveUserMovie(UserBookmark userBookmark, Statement stmt) throws SQLException {
		String query = "INSERT INTO User_Movie (user_id, movie_id)" + "values (" + userBookmark.getUser().getId() + ", "
				+ userBookmark.getBookmark().getId() + ")";
		stmt.executeUpdate(query); // executeUpdate used into Update
	}

	private void saveWebLink(UserBookmark userBookmark, Statement stmt) throws SQLException {
		String query = "INSERT INTO User_WebLink (user_id, weblink_id)" + "values (" + userBookmark.getUser().getId()
				+ ", " + userBookmark.getBookmark().getId() + ")";
		stmt.executeUpdate(query); // executeUpdate used into Update

	}

	private void saveUserAlbum(UserBookmark userBookmark, Statement stmt) throws SQLException {
		String query = "INSERT INTO User_Album (user_id, album_id)" + "values (" + userBookmark.getUser().getId() + ", "
				+ userBookmark.getBookmark().getId() + ")";
		stmt.executeUpdate(query); // executeUpdate used into Update

	}

	// SQL queries
	public List<WebLink> getAllWebLinks() {
		List<WebLink> result = new ArrayList<>();
		List<List<Bookmark>> bookmarks = DataStore.getBookmarks(); // get list of list of bookmarks

		List<Bookmark> allWebLinks = bookmarks.get(0);

		for (Bookmark bookmark : allWebLinks) {
			result.add((WebLink) bookmark); // Downcast
		}

		return result;
	}

	// get only new weblinks not attempted
	public List<WebLink> getWebLinks(WebLink.DownloadStatus downloadStatus) {
		List<WebLink> result = new ArrayList<>();

		List<WebLink> allWebLinks = getAllWebLinks();
		for (WebLink webLink : allWebLinks) {
			if (webLink.getDownloadStatus().equals(downloadStatus)) {
				result.add(webLink);
			}
		}
		return result;
	}

	// update KidFriendlyStatus in database
	public void updateKidFriendlyStatus(Bookmark bookmark) {
		// get kidfriendlyStatus, which is an enum constant and envoke the ordinal
		// method from enum class
		int kidFriendlyStatus = bookmark.getKidFrienldyStatus().ordinal(); // (the ordinal value can be anyone e.g
																			// status can be Approved - 0, Rejected - 1
																			// or Unknown - 2

		// get user who marked kidfriendly status
		long userId = bookmark.getKidFriendlyMarkedBy().getId();

		String tableToUpdate = "Book";

		// By default the bookmark is Movie, Check if the bookmark is Movie, update
		// bookmark to Movie
		if (bookmark instanceof Movie) {
			tableToUpdate = "Movie";
		}
		// Check if the bookmark is Album, update bookmark to Album
		else if (bookmark instanceof Album) {
			tableToUpdate = "Album";
		}
		// Check if the bookmark is WebLink, update bookmark to WebLink
		else if (bookmark instanceof WebLink) {
			tableToUpdate = "WebLink";
		}

		// Dao connection
		try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/booker?useSSL=false", "root",
				"vCEyuK6md6UuoM"); /*
									 * a) Database User Profile: root is who the user is b) Database user password
									 */
				Statement stmt = conn.createStatement();) /* execute mysql queries */ {

			// tableToUpdate could be any of the bookmarks
			// update table query: update table, about bookmark an item
			String query = "update " + tableToUpdate + " set kid_friendly_status = " + kidFriendlyStatus
					+ ", kid_friendly_marked_by = " + userId + " where id = " + bookmark.getId();
			System.out.println("query (updateKidFriendlyStatus): " + query);
			stmt.executeUpdate(query);

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void shareByInfo(Bookmark bookmark) {
		// get user who marked kidfriendly status
		long userId = bookmark.getShareBy().getId();

		String tableToUpdate = "Book";

		// By default the bookmark is WebLink, Check if the bookmark is WebLink, update
		// bookmark to WebLink
		if (bookmark instanceof WebLink) {
			tableToUpdate = "WebLink";
		}

		// Dao connection
		try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/booker?useSSL=false", "root",
				"vCEyuK6md6UuoM"); /*
									 * a) Database User Profile: root is who the user is b) Database user password
									 */
				Statement stmt = conn.createStatement();) /* execute mysql queries */ {

			// tableToUpdate could be any of the bookmarks
			// update table query: update table, about who shared the bookmark
			String query = "update " + tableToUpdate + " set shared_by = " + userId + " where id = " + bookmark.getId();
			System.out.println("query (updateKidFriendlyStatus): " + query);
			stmt.executeUpdate(query);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Collection<Bookmark> getBooks(boolean isBookmarked, long userId) {
		Collection<Bookmark> result = new ArrayList<>();

		try {
			Class.forName("com.mysql.cj.jdbc.Driver"); //loads driver and register with the jsp api
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/booker?useSSL=false", "root",
				"vCEyuK6md6UuoM");
				Statement stmt = conn.createStatement();) {

			String query = "";
			if (!isBookmarked) {
				query = "Select b.id, title, image_url, publication_year, GROUP_CONCAT(a.name SEPARATOR ',') AS authors, book_genre_id, "
						+ "amazon_rating from Book b, Author a, Book_Author ba where b.id = ba.book_id and ba.author_id = a.id and "
						+ "b.id NOT IN (select ub.book_id from User u, User_Book ub where u.id = " + userId
						+ " and u.id = ub.user_id) group by b.id"; 
				//Get all books by this users, where u.id
				//nested query to get all the books the users has queried
			} /*
				 * else { query =
				 * "Select b.id, title, image_url, publication_year, GROUP_CONCAT(a.name SEPARATOR ',') AS authors, book_genre_id, "
				 * +
				 * "amazon_rating from Book b, Author a, Book_Author ba where b.id = ba.book_id and ba.author_id = a.id and "
				 * + "b.id IN (select ub.book_id from User u, User_Book ub where u.id = " +
				 * userId + " and u.id = ub.user_id) group by b.id"; }
				 */

			ResultSet rs = stmt.executeQuery(query);

			while (rs.next()) {
				long id = rs.getLong("id");
				String title = rs.getString("title");
				String imageUrl = rs.getString("image_url");
				int publicationYear = rs.getInt("publication_year");
				// String publisher = rs.getString("name");
				String[] authors = rs.getString("authors").split(",");
				int genre_id = rs.getInt("book_genre_id");
				BookGenreEnum genre = BookGenreEnum.values()[genre_id];
				double amazonRating = rs.getDouble("amazon_rating");

				System.out.println(
						"id: " + id + ", title: " + title + ", publication year: " + publicationYear + ", authors: "
								+ String.join(", ", authors) + ", genre: " + genre + ", amazonRating: " + amazonRating);

				Bookmark bookmark = BookmarkManager.getInstance().createBook(id, title, imageUrl, publicationYear, null,
						authors, genre, amazonRating/* , values[7] */);
				result.add(bookmark);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;
	}
}
