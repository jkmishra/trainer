package com.tavant.trainer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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
	public Response validate(Data msg) {
		ResponseData validatorResponse = ResponseBuilder.validatorResponse(msg, DataValidator.validate(msg));
		return Response.status(200).entity(validatorResponse).header("Access-Control-Allow-Origin", "*").build();

	}

	@POST
	@Path("/train")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response train(Data data) throws IOException {
		String result = "Track saved : " + data;
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
		TrainResponseData trainingResponse = ResponseBuilder.trainingResponse(data, respData, isSuccess);
		return Response.status(200).entity(trainingResponse).header("Access-Control-Allow-Origin", "*").build();

	}

	@PUT
	@Path("/save")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response save(Data track) throws IOException {
		String result = "Track saved : " + track;

		if (DataValidator.isValidEntity(track.getEntity()) && DataValidator.isTrainingDataValid(track)) {
			NamedTest.testMultiName(track.getTrainingData());
		}
		return Response.status(201).entity(track).build();

	}

}