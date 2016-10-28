package com.tavant.trainer.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;

import com.tavant.trainer.constants.AppConstants;
import com.tavant.trainer.model.Data;
import com.tavant.trainer.model.QueryData;
import com.tavant.trainer.model.QueryResponseData;
import com.tavant.trainer.utils.Config;
import com.tavant.trainer.utils.DataUtils;

import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.NameSample;
import opennlp.tools.namefind.NameSampleDataStream;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.tokenize.SimpleTokenizer;
import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.util.ObjectStream;
import opennlp.tools.util.PlainTextByLineStream;
import opennlp.tools.util.Span;
import opennlp.tools.util.featuregen.AdaptiveFeatureGenerator;

public class NamedModelCreator {

	private static TokenNameFinderModel saveModel(Data data) throws IOException {
		File destFile = new File(DataUtils.fileNameBuilder(data.getEntity()));
		if (!destFile.exists()) {
			destFile.getParentFile().mkdirs();
			destFile.createNewFile();
		}
		ObjectStream<NameSample> nss = new NameSampleDataStream(new PlainTextByLineStream(new FileReader(destFile)));
		TokenNameFinderModel model = NameFinderME.train(Config.getProperty("LANGUAGE_CODE"), data.getEntity(), nss,
				(AdaptiveFeatureGenerator) null, Collections.<String, Object> emptyMap(), 70, 1);
		File outFile = new File(DataUtils.modelFileDestNameBuilder(data.getEntity()),
				DataUtils.modelFileNameBuilder(data.getEntity()));
		FileOutputStream outFileStream = new FileOutputStream(outFile);
		model.serialize(outFileStream);
		return model;
	}

	public static String createModelData(Data data) throws IOException {
		TokenNameFinderModel saveModelResp =null;		
			saveModelResp = saveModel(data);		
		if (saveModelResp != null) {
			return AppConstants.MODEL_DATA_SUCCESS;
		} else {
			return AppConstants.MODEL_DATA_FAILURE;
		}
	}

	public static QueryResponseData testSearchResp(QueryData data) throws IOException {
		QueryResponseData queryResp=new QueryResponseData();
		NameFinderME nameFinder = new NameFinderME(
				new TokenNameFinderModel(
						new FileInputStream(new File(DataUtils.modelFileDestNameBuilder(data.getEntity()),
								DataUtils.modelFileNameBuilder(data.getEntity())))),
				(AdaptiveFeatureGenerator) null, NameFinderME.DEFAULT_BEAM_SIZE);		
		Tokenizer tokenizer = SimpleTokenizer.INSTANCE; 
		String[] tokens = tokenizer.tokenize(data.getQueryData());
		Span[] names = nameFinder.find(tokens);
		for (int si = 0; si < names.length; si++) {
			StringBuilder cb = new StringBuilder();
			for (int ti = names[si].getStart(); ti < names[si].getEnd(); ti++) {
				cb.append(tokens[ti]).append(" ");
			}
			System.out.println(cb.substring(0, cb.length() - 1));
			System.out.println("\ttype: " + names[si].getType());			
			queryResp.setType(names[si].getType());
			queryResp.setDataStr(cb.substring(0, cb.length() - 1));
		}
		return queryResp;
	}

	/**
	 * @param data
	 * @return
	 * @throws IOException
	 */
	public static String getSingleWordAnswer(QueryData data) throws IOException {
		QueryResponseData queryResp=new QueryResponseData();
		NameFinderME nameFinder = new NameFinderME(
				new TokenNameFinderModel(
						new FileInputStream(new File(DataUtils.modelFileDestNameBuilder(data.getEntity()),
								DataUtils.modelFileNameBuilder(data.getEntity())))),
				(AdaptiveFeatureGenerator) null, NameFinderME.DEFAULT_BEAM_SIZE);		
		Tokenizer tokenizer = SimpleTokenizer.INSTANCE; 
		String[] tokens = tokenizer.tokenize(data.getQueryData());
		Span[] names = nameFinder.find(tokens);
		for (int si = 0; si < names.length; si++) {
			StringBuilder cb = new StringBuilder();
			for (int ti = names[si].getStart(); ti < names[si].getEnd(); ti++) {
				cb.append(tokens[ti]).append(" ");
			}
			return cb.toString();
		
		}
		return "Sorry !! noy been able to find anything relevent";
	}

	public static void main(String[] args) throws IOException {
		QueryData data=new QueryData();
		data.setEntity("person");
		data.setQueryData("James Bond was reunited with her sons");
		testSearchResp(data);
	}
}
