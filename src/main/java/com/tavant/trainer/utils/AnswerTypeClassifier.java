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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import com.tavant.trainer.constants.AppConstants;

import opennlp.maxent.GIS;
import opennlp.maxent.GISModel;
import opennlp.model.MaxentModel;
import opennlp.model.TwoPassDataIndexer;
import opennlp.tools.chunker.ChunkerME;
import opennlp.tools.chunker.ChunkerModel;
import opennlp.tools.doccat.DoccatModel;
import opennlp.tools.parser.Parse;
import opennlp.tools.parser.Parser;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;

/**
 * @author shishir.srivastava
 *
 */
public class AnswerTypeClassifier {

	private MaxentModel model;
	private double[] probs;
	private AnswerTypeContextGenerator atcg;
	private POSTaggerME tagger;
	private ChunkerME chunker;

	static AnswerTypeClassifier atc;
	static Parser parser = null;

	public static AnswerTypeClassifier getInstance() {
		synchronized (AnswerTypeClassifier.class) {
			if (atc == null) {
				AnswerTypeClassifier classfier = new AnswerTypeClassifier();
				atc = classfier;
				return classfier;
			} else {
				return atc;
			}
		}

	}
	
	public  Parser getParser() {
		 return parser;

	}
	
	

	private AnswerTypeClassifier() {

		init();
		atc = new AnswerTypeClassifier(model, probs, atcg);// <co
		parser = new ChunkParser(chunker, tagger);// <co id="qqpp.parser"/>
	}

	public void init() {
		//AnswerTypeContextGenerator atcg = null;
		if (AppConstants.ModelDirectory != null) {
			File modelsDir = new File(AppConstants.ModelDirectory);
			try {
				InputStream chunkerStream = new FileInputStream(new File(modelsDir, "en-chunker.bin"));
				ChunkerModel chunkerModel = new ChunkerModel(chunkerStream);
				chunker = new ChunkerME(chunkerModel); // <co
														// id="qqpp.chunker"/>
				InputStream posStream = new FileInputStream(new File(modelsDir, "en-pos-maxent.bin"));
				POSModel posModel = new POSModel(posStream);
				tagger = new POSTaggerME(posModel); // <co id="qqpp.tagger"/>
				model = new DoccatModel(new FileInputStream( // <co
																// id="qqpp.theModel"/>
						new File(AppConstants.ModelDirectory, "en-answer.bin"))).getChunkerModel();
				probs = new double[model.getNumOutcomes()];
				atcg = new AnswerTypeContextGenerator(new File(AppConstants.WordnetDirectory, "dict"));// <co
																										// id="qqpp.context"/>

				AnswerTypeClassifier atc = new AnswerTypeClassifier(model, probs, atcg);// <co
																						// id="qqpp.atc"/>
				parser = new ChunkParser(chunker, tagger);// <co
															// id="qqpp.parser"/>

			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}

	public AnswerTypeClassifier(MaxentModel model, double[] probs, AnswerTypeContextGenerator atcg) {
		this.model = model;
		this.probs = probs;
		this.atcg = atcg;
	}

	// <start id="atc.compute"/>
	public String computeAnswerType(Parse question) {
		double[] probs = computeAnswerTypeProbs(question);// <co
															// id="atc.getprobs"/>
		return model.getBestOutcome(probs);// <co id="atc.outcome"/>
	}

	public double[] computeAnswerTypeProbs(Parse question) {
		String[] context = atcg.getContext(question);// <co id="atc.context"/>
		return model.eval(context, probs);// <co id="atc.evaluate"/>
	}
	/**
	 * Train the answer model
	 * <p>
	 * Hint:
	 * 
	 * <pre>
	 *  mvn exec:java -Dexec.mainClass=com.tamingtext.qa.AnswerTypeClassifier \
	 *    -Dexec.args="dist/data/questions-train.txt en-answer.bin" \
	 *    -Dmodel.dir=../../opennlp-models \
	 *    -Dwordnet.dir=../../Wordnet-3.0/dict
	 * </pre>
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		if (args.length < 2) {
			System.err.println("Usage: AnswerTypeClassifier trainFile modelFile");
			System.exit(1);
		}
		String trainFile = args[0];
		File outFile = new File(args[1]);
		String modelsDirProp = System.getProperty("model.dir");
		File modelsDir = new File(modelsDirProp);
		String wordnetDir = System.getProperty("wordnet.dir");
		InputStream chunkerStream = new FileInputStream(new File(modelsDir, "en-chunker.bin"));
		ChunkerModel chunkerModel = new ChunkerModel(chunkerStream);
		ChunkerME chunker = new ChunkerME(chunkerModel);
		InputStream posStream = new FileInputStream(new File(modelsDir, "en-pos-maxent.bin"));
		POSModel posModel = new POSModel(posStream);
		POSTaggerME tagger = new POSTaggerME(posModel);
		Parser parser = new ChunkParser(chunker, tagger);
		AnswerTypeContextGenerator actg = new AnswerTypeContextGenerator(new File(wordnetDir));
		// <start id="atc.train"/>
		AnswerTypeEventStream es = new AnswerTypeEventStream(trainFile, actg, parser);
		GISModel model = GIS.trainModel(100, new TwoPassDataIndexer(es, 3));// <co
																			// id="atc.train.do"/>
		new DoccatModel("en", model).serialize(new FileOutputStream(outFile));
		/*
		 * <calloutlist> <callout arearefs="atc.train.do"><para>Using the event
		 * stream, which feeds us training examples, do the actual training
		 * using OpenNLP's Maxent classifier.</para></callout> </calloutlist>
		 */
		// <end id="atc.train"/>
	}
}
