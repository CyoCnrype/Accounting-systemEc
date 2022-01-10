package com.ubayKyu.accountingSystem.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.springframework.stereotype.Service;

@Service
public class WriteTextService {
	// 將指定文字寫入指定路徑+檔案名
	public void writeToText(String message, String path) throws IOException {

		// String path = "C:\\Users\\" + GetMac.getMacOnWindow() +
		// "\\Downloads\\log.txt"; //取得機器名稱寫C>>下載之路徑
		File file = new File(path);
		if (!file.exists()) {
			file.getParentFile().mkdirs();
		}
		file.createNewFile();

		// write
		FileWriter fw = new FileWriter(file, true);
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(message + "\r\n");
		bw.flush();
		bw.close();
		fw.close();

//	    // read
//	    FileReader fr = new FileReader(file);
//	    BufferedReader br = new BufferedReader(fr);
//	    String str = br.readLine();
	}

	// 將指定文字寫入指定路徑+檔案名
	public void writeToTextByUserName(String message, String userName) throws IOException {

		String path = "C:\\Users\\" + userName + "\\Downloads\\log.txt"; // 取得機器名稱寫C>>下載之路徑
		File file = new File(path);
		if (!file.exists()) {
			file.getParentFile().mkdirs();
		}
		file.createNewFile();

		// write
		FileWriter fw = new FileWriter(file, true);
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(message + "\r\n");
		bw.flush();
		bw.close();
		fw.close();

//	    // read
//	    FileReader fr = new FileReader(file);
//	    BufferedReader br = new BufferedReader(fr);
//	    String str = br.readLine();
	}
}
