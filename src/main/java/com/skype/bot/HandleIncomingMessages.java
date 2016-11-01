package com.skype.bot;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import com.skype.parser.DataParser;
import com.skype.util.TempDBUtil;

/**
 * Servlet implementation class HandleIncomingMessages
 */
@WebServlet("/HandleIncomingMessages")
public class HandleIncomingMessages extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public HandleIncomingMessages() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		// String token = getAuthToken();
		//String url ="https://skype.botframework.com/v3/conversations/";
	String url = "https://apis.skype.com/v3/conversations/";
		//String url = "https://skype.botframework.com/v3/conversations/";
		StringBuffer jb = new StringBuffer();
		String line = null;
		String respMessage = null;

		try {
			BufferedReader reader = request.getReader();
			while ((line = reader.readLine()) != null)
				jb.append(line);
		} catch (Exception e) { /* report an error */
		}
		/*
		 * JSONArray jsonArray = (JSONArray) JSONValue.parse(jb.toString());
		 * JSONObject jsonObject= (JSONObject) jsonArray.get(0);
		 */
		
		
		JSONObject jsonObject = (JSONObject) JSONValue.parse(jb.toString());
		JSONObject jsonObject1 = (JSONObject) jsonObject.get("from");
		String id = jsonObject1.get("id").toString();
		url = url + id + "/activities/";
		System.out.println(url);
		
		
		System.out.println(jb.toString());
		//String guestText = ((JSONObject)jsonObject.get("text")).toString();
		
		
		// IF user is landing first time
		if (TempDBUtil.isThisFirstTime(id)) {
			respMessage = "Hey <a href='abc' onClick='sendMEssage()'>Ready</a><script>function sendMessage(){console.log('Hi');alert('hi')}</script>" + jsonObject1.get("name")
					+ " May i help you";
			
			
			TempDBUtil.storeIdentities(id, jsonObject1.get("name")
					.toString());
			//TempDBUtil.storeConversation(id,guestText);
		}// If user has active session
		else if (TempDBUtil.isActiveSession(id)) {			// Add a NLP hook here
			
			respMessage = "Would you be intrested for below?";//getBotMessage(jsonObject.get("text").toString());
		}// User is returning
		else {
			respMessage = "Welcome back " + jsonObject1.get("name");
		}

		// TempDBUtil.configToken();
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("POST");
		con.setRequestProperty("Content-Type", "application/json");
		String access_token = TempDBUtil.isTokenExpired() ? TempDBUtil
				.getAuthToken() : TempDBUtil.TOKENMAP.get("token");
		con.setRequestProperty("Authorization", "Bearer " + access_token);
		// For POST only - START
		con.setDoOutput(true);
		// Send request
		
		JSONObject content= new JSONObject();
		
		content.put("content", "Hi! (wave)");
		JSONObject obj2 = new JSONObject();
		
		obj2.put("message", content);
	
	
		
		/*obj2.put("type", "message/text"); //message/card.carousel
		obj2.put("text", "respMessage");*/
		
		
		//Attachment Layout starts here
		//obj2.put("attachmentLayout","list");
		
		//create array for "attachments"
		JSONArray  attachArray = new JSONArray();
		JSONObject attachObject1 = new JSONObject();
		attachObject1.put("contentType","application/vnd.microsoft.card.hero");			
		
		
		JSONObject contentVal = new JSONObject();
		JSONArray list = new JSONArray();
		JSONObject object = new JSONObject();
		object.put("type", "I am Option1");
		object.put("title", "HR");
		object.put("value", "HR");
		
		JSONObject object1 = new JSONObject();
		object1.put("type", "I am option 2");
		object1.put("title", "Payroll");
		object1.put("value", "Payroll");
		
		list.add(object);
		list.add(object1);		
		//obj2.put("buttons", list);
		contentVal.put("text", "Choose any of the below");
		contentVal.put("buttons", list);
		
		
		attachObject1.put("content", contentVal);
		attachArray.add(attachObject1);	
		//obj2.put("attachments", attachArray);

		System.out.println("POST message is "+obj2.toJSONString());
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(obj2.toString());
		// System.out.println(obj2.toString());
		wr.flush();
		wr.close();
		// For POST only - END
		int responseCode = con.getResponseCode();
		if (responseCode == HttpURLConnection.HTTP_OK || responseCode == 201) { // success
			BufferedReader in = new BufferedReader(new InputStreamReader(
					con.getInputStream()));
			String inputLine;
			StringBuffer sb = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				sb.append(inputLine);
			}
			in.close();

			// print result
		} else {
			System.out.println("POST request not worked "+responseCode);
			BufferedReader in = new BufferedReader(new InputStreamReader(
					con.getInputStream()));
			String inputLine;
			StringBuffer sb = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				sb.append(inputLine);
			}
			in.close();
			System.out.println(sb);
			
			
			
		}
	}

	private String getBotMessage(String reqMessage) {
		String defString = "Lovely";
		boolean isBrainOnComa = false;	
		String url = "http://10.193.74.90:8983/solr/answer?q="+reqMessage;
		System.out.println("Requeted URL:" + url);
		StringBuilder sb = new StringBuilder();
		URLConnection urlConn = null;
		InputStreamReader in = null;
		try {
			URL reqUrl = new URL(url);
			urlConn = reqUrl.openConnection();
			if (urlConn != null)
				urlConn.setReadTimeout(60 * 1000);
			if (urlConn != null && urlConn.getInputStream() != null) {
				in = new InputStreamReader(urlConn.getInputStream(),
						Charset.defaultCharset());
				BufferedReader bufferedReader = new BufferedReader(in);
				if (bufferedReader != null) {
					int cp;
					while ((cp = bufferedReader.read()) != -1) {
						sb.append((char) cp);
					}
					bufferedReader.close();
				}
			}
		in.close();
		} catch (Exception e) {
			isBrainOnComa = true;
			e.printStackTrace();			
		} 
		
		String respMessage = isBrainOnComa? "I am sleeping, wait....": parseJSON(sb.toString());
		
		//System.out.println("Response is "+respMessage);
		return respMessage;
		
	}
	
	private String parseJSON(String jsonString){
		
		String respMessage = "Huh, I didn't understand, I need some more training";
		try{
			JSONObject jsonObject = (JSONObject) JSONValue.parse(jsonString);
			JSONObject jsonObject1 = (JSONObject) jsonObject.get("from");
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return respMessage;
	}
		
	
}
