package com.ubayKyu.accountingSystem.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Category")
public class Category {

	// 分類辨識碼
	@Id
	@Column(name = "CategoryID", nullable = false, columnDefinition = "uniqueidentifier")
	private String CategoryID;
	// 使用者辨識碼
	@Column(name = "UserID", nullable = false, columnDefinition = "uniqueidentifier")
	private String UserID;
	// 標題(分類)
	@Column(name = "Caption", nullable = false, columnDefinition = "nvarchar(100)")
	private String Caption;
	// 備註
	@Column(name = "Body", nullable = true, columnDefinition = "nvarchar(500)")
	private String Body;
	// 建立時間
	@Column(name = "CreateDate", nullable = false, columnDefinition = "datetime default getdate()")
	private LocalDateTime CreateDate;

	// ----getter & setter ----

	public String getCategoryID() {
		return CategoryID;
	}

	public void setCategoryID(String categoryID) {
		CategoryID = categoryID;
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

	public String getBody() {
		return Body;
	}

	public void setBody(String body) {
		Body = body;
	}

	public LocalDateTime getCreateDate() {
		return CreateDate;
	}

	public void setCreateDate(LocalDateTime createDate) {
		CreateDate = createDate;
	}

	@Override
	public String toString() {
		return "Category [CategoryID=" + CategoryID + ", UserID=" + UserID + ", Caption=" + Caption + ", Body=" + Body
				+ ", CreateDate=" + CreateDate + "]";
	}

}
