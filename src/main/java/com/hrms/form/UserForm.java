package com.hrms.form;

import lombok.Data;

@Data
public class UserForm {

  private String username;

  private String firstName;

  private String lastName;

  private String password;

  private String egn;

  private String email;

  private int departmentId;

  private int statusId;

}
