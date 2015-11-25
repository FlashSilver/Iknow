package com.qiadao.bean;

public class QuestionBean {
	private String questiontitle;
	private String price;
	private PresenterBean presenter;
	private String questionid;
	private int answercount;
	
	
	
	
	public int getAnswercount() {
		return answercount;
	}

	public void setAnswercount(int answercount) {
		this.answercount = answercount;
	}

	public String getQuestiontitle() {
		return questiontitle;
	}

	public void setQuestiontitle(String questiontitle) {
		this.questiontitle = questiontitle;
	}


	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getQuestionid() {
		return questionid;
	}

	public void setQuestionid(String questionid) {
		this.questionid = questionid;
	}

	public QuestionBean(String questiontitle, String price,PresenterBean presenter) {
		super();
		this.questiontitle = questiontitle;
		this.price = price;
		this.presenter=presenter;
		//this.presenter=presenter;
	}

	public PresenterBean getPresenter() {
		return presenter;
	}

	public void setPresenter(PresenterBean presenter) {
		this.presenter = presenter;
	}

	public QuestionBean() {
		super();
	}

}
