package com.ubayKyu.accountingSystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/test")
public class test2Controller {

	@GetMapping("/test")
	public String hello(Model model) {
		//model.addAttribute("message", "這裡是message1");
		return "test2";
	}

}
