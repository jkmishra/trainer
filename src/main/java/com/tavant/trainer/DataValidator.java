package com.tavant.trainer;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataValidator {

	public static String validate(Data data) {
		String resp = AppConstants.SUCCESS;
		if (!isValidEntity(data.getEntity())) {
			return resp = AppConstants.INVALID_ENTITY;
		}
		if (!isTrainingDataValid(data)) {
			return resp = AppConstants.INVALID_DATA;
		}
		return resp;
	}

	public static boolean isTrainingDataValid(Data data) {

		return isValidData(data);
	}

	public static boolean isValidData(Data data) {
		// String line = "This order was placed for QT3000! OK?";
		// String pattern = "(.*)(\\d+)(.*)";
		// Create a Pattern object
		if (data.getTrainingData() == null)
			return false;
		String pattern = "(.*)(<START:" + data.getEntity() + ">)(.*)(<END>)(.*)";
		Pattern r = Pattern.compile(pattern);
		// Now create matcher object.
		Matcher m = r.matcher(data.getTrainingData().trim());
		System.out.println(data.getTrainingData() + "   " + m.find() + " " + m.matches());
		return m.matches();

	}

	public static boolean isTrainingDataDuplicate(Data data) {
		List<String> dataFileReader = DataUtils.dataFileReader(data.getEntity());
		return dataFileReader.contains(data.getTrainingData());
	}

	public static boolean isValidEntity(String entity) {
		return Arrays.asList(AppConstants.MODEL_NAME).contains(entity);
	}

	public static void main(String[] args) {
		Data dt = new Data();
		dt.setEntity("person");
		String taggedSent = "<START:person> Rajesh Spears <END> was reunited with her sons <START:date> Saturday <END>";
		dt.setTrainingData(taggedSent);
		System.out.println(isValidData(dt));
	}
}
