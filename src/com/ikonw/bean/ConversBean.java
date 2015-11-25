package com.ikonw.bean;

public class ConversBean {

	private String answerURL;
	private UserBean answerer;
	private String answertitle;
	private String conversationid;
	private long createtime;
	private boolean  income;
	private double money;
	private Boolean ischeck=false;
	
	
	public Boolean getIscheck() {
		return ischeck;
	}
	public void setIscheck(Boolean ischeck) {
		this.ischeck = ischeck;
	}
	public String getAnswerURL() {
		return answerURL;
	}
	public void setAnswerURL(String answerURL) {
		this.answerURL = answerURL;
	}
	public UserBean getAnswerer() {
		return answerer;
	}
	public void setAnswerer(UserBean answerer) {
		this.answerer = answerer;
	}
	public String getAnswertitle() {
		return answertitle;
	}
	public void setAnswertitle(String answertitle) {
		this.answertitle = answertitle;
	}
	public String getConversationid() {
		return conversationid;
	}
	public void setConversationid(String conversationid) {
		this.conversationid = conversationid;
	}
	public long getCreatetime() {
		return createtime;
	}
	public void setCreatetime(long createtime) {
		this.createtime = createtime;
	}
	public boolean isIncome() {
		return income;
	}
	public void setIncome(boolean income) {
		this.income = income;
	}
	public double getMoney() {
		return money;
	}
	public void setMoney(double money) {
		this.money = money;
	}
	public ConversBean(String answerURL, UserBean answerer, String answertitle,
			String conversationid, long createtime, boolean income, double money) {
		super();
		this.answerURL = answerURL;
		this.answerer = answerer;
		this.answertitle = answertitle;
		this.conversationid = conversationid;
		this.createtime = createtime;
		this.income = income;
		this.money = money;
	}
	public ConversBean() {
		super();
	}
	
	
	
}
