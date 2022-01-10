package com.ubayKyu.accountingSystem.service;

//程序代碼如下
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;

import org.springframework.stereotype.Service;

/*
* Created on 2006/8/1
*
* TODO To change the template for this generated file go to
* Window - Preferences - Java - Code Style - Code Templates
*/
/**
 * @author Administrator
 *
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
@Service
public class GetMac {
	// 取得機器名稱
	public static String getMacOnWindow() {
		String s = "";
		try {
			String s1 = "ipconfig /all";
			Process process = Runtime.getRuntime().exec(s1);
			BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String nextLine;
			for (String line = bufferedreader.readLine(); line != null; line = nextLine) {
				nextLine = bufferedreader.readLine();
				if (line.indexOf("Physical Address") <= 0) {
					continue;
				}
				int i = line.indexOf("Physical Address") + 36;
				s = line.substring(i);
				break;
			}
			bufferedreader.close();
			process.waitFor();
		} catch (Exception exception) {
			s = "";
			System.out.println(exception.toString());
		}
		return s.trim();
	}

	public static String getMacName() {
		String address = "GetNothing";
		InetAddress addr;
		try {
			addr = InetAddress.getLocalHost();
			//String ip = addr.getHostAddress().toString();// 獲得本機IP
			address = addr.getHostName().toString();// 獲得本機名稱
		} catch (UnknownHostException e) {
			// TODO 自動產生的 catch 區塊
			e.printStackTrace();
		}

		return address;

	}

}