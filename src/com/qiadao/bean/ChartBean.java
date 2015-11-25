package com.qiadao.bean;

import java.util.Date;

public class ChartBean {
	private String answererid;
	private String answerername;
	private String answereravatar;
	private String title;
	private Date createtime=new Date();
	private String questionid;
	private boolean isput=false;
	
	public String getAnswererid() {
		return answererid;
	}

	public void setAnswererid(String answererid) {
		this.answererid = answererid;
	}

	public String getAnswerername() {
		return answerername;
	}

	public void setAnswerername(String answerername) {
		this.answerername = answerername;
	}

	public String getAnswereravatar() {
		return answereravatar;
	}

	public void setAnswereravatar(String answereravatar) {
		this.answereravatar = answereravatar;
	}

	public ChartBean(String answererid, String answerername, String answereravatar) {
		super();
		this.answererid = answererid;
		this.answerername = answerername;
		this.answereravatar = answereravatar;
	}

	public ChartBean() {
		super();
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public String getQuestionid() {
		return questionid;
	}

	public void setQuestionid(String questionid) {
		this.questionid = questionid;
	}

	public boolean isIsput() {
		return isput;
	}

	public void setIsput(boolean isput) {
		this.isput = isput;
	}
	
}
