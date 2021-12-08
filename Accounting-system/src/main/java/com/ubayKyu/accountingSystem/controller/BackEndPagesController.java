package com.ubayKyu.accountingSystem.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ubayKyu.accountingSystem.service.LoginService;

@Controller
@RequestMapping("/BackEndPages")
public class BackEndPagesController {
	@Autowired
	HttpSession session;

	@GetMapping("/UserDetail")
	public String UserDetail(Model model) {
		// -------判斷登入----//
		if (!LoginService.IsLogin(session)) {
			String url = "/Default/Logout"; // 重新導向到指定的url
			return "redirect:" + url; // 重新導向到指定的url
		}
		// -------判斷登入----//
		model.addAttribute("account", "Account_1");
		model.addAttribute("creatTime", "2021/8/2 下午 09:24:16");
		model.addAttribute("updateTime", "2021/8/2 下午 09:24:16");
		return "/BackEndPages/UserDetail";
	}

	@GetMapping("/UserList")
	public String UserList(Model model) {
		// -------判斷登入----//
		if (!LoginService.IsLogin(session)) {
			String url = "/Default/Logout"; // 重新導向到指定的url
			return "redirect:" + url; // 重新導向到指定的url
		}
		// -------判斷登入----//
		return "/BackEndPages/UserList";
	}

}
