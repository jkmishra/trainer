package com.tavant.trainer.vo;

import java.util.Collections;
import java.util.List;

import com.tavant.trainer.model.Data;

public class TrainingResponse {
	private String entity;
	private String trainingData;
	private List<String> trainingList;
	
	public TrainingResponse() {
		// TODO Auto-generated constructor stub
	}
	
	
	
	public TrainingResponse(Data data) {
		this.entity = data.getEntity();
		this.trainingData = data.getTrainingData();
		this.trainingList = Collections.<String>emptyList();
	}



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
	public List<String> getTrainingList() {
		return trainingList;
	}
	public void setTrainingList(List<String> trainingList) {
		this.trainingList = trainingList;
	}
	
	
}
