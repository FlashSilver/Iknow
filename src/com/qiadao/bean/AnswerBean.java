package com.qiadao.bean;

import java.util.List;

public class AnswerBean {
	List<ConversationsBean> conversations;

	public List<ConversationsBean> getConversations() {
		return conversations;
	}

	public void setConversations(List<ConversationsBean> conversations) {
		this.conversations = conversations;
	}

	public AnswerBean(List<ConversationsBean> conversations) {
		super();
		this.conversations = conversations;
	}

	public AnswerBean() {
		super();
	}
	

}
