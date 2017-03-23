package com.codename1.de.cloud.drive.storage.box.auth;

import com.codename1.de.cloud.net.HttpRequest;
import com.codename1.de.cloud.net.XMLRequest;
import com.codename1.de.cloud.util.CommonUtils;
import com.codename1.de.cloud.util.ConfigUtils;
import com.codename1.io.Log;
import com.codename1.xml.Element;


import java.util.Enumeration;
import java.util.Hashtable;

/**
 * 
 * @author Cosmin Zamfir
 *
 */
public class BoxAuth {

	private static String REST_API_URL = "https://www.box.com/api/1.0/rest";
	private static String AUTHORIZATION_URL = "https://www.box.com/api/1.0/auth/";

	/**
	 * The key assigned to the application
	 */
	private String apiKey;
	/**
	 * The token which authorizes this application to the Box account
	 * Assumption: one application instance is linked to one Box account
	 */
	private String authToken;

	public static BoxAuth newInstance() {
		return new BoxAuth();
	}

	private BoxAuth() {
		initTokens();
		if (!isComplete()) {
			String ticket = getTicket();
			String authUrl = AUTHORIZATION_URL + ticket;
			System.out.print("Please go to " + authUrl
					+ " and grant access to the application to your account");
			setAuthorizationToken(ticket);
		}
	}

	/**
	 * Authorization request headers: -H
	 * "Authorization: BoxAuth api_key=API_KEY&auth_token=AUTH_TOKEN"
	 * 
	 * @param request
	 */
	public void sign(HttpRequest request) {
		if (!isComplete()) {
			throw new RuntimeException(
					"Cannot sign "
							+ request
							+ ". At least one of apiToken or authorizationToken is missing");
		}
		request.addRequestHeader("Authorization", buildAuth());
	}

	private String buildAuth() {
		return "BoxAuth api_key=" + apiKey + "&auth_token=" + authToken;
	}

	private void initTokens() {
		Hashtable tokens = ConfigUtils.readTokens("box");
		Enumeration e = tokens.keys();
		while (e.hasMoreElements()) {
			String key = (String) e.nextElement();
			if (key.equals("apiKey")) {
				apiKey = (String) tokens.get(key);
			} else if (key.equals("authToken")) {
				authToken = (String) tokens.get(key);
			} else {
				throw new RuntimeException(
						"Unsupported property found in app config for Box: "
								+ key);
			}
		}
	}

	/**
	 * Retrieve a ticket (an authorization key) used to build the url which
	 * allows the user to grant access for the application to his account
	 * <p>
	 * GET https://www.box.com/api/1.0/rest?action=get_ticket&api_key={your api
	 * key}
	 * <p>
	 * Response format: <response> <status>get_ticket_ok</status>
	 * <ticket>bxquuv025araaaaaze2n4md9zef95e8</ticket> </response>
	 * 
	 * @return the authorization ticket
	 */
	public String getTicket() {
		XMLRequest request = new XMLRequest(REST_API_URL, false);
		request.getUnderlying().addArgument("action", "get_ticket");
		request.getUnderlying().addArgument("api_key", apiKey);
		Element resp = request.getXmlResponse(5000);
		Element status = resp.getFirstChildByTagName("status");
		Element ticket = resp.getFirstChildByTagName("ticket");
		if (status == null) {
			throw new RuntimeException(
					"Cannot retrieve Box ticket. No element 'status' in the reponse.");
		}
		String s = getText(status);
		if (!s.equals("get_ticket_ok")) {
			throw new RuntimeException("Unexpected value for status: " + s);
		}
		if (ticket == null) {
			throw new RuntimeException(
					"Cannot retrieve Box ticket. No element 'ticket' in the reponse.");
		}
		return getText(ticket);
	}

	private String getText(Element status) {
		Element text = status.getChildAt(0);
		return text.getText();
	}

	/**
	 * Retrieve the authorization token. Requires the apiKey, user ticket
	 * 
	 * GET https://www.box.com/api/1.0/rest?action=get_auth_token&api_key={your
	 * api key}&ticket={your ticket} <response>
	 * <status>get_auth_token_ok</status>
	 * <auth_token>9byo5bg8d2o3otp0voji0ej0v49bqcmo</auth_token> <user>
	 * <login>stas@itscript.com</login> <email>stas@itscript.com</email>
	 * <access_id>453</access_id> <user_id>453</user_id>
	 * <space_amount>2147483648</space_amount> <space_used>1024</space_used>
	 * <max_upload_size>26214400</max_upload_size> </user> </response>
	 * 
	 * @param ticket
	 *            the ticket received in {@link #getTicket()}
	 * @throws RuntimeExceptiono
	 *             if the user did not authorize the application to his account
	 */
	private void setAuthorizationToken(String ticket) {
		Log.p("Retrieve authorization token for ticket " + ticket, Log.DEBUG);
		Hashtable headers = new Hashtable();
		headers.put("action", "get_auth_token");
		headers.put("api_key", apiKey);
		headers.put("ticket", ticket);
		XMLRequest request = new XMLRequest(REST_API_URL + CommonUtils.buildHttpHeader(headers), false);
		Element response = request.getXmlResponse(20000);

		Element status = response.getFirstChildByTagName("status");
		Element token = response.getFirstChildByTagName("auth_token");
		if (status == null) {
			throw new RuntimeException(
					"Cannot retrieve Box authorization token. No element 'status' in the reponse.");
		}
		String s = status.getText();
		if (!s.equals("get_ticket_ok")) {
			throw new RuntimeException(
					"Cannot retrieve Box authorization token. Unexpected value for status: "
							+ s);
		}
		if (authToken == null) {
			throw new RuntimeException(
					"Cannot retrieve Box authorization token. No element 'authToken' in the reponse.");
		}
		authToken = token.getText();
		Log.p("Authorization token retrieved " + token.getText(), Log.DEBUG);
	}

	private boolean isComplete() {
		return apiKey != null && authToken != null;
	}

}
