package com.hrms.util;

public enum UserStatuses {

  ACTIVE(1), INACTIVE(2);

  private int id;

  private UserStatuses(int id) {
    this.id = id;
  }

  public int getId() {
    return id;
  }

}
