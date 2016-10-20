package com.tavant.trainer;

public class ErrorMessage {
	private AppException appException;
	private int status;
	private String message;
	private String developerMessage;
	private String link;
	private int code;
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public ErrorMessage(AppException ex){
		this.appException=ex;
	}
	public ErrorMessage(){
		
	}
	public AppException getAppException() {
		return appException;
	}
	public void setAppException(AppException appException) {
		this.appException = appException;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getDeveloperMessage() {
		return developerMessage;
	}
	public void setDeveloperMessage(String developerMessage) {
		this.developerMessage = developerMessage;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	
}
