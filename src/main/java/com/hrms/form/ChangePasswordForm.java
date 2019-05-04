package com.hrms.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class ChangePasswordForm {

	private String oldPassword;

	@NotBlank(message = "{newPassword.size}")
	@Size(min = 6, message = "{newPassword.size}")
	private String newPassword;

	private String repeatNewPassword;

}
