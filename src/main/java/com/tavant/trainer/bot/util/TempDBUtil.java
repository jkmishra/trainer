package com.tavant.trainer.bot.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import com.tavant.trainer.bot.ChatConstants;

public class TempDBUtil {

	private static List<String> nameList = new ArrayList<String>();

	private static Map<String, String> map = new WeakHashMap<String, String>();

	public static Map<String, String> TOKENMAP = new WeakHashMap<String, String>();

	public static void storeIdentities(String id, String timestamp) {
		map.put(id, timestamp);
		nameList.add(id);
	}

	public static boolean isActiveSession(String id) {
		return map.containsKey(id) ? true : false;
	}

	public static boolean isThisFirstTime(String id) {
		return nameList.contains(id) ? false : true;

	}

	public static void setTokenMap(String token, String expiry) {
		SimpleDateFormat formatter = new SimpleDateFormat(
				ChatConstants.DATEFORMAT_DD_MM_YYYY_HMMSS);
		String dateCreated = formatter.format(new Date().getTime());
		TOKENMAP.put(ChatConstants.TOKEN, token);
		TOKENMAP.put(ChatConstants.EXPIRY, expiry);
		TOKENMAP.put(ChatConstants.DATECREATED, dateCreated);
		System.out.println(TOKENMAP);
	}

	/**
	 * Convert token expiry to readbale data format dd-MM-yyyy H:mm:ss
	 * 
	 * @param expires
	 * @return
	 */
	private static String calculateExpiresDate(String expires) {
		String expiresDate;
		Date dt = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat(
				ChatConstants.DATEFORMAT_DD_MM_YYYY_HMMSS);

		Calendar c = Calendar.getInstance();
		c.setTime(dt);
		c.add(Calendar.SECOND, Integer.parseInt(expires));
		expiresDate = formatter.format(c.getTime());

		return expiresDate;
	}

	/**
	 * Validate if token is expired
	 * 
	 * @return
	 */
	public static boolean isTokenExpired() {
		SimpleDateFormat formatter = new SimpleDateFormat(
				ChatConstants.DATEFORMAT_DD_MM_YYYY_HMMSS);
		Date now = new Date();
		Date expireDate;
		try {
			if (TOKENMAP.get(ChatConstants.EXPIRY) == null)
				return true;

			expireDate = formatter.parse(calculateExpiresDate(TOKENMAP
					.get(ChatConstants.EXPIRY)));
			if (now.compareTo(expireDate) > 0) {
				// Token expired
				return true;
			}
		} catch (Exception ex) {

		}
		return false;
	}

	/**
	 * Retrives the fresh Auth Token
	 * 
	 * @return
	 */
	public static String getAuthToken() {
		String token = "";
		try {
			URL url = new URL(
					"https://login.microsoftonline.com/common/oauth2/v2.0/token");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");

			conn.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream(conn.getOutputStream());

			wr.writeBytes("client_id="
					+ ChatConstants.APP_CLIENT_ID
					+ "&client_secret="
					+ ChatConstants.APP_CLIENT_SECRET
					+ "&grant_type=client_credentials&scope=https%3A%2F%2Fgraph.microsoft.com%2F.default");
			wr.flush();
			wr.close();

			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader(
					(conn.getInputStream())));
			StringBuilder sb = new StringBuilder();
			String output;
			// System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				System.out.println(output);
				sb.append(output);
			}
			JSONObject jsonObject = (JSONObject) JSONValue.parse(sb.toString());
			token = (String) jsonObject.get("access_token");
			String expiry = jsonObject.get("expires_in").toString();
			// Set the token attribute in weekhashmap
			setTokenMap(token, expiry);
			conn.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return token;
	}

}
