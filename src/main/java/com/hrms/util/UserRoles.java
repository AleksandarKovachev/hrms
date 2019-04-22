package com.hrms.util;

public enum UserRoles {

  ADMIN(1), EMPLOYEE(2);

  private int id;

  private UserRoles(int id) {
    this.id = id;
  }

  public int getId() {
    return id;
  }

}
