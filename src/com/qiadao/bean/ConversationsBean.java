package com.qiadao.bean;

public class ConversationsBean {
	Answerer answerer;
	String answertitle;

	public Answerer getAnswerer() {
		return answerer;
	}

	public void setAnswerer(Answerer answerer) {
		this.answerer = answerer;
	}

	public String getAnswertitle() {
		return answertitle;
	}

	public void setAnswertitle(String answertitle) {
		this.answertitle = answertitle;
	}

	public ConversationsBean(Answerer answerer, String answertitle) {
		super();
		this.answerer = answerer;
		this.answertitle = answertitle;
	}

	public ConversationsBean() {
		super();
	}

}
