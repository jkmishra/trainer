package com.tavant.trainer.utils;

import java.util.HashMap;
import java.util.Map;

import opennlp.tools.cmdline.parser.ParserTool;
import opennlp.tools.parser.Parse;
import opennlp.tools.parser.Parser;

/**
 * @author shishir.srivastava
 *
 */
public class AnswerUtil {
	private static Map<String, String> answerTypeMap;

	private static AnswerTypeClassifier atc= null;
	static {
			atc = AnswerTypeClassifier.getInstance();
		 	answerTypeMap = new HashMap<String, String>();
		    answerTypeMap.put("L", "LOCATION");
		    answerTypeMap.put("T", "TIME|DATE");
		    answerTypeMap.put("P", "PERSON");
		    answerTypeMap.put("O", "ORGANIZATION");
		  	answerTypeMap.put("M", "MONEY");
		    answerTypeMap.put("C", "PERCENTAGE");
		    answerTypeMap.put("W", "TITLE");
		    answerTypeMap.put("B", "DEFINATION");
		    answerTypeMap.put("R", "DURATION");
		    answerTypeMap.put("A", "AMOUNT");
		    answerTypeMap.put("R", "DURATION");
		    answerTypeMap.put("D", "DISTANCE");
		    answerTypeMap.put("F", "DISCRIPTION");
	}

	public static String getAnswerType(String qstr) {

		Parser parser = atc.getParser();
		Parse parse = ParserTool.parseLine(qstr, parser, 1)[0];
		String type = atc.computeAnswerType(parse);
		return type;
	}

	public static void main(String[] args) {
		System.out.println(AnswerUtil.getAnswerType("Where is great wall of chine"));
		System.out.println(AnswerUtil.getAnswerType("WhAT FRACTION OF people live in China"));
		System.out.println(AnswerUtil.getAnswerType("What is dry ice?"));
		System.out.println(AnswerUtil.getAnswerType("Who is Sachin Tendulkar"));
		System.out.println(AnswerUtil.getAnswerType("What is the cost of honda"));
		System.out.println(AnswerUtil.getAnswerType("How much is cost to buy a bike"));
		System.out.println(AnswerUtil.getAnswerType("what is your name"));
		
	}

}