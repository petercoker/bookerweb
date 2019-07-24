package com.booker.constants;

public enum UserTypeEnum {
  USER("user"),
  EDITOR("editor"),
  CHIEF_EDITOR("chiefeditor");

  private UserTypeEnum(String name) {
    this.name = name;
  }

  private String name;
  public String getName() {
    return name;
  }
}