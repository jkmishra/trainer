package com.tavant.trainer.utils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

import com.tavant.trainer.constants.AppConstants;
import com.tavant.trainer.model.Data;

public class DataUtils {
	

	public static List<String> dataFileReader(String entityName) {
		List<String> lines = new ArrayList<>();
		try {
			lines = Files.readAllLines(Paths.get(fileNameBuilder(entityName)), Charset.forName("UTF-8"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return lines;
	}

	public static String fileNameBuilder(String entityName) {
		return AppConstants.FILE_PATH_PREFIX + entityName + AppConstants.FILE_NAME;
	}
	public static String modelFileDestNameBuilder(String entityName) {
		return AppConstants.FILE_PATH_PREFIX ;
	}
	public static String modelFileNameBuilder(String entityName) {
		return AppConstants.MODEL_PATH_PREFIX+entityName+AppConstants.MODEL_FILE_SUFFIX;
	}
	public static String appendDataInFile(Data data) {
		String resp = "";
		try { 
			Files.write(Paths.get(fileNameBuilder(data.getEntity())), data.getTrainingData().getBytes(),
					StandardOpenOption.APPEND);
			Files.write(Paths.get(fileNameBuilder(data.getEntity())), "\n".getBytes(),
					StandardOpenOption.APPEND);
			resp = AppConstants.APPEND_DATA_SUCCESS;
		} catch (IOException e) {
			resp = AppConstants.APPEND_DATA_FAIL + " :" + e.getMessage();
		}
		return resp;
	}

}
