package com.ubayKyu.accountingSystem.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FormatService {
	
	public static String FormatDateTime (LocalDateTime dateTime) {
		DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		String answer =  dateFormat.format(dateTime);
		return answer;
		}

}
