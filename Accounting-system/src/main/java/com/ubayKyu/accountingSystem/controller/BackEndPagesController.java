package com.ubayKyu.accountingSystem.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import com.ubayKyu.accountingSystem.service.GetMac;
import com.ubayKyu.accountingSystem.service.LoginService;
import com.ubayKyu.accountingSystem.service.UserInfoService;
import com.ubayKyu.accountingSystem.service.WriteTextService;

@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
@Controller
@RequestMapping("/BackEndPages")
public class BackEndPagesController {
	@Autowired
	UserInfoService UserInfoService;
	@Autowired
	WriteTextService WriteTextService;
	@Autowired
	HttpSession session;
	@Value("${username}")
	String username;

	@GetMapping("/UserDetail")
	public String UserDetail(Model model, @RequestParam(value = "userID", required = false) String userID,
			RedirectAttributes redirectAttrs) {
		// -------判斷登入----//
		if (!LoginService.IsLogin(session)) {
			String url = "/Default/Logout"; // 重新導向到指定的url
			return "redirect:" + url; // 重新導向到指定的url
		}
		// -------判斷登入_end----//

		UserInfo user = (UserInfo) session.getAttribute("LoginState");
		if (user.getUserLevel() != 0) {
			redirectAttrs.addFlashAttribute("message", "權限不足，導向至元頁面");
			return "redirect:/UserProfilePages/UserProfile";
		}
		if (userID != "") {
			Optional<UserInfoInterface> userInfo = UserInfoService.GetUserInfoInterfaceByUserID(userID);
			model.addAttribute("Account", userInfo.get().getaccount());
			model.addAttribute("Name", userInfo.get().getname());
			model.addAttribute("Email", userInfo.get().getemail());
			model.addAttribute("CreateTime", userInfo.get().getcreate_date());
			model.addAttribute("EditTime", userInfo.get().getedit_date());
			model.addAttribute("UserLevel", userInfo.get().getuser_level());
		}
		return "/BackEndPages/UserDetail";
	}

	@PostMapping("/UserDetail")
	public String UserDetail(@RequestParam(value = "userID", required = false) String userID,
			@RequestParam(value = "txtAccount", required = false) String txtAccount,
			@RequestParam(value = "txtName", required = false) String txtName,
			@RequestParam(value = "txtEmail", required = false) String txtEmail,
			@RequestParam(value = "ddlUserLevel", required = false) Integer ddlUserLevel,
			@RequestParam(value = "hiddenCreateDate", required = false) String CreateDate,
			RedirectAttributes redirAttrs, Model model) {
		// -------判斷登入----//
		if (!LoginService.IsLogin(session)) {
			String url = "/Default/Logout"; // 重新導向到指定的url
			return "redirect:" + url; // 重新導向到指定的url
		}
		// -------判斷登入_end----//
		String message = "";
		if (txtAccount == null)
			message += "帳號不能為空" + "</br>";
		if (txtName == null)
			message += "姓名不能為空" + "</br>";
		if (txtEmail == null)
			message += "Email不能為空" + "</br>";
		if (!message.isEmpty()) {
			redirAttrs.addFlashAttribute("message", message);
			// 判斷是新增還是編輯，決定回傳地址
			if (userID != null)
				return "redirect:/BackEndPages/UserDetail?userID=" + userID;
			else
				return "redirect:/BackEndPages/UserDetail";
		}

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		UserInfo User = (UserInfo) session.getAttribute("LoginState");
		UserInfo UserInfo = new UserInfo();
		if (userID != "") {

			UserInfo.setCreateDate(LocalDateTime.parse(CreateDate, formatter));
			UserInfo.setEditDate(LocalDateTime.now());
			message = "編輯成功";
		} else {
			userID = UUID.randomUUID().toString();
			UserInfo.setCreateDate(LocalDateTime.now());
			message = "新增成功";
		}

		if (User.getId().equals(userID)) {
			UserInfo NewSessionUserInfo = UserInfoService.findByUserID(userID).get();
			session.setAttribute("LoginState", NewSessionUserInfo);
		}
		UserInfoService.SaveUserInfo(UserInfo, userID, txtAccount, txtName, txtEmail, ddlUserLevel);
		redirAttrs.addFlashAttribute("message", message);
		return "redirect:/BackEndPages/UserDetail?userID=" + userID;

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
		if (userLevel != 0)
			return "redirect:/UserProfilePages/UserProfile";

		// 於html使用th:each將UserInfo的List加入table中印出會員列表
		List<UserInfoInterface> userInfoList = UserInfoService.getUserInfoInterface();
		model.addAttribute("userInfoListTable", userInfoList);

		return "/BackEndPages/UserList";
	}

	@PostMapping("/UserList")
	public String UserList(Model model, @RequestParam(value = "ckbDelete", required = false) String[] userIDsForDel,
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
		String saveIfDeleteSelf = "";
		Integer userInfoCount = 0;
		String message = ""; // 提示訊息
		if (userIDsForDel != null) {
			for (String eachUserID : userIDsForDel) {

				Optional<UserInfo> userInfoToDel = UserInfoService.findByUserID(eachUserID);
				String account = userInfoToDel.get().getAccount();
				String logMessage = "管理者 " + currentAccount + " 於 " + LocalDate.now() + " 刪除使用者 " + account ;//寫入log之訊息
				message += logMessage + "\n";

				// System.out.println("GetMac.getMacOnWindow()=" + macName);
				try {
					WriteTextService.writeToTextByUserName(logMessage, username);
				} catch (IOException e) {
					// TODO 自動產生的 catch 區塊
					e.printStackTrace(); 
				}

				userInfoCount = UserInfoService.deleteUserInfoAccountingNoteAndCategoryByUserID(eachUserID);

				if (currentUserID.equals(eachUserID)) // 有刪除自己的狀況則先儲存該ID
					saveIfDeleteSelf = eachUserID;
			}
			if (currentUserID.equals(saveIfDeleteSelf)) {// 刪除自己後登出
				message += "已刪除自己，回到預設頁" + "\n";
				// redirectAttrs.addFlashAttribute("message", "已刪除自己，回到預設頁");
				LoginService.RemoveLoginSession(session);
				return "redirect:/Default/Default";
			}
			message += "已將選取之會員及其流水帳、分類刪除，剩餘 " + userInfoCount + " 位會員" + "\n";
			// redirectAttrs.addFlashAttribute("message", "已將選取之會員及其流水帳、分類刪除，剩餘 " +
			// userInfoCount + " 位會員");
		} else {
			message += "未勾選任何項目" + "\n";
			redirectAttrs.addFlashAttribute("message", "未勾選任何項目");
		}

		redirectAttrs.addFlashAttribute("message", message);
		return "redirect:/BackEndPages/UserList";
	}

}
