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
	
	private static Map<String, String> entityModelMap;

	
	
	
	private static AnswerTypeClassifier atc= null;
	static {
			atc = AnswerTypeClassifier.getInstance();
		 	answerTypeMap = new HashMap<String, String>();
		 	entityModelMap = new HashMap<String, String>();
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
		   
		    entityModelMap.put("P", "person");
		    entityModelMap.put("L", "location");
		    entityModelMap.put("D", "duration");
		    entityModelMap.put("T", "time");
		    entityModelMap.put("X", "unknown");
		    entityModelMap.put("A", "amount");
		    
		    
		    
		    
	}

	public static String getAnswerType(String qstr) {

		Parser parser = atc.getParser();
		Parse parse = ParserTool.parseLine(qstr, parser, 1)[0];
		String type = atc.computeAnswerType(parse);
		return type;
	}
	
	/**
	 * @param qstr
	 * @return
	 */
	public static String getAssociatedEntityForQuestion(String qstr) {
		return entityModelMap.get(getAnswerType(qstr)).toString();

	}
	

	public static void main(String[] args) {
		//System.out.println(.get(AnswerUtil.getAnswerType("Where is great wall of chine")));
		System.out.println(AnswerUtil.getAnswerType("WhAT FRACTION OF people live in China"));
		System.out.println(AnswerUtil.getAnswerType("What is dry ice?"));
		System.out.println(AnswerUtil.getAnswerType("Who is Sachin Tendulkar"));
		System.out.println(AnswerUtil.getAnswerType("What is the cost of honda"));
		System.out.println(AnswerUtil.getAnswerType("How much is cost to buy a bike"));
		System.out.println(AnswerUtil.getAnswerType("who is Michal Farera"));
		
	}

}