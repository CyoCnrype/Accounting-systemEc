package com.ubayKyu.accountingSystem.dto;

import java.time.LocalDateTime;

public class ListedCategory {

	// 建立日期
	private LocalDateTime CreateDate;
	// 分類
	private String Caption;
	// 流水帳數
	private int Count;

	public ListedCategory() {}

	public ListedCategory(LocalDateTime CreateDate, String Caption,int Count) {
        this.CreateDate = CreateDate;
        this.Caption = Caption;
        this.Count = Count;
    }

	public LocalDateTime getCreateDate() {
		return CreateDate;
	}

	public void setCreateDate(LocalDateTime createDate) {
		CreateDate = createDate;
	}

	public String getCaption() {
		return Caption;
	}

	public void setCaption(String caption) {
		Caption = caption;
	}

	public int getCount() {
		return Count;
	}

	public void setCount(int count) {
		Count = count;
	}

	@Override
	public String toString() {
		return "ListedCategory [CreateDate=" + CreateDate + ", Caption=" + Caption + ", Count=" + Count + "]";
	}

}
