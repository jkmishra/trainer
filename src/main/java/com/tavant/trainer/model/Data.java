package com.tavant.trainer.model;

public class Data {
	private String entity;
	private String trainingData;
	public String getEntity() {
		return entity;
	}
	public void setEntity(String entity) {
		this.entity = entity;
	}
	public String getTrainingData() {
		return trainingData;
	}
	public void setTrainingData(String trainingData) {
		this.trainingData = trainingData;
	}
	@Override
	public String toString() {
		return "Data [entity=" + entity + ", trainingData=" + trainingData + "]";
	}

	}
