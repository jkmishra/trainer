package com.tavant.trainer.bot.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MessageFormatter {
	
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
    
    public static String buildMessage(){
    	String message =null;
    	
    	return message;
    }
	
}
