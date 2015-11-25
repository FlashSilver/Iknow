package com.qiadao.bean;

import java.io.Serializable;
import java.util.List;

public class ExpensiveBean {
	private String username;
	private String questiontitle;
	private PresenterBean presenter;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getQuestiontitle() {
		return questiontitle;
	}

	public void setQuestiontitle(String questiontitle) {
		this.questiontitle = questiontitle;
	}


	public ExpensiveBean(String username, String questiontitle,PresenterBean presenter) {
		super();
		this.username = username;
		this.questiontitle = questiontitle;
		this.presenter = presenter;
	}

	public ExpensiveBean() {
		super();
	}

}
