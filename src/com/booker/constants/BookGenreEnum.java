package com.booker.constants;

public enum BookGenreEnum {

	  ART("Art"),
	  BIOGRAPHY("Biography"),
	  CHILDREN("Children"),
	  FICTION("Fiction"),
	  HISTORY("History"),
	  MYSTERY("Mystery"),
	  PHILOSOPHY("Philosophy"),
	  RELIGION("Religion"),
	  ROMANCE("Romance"),
	  SELF_HELP("Self help"),
	  TECHNICAL("Technical"),
	  EROTIC("Erotic"),
	  HORROR("Horror");

	  private BookGenreEnum(String name) {
	    this.name = name;
	  }

	  private String name;
	  public String getName() {
	    return name;
	  }
	}