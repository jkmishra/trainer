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

package com.tavant.trainer.utils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CommonsHttpSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.parser.html.HtmlParser;
import org.apache.tika.sax.BodyContentHandler;
import org.apache.tika.sax.LinkContentHandler;
import org.apache.tika.sax.TeeContentHandler;
import org.junit.Test;
import org.xml.sax.ContentHandler;

import com.tavant.trainer.service.TamingTextTestJ4;

import opennlp.tools.chunker.ChunkerME;
import opennlp.tools.chunker.ChunkerModel;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.sentdetect.SentenceDetector;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.tokenize.SimpleTokenizer;
import opennlp.tools.util.InvalidFormatException;

/**
 * Demonstrate basic Tika usage
 *
 **/
public class TikaTest extends TamingTextTestJ4 {

	@Test
	public void testTika() throws Exception {
	
		SolrServer solr = new CommonsHttpSolrServer(new URL("http://localhost:" + 8983 + "/solr"));// <co
																									// id="co.solrj.server"/
		
		InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream("tavant_docs/Tavant Leave Policy.pdf");// <co
		
		
		ContentHandler textHandler = new BodyContentHandler();// <co
																
		Metadata metadata = new Metadata();// 
		Parser parser = new AutoDetectParser();// 
		ParseContext context = new ParseContext();
		parser.parse(input, textHandler, metadata, context);
		System.out.println("Title: " + metadata.get(Metadata.TITLE));// 
																		
		System.out.println("Body: " + textHandler.toString());

		File modelDir = getModelDir();

		File modelFile = new File(modelDir, "en-sent.bin");
		InputStream modelStream = new FileInputStream(modelFile);
		SentenceModel model = new SentenceModel(modelStream);
		SentenceDetector detector = // <co id="openSentDetect.co.detect"/>
				new SentenceDetectorME(model);
		String[] result = detector.sentDetect(textHandler.toString()); // <co

		File posModelFile = new File( // <co id="opennlpPOS.co.tagger"/>
				modelDir, "en-pos-maxent.bin");
		FileInputStream posModelStream = new FileInputStream(posModelFile);
		POSModel model1 = new POSModel(posModelStream);

		POSTaggerME tagger = new POSTaggerME(model1);

		for (int i = 0; i < result.length; i++) {
			boolean isProcessDocument = false;
			System.out.println("Sentence: " + result[i]);

			String[] words = SimpleTokenizer.INSTANCE.tokenize( // <co
																// id="opennlpPOS.co.tokenize"/>
					result[i]);
			String[] phraseResult = tagger.tag(words);// <co
			isProcessDocument = containsValue(phraseResult); // id="opennlpPOS.co.dotag"/>
			if (isProcessDocument) {
				AddDoc2(solr, result[i]);
				System.out.print("Successfully saved");
			}

		}

	}

	public static boolean containsValue(String[] phraseResult) {
		List phaseResult = Arrays.asList(phraseResult);
		boolean isAdjective = false;
		boolean isNoun = false;
		boolean isPronoun = false;
		boolean isVerb = false;
		boolean isAdVerb = false;
		boolean isContains = false;

		if (phaseResult.contains("JJ") || phaseResult.contains("JJS") || phaseResult.contains("JJR")) {
			isAdjective = true;
		}
		if (phaseResult.contains("NN") || phaseResult.contains("NNS") || phaseResult.contains("NNP")
				|| phaseResult.contains("NNPS")) {
			isNoun = true;
		}
		if (phaseResult.contains("PRP") || phaseResult.contains("PRP$")) {
			isPronoun = true;
		}
		if (phaseResult.contains("VB") || phaseResult.contains("VBD") || phaseResult.contains("VBN")
				|| phaseResult.contains("VBZ") || phaseResult.contains("VBP") || phaseResult.contains("VBG")) {
			isVerb = true;
		}
		if (phaseResult.contains("RB") || phaseResult.contains("RBR") || phaseResult.contains("RBS")) {
			isAdVerb = true;
		}

		if ((isAdjective && isPronoun) || (isAdjective && isNoun) || (isVerb && isPronoun) || (isVerb && isNoun)) {
			isContains = true;
		}

		return isContains;

	}

	private static void AddDoc2(SolrServer solr, String docString) throws SolrServerException, IOException {

		SolrInputDocument doc = new SolrInputDocument();
		doc.addField("docid", (int) (Math.random() * (1000000 - 1)) + 1);// <co
		doc.addField("body", docString);
		doc.addField("body", "\r\n Please Refer this doument:  Tavant Leave Policy.pdf");
		solr.add(doc);// <co id="co.solrj.add"/>
		solr.commit();// <co id="co.solrj.commit"/>
	}

	public void chunk() throws InvalidFormatException, IOException {

		File modelDir = getModelDir();

		// <start id="openChunkParse"/>
		FileInputStream chunkerStream = new FileInputStream(new File(modelDir, "en-chunker.bin"));
		ChunkerModel chunkerModel = new ChunkerModel(chunkerStream);
		ChunkerME chunker = new ChunkerME(chunkerModel);
		FileInputStream posStream = new FileInputStream(new File(modelDir, "en-pos-maxent.bin"));
		POSModel posModel = new POSModel(posStream);
		POSTaggerME tagger = new POSTaggerME(posModel);

	}

	@Test
	public void testHtml() throws Exception {
		String html = "<html><head><title>The Big Brown Shoe</title></head><body><p>The best pizza place "
				+ "in the US is <a href=\"http://antoniospizzas.com/\">Antonio's Pizza</a>.</p>"
				+ "<p>It is located in Amherst, MA.</p></body></html>";
		// <start id="tika-html"/>
		InputStream input = new ByteArrayInputStream(html.getBytes(Charset.forName("UTF-8")));
		ContentHandler text = new BodyContentHandler();// <co
														// id="html.text.co"/>
		LinkContentHandler links = new LinkContentHandler();// <co
															// id="html.link.co"/>
		ContentHandler handler = new TeeContentHandler(links, text);// <co
																	// id="html.merge"/>
		Metadata metadata = new Metadata();// <co id="html.store"/>
		Parser parser = new HtmlParser();// <co id="html.parser"/>
		ParseContext context = new ParseContext();
		parser.parse(input, handler, metadata, context);// <co id="html.parse"/>
		System.out.println("Title: " + metadata.get(Metadata.TITLE));
		System.out.println("Body: " + text.toString());
		System.out.println("Links: " + links.getLinks());

	
	}

}
