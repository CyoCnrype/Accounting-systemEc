package com.ubayKyu.accountingSystem.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ubayKyu.accountingSystem.repository.UserInfoRepository;
import com.ubayKyu.accountingSystem.service.FormatService;
import com.ubayKyu.accountingSystem.repository.AccountingNoteRepository;

@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
@Controller
@RequestMapping("/Default")
public class DefaultController {

	@Autowired
	UserInfoRepository UserInfoRepository; // 找到查找資料庫的方法集
	@Autowired
	AccountingNoteRepository AccountingNoteRepository; // 找到查找資料庫的方法集
	@Autowired
	HttpSession session;

	@GetMapping("/Default")
	public String Default(Model model) {

		// 查詢最新、最舊時間
		String oldestAccDate = "無紀錄";
		String latestAccDate = "無紀錄";
		// 驗證時間是否為空
		if (AccountingNoteRepository.GetFirstDate() != null) {
			oldestAccDate = FormatService.FormatDateTime(AccountingNoteRepository.GetFirstDate()); // string=FormatHelper.時間格式化(Get時間.())
		}
		if (AccountingNoteRepository.GetLastDate() != null) {
			latestAccDate = FormatService.FormatDateTime(AccountingNoteRepository.GetLastDate()); // string=FormatHelper.時間格式化(Get時間.())
		}
		// 查詢記帳數量
		int accSize = AccountingNoteRepository.GetCount();
		// 查詢會員人數
		int userSize = UserInfoRepository.GetCount();
		// 將所得資料填入html
		model.addAttribute("firstAccRecordTime", latestAccDate);
		model.addAttribute("lastAccRecordTime", oldestAccDate);
		model.addAttribute("recordNumber", accSize);
		model.addAttribute("menberNumber", userSize);
		
		//驗證彈出視窗
		//return "<Script language='JavaScript'>alert('回應填寫成功!');</Script>";

		//驗證彈出視窗

		return "Default.html";
	}

	@GetMapping("/Logout")
	public String Logout(Model model,RedirectAttributes redirAttrs) {
		session.removeAttribute("LoginState");
		redirAttrs.addFlashAttribute("message", "登出成功");		
		String url = "/Default/Default"; // 重新導向到指定的url
		return "redirect:" + url; // 重新導向到指定的url
	}
	
	//驗證彈出視窗
	@ResponseBody
    public String testJS() {
        return "<Script language='JavaScript'>alert('回應填寫成功!');</Script>";
        //return "<Script language='JavaScript'>alert('回應填寫成功!');location.href='/Default/Default'; </Script>";
    }
	
	//@GetMapping("/testJS")
	@RequestMapping("/testJS")
	public String  testJS(Model model ,  RedirectAttributes redirAttrs) {
		redirAttrs.addFlashAttribute("message", "彈跳甲魚湯");
		String url = "/Default/Default"; // 重新導向到指定的url
		return "redirect:" + url; // 重新導向到指定的url
	}

}
