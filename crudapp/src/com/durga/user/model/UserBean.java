package com.durga.user.model;

public class UserBean {

	private String userId;
	private String userPassword;
	private String userFirstName;
	private String userMiddleName;
	private String userLastName;
	private String userGender;
	private String userZipCode;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserPassword() {
		return userPassword;
	}
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	public String getUserFirstName() {
		return userFirstName;
	}
	public void setUserFirstName(String userFirstName) {
		this.userFirstName = userFirstName;
	}
	public String getUserMiddleName() {
		return userMiddleName;
	}
	public void setUserMiddleName(String userMiddleName) {
		this.userMiddleName = userMiddleName;
	}
	public String getUserLastName() {
		return userLastName;
	}
	public void setUserLastName(String userLastName) {
		this.userLastName = userLastName;
	}
	public String getUserGender() {
		return userGender;
	}
	public void setUserGender(String userGender) {
		this.userGender = userGender;
	}
	
	public String getUserZipCode() {
		return userZipCode;
	}
	public void setUserZipCode(String userZipCode) {
		this.userZipCode = userZipCode;
	}
	
	@Override
	public String toString() {
		return "User [userId=" + userId + ", userPassword=" + userPassword + ", userFisrtName=" + userFirstName
				+ ", userMiddleName=" + userMiddleName + ", userLastName=" + userLastName + ", userGender=" + userGender
				+ ", userZIpCode=" + userZipCode + "]";
	}
	
}
