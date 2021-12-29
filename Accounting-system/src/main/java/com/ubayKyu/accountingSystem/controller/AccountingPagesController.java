package com.ubayKyu.accountingSystem.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ubayKyu.accountingSystem.dto.AccountingNoteInterFace;
import com.ubayKyu.accountingSystem.dto.CategoryInterFace;
import com.ubayKyu.accountingSystem.entity.AccountingNote;
import com.ubayKyu.accountingSystem.entity.Category;
import com.ubayKyu.accountingSystem.repository.AccountingNoteRepository;
import com.ubayKyu.accountingSystem.repository.CategoryRepository;
import com.ubayKyu.accountingSystem.service.AccountingNoteService;
import com.ubayKyu.accountingSystem.service.CategoryService;
import com.ubayKyu.accountingSystem.service.FormatService;
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
	CategoryService CategoryService;
	@Autowired
	AccountingNoteService AccountingNoteService;
	@Autowired
	AccountingNoteRepository AccountingNoteRepository;

	@GetMapping("/AccountingDetail")
	public String AccountingDetail(Model model, HttpServletRequest request) {
		// -------判斷登入----//
		if (!LoginService.IsLogin(session)) {
			String url = "/Default/Logout"; // 重新導向到指定的url
			return "redirect:" + url; // 重新導向到指定的url
		}
		// -------判斷登入_end----//
		String userId = request.getParameter("id");
		String strAccid = request.getParameter("accid");
		Integer accid = FormatService.parseIntOrNull(strAccid); // 檢查accID是否為空
		// 取得既有的acc note
		if (strAccid != null && strAccid != "") {
			Optional<AccountingNote> accountingNote = AccountingNoteService.getAccountingNoteByID(accid);
			model.addAttribute("AccAmount", accountingNote.get().getAmount());
			model.addAttribute("AccCaption", accountingNote.get().getCaption());
			model.addAttribute("AccBody", accountingNote.get().getBody());
			model.addAttribute("AccCategory", accountingNote.get().getCategoryID());
			model.addAttribute("AccDatetime", accountingNote.get().getCreateDate());
			model.addAttribute("AccActType", accountingNote.get().getActType());
		}
		List<Category> categoryList = CategoryService.getCategoryByUserID(userId); // 提供前端分類列表供下拉式選單使用
		model.addAttribute("CategoryList", categoryList);

		return "/AccountingPages/AccountingDetail";
	}

	@PostMapping("/AccountingDetail")
	public String AccountingDetail(Model model, HttpServletRequest request,
			@RequestParam(value = "txtCaption", required = false) String txtCaption,
			@RequestParam(value = "txtBody", required = false) String txtBody,
			@RequestParam(value = "txtAmount", required = false) String txtAmount,
			@RequestParam(value = "PrivateType", required = false) String categoryid,
			@RequestParam(value = "ActType", required = false) Integer actType,
			@RequestParam(value = "hiddenAccDate", required = false) String accDatetime,
			RedirectAttributes redirAttrs) {
		// -------判斷登入----//
		if (!LoginService.IsLogin(session)) {
			String url = "/Default/Logout"; // 重新導向到指定的url
			return "redirect:" + url; // 重新導向到指定的url
		}
		// -------判斷登入_end----//

		String userId = request.getParameter("id");
		String strAccid = request.getParameter("accid");
		Integer accid = FormatService.parseIntOrNull(strAccid); // 檢查accID是否為空

		// 驗證取得之資料
		if (txtAmount.isEmpty() || txtAmount == null) {
			redirAttrs.addFlashAttribute("message", "金額不可為空");
			String url = "/AccountingPages/AccountingDetail?" + "id=" + userId + "&accid=" + strAccid; // 返回元分頁
			return "redirect:" + url;
		}

		Integer amount = Integer.parseInt(txtAmount);
		if (amount > 10000000 || amount < 0) {
			redirAttrs.addFlashAttribute("message", "輸入金額不可超過一千萬");
			String url = "/AccountingPages/AccountingDetail?" + "id=" + userId + "&accid=" + strAccid; // 返回元分頁
			return "redirect:" + url;
		}

		if (txtCaption.isEmpty() || txtCaption == null) {
			redirAttrs.addFlashAttribute("message", "標題不可為空");
			String url = "/AccountingPages/AccountingDetail?" + "id=" + userId + "&accid=" + strAccid; // 返回元分頁
			return "redirect:" + url;
		}

		AccountingNote accountingNote = new AccountingNote();
		if (!categoryid.isEmpty())
			accountingNote.setCategoryID(categoryid);
		accountingNote.setActType(actType);
		accountingNote.setAmount(Integer.parseInt(txtAmount));
		accountingNote.setCaption(txtCaption);
		accountingNote.setBody(txtBody);
		accountingNote.setUserID(userId);

		redirAttrs.addFlashAttribute("message", "編輯成功");
		if (strAccid == null || strAccid == "") {
			accountingNote.setCreateDate(LocalDateTime.now());
			redirAttrs.addFlashAttribute("message", "新增成功");
		} else {
			accountingNote.setAccID(accid);
			accountingNote.setCreateDate(LocalDateTime.parse(accDatetime));
		}
		accid = AccountingNoteService.saveAccountingNote(accountingNote, userId); // 返還Accid

		String url = "/AccountingPages/AccountingDetail?" + "id=" + userId + "&accid=" + accid; // 返回元分頁
		return "redirect:" + url;

	}

	// AccountingList顯示
	@GetMapping("/AccountingList")
	public String AccountingList(Model model, @RequestParam(value = "id", required = true) String userid) {
		// -------判斷登入----//
		if (!LoginService.IsLogin(session)) {
			String url = "/Default/Logout"; // 重新導向到指定的url
			return "redirect:" + url; // 重新導向到指定的url
		}
		// -------判斷登入_end----//

		// 於html使用th:each將AccountingNote的List加入table中印出流水帳列表
		List<AccountingNoteInterFace> accountingNoteList = AccountingNoteService
				.getAccountingNoteInterfaceListByUserID(userid);
		model.addAttribute("accountingNoteListTable", accountingNoteList);
		// 計算小計(總金額)
		int subtotalTotal = (AccountingNoteService.getAccountingNoteAmountSum(userid, 1)
				- AccountingNoteService.getAccountingNoteAmountSum(userid, 0));
		model.addAttribute("subtotalTotal", subtotalTotal);
		// 計算小計(本月)
		int subtotalThisMonth = (AccountingNoteService.getAccountingNoteAmountOfMonth(userid, 1)
				- AccountingNoteService.getAccountingNoteAmountOfMonth(userid, 0));
		model.addAttribute("subtotalThisMonth", subtotalThisMonth);

		return "/AccountingPages/AccountingList";
	}

	// AccountingList動作
	@PostMapping("/AccountingList")
	public String AccountingListDel(Model model, @RequestParam(value = "id", required = false) String userid,
			@RequestParam(value = "ckbDelete", required = false) Integer[] accIDsForDel,
			RedirectAttributes redirectAttrs) {
		// -------判斷登入----//
		if (!LoginService.IsLogin(session)) {
			String url = "/Default/Logout"; // 重新導向到指定的url
			return "redirect:" + url; // 重新導向到指定的url
		}
		// -------判斷登入_end----//
		// 刪除
		if (accIDsForDel != null) {
			for (Integer eachAccountingNote : accIDsForDel) {
				System.out.println(eachAccountingNote);
				AccountingNoteService.deleteById(eachAccountingNote); // 執行刪除
				redirectAttrs.addFlashAttribute("message", "刪除成功");
			}
		} else
			redirectAttrs.addFlashAttribute("message", "未選取任何項目");

		String url = "/AccountingPages/AccountingList?id=" + userid; // 重新導向到指定的url
		return "redirect:" + url; // 重新導向到指定的url
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
//			model.addAttribute("txtCaption", "請輸入標題");
//			model.addAttribute("txtBody", "請輸入內容");
		}

		return "/AccountingPages/CategoryDetail";
	}

	// CategoryDetail動作
	@PostMapping(value = "/CategoryDetail") // , method = RequestMethod.POST
	public String CategoryDetail(Model model, HttpServletRequest request,
			@RequestParam(value = "txtCaption", required = false) String Caption,
			@RequestParam(value = "txtBody", required = false) String BodyText, RedirectAttributes redirAttrs) {
		// -------判斷登入----//
		if (!LoginService.IsLogin(session)) {
			String url = "/Default/Logout"; // 重新導向到指定的url
			return "redirect:" + url; // 重新導向到指定的url
		}
		String CategoryID = request.getParameter("CategoryID");
		String userid = request.getParameter("id");

		// 後端驗證標題IsNull
		if (Caption.isEmpty() || Caption == null) {
			redirAttrs.addFlashAttribute("message", "標題不可為空");
			String url = "/AccountingPages/CategoryDetail" + "?id=" + userid + "&CategoryID=" + CategoryID; // 返回元分頁
			return "redirect:" + url;
		}

		if (CategoryService.IsUpdating(Caption, userid, CategoryID)) // 標題重複但有CategoryID => 編輯
		{
			CategoryService.Update(CategoryID, Caption, BodyText);
			redirAttrs.addFlashAttribute("message", "修改成功");
		} else if (CategoryService.IsUsedCaption(userid, Caption)) // 判斷標題是否重複
		{
			redirAttrs.addFlashAttribute("message", "標題不予許重複");
		} else { // 新增
			CategoryID = CategoryService.Add(Caption, BodyText, userid);
			redirAttrs.addFlashAttribute("message", "新增成功");
		}

		String url = "/AccountingPages/CategoryDetail" + "?id=" + userid + "&CategoryID=" + CategoryID; // 返回元分頁
		return "redirect:" + url;
		// return "/AccountingPages/CategoryDetail";
	}

	@GetMapping("/CategoryList")
	public String CategoryList(Model model, @RequestParam(value = "id", required = false) String userid) {
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
	public String CategoryList(@RequestParam(value = "id", required = false) String userid,
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
