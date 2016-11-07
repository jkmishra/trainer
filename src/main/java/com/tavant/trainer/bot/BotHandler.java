package com.tavant.trainer.bot;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
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

import com.skype.util.TempDBUtil;


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
		
/*		String id = jsonObject1.get("id").toString();
		url = url + id + "/activities/";*/
		
		HttpURLConnection connection=constructMessageWrapper(url);
		DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
		
		
		
		JSONObject obj2 = new JSONObject();
		obj2.put("type", "message/text");
		obj2.put("text", "mycdvd");

		System.out.println(obj2.toString());
		
		String escaped =obj2.toString().replace("\\","").replace("\\\\","");
		
		System.out.println(escaped);
		
		System.out.println(obj2.toString().replace("\\","").replace("\\\\","") );
		
		wr.writeBytes(obj2.toString());
		// System.out.println(obj2.toString());
		wr.flush();
		wr.close();
		connection.getURL();
		int responseCode = connection.getResponseCode();
		System.out.println(connection.getResponseMessage());
		System.out.println(responseCode);
		System.out.println(connection.getURL());
		if (responseCode == HttpURLConnection.HTTP_OK || responseCode == 201) { // success
			BufferedReader in = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
			String inputLine;
			StringBuffer sb = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				sb.append(inputLine);
			}
			in.close();

			// print result
		}
		
		
		
		
		return response;
		
		
		

	}

	private HttpURLConnection constructMessageWrapper(String URL) throws MalformedURLException, IOException, ProtocolException {
		URL obj = new URL(URL);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("POST");
		con.setRequestProperty("Content-Type", "application/json");
		/*String access_token = TempDBUtil.isTokenExpired() ? TempDBUtil
				.getAuthToken() : TempDBUtil.TOKENMAP.get("token");
		*/		
		String access_token = TempDBUtil.getAuthToken();	
		
		System.out.println("Bearer " + access_token);
		con.setRequestProperty("Authorization", "Bearer " + access_token);
		// For POST only - START
		con.setDoOutput(true);
		return con;
	}
	
	
	
	
}
