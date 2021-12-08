package com.ubayKyu.accountingSystem.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.ubayKyu.accountingSystem.entity.UserInfo;
import com.ubayKyu.accountingSystem.repository.UserInfoRepository;
import com.ubayKyu.accountingSystem.service.LoginService;

@Controller
@RequestMapping("/UserProfilePages")
public class UserProfilePagesController {

	@Autowired
	HttpSession session;

	@GetMapping("/Login")
	public String Login(Model model) {

		// 判斷session、如果為空則令其登入、否則直接進一頁
		// -------判斷登入----//
		if (!LoginService.IsLogin(session)) {
			return "/UserProfilePages/Login";
		}
		// -------判斷登入----//
		String url = "/AccountingPages/AccountingList"; // 重新導向到指定的url
		return "redirect:" + url; // 重新導向到指定的url

	}

	@GetMapping("/UserProfile")
	public String UserProfile(Model model) {
		// -------判斷登入----//
		if (!LoginService.IsLogin(session)) {
			String url = "/Default/Logout"; // 重新導向到指定的url
			return "redirect:" + url; // 重新導向到指定的url
		}
		// -------判斷登入----//

		return "/UserProfilePages/UserProfile.html";
	}

	@Autowired
	UserInfoRepository UserInfoRepository; // 找到查找資料庫的方法集

	@RequestMapping("/userLogin")
	// 登入(帳號、密碼)
	public String getLogin(@RequestParam("account") String account, @RequestParam("password") String password,
			Model model ,  RedirectAttributes redirAttrs) {

		boolean result = false;
		UserInfo currentUserInfo = UserInfoRepository.GetUserByLogin(account, password);
		if (currentUserInfo != null) {
			// if (UserInfoRepository.GetUserByLogin(account, password) != null) {
			result = true;
			// 寫入session
			// 12/7實作預定
			session.setAttribute("LoginState", currentUserInfo);
		}

		if (result == true) {// 如果成功
			
			redirAttrs.addFlashAttribute("message", "登入成功");			
			String url = "/AccountingPages/AccountingList"; // 重新導向到指定的url
			return "redirect:" + url; // 重新導向到指定的url
		} else {// 如果失敗
			redirAttrs.addFlashAttribute("message", "登入失敗");	
			String url = "/Default/Default";
			return "redirect:" + url; // 重新導向到指定的url
		}

	}

//	public HttpSession writeSession(HttpSession session, String account) {
//		session.setAttribute("LoginState", account); // 將memberAccount object存入session命名為uid
//		return session;
//	}
	

	// 實驗URL導向的方法(未實際作用)
	@GetMapping("/redirect")
	public RedirectView redirect() {

		String url = "https://www.youtube.com/";

		return new RedirectView(url); // 重新導向到指定的url
	}

}
