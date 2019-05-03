package com.hrms.controller;

import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.hrms.entity.Vacation;
import com.hrms.form.VacationForm;
import com.hrms.repository.UserRepository;
import com.hrms.repository.VacationRepository;
import com.hrms.util.DateUtil;

@Controller
public class VacationController {

	@Autowired
	private VacationRepository vacationRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private Validator validator;

	@GetMapping("/vacation")
	public ModelAndView vacationGet(@ModelAttribute VacationForm form, Principal principal) {
		ModelMap modelMap = new ModelMap();
		modelMap.addAttribute("form", form);
		modelMap.addAttribute("vacations", vacationRepository.getVacationsByDepartment(
				userRepository.findByUsernameAndIsActive(principal.getName(), 1).getDepartment().getId(), new Date()));
		return new ModelAndView("vacation", modelMap);
	}

	@PostMapping("/vacation")
	public ModelAndView vacationPost(@ModelAttribute VacationForm form, Principal principal) {
		ModelMap modelMap = new ModelMap();
		modelMap.addAttribute("form", form);
		modelMap.addAttribute("vacations", vacationRepository.getVacationsByDepartment(
				userRepository.findByUsernameAndIsActive(principal.getName(), 1).getDepartment().getId(), new Date()));
		List<String> errors = validator.validate(form).stream().map(v -> v.getMessage()).collect(Collectors.toList());
		if (DateUtil.parse(form.getFromDate()).after(DateUtil.parse(form.getToDate()))) {
			errors.add(messageSource.getMessage("fromDate.before.toDate", null, Locale.getDefault()));
		}
		if (CollectionUtils.isEmpty(errors)) {
			modelMap.addAttribute("message",
					messageSource.getMessage("vacation.successful", null, Locale.getDefault()));
			Vacation vacation = form.toVacation();
			vacation.setUser(userRepository.findByUsernameAndIsActive(principal.getName(), 1));
			vacationRepository.save(vacation);
		} else {
			modelMap.addAttribute("errors", errors);
		}
		return new ModelAndView("vacation", modelMap);
	}

}
