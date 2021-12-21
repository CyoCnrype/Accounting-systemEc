package com.ubayKyu.accountingSystem.controller;

import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ubayKyu.accountingSystem.entity.UserInfo;
import com.ubayKyu.accountingSystem.repository.UserInfoRepository;
import com.ubayKyu.accountingSystem.service.LoginService;
import com.ubayKyu.accountingSystem.service.UserInfoService;

@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
@Controller
@RequestMapping("/UserProfilePages")
public class UserProfilePagesController {

	@Autowired
	HttpSession session;
	@Autowired
	UserInfoService UserInfoService;
	@Autowired
	UserInfoRepository UserInfoRepository; // 找到查找資料庫的方法集

	@GetMapping("/Login")
	public String Login(Model model) {

		// 判斷session、如果為空則令其登入、否則直接進下一頁
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
		// 取得登入者資訊
		UserInfo user = (UserInfo) session.getAttribute("LoginState");
		String userID = user.getUserID();

		Optional<UserInfo> userInfoForEdit = UserInfoService.findByUserID(userID);
		model.addAttribute("account", userInfoForEdit.get().getAccount());
		model.addAttribute("name", userInfoForEdit.get().getName());
		model.addAttribute("email", userInfoForEdit.get().getEmail());

		return "/UserProfilePages/UserProfile.html";
	}

	@PostMapping("/UserProfile")
	public String UserProfilePageUpdate(Model model, @RequestParam(value = "txtName", required = false) String txtName,
			@RequestParam(value = "txtEmail", required = false) String txtEmail, RedirectAttributes redirectAttrs) {

		// -------判斷登入----//
		if (!LoginService.IsLogin(session)) {
			String url = "/Default/Logout"; // 重新導向到指定的url
			return "redirect:" + url; // 重新導向到指定的url
		}
		// -------判斷登入----//

		// 取得登入者資訊
		UserInfo user = (UserInfo) session.getAttribute("LoginState");
		String userID = user.getUserID();

		UserInfoService.UpdateUserProfile(userID, txtName, txtEmail);
		redirectAttrs.addFlashAttribute("message", "個人資訊修改成功");

		String url = "/UserProfilePages/UserProfile"; // 重新導向到指定的url
		return "redirect:" + url; // 重新導向到指定的url
	}

	@RequestMapping("/userLogin")
	// 登入(帳號、密碼)
	public String getLogin(@RequestParam("account") String account, @RequestParam("password") String password,
			Model model, RedirectAttributes redirAttrs) {

		boolean result = false;
		UserInfo currentUserInfo = UserInfoRepository.GetUserByLogin(account, password);
		if (currentUserInfo != null) {// 有抓到值=登入成功
			// if (UserInfoRepository.GetUserByLogin(account, password) != null) {
			result = true;
			// 寫入session
			// 12/7實作預定
			session.setAttribute("LoginState", currentUserInfo);
			int userLevel = currentUserInfo.getUserLevel();
			session.setAttribute("UserLevel", userLevel);
		}

		if (result == true) {// 如果登入成功
			redirAttrs.addFlashAttribute("message", "登入成功");
			String url = "/UserProfilePages/UserProfile"; // 重新導向到指定的url
			return "redirect:" + url; // 重新導向到指定的url
		} else {// 如果失敗
			redirAttrs.addFlashAttribute("message", "登入失敗");
			String url = "/Default/Default";
			return "redirect:" + url; // 重新導向到指定的url
		}

	}

}
