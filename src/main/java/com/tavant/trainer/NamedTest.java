package com.tavant.trainer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.util.Collections;

import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.NameSample;
import opennlp.tools.namefind.NameSampleDataStream;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.util.ObjectStream;
import opennlp.tools.util.PlainTextByLineStream;
import opennlp.tools.util.Span;
import opennlp.tools.util.featuregen.AdaptiveFeatureGenerator;

public class NamedTest {

  static void testMultiName(String taggedSent) throws IOException {
		File destDir = new File("E:/MenuItem");

		// <start id="ne-namesample-type"/>
	
		ObjectStream<NameSample> nss = new NameSampleDataStream(
				new PlainTextByLineStream(new StringReader(taggedSent)));
		TokenNameFinderModel model = NameFinderME.train("en", "person", nss,
				(AdaptiveFeatureGenerator) null,
				Collections.<String, Object> emptyMap(), 70, 1);

		File outFile = new File(destDir, "en-ner-custom.bin");
		FileOutputStream outFileStream = new FileOutputStream(outFile);
		model.serialize(outFileStream);
/*
		NameFinderME nameFinder = new NameFinderME(model);

		String[] tokens = ("Eligibility  Employees who have worked for a minimum of 80 days for the company can avail the adoption leave.")
				.split("\\s+");
		Span[] names = nameFinder.find(tokens);
		displayNames(names, tokens);*/
	}

	static void displayNames(Span[] names, String[] tokens) {
		for (int si = 0; si < names.length; si++) { // <co
													// id="co.opennlp.name.eachname"/>
			StringBuilder cb = new StringBuilder();
			for (int ti = names[si].getStart(); ti < names[si].getEnd(); ti++) {
				cb.append(tokens[ti]).append(" "); // <co
													// id="co.opennlp.name.eachtoken"/>
			}
			System.out.println(cb.substring(0, cb.length() - 1)); // <co
																	// id="co.opennlp.name.extra"/>
			System.out.println("\ttype: " + names[si].getType());
		}
	}
	


}
