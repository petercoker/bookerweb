package com.booker.entities;

import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;

import com.booker.partner.IShareable;
import com.booker.constants.BookGenreEnum;
import com.booker.constants.MovieGenreEnum;

public class Book extends Bookmark implements IShareable {
	private int publicationYear;
	private String publisher;
	private String[] authors;
	private BookGenreEnum genre;
	private double amazonRating;
	private String imageUrl;

	public int getPublicationYear() {
		return publicationYear;
	}

	public void setPublicationYear(int publicationYear) {
		this.publicationYear = publicationYear;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String[] getAuthors() {
		return authors;
	}

	public void setAuthors(String[] authors) {
		this.authors = authors;
	}

	public BookGenreEnum getGenre() {
		return genre;
	}

	public void setGenre(BookGenreEnum genre) {
		this.genre = genre;
	}

	public double getAmazonRating() {
		return amazonRating;
	}

	public void setAmazonRating(double amazonRating) {
		this.amazonRating = amazonRating;
	}

	@Override
	public String toString() {
		return "Book [publicationYear=" + publicationYear + ", publisher=" + publisher + ", authors="
				+ Arrays.toString(authors) + ", genre=" + genre + ", amazonRating=" + amazonRating + "]";
	}

	 @Override
	  public boolean isKidFriendlyEligible() {
	    return (genre.equals(BookGenreEnum.HORROR) || genre.equals(BookGenreEnum.EROTIC)) ? false : true;
	  }


	@Override
	//returns information about this particular item
	public String getItemData() {
		//StringBuilder instead of "+" to improve performance
		StringBuilder builder = new StringBuilder();
		builder.append("<item>"); //starting tag
			builder.append("<type>Book</type>");//the real data "Book" & type is other tag
			//builder.append("<authors>").append(Arrays.toString(authors)).append("</authors>");
			builder.append("<authors>").append(String.join(",", authors)).append("</authors>");
			
			//builder.append("<authors>").append(StringUtils.join(authors, ",")).append("</authors>"); 
			
			
			//Due to authors being a string array, Apache commons slang was used because it has
			//a class called String Udall's which can be used combine all 
			//the authors using just only a single function into a single string
			
			builder.append("<title>").append(getTitle()).append("</title>"); //get title in the super book 
			builder.append("<publisher>").append(publisher).append("</publisher>");
			builder.append("<publicationYear>").append(publicationYear).append("</publicationYear>");
			builder.append("<genre>").append(genre).append("</genre>");
			builder.append("<amazonRating>").append(amazonRating).append("</amazonRating>");
		builder.append("</item>"); //ending tag
		
		return builder.toString();
			
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageURL) {
		this.imageUrl = imageURL;
	}

}
