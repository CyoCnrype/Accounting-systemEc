package com.ubayKyu.accountingSystem.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ubayKyu.accountingSystem.dto.AccountingNoteInterFace;
import com.ubayKyu.accountingSystem.dto.CategoryInterFace;
import com.ubayKyu.accountingSystem.entity.AccountingNote;
import com.ubayKyu.accountingSystem.entity.Category;
import com.ubayKyu.accountingSystem.entity.UserInfo;
import com.ubayKyu.accountingSystem.repository.AccountingNoteRepository;
import com.ubayKyu.accountingSystem.repository.CategoryRepository;
import com.ubayKyu.accountingSystem.service.CategoryService;
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
	public String AccountingList(Model model, @RequestParam(value = "id") String userid) {
		// -------判斷登入----//
		if (!LoginService.IsLogin(session)) {
			String url = "/Default/Logout"; // 重新導向到指定的url
			return "redirect:" + url; // 重新導向到指定的url
		}
		// -------判斷登入_end----//
		// 找到AccountingNoteList並丟到前台
		List<AccountingNoteInterFace> AccountingNote = AccountingNoteRepository.GetAccNotebyUserId(userid);
		model.addAttribute("AccountingNoteModelList", AccountingNote);// 將Model傳至前台
		return "/AccountingPages/AccountingList";
	}

	// CategoryDetail顯示
	@GetMapping("/CategoryDetail")
	public String CategoryDetail(Model model, @RequestParam(value = "CategoryID", required = false) String CategoryID,
			RedirectAttributes redirAttrs) {
		// -------判斷登入----//
		if (!LoginService.IsLogin(session)) {
			String url = "/Default/Logout"; // 重新導向到指定的url
			return "redirect:" + url; // 重新導向到指定的url
		}
		// -----檢查是否為修改------//
		if (CategoryID != "") // 修改並檢查DB內是否有資料
		// if (CategoryID != null && CategoryRepository.FindCategoryidNumber(CategoryID)
		// > 0) // 修改並檢查DB內是否有資料
		{
			if (CategoryRepository.FindCategoryidNumber(CategoryID) > 0) { // 檢查DB內是否有資料
				// 將既有資料寫入前端
				Optional<Category> currentCategory = CategoryRepository.findById(CategoryID);
				model.addAttribute("txtCaption", currentCategory.get().getCaption());
				model.addAttribute("txtBody", currentCategory.get().getBody());
			}

		} else {
			model.addAttribute("txtCaption", "請輸入標題");
			model.addAttribute("txtBody", "請輸入內容");
		}

		return "/AccountingPages/CategoryDetail";
	}

	// CategoryDetail動作
	@RequestMapping(value = "/CategoryDetail", method = RequestMethod.POST)
	public String CategoryDetail(Model model, 
			HttpServletRequest request,
			@RequestParam(value = "txtCaption", required = false) String Caption,
			@RequestParam(value = "txtBody", required = false) String BodyText) {
		// -------判斷登入----//
		if (!LoginService.IsLogin(session)) {
			String url = "/Default/Logout"; // 重新導向到指定的url
			return "redirect:" + url; // 重新導向到指定的url
		}
		// -----檢查是否為修改------//
		String CategoryID = request.getParameter("CategoryID");
		String userid = request.getParameter("userid");
//		if (CategoryID != "") // 修改並檢查DB內是否有資料
//		{
//			if (CategoryRepository.FindCategoryidNumber(CategoryID) > 0) { // 檢查DB內是否有資料
//
//				 將既有資料寫入前端
//				Optional<Category> currentCategory = CategoryRepository.findById(CategoryID);	
//			}
//		} else {// 新增
//			Category cleanCategory = CategoryService.NewCleanCategory(userid);
//			cleanCategory.setCaption(Caption);
//			cleanCategory.setBody(BodyText);
//		}
		System.out.println(CategoryID);
		System.out.println(userid);

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

	@RequestMapping(value = "/CategoryList", method = RequestMethod.POST)
	public String CategoryListDel(@RequestParam(value = "id") String userid,
			@RequestParam(value = "chbCategoryDel", required = false) String[] categoryDel, Model model,
			RedirectAttributes redirAttrs) {
		// -------判斷登入----//
		if (!LoginService.IsLogin(session)) {
			String url = "/Default/Logout"; // 重新導向到指定的url
			return "redirect:" + url; // 重新導向到指定的url
		}
		// -------執行刪除---//
		if (categoryDel != null) { // 判斷是否有選中的check box
			for (String categoryid : categoryDel) { // foreach遍歷
				// System.out.println(categoryid);
				int count = CategoryRepository.FindCountByCategoryid(categoryid); // 檢查自訂分類底下有無剩餘的流水帳數
				if (count == 0) { // 分類底下沒有流水帳
					CategoryRepository.deleteById(categoryid); // 執行刪除
					redirAttrs.addFlashAttribute("message", "刪除成功");
				} else {
					redirAttrs.addFlashAttribute("message", "刪除失敗：分類底下有剩餘帳數");
				}
			}
		}

		String url = "/AccountingPages/CategoryList?id=" + userid; // 返回元分頁
		return "redirect:" + url;
	}

}
