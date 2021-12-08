package com.ubayKyu.accountingSystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/Ray")
public class Rayyyyyyy {
	
	 @GetMapping("/Ray")
	 public String hello(Model model) {
		 
	        model.addAttribute("ray", "這裡是message1");
	        return "Rayyyyyyy";
	    }

}
