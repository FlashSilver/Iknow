package com.ikonw.bean;

import java.util.List;

public class QuestionBean {

	private int answercount;
	private long createtime;
	private boolean isOpenLocation;
	private double price;
	private String questionURL;
	private String questionid;
	private String questiontitle;
	private int status;
	private double x;
	private double y;
	private UserBean presenter;
	private List<ConversBean> conversations;
	
	public int getAnswercount() {
		return answercount;
	}
	public void setAnswercount(int answercount) {
		this.answercount = answercount;
	}
	public long getCreatetime() {
		return createtime;
	}
	public void setCreatetime(long createtime) {
		this.createtime = createtime;
	}
	public boolean isOpenLocation() {
		return isOpenLocation;
	}
	public void setOpenLocation(boolean isOpenLocation) {
		this.isOpenLocation = isOpenLocation;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getQuestionURL() {
		return questionURL;
	}
	public void setQuestionURL(String questionURL) {
		this.questionURL = questionURL;
	}
	public String getQuestionid() {
		return questionid;
	}
	public void setQuestionid(String questionid) {
		this.questionid = questionid;
	}
	public String getQuestiontitle() {
		return questiontitle;
	}
	public void setQuestiontitle(String questiontitle) {
		this.questiontitle = questiontitle;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}
	public double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
	}
	public QuestionBean(int answercount, long createtime,
			boolean isOpenLocation, double price, String questionURL,
			String questionid, String questiontitle, int status, double x,
			double y) {
		super();
		this.answercount = answercount;
		this.createtime = createtime;
		this.isOpenLocation = isOpenLocation;
		this.price = price;
		this.questionURL = questionURL;
		this.questionid = questionid;
		this.questiontitle = questiontitle;
		this.status = status;
		this.x = x;
		this.y = y;
	}
	public QuestionBean() {
		super();
	}
	public UserBean getPresenter() {
		return presenter;
	}
	public void setPresenter(UserBean presenter) {
		this.presenter = presenter;
	}
	public List<ConversBean> getConversations() {
		return conversations;
	}
	public void setConversations(List<ConversBean> conversations) {
		this.conversations = conversations;
	}
	
	
}
