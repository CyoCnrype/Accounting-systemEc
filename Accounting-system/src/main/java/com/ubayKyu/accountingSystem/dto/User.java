package com.ubayKyu.accountingSystem.dto;

public class User {
	public int ID;
	public String name;
	public String password;
	
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	@Override
	public String toString() {
		return "User [ID=" + ID + ", name=" + name + ", password=" + password + "]";
	}

	

}
