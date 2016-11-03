package com.tavant.trainer.model;

public class ResponseData {
	private String entity;
	private String respData;
	public String getEntity() {
		return entity;
	}
	public void setEntity(String entity) {
		this.entity = entity;
	}
	public String getRespData() {
		return respData;
	}
	public void setRespData(String respData) {
		this.respData = respData;
	}
	@Override
	public String toString() {
		return "ResponseData [entity=" + entity + ", respData=" + respData + "]";
	}
	

}
