package com.ubayKyu.accountingSystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ubayKyu.accountingSystem.dto.ListedCategory;
import com.ubayKyu.accountingSystem.repository.CategoryRepository;
import com.ubayKyu.accountingSystem.repository.testRepo;

@Controller
@RequestMapping("/Default")
public class tester {		
	
	@Autowired
	CategoryRepository CategoryRepository; 
	@Autowired
	testRepo testRepo; 

	@GetMapping("/test")
	public String test(Model model) {		
		String userid = "94037537-7245-4530-BB73-01FE015973E7";
		List<ListedCategory> listedCategory = CategoryRepository.GetListedCategory(userid);
		String result =  listedCategory.get(0).getCaption();
		model.addAttribute("testResult",result );			
	
		
		//model.addAttribute("testResult", "test");			

		return "TestLand.html";
		//return "Default.html";
	}

}
