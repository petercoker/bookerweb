package com.booker;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.booker.constants.AlbumGenreEnum;
import com.booker.constants.BookGenreEnum;
import com.booker.constants.GenderEnum;
import com.booker.constants.MovieGenreEnum;
import com.booker.constants.UserTypeEnum;
import com.booker.entities.Album;
import com.booker.entities.Bookmark;
import com.booker.entities.User;
import com.booker.entities.UserBookmark;
import com.booker.managers.UserManager;

import com.booker.managers.BookmarkManager;

public class DataStore {

	// Added getter to allow other classes to invoke user
	private static List<User> users = new ArrayList<>();

	// Added getter to allow other classes to invoke bookmark
	// All bookmarks are stored here in this data structure
	public static List<User> getUsers() {
		return users;
	}

	// two dimensional array because of different kinds of bookmarks
	// one array is for the type of bookmark
	// another array for the actual bookmark
	private static List<List<Bookmark>> bookmarks = new ArrayList<>();
	// There are 5 bookmarks
	// Each user has a limit to bookmark only 5 bookmarks

	// particular user & other users will be bookmarking bookmarks in a similar way
	// Which an index was created to keep track of those booking and
	// that index need to be incrementated with each added bookmark
	// This method is load the data
	public static List<List<Bookmark>> getBookmarks() {
		return bookmarks;
	}

	private static List<UserBookmark> userBookmarks = new ArrayList<>();

