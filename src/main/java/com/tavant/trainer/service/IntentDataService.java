package com.tavant.trainer.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.tavant.trainer.constants.AppConstants;

public class IntentDataService {
	public List<String> getIntentFileData() {
		List<String> lines = new ArrayList<>();
		// Get file from resources folder
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource(AppConstants.INTENT_FILE_NAME).getFile());
		try (Scanner scanner = new Scanner(file)) {
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				lines.add(line);
			}
			scanner.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
		return lines;

	}
	public static void main(String[] args) {
		System.out.println(new IntentDataService().getIntentFileData());
	}

}
