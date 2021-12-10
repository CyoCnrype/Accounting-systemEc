package com.ubayKyu.accountingSystem.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ubayKyu.accountingSystem.dto.AccountingNoteInterFace;
import com.ubayKyu.accountingSystem.dto.CategoryInterFace;
import com.ubayKyu.accountingSystem.entity.AccountingNote;
import com.ubayKyu.accountingSystem.entity.UserInfo;
import com.ubayKyu.accountingSystem.repository.AccountingNoteRepository;
import com.ubayKyu.accountingSystem.repository.CategoryRepository;
import com.ubayKyu.accountingSystem.service.LoginService;

@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
@Controller
@RequestMapping("/AccountingPages")
public class AccountingPagesController {

	@Autowired
	HttpSession session;
	@Autowired
	CategoryRepository CategoryRepository;
	@Autowired
	AccountingNoteRepository AccountingNoteRepository;

	@GetMapping("/AccountingDetail")
	public String AccountingDetail(Model model) {
		// -------判斷登入----//
		if (!LoginService.IsLogin(session)) {
			String url = "/Default/Logout"; // 重新導向到指定的url
			return "redirect:" + url; // 重新導向到指定的url
		}
		// -------判斷登入_end----//
		return "/AccountingPages/AccountingDetail";
	}

	@GetMapping("/AccountingList")
	public String AccountingList(Model model,@RequestParam(value = "id") String userid) {
		// -------判斷登入----//
		if (!LoginService.IsLogin(session)) {
			String url = "/Default/Logout"; // 重新導向到指定的url
			return "redirect:" + url; // 重新導向到指定的url
		}
		// -------判斷登入_end----//		
		//找到AccountingNoteList並丟到前台		
		List<AccountingNoteInterFace> AccountingNote =  AccountingNoteRepository.GetAccNotebyUserId(userid);
		model.addAttribute("AccountingNoteModelList", AccountingNote);// 將Model傳至前台
		return "/AccountingPages/AccountingList";
	}

	@GetMapping("/CategoryDetail")
	public String CategoryDetail(Model model) {
		// -------判斷登入----//
		if (!LoginService.IsLogin(session)) {
			String url = "/Default/Logout"; // 重新導向到指定的url
			return "redirect:" + url; // 重新導向到指定的url
		}
		// -------判斷登入_end----//
		return "/AccountingPages/CategoryDetail";
	}

	@GetMapping("/CategoryList")
	public String CategoryList(Model model, @RequestParam(value = "id") String userid) {
		// -------判斷登入----//
		if (!LoginService.IsLogin(session)) {
			String url = "/Default/Logout"; // 重新導向到指定的url
			return "redirect:" + url; // 重新導向到指定的url
		}
		// -------判斷登入_end----//
		List<CategoryInterFace> CategoryModelList = CategoryRepository.FindCategoryModelListByUserid(userid);
		model.addAttribute("CategoryModelList", CategoryModelList);// 將自定義的分類Model傳至前台

		return "/AccountingPages/CategoryList";
	}

	@RequestMapping(value="/CategoryList",method = RequestMethod.POST)
	public String CategoryListDel(@RequestParam(value = "id") String userid,
			@RequestParam(value = "chbCategoryDel", required = false) String[] categoryDel, Model model) {
		// -------判斷登入----//
		if (!LoginService.IsLogin(session)) {
			String url = "/Default/Logout"; // 重新導向到指定的url
			return "redirect:" + url; // 重新導向到指定的url
		}
		// -------執行刪除---//
		if (categoryDel != null) { //判斷是否有選中的check box
			for (String categoryid : categoryDel) { //foreach遍歷
				//System.out.println(categoryid);
				CategoryRepository.deleteById(categoryid); //執行刪除
			}
		}

		String url = "/AccountingPages/CategoryList?id=" + userid;  //返回元分頁
		return "redirect:" + url;
	}

}
