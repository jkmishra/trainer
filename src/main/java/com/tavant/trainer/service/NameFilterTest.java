/*
 * Copyright 2008-2011 Grant Ingersoll, Thomas Morton and Drew Farris
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 * -------------------
 * To purchase or learn more about Taming Text, by Grant Ingersoll, Thomas Morton and Drew Farris, visit
 * http://www.manning.com/ingersoll
 */

package com.tavant.trainer.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.analysis.tokenattributes.PositionIncrementAttribute;

import com.tavant.trainer.constants.AppConstants;
import com.tavant.trainer.model.QueryData;
import com.tavant.trainer.utils.DataUtils;

import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.sentdetect.SentenceDetector;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;

public class NameFilterTest extends TamingTextTestJ4 {
	
	public static String testNameFilter(QueryData data) throws IOException {
		File modelDir = new File(DataUtils.modelDir());
		NameFinderME[] finder = new NameFinderME[AppConstants.MODEL_NAME.length];
		for (int i = 0; i < AppConstants.MODEL_NAME.length; i++) {
			finder[i] = new NameFinderME(new TokenNameFinderModel(
					new FileInputStream(new File(modelDir, "en-ner-" + AppConstants.MODEL_NAME[i] + ".bin"))));
		}
		File modelFile = new File(modelDir, "en-sent.bin");
		InputStream modelStream = new FileInputStream(modelFile);
		SentenceModel model = new SentenceModel(modelStream);
		SentenceDetector detector = new SentenceDetectorME(model);
		Reader in = new StringReader(data.getQueryData());
		Tokenizer tok = new SentenceTokenizer(in, detector);
		NameFilter nf = new NameFilter(tok, AppConstants.MODEL_NAME, finder);
		CharTermAttribute cta;
		PositionIncrementAttribute pta;
		OffsetAttribute oa;
		int pass = 0;
		StringBuffer sbResp = new StringBuffer();		
		while (pass < 2) { // test reuse.
			int pos = 0;
			int lastStart = 0;
			int lastEnd = 0;
			boolean found=false;			
			String entityName="";
			while (nf.incrementToken()) {
				cta = (CharTermAttribute) nf.getAttribute(CharTermAttribute.class);
				pta = (PositionIncrementAttribute) nf.getAttribute(PositionIncrementAttribute.class);
				oa = (OffsetAttribute) nf.getAttribute(OffsetAttribute.class);				
				if(cta.toString().startsWith("NE") && !found){
					found=true;	
					entityName=cta.toString();
					sbResp.append("<"+entityName.substring(3)+">");
				}if(found && pta.getPositionIncrement()==0){					
					sbResp.append(cta.toString()+" ");
				}
				if(found && !cta.toString().startsWith("NE") && pta.getPositionIncrement()==1){
					found=false;					
					sbResp.append("</"+entityName.substring(3)+"> ");
				}
				else if(!found){
					sbResp.append(cta.toString()+" ");
				}
				lastStart = oa.startOffset();
				lastEnd = oa.endOffset();
				pos++;
			}
			// if (pass == 1) nf.dumpState();
			if(found){
				found=false;			
				sbResp.append("</"+entityName.substring(3)+">");
			}
			nf.end();
			in.close();
			in = new StringReader(data.getQueryData());
			tok.reset(in);
			pass++;

		}
		return sbResp.toString();
	}

	
	
	public static void main(String[] args) throws IOException {
		final String input = "The quick brown fox jumped over William Taft the President. "
				+ "There once was a man from New York City who had to catch the bus at 10:30 "
				+ "in the morning of December 21, 1992 ";

		QueryData data = new QueryData();
		data.setEntity("person");
		data.setQueryData(input);
		String testNameFilterTest = testNameFilter(data);
		List<String> testList = new ArrayList<>();

	}
}
