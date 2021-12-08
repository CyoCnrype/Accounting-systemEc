package com.ubayKyu.accountingSystem.service;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.ubayKyu.accountingSystem.repository.UserInfoRepository;

public class LoginService {

	@Autowired
	static UserInfoRepository UserInfoRepository; // 找到查找資料庫的方法集
	@Autowired
	HttpSession session;

	public boolean TryLogin(String account, String password) {
		boolean answer = false;

		if (UserInfoRepository.GetUserByLogin(account, password) != null) {
			answer = true;
			return answer;
		}

		return answer;
	}

	public static Boolean IsLogin(HttpSession session) {		
		Object currentUserInfo =  session.getAttribute("LoginState");
		if (currentUserInfo != null) {
			// if (session.getAttribute("LoginState") != null) {
			return true;
		}
		// String url = "/default/default";
		// return "redirect:" + url;
		return false;
	}
}
