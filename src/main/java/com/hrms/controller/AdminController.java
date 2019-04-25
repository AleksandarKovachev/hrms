package com.hrms.controller;

import com.hrms.form.UserForm;
import com.hrms.repository.DepartmentRepository;
import com.hrms.repository.UserStatusRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AdminController {

  @Autowired
  private UserStatusRepository userStatusRepository;

  @Autowired
  private DepartmentRepository departmentRepository;

  @GetMapping("/addUser")
  public ModelAndView addUserView(@ModelAttribute UserForm form) {
    ModelMap modelMap = new ModelMap();
    modelMap.addAttribute("form", form);
    modelMap.addAttribute("statuses", userStatusRepository.findAll());
    modelMap.addAttribute("departments", departmentRepository.findAll());
    return new ModelAndView("addUser", modelMap);
  }

}
