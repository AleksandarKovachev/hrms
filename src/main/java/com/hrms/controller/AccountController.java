package com.hrms.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.hrms.entity.User;
import com.hrms.repository.UserRepository;

@Controller
public class AccountController {

	@Autowired
	private UserRepository userRepository;

	@GetMapping("/{username}")
	@PreAuthorize("isAuthenticated()")
	public ModelAndView account(@PathVariable String username) {
		ModelMap modelMap = new ModelMap();
		modelMap.addAttribute("account", userRepository.findByUsernameAndIsActive(username, 1));
		return new ModelAndView("account", modelMap);
	}

	@PatchMapping("/profileImage/{id}")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<Void> profileImage(@PathVariable String id,
			@ModelAttribute("accountImage") MultipartFile accountImage) {
		if (accountImage == null || accountImage.isEmpty()) {
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		}

		User user = userRepository.findById(Long.valueOf(id)).orElse(null);
		if (user == null) {
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		}

		try {
			user.setProfileImage(Base64Utils.encodeToString(accountImage.getBytes()));
		} catch (IOException e) {
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		}

		userRepository.save(user);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@DeleteMapping("/profileImage/{id}")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<Void> profileImage(@PathVariable String id) {
		User user = userRepository.findById(Long.valueOf(id)).orElse(null);
		user.setProfileImage(null);
		userRepository.save(user);
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
