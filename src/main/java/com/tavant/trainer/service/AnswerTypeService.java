package com.tavant.trainer.service;

import com.tavant.trainer.model.QueryData;
import com.tavant.trainer.utils.AnswerUtil;

public class AnswerTypeService {
	public static String getAnswerType(QueryData data) {
		return AnswerUtil.getAnswerType(data.getQueryData());
	}

}
