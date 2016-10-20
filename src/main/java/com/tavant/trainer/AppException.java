package com.tavant.trainer;

public class AppException extends Exception{
	private int status;

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}
