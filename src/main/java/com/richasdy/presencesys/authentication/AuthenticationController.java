package com.richasdy.presencesys.authentication;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.richasdy.presencesys.account.Account;

@Controller
public class AuthenticationController {
	
	@GetMapping("/login")
	public String login() {

		return "base/login";
	}
	
	@GetMapping("/register")
	public String register() {

		return "base/register";
	}
	
	@GetMapping("/forgetPassword")
	public String forgetPassword() {

		return "base/login";
	}

}
