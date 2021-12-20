package com.ubayKyu.accountingSystem.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Service;

@Service
public class FormatService {
	
	public static String FormatDateTime (LocalDateTime dateTime) {
		DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		String answer =  dateFormat.format(dateTime);
		return answer;
		}
	
	public static Integer parseIntOrNull(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }

}
