package com.tavant.trainer.controller;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CommonsHttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;

import com.tavant.trainer.service.ResponseData;

	

@Path("/query")
public class QueryHandler {

	public QueryHandler() throws MalformedURLException {
		solr = new CommonsHttpSolrServer(new URL("http://localhost:8983/solr"));
		query = new SolrQuery();
		query.setRows(10);
	}
	private static SolrServer solr;
	private SolrQuery query;

	@GET
	@Path("/getanswer")
	public Response getRoot() {
		ResponseData queryResponse = null;
		return Response.status(200).entity(queryResponse).build();
	}

	

	
	public Iterator<SolrDocument> getCandidates(String questionString) throws SolrServerException {
		query.setQuery("body:\"" + questionString + "\"");
		QueryResponse response = solr.query(query);
		SolrDocumentList dl = response.getResults();
		return dl.iterator();
	}

	public static void main(String[] args) throws MalformedURLException, SolrServerException {
		
		QueryHandler handler = new QueryHandler();
		handler.handleRequest("basketball");
		
		
	}




	private void handleRequest(String questionString) throws SolrServerException {
		
		Iterator<SolrDocument> docs=getCandidates(questionString);
		while(docs.hasNext()){
			SolrDocument doc = docs.next();
			System.out.println(doc.getFieldValue("body"));
			
			
			
		}
		
	}
	
	
}
