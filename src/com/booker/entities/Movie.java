package com.booker.entities;

import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;

import com.booker.constants.MovieGenreEnum;
import com.booker.partner.IShareable;

//public class Movie extends Bookmark implements IShareable {
public class Movie extends Bookmark {
	private int releaseYear;
	private String[] cast;
	private String[] directors;
	private MovieGenreEnum genre;
	private double imdbRating;

	public int getReleaseYear() {
		return releaseYear;
	}

	public void setReleaseYear(int releaseYear) {
		this.releaseYear = releaseYear;
	}

	public String[] getCast() {
		return cast;
	}

	public void setCast(String[] cast) {
		this.cast = cast;
	}

	public String[] getDirectors() {
		return directors;
	}

	public void setDirectors(String[] directors) {
		this.directors = directors;
	}

	public MovieGenreEnum getGenre() {
		return genre;
	}

	public void setGenre(MovieGenreEnum genre) {
		this.genre = genre;
	}

	public double getImdbRating() {
		return imdbRating;
	}

	public void setImdbRating(double imdbRating) {
		this.imdbRating = imdbRating;
	}

	@Override
	public String toString() {
		return "Movie [releaseYear=" + releaseYear + ", cast=" + Arrays.toString(cast) + ", directors="
				+ Arrays.toString(directors) + ", genre=" + genre + ", imdbRating=" + imdbRating + "]";
	}

	 @Override
	  public boolean isKidFriendlyEligible() {
	    return (genre.equals(MovieGenreEnum.HORROR) || genre.equals(MovieGenreEnum.THRILLERS)) ? false : true;
	  }

//	@Override
//	public String getItemData() {
//		//StringBuilder instead of "+" to improve performance
//		StringBuilder builder = new StringBuilder();
//		builder.append("<item>"); //starting tag
//			builder.append("<type>Movie</type>");
//			builder.append("<cast>").append(getCast()).append("</cast>"); 
//			builder.append("<directors>").append(StringUtils.join(directors, ",")).append("</directors>"); 
//			//Due to authors being a string array, Apache commons slang was used because it has
//			//a class called String Udall's which can be used combine all 
//			//the directors using just only a single function into a single string
//			builder.append("<genre>").append(genre).append("</genre>");
//			builder.append("<imdbRating>").append(imdbRating).append("</imdbRating>");
//			
//		builder.append("</item>"); //ending tag
//		
//		return builder.toString();
//	}
	
	


}
