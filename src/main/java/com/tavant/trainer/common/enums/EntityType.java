package com.tavant.trainer.common.enums;

public enum EntityType {
	PERSON("person",
			"The data must be converted to the OpenNLP name finder training format. "
					+ "Which is one sentence per line. The sentence must be tokenized and contain spans which mark the entities. "
					+ "Documents are separated by empty lines which trigger the reset of the adaptive feature generators."
					+ " Sample sentence of the data:"
					+ "<br/>"
					+ "<START:person> Pierre Vinken <END> , 61 years old , will join the board as a "
					+ "nonexecutive director Nov. 29 ." + "<br/>"
					+ "The training data should contain at least 15000 sentences to create a model"
					+ " which performs well. Usage of the tool:"),
	
	MONEY("money",
					"The data must be converted to the OpenNLP name finder training format. "
							+ "Which is one sentence per line. The sentence must be tokenized and contain spans which mark the entities. "
							+ "Documents are separated by empty lines which trigger the reset of the adaptive feature generators."
							+ " Sample sentence of the data:"
							+ "<START:person> Pierre Vinken <END> , 61 years old , will join the board as a "
							+ "nonexecutive director Nov. 29 ." + "\n"
							+ "The training data should contain at least 15000 sentences to create a model"
							+ " which performs well. Usage of the tool:"),

	ANSWER("answer",
			"I am answer, Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vestibulum tincidunt est vitae ultrices accumsan. Aliquam ornare lacus adipiscing, posuere lectus et, fringilla augue.");

	private String value;
	private String instructions;

	private EntityType(String value, String instructions) {
		this.value = value;
		this.instructions = instructions;
	}

	public String getValue() {
		return value;
	}

	public String getInstructions() {
		return instructions;
	}

	public static EntityType getByValue(String value) {
		for (EntityType entityType : EntityType.values()) {
			if (entityType.value.equalsIgnoreCase(value)) {
				return entityType;
			}
		}
		throw new IllegalArgumentException("Invalid value for entity type: " + value);
	}

}
