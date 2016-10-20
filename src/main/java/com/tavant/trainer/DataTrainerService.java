package com.tavant.trainer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.util.Collections;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.NameSample;
import opennlp.tools.namefind.NameSampleDataStream;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.util.ObjectStream;
import opennlp.tools.util.PlainTextByLineStream;
import opennlp.tools.util.Span;
import opennlp.tools.util.featuregen.AdaptiveFeatureGenerator;

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
		ResponseData validatorResponse = ResponseBuilder.validatorResponse(msg,
				DataValidator.validate(msg));
		return Response.status(200).entity(validatorResponse)
				.header("Access-Control-Allow-Origin", "*").build();

	}

	@POST
	@Path("/train")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response train(Data data) {
		String result = "Track saved : " + data;
		return Response.status(200).entity(data).build();

	}

	@PUT
	@Path("/save")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response save(Data track) throws IOException {
		String result = "Track saved : " + track;

		if (DataValidator.isValidEntity(track.getEntity())
				&& DataValidator.isTrainingDataValid(track)) {
			NamedTest.testMultiName(track.getTrainingData());
		}
		return Response.status(201).entity(track).build();

	}

}