package com.qiadao.bean;

public class PresenterBean {
	private String avatar;
	private String username;


	public String getUsername() {
		return username;
	}



	public void setUsername(String username) {
		this.username = username;
	}



	public String getAvatar() {
		return avatar;
	}



	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}



	public PresenterBean(String avatar) {
		super();
		this.avatar = avatar;
	}



	public PresenterBean() {
		super();
	}
	
}
