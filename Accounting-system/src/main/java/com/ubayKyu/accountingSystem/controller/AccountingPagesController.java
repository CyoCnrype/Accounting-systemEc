package com.ubayKyu.accountingSystem.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ubayKyu.accountingSystem.service.LoginService;

@Controller
@RequestMapping("/AccountingPages")
public class AccountingPagesController {

	@Autowired
	HttpSession session;

	@GetMapping("/AccountingDetail")
	public String AccountingDetail(Model model) {
		// -------判斷登入----//
		if (!LoginService.IsLogin(session)) {
			String url = "/Default/Logout"; // 重新導向到指定的url
			return "redirect:" + url; // 重新導向到指定的url
		}
		// -------判斷登入----//
		return "/AccountingPages/AccountingDetail";
	}

	@GetMapping("/AccountingList")
	public String AccountingList(Model model) {
		// -------判斷登入----//
		if (!LoginService.IsLogin(session)) {
			String url = "/Default/Logout"; // 重新導向到指定的url
			return "redirect:" + url; // 重新導向到指定的url
		}
		// -------判斷登入----//
		return "/AccountingPages/AccountingList";
	}

	@GetMapping("/CategoryDetail")
	public String CategoryDetail(Model model) {
		// -------判斷登入----//
		if (!LoginService.IsLogin(session)) {
			String url = "/Default/Logout"; // 重新導向到指定的url
			return "redirect:" + url; // 重新導向到指定的url
		}
		// -------判斷登入----//
		return "/AccountingPages/CategoryDetail";
	}

	@GetMapping("/CategoryList")
	public String CategoryList(Model model) {
		// -------判斷登入----//
		if (!LoginService.IsLogin(session)) {
			String url = "/Default/Logout"; // 重新導向到指定的url
			return "redirect:" + url; // 重新導向到指定的url
		}
		// -------判斷登入----//
		return "/AccountingPages/CategoryList";
	}

}