	public static void loadData() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		// try-with-resources ==> conn & stmt will be closed
		// Connection string: <protocol>:<sub-protocol>:<data-source details>
		// Connection to my database
		try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/booker?useSSL=false", "root",
				"vCEyuK6md6UuoM"); /* a) Database User Profile: root is who the user is b) Database user password */
				Statement stmt = conn.createStatement();) /* execute mysql queries */ {
			loadUsers(stmt);
			loadWebLinks(stmt);
			loadMovies(stmt);
			loadBooks(stmt);
			LoadAlbums(stmt);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private static void loadUsers(Statement stmt) throws SQLException {
		String query = "Select * from User";
		ResultSet rs = stmt.executeQuery(query);

		while (rs.next()) {
			long id = rs.getLong("id");
			String email = rs.getString("email");
			String password = rs.getString("password");
			String firstName = rs.getString("first_name");
			String lastName = rs.getString("last_name");
			int gender_id = rs.getInt("gender_id");
			GenderEnum gender = GenderEnum.values()[gender_id];
			int user_type_id = rs.getInt("user_type_id");
			UserTypeEnum userType = UserTypeEnum.values()[user_type_id];

			User user = UserManager.getInstance().createUser(id, email, password, firstName, lastName, gender,
					userType);
			users.add(user);
		}
	}

	private static void loadWebLinks(Statement stmt) throws SQLException {
		String query = "Select * from WebLink";
		ResultSet rs = stmt.executeQuery(query);

		List<Bookmark> bookmarkList = new ArrayList<>();
		while (rs.next()) {
			long id = rs.getLong("id");
			String title = rs.getString("title");
			String url = rs.getString("url");
			String host = rs.getString("host");

			Bookmark bookmark = BookmarkManager.getInstance().createWebLink(id, title, url, host);
			bookmarkList.add(bookmark);
		}

		bookmarks.add(bookmarkList);

	}

	private static void loadMovies(Statement stmt) throws SQLException {
		String query = "Select m.id, title, release_year, GROUP_CONCAT(DISTINCT a.name SEPARATOR ',') AS cast, GROUP_CONCAT(DISTINCT d.name SEPARATOR ',') AS directors, movie_genre_id, imdb_rating"
				+ " from Movie m, Actor a, Movie_Actor ma, Director d, Movie_Director md "
				+ "where m.id = ma.movie_id and ma.actor_id = a.id and "
				+ "m.id = md.movie_id and md.director_id = d.id group by m.id";
		ResultSet rs = stmt.executeQuery(query);

		List<Bookmark> bookmarkList = new ArrayList<>();
		while (rs.next()) {
			long id = rs.getLong("id");
			String title = rs.getString("title");
			int releaseYear = rs.getInt("release_year");
			String[] cast = rs.getString("cast").split(",");
			String[] directors = rs.getString("directors").split(",");
			int genre_id = rs.getInt("movie_genre_id");
			MovieGenreEnum genre = MovieGenreEnum.values()[genre_id];
			double imdbRating = rs.getDouble("imdb_rating");

			Bookmark bookmark = BookmarkManager.getInstance().createMovie(id, title, "", releaseYear, cast, directors,
					genre, imdbRating/* , values[7] */);
			bookmarkList.add(bookmark);
		}

		bookmarks.add(bookmarkList);
	}

	private static void loadBooks(Statement stmt) throws SQLException {
		String query = "Select b.id, title, publication_year, p.name, GROUP_CONCAT(a.name SEPARATOR ',') AS authors, book_genre_id, amazon_rating, created_date"
				+ " from Book b, Publisher p, Author a, Book_Author ba "
				+ "where b.publisher_id = p.id and b.id = ba.book_id and ba.author_id = a.id group by b.id";
		ResultSet rs = stmt.executeQuery(query);

		List<Bookmark> bookmarkList = new ArrayList<>();
		while (rs.next()) {
			long id = rs.getLong("id");
			String title = rs.getString("title");
			int publicationYear = rs.getInt("publication_year");
			String publisher = rs.getString("name");
			String[] authors = rs.getString("authors").split(",");
			int genre_id = rs.getInt("book_genre_id");
			BookGenreEnum genre = BookGenreEnum.values()[genre_id];
			double amazonRating = rs.getDouble("amazon_rating");

			// Get date
			Date createdDate = rs.getDate("created_date");
			System.out.println("createdDate: " + createdDate);
			Timestamp timeStamp = rs.getTimestamp(8);
			System.out.println("timeStamp: " + timeStamp);
			System.out.println("localDateTime: " + timeStamp.toLocalDateTime());

			System.out.println("id: " + id + ", title: " + title + ", publication year: " + publicationYear
					+ ", publisher: " + publisher + ", authors: " + String.join(", ", authors) + ", genre: " + genre
					+ ", amazonRating: " + amazonRating);

			Bookmark bookmark = BookmarkManager.getInstance().createBook(id, title, publicationYear, publisher, authors,
					genre, amazonRating/* , values[7] */);
			bookmarkList.add(bookmark); // add the bookmark to mysql database
		}

		bookmarks.add(bookmarkList); // add the bookmark
	}

	private static void LoadAlbums(Statement stmt) throws SQLException {
		
		String query = " Select a.id, GROUP_CONCAT(DISTINCT ar.name SEPARATOR ',') AS artists, title, album_genre_id, created_date "
				+ " from Album a, Artist ar, Album_Artist aar "
				+ " where a.id = aar.album_id and aar.artist_id = ar.id group by a.id ";
		
		//String query = "Select * from album";

		ResultSet rs = stmt.executeQuery(query);
		List<Bookmark> bookmarkList = new ArrayList<>();
		while (rs.next()) {
			long id = rs.getLong("id");
			String[] artists = rs.getString("artists").split(",");
			String albumName = rs.getString("title");
			int genre_id = rs.getInt("album_genre_id");
			AlbumGenreEnum genre = AlbumGenreEnum.values()[genre_id];
			
			Bookmark bookmark = BookmarkManager.getInstance().createAlbum(id, artists, albumName, genre/* , values[4] */);
			bookmarkList.add(bookmark);
		}

		bookmarks.add(bookmarkList);
	}

	public static void add(UserBookmark userBookmark) {
		userBookmarks.add(userBookmark);
	}
}