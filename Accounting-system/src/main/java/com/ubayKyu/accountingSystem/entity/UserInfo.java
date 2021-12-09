package com.ubayKyu.accountingSystem.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "UserInfo")
public class UserInfo {

	// 使用者辨識碼
	@Id
	@Column(name = "UserID", nullable = false, columnDefinition = "uniqueidentifier")
	private String UserID;
	// 帳號
	@Column(name = "Account", nullable = false, columnDefinition = "varchar(50)")
	private String Account;
	// 密碼
	@Column(name = "PWD", nullable = false, columnDefinition = "varchar(50)")
	private String PWD;
	// 姓名
	@Column(name = "Name", nullable = false, columnDefinition = "nvarchar(50)")
	private String Name;
	// Email
	@Column(name = "Email", nullable = false, columnDefinition = "nvarchar(100)")
	private String Email;
	// 等級
	@Column(name = "UserLevel", nullable = false, columnDefinition = "int")
	private Integer UserLevel;
	// 建立時間
	@Column(name = "CreateDate", nullable = false, columnDefinition = "datetime default getdate()")
	private LocalDateTime CreateDate;
	
	//----getter & setter ----
	public String getUserID() {
		return UserID;
	}

	public void setUserID(String userID) {
		UserID = userID;
	}

	public String getAccount() {
		return Account;
	}

	public void setAccount(String account) {
		Account = account;
	}

	public String getPWD() {
		return PWD;
	}

	public void setPWD(String pWD) {
		PWD = pWD;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getEmail() {
		return Email;
	}

	public void setEmail(String email) {
		Email = email;
	}

	public Integer getUserLevel() {
		return UserLevel;
	}

	public void setUserLevel(Integer userLevel) {
		UserLevel = userLevel;
	}

	public LocalDateTime getCreateDate() {
		return CreateDate;
	}

	public void setCreateDate(LocalDateTime createDate) {
		CreateDate = createDate;
	}

	@Override
	public String toString() {
		return "UserInfo [UserID=" + UserID + ", Account=" + Account + ", PWD=" + PWD + ", Name=" + Name + ", Email="
				+ Email + ", UserLevel=" + UserLevel + ", CreateDate=" + CreateDate + "]";
	}

}
