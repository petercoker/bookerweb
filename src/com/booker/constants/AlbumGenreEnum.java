package com.booker.constants;

public enum AlbumGenreEnum {
	BLUES("Blues"),
	  CLASSICAL("Classical"),
	  LATIN("Latin"),
	  RB("R&B"),
	  RAP("Rap"),
	  HEAVYMETAL("HeavyMetal"),
	  HIPHOP("Hip Hop"),
	  POP("Pop"),
	  GOSPEL("Gospel"),
	  JAZZ("Jazz");

	  private AlbumGenreEnum(String name) {
	    this.name = name;
	  }

	  private String name;

	  public String getName() {
	    return name;
	  }
}
