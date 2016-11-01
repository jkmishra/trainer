package com.tavant.trainer.bot;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import com.tavant.trainer.bot.util.TempDBUtil;


@Path("/bot")
public class BotHandler {

	final String SKYPE_API_URL = "https://apis.skype.com/v3/conversations/";
	
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/message")
	public BotResponse getanswer(String request) throws IOException {
		BotResponse response = new BotResponse();
		System.out.println(request);
		//JSONObject jsonObject = (JSONObject) JSONValue.parse(request);
		
		JSONObject jsonObject = (JSONObject) JSONValue.parse(request);
		JSONObject jsonObject1 = (JSONObject) jsonObject.get("from");
		String idFrom = jsonObject1.get("id").toString();
		String url = SKYPE_API_URL + idFrom + "/activities/";
		
		HttpURLConnection connection=constructMessageWrapper(url);
		DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
		
		JSONObject obj2 = new JSONObject();
		obj2.put("type", "message" + "/" + "text");
		obj2.put("text", "mycdvd");

		
		wr.writeBytes(obj2.toString());
		// System.out.println(obj2.toString());
		wr.flush();
		wr.close();
		
		return response;
		
		
		

	}

	private HttpURLConnection constructMessageWrapper(String URL) throws MalformedURLException, IOException, ProtocolException {
		URL obj = new URL(URL);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("POST");
		/*String access_token = TempDBUtil.isTokenExpired() ? TempDBUtil
				.getAuthToken() : TempDBUtil.TOKENMAP.get("token");
		*/		
		String access_token = TempDBUtil.getAuthToken();		
		con.setRequestProperty("Authorization", "Bearer " + access_token);
		// For POST only - START
		con.setDoOutput(true);
		return con;
	}
	
	
	
	
}
