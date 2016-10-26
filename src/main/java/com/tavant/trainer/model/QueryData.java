package com.tavant.trainer.model;

public class QueryData {
	private String entity;
	private String queryData;
	public String getEntity() {
		return entity;
	}
	public void setEntity(String entity) {
		this.entity = entity;
	}
	public String getQueryData() {
		return queryData;
	}
	public void setQueryData(String queryData) {
		this.queryData = queryData;
	}

	@Override
	public String toString() {
		return "QueryData [entity=" + entity + ", queryData=" + queryData + "]";
	}

}
