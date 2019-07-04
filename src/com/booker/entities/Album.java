package com.booker.entities;

import com.booker.constants.AlbumGenreEnum;
import com.booker.partner.IShareable;

import java.util.Arrays;

//public class Album extends Bookmark implements IShareable {
public class Album extends Bookmark {

  private String[] artists;
  private String albumName;
  private AlbumGenreEnum genre;

  public String[] getArtist() {
    return artists;
  }

  public void setArtist(String[] artist) {
    this.artists = artist;
  }

  public String getAlbumName() {
    return albumName;
  }

  public void setAlbumName(String albumName) {
    this.albumName = albumName;
  }

  @Override
  public String toString() {
    return "Album{" +
        "artists=" + Arrays.toString(artists) +
        ", albumName='" + albumName + '\'' +
        ", genre='" + genre + '\'' +
        '}';
  }

  public AlbumGenreEnum getGenre() {
    return genre;
  }

  public void setGenre(AlbumGenreEnum genre) {
    this.genre = genre;
  }

  @Override
  public boolean isKidFriendlyEligible() {

    return (genre.equals(AlbumGenreEnum.HEAVYMETAL) || genre.equals(AlbumGenreEnum.RAP)) ? false : true;
  }

//  @Override
//  public String getItemData() {
//    StringBuilder builder = new StringBuilder();
//    builder.append("<item>");
//    builder.append("<type>Album</type>");
//    builder.append("<artists>").append(getArtist()).append("</artists");
//    builder.append("<albumName>").append(getAlbum()).append("</albumName");
//    builder.append("</item>");
//    return builder.toString();
//  }


}
