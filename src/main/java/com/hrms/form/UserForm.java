package com.hrms.form;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

import org.springframework.format.annotation.DateTimeFormat;

import com.hrms.entity.User;

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

	public User toUser() {
		User user = new User();
		user.setUsername(username);
		user.setPassword(password);
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setEgn(egn);
		user.setEmail(email);
		user.setCreateDate(new Date());
		try {
			user.setReceiptDate(new SimpleDateFormat("dd/MM/yyyy").parse(receiptDate));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return user;
	}

}
