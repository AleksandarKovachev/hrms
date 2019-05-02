package com.hrms.form;

import java.util.Date;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

import org.springframework.format.annotation.DateTimeFormat;

import com.hrms.entity.User;
import com.hrms.util.DateUtil;

import lombok.Data;

@Data
public class UserForm {

	@NotBlank(message = "{username.empty}")
	private String username;

	@NotBlank(message = "{firstName.empty}")
	private String firstName;

	@NotBlank(message = "{lastName.empty}")
	private String lastName;

	@NotBlank(message = "{password.empty}")
	private String password;

	@NotBlank(message = "{egn.empty}")
	private String egn;

	@Email(message = "{email.empty}")
	@NotBlank(message = "{email.empty}")
	private String email;

	@Positive(message = "{department.empty}")
	private int departmentId;

	@NotBlank(message = "{receiptDate.empty}")
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private String receiptDate;

	private int managerId;

	public User toUser() {
		User user = new User();
		user.setUsername(username);
		user.setPassword(password);
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setEgn(egn);
		user.setEmail(email);
		user.setCreateDate(new Date());
		user.setReceiptDate(DateUtil.parse(receiptDate));
		return user;
	}

}
