package com.hrms.form;

import java.util.Date;

import javax.validation.constraints.NotBlank;

import org.springframework.format.annotation.DateTimeFormat;

import com.hrms.entity.Vacation;
import com.hrms.util.DateUtil;

import lombok.Data;

@Data
public class VacationForm {

	@NotBlank(message = "{fromDate.empty}")
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private String fromDate;

	@NotBlank(message = "{toDate.empty}")
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private String toDate;

	public Vacation toVacation() {
		Vacation vacation = new Vacation();
		vacation.setCreateDate(new Date());
		vacation.setFromDate(DateUtil.parse(fromDate));
		vacation.setToDate(DateUtil.parse(toDate));
		return vacation;
	}

}
