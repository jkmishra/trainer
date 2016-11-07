package com.skype.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MessageFormatter {
	
	static String strAppURL = "https://3fc3409c.ngrok.io";
	
	public static String getWelcomeMessage(){
		String welcomeMessage = "Hello, My name is Nancy, what is your name?";
		return welcomeMessage;
	}
	
	public static String doReply(String question){
		String strReply = null;
		
		return strReply;
	}
	
	public static String match(String pattern, String keyword){
        Pattern p = Pattern.compile(pattern.toLowerCase());
        Matcher m = p.matcher(keyword.toLowerCase());
        if (m.matches()) {
            return m.group(1);
        }
        return "";
    }

    public static String clear(String text){
        Pattern pattern = Pattern.compile("\\[.*\\]");

        // Replace all occurrences of pattern in input
        Matcher matcher = pattern.matcher(text);
        return matcher.replaceAll("");
    }
    
    /**
	 * Generate Automate response and hook to NLP
	 * @param text
	 * @return
	 */
	public static String getAutomatedResponse(String text) {

		
		if (text.equals("Welcome")) {
			return ChatConstants.JSON_DOMAINS;
		}
		
		if (text.equals("Greetings")) {
			return getGreetingJSON();
		}
		
		if (text.equals("Manufacturing")) {
			return ChatConstants.JSON_AUTOMOTIVE;
		}
		
		if (text.equals("Manufacturing")) {
			return ChatConstants.JSON_AUTOMOTIVE;
		}
		if (text.equals("Articulated Boom Lifts")){
			return ChatConstants.JSON_AUTOMOTIVE_AERIAL_ARCTICULATED;
		}
		if (text.equals("Wait, Let me find HR related topics for you")) {
			return ChatConstants.JSONHR;
		}
		
		return getJSONForNoAnswer();

	}
    
    public static String buildMessage(){
    	String message =null;
    	
    	return message;
    }
    
    public static String randomizeGreet(){
    	
    	
    	
    	return "";
    }
    
    public static void setAppURL(String strURL){
    	strAppURL = "https://034782e8.ngrok.io/";
    	System.out.println(strAppURL);
    }
	
    public static String getJSONForNoAnswer(){
    	
    	StringBuffer jsonGreet= new StringBuffer();
    	
    	jsonGreet.append("{\"type\": \"message/text\",\"text\": \"");
    	jsonGreet.append(new InteractiveMessageUtil().anyItemForNo()+"\"}");
    	return jsonGreet.toString();
    }
    
    public static String getGreetingJSON(){
    	
    	StringBuffer jsonGreet= new StringBuffer();
    	
    	jsonGreet.append("{\"type\": \"message/text\",\"text\": \"");
    	jsonGreet.append(new InteractiveMessageUtil().anyItemToGreet()+"\"}");
    	return jsonGreet.toString();
    }
    
    
	

}
