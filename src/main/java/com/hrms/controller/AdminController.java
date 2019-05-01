package com.hrms.controller;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.hrms.entity.User;
import com.hrms.form.UserForm;
import com.hrms.repository.DepartmentRepository;
import com.hrms.repository.UserRepository;
import com.hrms.repository.UserRoleRepository;
import com.hrms.util.UserRoles;

@Controller
public class AdminController {

	@Autowired
	private UserRoleRepository userRoleRepository;

	@Autowired
	private DepartmentRepository departmentRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private Validator validator;

	@Autowired
	private MessageSource messageSource;

	@GetMapping("/addUser")
	public ModelAndView addUserView(@ModelAttribute UserForm form) {
		ModelMap modelMap = new ModelMap();
		modelMap.addAttribute("form", form);
		modelMap.addAttribute("departments", departmentRepository.findAll());
		modelMap.addAttribute("users", userRepository.findAll());
		return new ModelAndView("addUser", modelMap);
	}

	@PostMapping("/addUser")
	public ModelAndView addUser(@ModelAttribute UserForm form) {
		ModelMap modelMap = new ModelMap();
		modelMap.addAttribute("form", form);
		modelMap.addAttribute("departments", departmentRepository.findAll());
		modelMap.addAttribute("users", userRepository.findAll());
		List<String> errors = validator.validate(form).stream().map(v -> v.getMessage()).collect(Collectors.toList());
		if (CollectionUtils.isEmpty(errors)) {
			modelMap.addAttribute("message", messageSource.getMessage("addUser.successful", null, Locale.getDefault()));
			User user = form.toUser();
			user.setPassword(passwordEncoder.encode(form.getPassword()));
			user.setDepartment(departmentRepository.getOne(form.getDepartmentId()));
			user.setIsActive(1);
			user.setRole(userRoleRepository.getOne(UserRoles.EMPLOYEE.getId()));
			user.setDirectManager(userRepository.getOne((long) form.getManagerId()));
			userRepository.save(user);
		} else {
			modelMap.addAttribute("errors", errors);
		}
		return new ModelAndView("addUser", modelMap);
	}

}
