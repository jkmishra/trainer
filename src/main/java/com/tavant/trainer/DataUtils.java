package com.tavant.trainer;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class DataUtils {
	private static String FILE_PATH_PREFIX = "D:/data/";
	private static String FILE_NAME = ".txt";

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
		return FILE_PATH_PREFIX + entityName + FILE_NAME;
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
