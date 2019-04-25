package com.hrms.config;

import com.hrms.entity.User;
import com.hrms.repository.DepartmentRepository;
import com.hrms.repository.UserRepository;
import com.hrms.repository.UserRoleRepository;
import com.hrms.repository.UserStatusRepository;
import com.hrms.util.Department;
import com.hrms.util.UserRoles;
import com.hrms.util.UserStatuses;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  private UserDetailsService userDetailsService;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private UserStatusRepository userStatusRepository;

  @Autowired
  private UserRoleRepository userRoleRepository;

  @Autowired
  private DepartmentRepository departmentRepository;

  @Autowired
  public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
  }

  @Override
  // @formatter:off
  protected void configure(HttpSecurity http) throws Exception {
    http.csrf().disable()
        .logout().logoutSuccessUrl("/").logoutUrl("/logout")
        .invalidateHttpSession(true).deleteCookies("JSESSIONID")
        .and()
        .formLogin().loginPage("/login").permitAll()
        .and()
        .authorizeRequests().antMatchers("/addUser").hasRole("ADMIN")
        .and()
        .authorizeRequests().anyRequest().permitAll();

    User user = userRepository.findByUsername("admin");
    if (user == null) {
      user = new User();
      user.setUsername("admin");
      user.setPassword(passwordEncoder().encode("admin"));
      user.setEmail("admin@admin.admin");
      user.setEgn("101010101010");
      user.setFirstName("Admin");
      user.setLastName("Admin");
      user.setRole(userRoleRepository.findById(UserRoles.ADMIN.getId()).get());
      user.setStatus(userStatusRepository.findById(UserStatuses.ACTIVE.getId()).get());
      user.setDepartment(departmentRepository.findById(Department.ADMIN.getId()).get());
      user.setCreateDate(new Date());
      user.setReceiptDate(new Date());
      userRepository.save(user);
    }
  }
  // @formatter:on

  @Bean
  public AuthenticationManager customAuthenticationManager() throws Exception {
    return authenticationManager();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

}
