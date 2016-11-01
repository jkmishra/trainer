package com.tavant.trainer.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CommonsHttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;

import com.tavant.trainer.model.QueryData;
import com.tavant.trainer.service.NamedModelCreator;
import com.tavant.trainer.service.ResponseData;
import com.tavant.trainer.utils.AnswerUtil;

@Path("/query")
public class QueryHandler {

	private static SolrServer solr;
	private SolrQuery query;
	
	public QueryHandler() throws MalformedURLException {
		solr = new CommonsHttpSolrServer(new URL("http://localhost:8983/solr"));
		query = new SolrQuery();
		query.setRows(10);
	}



	@GET
	@Path("/getanswer")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getanswer(@Context UriInfo uriInfo) throws IOException {
		ResponseData queryResponse = new ResponseData();
		
		String questionString =uriInfo.getQueryParameters().get("search").get(0);
		
		//String questionString = uriInfo.getRequestUri().getQuery();
		Iterator<SolrDocument> docs;
		SolrDocument mostReleventResult = null;
		try {
			docs = getCandidates(questionString);
			while (docs.hasNext()) {
				mostReleventResult = docs.next();
				System.out.println(mostReleventResult.getFieldValue("body"));

			}
		} catch (SolrServerException e) {
			e.printStackTrace();
		}

		String answertype = AnswerUtil.getAnswerType(questionString);

		QueryData data = new QueryData();

		String entity = AnswerUtil.getAssociatedEntityForQuestion(questionString);

		if (!"unknown".equalsIgnoreCase(entity) && mostReleventResult != null) {
			data.setEntity(entity);
			data.setQueryData(mostReleventResult.getFieldValue("body").toString());
			String answerFromSearch = NamedModelCreator.getSingleWordAnswer(data);
			queryResponse.setResponse(answerFromSearch);
			queryResponse.setStatusCode(200);

		}else {
			queryResponse.setResponse("Did not found any thing relevent!!!.");
			queryResponse.setStatusCode(200);
		}
		
		
		

		return Response.status(200).entity(queryResponse).build();
	}

	public Iterator<SolrDocument> getCandidates(String questionString) throws SolrServerException {
		query.setQuery("body:\"" + questionString + "\"");
		QueryResponse response = solr.query(query);
		SolrDocumentList dl = response.getResults();
		return dl.iterator();
	}

	public static void main(String[] args) throws SolrServerException, IOException {

		QueryHandler handler = new QueryHandler();
		handler.handleRequest("Who is the greatest basketball player of all time?");
		System.out.println(AnswerUtil.getAnswerType("Who is the greatest player of all time?"));

		QueryData data = new QueryData();
		data.setEntity("person");
		data.setQueryData("Michael Jordan is the greatest basketball player of all time.");
		NamedModelCreator.getSingleWordAnswer(data);

		QueryData data2 = new QueryData();
		data2.setEntity("date");
		data2.setQueryData("Gandhi was born on 24th October 2016");
		NamedModelCreator.getSingleWordAnswer(data2);

		QueryData data3 = new QueryData();
		data3.setEntity("location");
		data3.setQueryData("Gandhi was born in London.");
		NamedModelCreator.getSingleWordAnswer(data3);

	}

	private void handleRequest(String questionString) throws SolrServerException {
		Iterator<SolrDocument> docs = getCandidates(questionString);
		while (docs.hasNext()) {
			SolrDocument doc = docs.next();
			System.out.println(doc.getFieldValue("body"));

		}

	}

}
