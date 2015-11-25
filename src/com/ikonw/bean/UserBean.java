package com.ikonw.bean;

public class UserBean {

	private String address;
	private String avatar;
	private long createtime;
	private String introduce;
	private double latitude;
	private double longitude;
	private double money;
	private boolean rule;
	private String sex;
	private String userid;
	private String username;
	private boolean weiboAuthentication;
	private String wxid;
	private double x;
	private double y;
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public long getCreatetime() {
		return createtime;
	}
	public void setCreatetime(long createtime) {
		this.createtime = createtime;
	}
	public String getIntroduce() {
		return introduce;
	}
	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public double getMoney() {
		return money;
	}
	public void setMoney(double money) {
		this.money = money;
	}
	public boolean isRule() {
		return rule;
	}
	public void setRule(boolean rule) {
		this.rule = rule;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public boolean isWeiboAuthentication() {
		return weiboAuthentication;
	}
	public void setWeiboAuthentication(boolean weiboAuthentication) {
		this.weiboAuthentication = weiboAuthentication;
	}
	public String getWxid() {
		return wxid;
	}
	public void setWxid(String wxid) {
		this.wxid = wxid;
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
	public UserBean(String address, String avatar, long createtime,
			String introduce, double latitude, double longitude, double money,
			boolean rule, String sex, String userid, String username,
			boolean weiboAuthentication, String wxid, double x, double y) {
		super();
		this.address = address;
		this.avatar = avatar;
		this.createtime = createtime;
		this.introduce = introduce;
		this.latitude = latitude;
		this.longitude = longitude;
		this.money = money;
		this.rule = rule;
		this.sex = sex;
		this.userid = userid;
		this.username = username;
		this.weiboAuthentication = weiboAuthentication;
		this.wxid = wxid;
		this.x = x;
		this.y = y;
	}
	public UserBean() {
		super();
	}
	
	
}
