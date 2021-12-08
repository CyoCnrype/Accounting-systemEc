package com.ubayKyu.accountingSystem.service;

import org.springframework.stereotype.Service;

//import com.example.smalldemo.dao.*;
//import com.example.smalldemo.model.*;

@Service
public class LoginClservice {
	// @Autowired
	// private Userdao userdao;// 與dto層進行互動

	public boolean Find(String name, String password) {
		boolean b = false;
		
		if (name.equals("Q") && password.equals("Q")) {// 兩種渠道的得到的資料進行比較
			b = true;
		}

		//List<UserBean> All = userdao.findAll();// 呼叫dao層方法，讀取資料庫資料
		//System.out.println("================" + All);
//		for (int i = 0; i <= All.size(); i++) {// 將資料庫中的資料全部拿出，一個一個比較
//
//			UserBean one = All.get(i);
//			if (name.equals(one.getName()) && password.equals(one.getpassword())) {// 兩種渠道的得到的資料進行比較
//
//				b = true;
//				break;
//			} else {
//
//				b = false;
//				break;
//			}
//
//		}

		return b;
	}
}
