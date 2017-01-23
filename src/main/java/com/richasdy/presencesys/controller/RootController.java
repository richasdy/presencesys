package com.richasdy.presencesys.controller;

import java.util.Date;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;

import com.richasdy.presencesys.domain.Card;

@Controller
public class RootController {

	@GetMapping()
	public String save(@Valid Card entity, BindingResult result) {

		return "redirect:/account";

	}

}
