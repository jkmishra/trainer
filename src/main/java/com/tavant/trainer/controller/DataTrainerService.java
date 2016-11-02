package com.tavant.trainer.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.tavant.trainer.common.enums.EntityType;
import com.tavant.trainer.constants.AppConstants;
import com.tavant.trainer.model.Data;
import com.tavant.trainer.model.QueryData;
import com.tavant.trainer.model.QueryResponseData;
import com.tavant.trainer.service.AnswerTypeService;
import com.tavant.trainer.service.DataValidator;
import com.tavant.trainer.service.NamedModelCreator;
import com.tavant.trainer.service.ResponseBuilder;
import com.tavant.trainer.service.ResponseData;
import com.tavant.trainer.service.TrainResponseData;
import com.tavant.trainer.utils.DataUtils;
import com.tavant.trainer.vo.TrainingResponse;

@Path("/data")
public class DataTrainerService {	
	
	@GET
	@Path("/test")
	public Response getRoot() {
		return Response.status(200).entity("OK").build();
	}

	@POST
	@Path("/validate")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response validate(Data data) {
		ResponseData validatorResponse = ResponseBuilder.validatorResponse(data, DataValidator.validate(data));
		return Response.status(200).entity(validatorResponse).header("Access-Control-Allow-Origin", "*").build();

	}

	@GET
	@Path("/instructions/{input}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getInstructions(@PathParam("input") String input) {	
		System.out.println("***************dqwdqwdwq***input" + input);
		return Response.status(200).entity(EntityType.getByValue(input).getInstructions()).build();
	}
	
	
	
	@POST
	@Path("/train")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response train(Data data) throws IOException {		
		List<String> respData = new ArrayList<>();
		boolean isSuccess = false;
		if (DataValidator.isValidEntity(data.getEntity()) && DataValidator.isTrainingDataValid(data)) {
			File file = new File(DataUtils.fileNameBuilder(data.getEntity()));
			if (!file.exists()) {
				file.getParentFile().mkdirs(); 
				file.createNewFile();
			}
			if (!DataValidator.isTrainingDataDuplicate(data)) {
				String appendDataInFile = DataUtils.appendDataInFile(data);
				if (!AppConstants.APPEND_DATA_FAIL.equalsIgnoreCase(appendDataInFile)) {
					respData.addAll(DataUtils.dataFileReader(data.getEntity()));
					isSuccess = true;
				} else {
					respData.add(appendDataInFile);
				}
			} else {
				respData.add(AppConstants.DUPLICATE_DATA);

			}			

		} else {
			respData.add(DataValidator.validate(data));
		}	
		
		/*TrainingResponse trainingResponse = new TrainingResponse(data);
		trainingResponse.setTrainingList(Arrays.asList(new String[]{"default rec1", "default rec2", trainingResponse.getTrainingData()}));
		*/
		TrainResponseData trainingResponse = ResponseBuilder.trainingResponse(data, respData, isSuccess);
		return Response.status(200).entity(trainingResponse).header("Access-Control-Allow-Origin", "*").build();

	}

	@PUT
	@Path("/save")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response save(Data data) throws IOException {	
		String saveModelResp =AppConstants.MODEL_DATA_FAILURE;
		if (DataValidator.isValidEntity(data.getEntity()) && DataValidator.isTrainingDataValid(data)) {
			saveModelResp = NamedModelCreator.createModelData(data);
		}
		ResponseData validatorResponse = ResponseBuilder.validatorResponse(data, saveModelResp);
		return Response.status(201).entity(validatorResponse).build();

	}
	
	@POST
	@Path("/testData")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response testData(QueryData data) throws IOException {
		System.out.println(data);
		QueryResponseData queryResp = NamedModelCreator.testSearchResp(data);
		queryResp = ResponseBuilder.queryResp(data, queryResp);
		return Response.status(200).entity(queryResp).header("Access-Control-Allow-Origin", "*").build();

	}
	
	@POST
	@Path("/analysis")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response analysis(QueryData data) throws IOException {
		System.out.println(data);
		QueryResponseData queryResp = NamedModelCreator.testSearchResp(data);
		queryResp = ResponseBuilder.queryResp(data, queryResp);
		return Response.status(200).entity(queryResp).header("Access-Control-Allow-Origin", "*").build();

	}
	
	
	@POST
	@Path("/answerType")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response answerType(QueryData data) throws IOException {
		String queryResp = AnswerTypeService.getAnswerType(data);
		ResponseData response = ResponseBuilder.answerType(data, queryResp);
		return Response.status(200).entity(response).header("Access-Control-Allow-Origin", "*").build();

	}
}