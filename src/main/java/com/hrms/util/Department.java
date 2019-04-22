package com.hrms.util;

public enum Department {

  ADMIN(1), MANAGER(2), IT(3), FINANCE(4), ACCOUNTING(5), DEVELOPMENT(6), HR(7);

  private int id;

  private Department(int id) {
    this.id = id;
  }

  public int getId() {
    return id;
  }

}
