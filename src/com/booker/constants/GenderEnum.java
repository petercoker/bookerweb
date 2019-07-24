package com.booker.constants;

public enum GenderEnum {
	  MALE(0),
	  FEMALE(1),
	  TRANSGENDER(2);

	  private GenderEnum(int value) {
	    this.value = value;
	  }

	  private int value;
	  public int getGender() {
	    return value;
	  }
	}