package com.tavant.trainer.bot.util;

import java.util.ArrayList;
import java.util.Random;

/**
 * @author shishir.srivastava
 *
 */
public class InteractiveMessageUtil {

	private Random randomGenerator;
	private ArrayList<String> catalogue;

	String[] magicAnswers = { "It is certain", "It is decidedly so", "Without a doubt", "Yes, definitely",
			"You may rely on it", "As I see it, yes", "Most likely", "Outlook good", "Yes", "Signs point to yes",
			"Reply hazy try again", "Ask again later", "Better not tell you now", "Cannot predict now",
			"Concentrate and ask again", "Don't count on it", "My reply is no", "My sources say no",
			"Outlook not so good", "Very doubtful" };

	public InteractiveMessageUtil() {
		catalogue = new ArrayList<String>();
		randomGenerator = new Random();
	}

	public String anyItem() {
		int index = randomGenerator.nextInt(catalogue.size());
		String item = catalogue.get(index);
		System.out.println("Managers choice this week" + item + "our recommendation to you");
		return item;
	}
}
