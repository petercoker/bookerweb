package com.booker.managers;

import java.util.Collection;
import java.util.List;

import com.booker.constants.AlbumGenreEnum;
import com.booker.constants.BookGenreEnum;
import com.booker.constants.KidFriendlyStatusEnum;
//import com.booker.constants.GenderGenreEnum;
import com.booker.constants.MovieGenreEnum;
import com.booker.dao.BookmarkDao;
import com.booker.entities.Album;
import com.booker.entities.Book;
import com.booker.entities.Bookmark;
import com.booker.entities.Movie;
import com.booker.entities.User;
import com.booker.entities.UserBookmark;
import com.booker.entities.WebLink;

public class BookmarkManager {

	private static BookmarkManager instance = new BookmarkManager();
	private static BookmarkDao dao = new BookmarkDao();

	private BookmarkManager() {
	}

	public static BookmarkManager getInstance() {
		return instance;
	}

	public Book createBook(long id, String title, String imageURL, int publicationYear, String publisher, String[] authors, BookGenreEnum genre,
			double amazonRating) {

		Book book = new Book();
		book.setId(id);
		book.setTitle(title);
		book.setImageURL(imageURL);
		book.setPublicationYear(publicationYear);
		book.setPublisher(publisher);
		book.setAuthors(authors);
		book.setGenre(genre);
		book.setAmazonRating(amazonRating);

		return book;
	}
	
	public Album createAlbum(long id, String[] artists, String albumName, AlbumGenreEnum genre) {
		Album album = new Album();
		album.setId(id);
		album.setArtist(artists);
		album.setAlbumName(albumName);
		album.setGenre(genre);
		return album;
	}

	public Movie createMovie(long id, String title, String profileUrl, int releaseYear, String[] cast,
			String[] directors, MovieGenreEnum genre, double imdbRating) {
		Movie movie = new Movie();
		movie.setId(id);
		movie.setTitle(title);
		movie.setProfileUrl(profileUrl);
		movie.setReleaseYear(releaseYear);
		movie.setCast(cast);
		movie.setDirectors(directors);
		movie.setGenre(genre);
		movie.setImdbRating(imdbRating);

		return movie;
	}

	public WebLink createWebLink(long id, String title, String url, String host) {
		WebLink webLink = new WebLink();
		webLink.setId(id);
		webLink.setTitle(title);
		webLink.setUrl(url);
		webLink.setHost(host);
		return webLink;

	}

	// This featches data from the data source
	public List<List<Bookmark>> getBokmarks() {
		return dao.getBookmarks();
	}

	// Initiating userbookmark with User and Bookmark object
	// Maintains the relationship between a user and a bookmark
		public void saveUserBookmark(User user, Bookmark bookmark) {
			UserBookmark userBookmark = new UserBookmark();

			userBookmark.setUser(user);
			userBookmark.setBookmark(bookmark);
			
			// passing these particular objects to DAO package
			dao.saveUserBookmark(userBookmark); //interacts with Database
		}


    //Bookmark status is being set to kidFriendlyStatus and which user marked boookmark as kidfriendly
	public void setKidFriendlyStatus(User user, KidFriendlyStatusEnum kidFriendlyStatus, Bookmark bookmark) {
	    //bookmark is set to kidFriendlyStatus
		bookmark.setKidFrienldyStatus(kidFriendlyStatus);
		
		//user who mark the bookmark as kidfriendly
		bookmark.setKidFriendlyMarkedBy(user);
		
		//update kidfriendlystatus within in the DAO 
		dao.updateKidFriendlyStatus(bookmark);
		
		System.out.println(
				"kid-friendly status: " + kidFriendlyStatus + ", Marked by " + user.getEmail() + ", " + bookmark);
	}

	public void share(User user, Bookmark bookmark) {
		//set which bookmark is share by a particular user 
		bookmark.setShareBy(user);
		
		System.out.println("Data to be share: ");
		if(bookmark instanceof Book) {
			System.out.println(((Book) bookmark).getItemData());
		}else if(bookmark instanceof WebLink) {
			System.out.println(((WebLink) bookmark).getItemData());
		}
		
		//update the table in database, about who shared the bookmark
		dao.shareByInfo(bookmark);
	}

	public Collection<Bookmark> getBooks(boolean isBookmarked, long id) { //long instead of int because Bookmark ID is long
		return dao.getBooks(isBookmarked, id);
	}
}
