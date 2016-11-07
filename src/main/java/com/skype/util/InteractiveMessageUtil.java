package com.skype.util;


import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * @author shishir.srivastava
 *
 */
public class InteractiveMessageUtil {

	private Random randomGenerator;


	String[] magicAnswers = { "It is certain", "It is decidedly so", "Without a doubt", "Yes, definitely",
			"You may rely on it", "As I see it, yes", "Most likely", "Outlook good", "Yes", "Signs point to yes",
			"Reply hazy try again", "Ask again later", "Better not tell you now", "Cannot predict now",
			"Concentrate and ask again", "Don't count on it", "My reply is no", "My sources say no",
			"Outlook not so good", "Very doubtful" };

	String[] magicGreets = { "Welcome", "Hey, Good to see you!", "It is my honor to serve you", "Hi, What's up dear","Hope you doing great, Welcome to AMA world" };

	
	public InteractiveMessageUtil() {				
		randomGenerator = new Random();
	}

	public String anyItemForNo() {
		List<String>  catalogue = Arrays.asList(magicAnswers);
		int index = randomGenerator.nextInt(catalogue.size());
		String item = catalogue.get(index);
		System.out.println("Managers choice this week" + item + "our recommendation to you");
		return item;
	}
	
	public String anyItemToGreet() {
		List<String> catalogue = Arrays.asList(magicGreets);
		int index = randomGenerator.nextInt(catalogue.size());
		String item = catalogue.get(index);
		System.out.println("Managers choice this week" + item + "our recommendation to you");
		return item;
	}
}

