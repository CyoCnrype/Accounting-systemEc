package com.ubayKyu.accountingSystem.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "AccountingNote")
public class AccountingNote {

	// 流水帳辨識碼
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "AccID", nullable = false, columnDefinition = "int")
	private Integer AccID;
	// 使用者辨識碼
	@Column(name = "UserID", nullable = false, columnDefinition = "uniqueidentifier")
	private String UserID;
	// 標題
	@Column(name = "Caption", nullable = true, columnDefinition = "nvarchar(100)")
	private String Caption;
	// 金額
	@Column(name = "Amount", nullable = false, columnDefinition = "int")
	private Integer Amount;
	// 收支種類
	// 0=支出、1=收入
	@Column(name = "ActType", nullable = false, columnDefinition = "int")
	private Integer ActType;
	// 建立時間
	@Column(name = "CreateDate", nullable = false, columnDefinition = "datetime default getdate()")
	private LocalDateTime CreateDate;
	// 備註
	@Column(name = "Body", nullable = true, columnDefinition = "nvarchar(500)")
	private String Body;
	// 分類辨識碼
	@Column(name = "CategoryID", nullable = true, columnDefinition = "uniqueidentifier")
	private String CategoryID;

	// ----getter & setter ----

	public Integer getAccID() {
		return AccID;
	}

	public void setAccID(Integer accID) {
		AccID = accID;
	}

	public String getUserID() {
		return UserID;
	}

	public void setUserID(String userID) {
		UserID = userID;
	}

	public String getCaption() {
		return Caption;
	}

	public void setCaption(String caption) {
		Caption = caption;
	}

	public Integer getAmount() {
		return Amount;
	}

	public void setAmount(Integer amount) {
		Amount = amount;
	}

	public Integer getActType() {
		return ActType;
	}

	public void setActType(Integer actType) {
		ActType = actType;
	}

	public LocalDateTime getCreateDate() {
		return CreateDate;
	}

	public void setCreateDate(LocalDateTime createDate) {
		CreateDate = createDate;
	}

	public String getBody() {
		return Body;
	}

	public void setBody(String body) {
		Body = body;
	}

	public String getCategoryID() {
		return CategoryID;
	}

	public void setCategoryID(String categoryID) {
		CategoryID = categoryID;
	}

	@Override
	public String toString() {
		return "AccountingNote [AccID=" + AccID + ", UserID=" + UserID + ", Caption=" + Caption + ", Amount=" + Amount
				+ ", ActType=" + ActType + ", CreateDate=" + CreateDate + ", Body=" + Body + ", CategoryID="
				+ CategoryID + "]";
	}

}