package com.ubayKyu.accountingSystem.controller;

import java.time.LocalDate;
import java.util.List;
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
import com.ubayKyu.accountingSystem.dto.UserInfoInterface;
import com.ubayKyu.accountingSystem.entity.UserInfo;
import com.ubayKyu.accountingSystem.service.LoginService;
import com.ubayKyu.accountingSystem.service.UserInfoService;

@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
@Controller
@RequestMapping("/BackEndPages")
public class BackEndPagesController {
	@Autowired
	UserInfoService UserInfoService;
	@Autowired
	HttpSession session;

	@GetMapping("/UserDetail")
	public String UserDetail(Model model) {
		// -------判斷登入----//
		if (!LoginService.IsLogin(session)) {
			String url = "/Default/Logout"; // 重新導向到指定的url
			return "redirect:" + url; // 重新導向到指定的url
		}
		// -------判斷登入_end----//
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
		// -------判斷登入_end----//

		// 取得登入者資訊
		UserInfo user = (UserInfo) session.getAttribute("LoginState");
		Integer userLevel = user.getUserLevel();
		if (userLevel == 1)
			return "redirect:/BackEndPages/UserProfile";

		// 於html使用th:each將UserInfo的List加入table中印出會員列表
		List<UserInfoInterface> userInfoList = UserInfoService.getUserInfoInterface();
		model.addAttribute("userInfoListTable", userInfoList);

		return "/BackEndPages/UserList";
	}

	@PostMapping("/UserList")
	public String userListDel(Model model, @RequestParam(value = "ckbDelete", required = false) String[] userIDsForDel,
			RedirectAttributes redirectAttrs) {

		// -------判斷登入----//
				if (!LoginService.IsLogin(session)) {
					String url = "/Default/Logout"; // 重新導向到指定的url
					return "redirect:" + url; // 重新導向到指定的url
				}

		// 取得登入者資訊
		UserInfo user = (UserInfo) session.getAttribute("LoginState");
		String currentUserID = user.getUserID();
		String currentAccount = user.getAccount();

		Integer userInfoCount = 0;
		if (userIDsForDel != null) {
			for (String eachUserID : userIDsForDel) {

				Optional<UserInfo> userInfoToDel = UserInfoService.findByUserID(eachUserID);
				String account = userInfoToDel.get().getAccount();

				System.out.println("管理者 " + currentAccount + " 於 " + LocalDate.now() + " 刪除使用者 " + account);
				userInfoCount = UserInfoService.deleteUserInfoAccountingNoteAndCategoryByUserID(eachUserID);

				if (currentUserID.equals(eachUserID)) { // 若刪除自己
					redirectAttrs.addFlashAttribute("message", "本會員已刪除，回到預設頁");
					//LoginService.RemoveLoginSession(session);
					return "redirect:/Default";
				}

			}
			redirectAttrs.addFlashAttribute("message", "已將選取之會員及其流水帳、分類刪除，剩餘 " + userInfoCount + " 位會員");
		} else
			redirectAttrs.addFlashAttribute("message", "未選取任何項目");

		return "redirect:/UserList";
	}

}
