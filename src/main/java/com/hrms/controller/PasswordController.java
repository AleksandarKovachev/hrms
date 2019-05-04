package com.hrms.controller;

import java.security.Principal;
import java.util.Arrays;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.hrms.entity.User;
import com.hrms.form.ChangePasswordForm;
import com.hrms.repository.UserRepository;

@Controller
public class PasswordController {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private MessageSource messageSource;

	@GetMapping("/changePassword")
	public ModelAndView changePasswordGet(@ModelAttribute ChangePasswordForm form) {
		ModelMap modelMap = new ModelMap();
		modelMap.addAttribute("form", form);
		return new ModelAndView("changePassword", modelMap);
	}

	@PostMapping("/changePassword")
	public ModelAndView changePasswordPost(@ModelAttribute ChangePasswordForm form, Principal principal) {
		ModelMap modelMap = new ModelMap();
		String error = null;
		if (!form.getNewPassword().equals(form.getRepeatNewPassword())) {
			error = messageSource.getMessage("repeat.password.wrong", null, Locale.getDefault());
		}
		if (error == null) {
			User user = userRepository.findByUsernameAndIsActive(principal.getName(), 1);
			if (!passwordEncoder.matches(form.getOldPassword(), user.getPassword())) {
				error = messageSource.getMessage("old.password.wrong", null, Locale.getDefault());
			}
			if (error == null) {
				user.setPassword(passwordEncoder.encode(form.getNewPassword()));
				userRepository.save(user);
				modelMap.addAttribute("message",
						messageSource.getMessage("changePassword.successful", null, Locale.getDefault()));
			}
		}
		if (error != null) {
			modelMap.addAttribute("errors", Arrays.asList(error));
		}
		modelMap.addAttribute("form", form);
		return new ModelAndView("changePassword", modelMap);
	}

}
