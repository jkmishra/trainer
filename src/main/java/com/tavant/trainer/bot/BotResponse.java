package com.tavant.trainer.bot;

/**
 * @author shishir.srivastava
 *
 */
public class BotResponse {

	String type = "message/text";
	String text = "Hi Shishir ";

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String toString() {
		return "Type... : " + type + "Text..." + text;
	}

}
