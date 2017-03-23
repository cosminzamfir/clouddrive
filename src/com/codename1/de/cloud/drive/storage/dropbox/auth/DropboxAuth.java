package com.codename1.de.cloud.drive.storage.dropbox.auth;

/**
 * 
 * @author Cosmin Zamfir
 *
 */
import com.codename1.de.cloud.net.HttpRequest;
import com.codename1.de.cloud.net.KeyValuePairsRequest;
import com.codename1.de.cloud.util.CommonUtils;
import com.codename1.de.cloud.util.IOUtils;
import com.codename1.io.FileSystemStorage;


import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

/**
 * Implementation of the oauth protocol for Dropbox. The authorization steps
 * are:
 * <ol>
 * <li>Initial application setup: request application tokens from
 * www.dropbox.com/developer/apps - assumed already done
 * <li>Send a request to {@link #REQUEST_TOKEN_URL}, signed with the application
 * tokens and receive an authorization token pair
 * <li>Direct the user to the {@link #AUTHORIZE_URL}+authToken.key as GET
 * parameter. ON this page, the user must grant access rights to the app for his
 * dropbox account
 * <li>Send a request to {@link #ACCESS_TOKEN_URL}, signed with applicationToken
 * + authorizationTokens and receive the access token pair
 * <li>Store the above access token pair to sign all subsequent API calls to
 * dropbox for this user account
 * <ol>
 * 
 * @author Cosmin Zamfir
 * 
 */

public class DropboxAuth {

	/**
	 * URL providing the authorization tokens
	 */
	private static final String REQUEST_TOKEN_URL = "https://api.dropbox.com/1/oauth/request_token";
	/**
	 * URL for the dropbox authorization interactive web page
	 */
	private static final String AUTHORIZE_URL = "https://www.dropbox.com:443/1/oauth/authorize";
	/**
	 * URL providing the access tokens
	 */
	private static final String ACCESS_TOKEN_URL = "https://api.dropbox.com:443/1/oauth/access_token";

	private static DropboxAuth instance;

	private TokenPair appTokens;
	private TokenPair accessTokens;

	public static synchronized DropboxAuth instance() {
		if (instance == null) {
			instance = new DropboxAuth();
		}
		return instance;
	}

	/**
	 * Sign the {@link HttpRequest} wrapping an api call with the application
	 * and access tokens
	 * 
	 * @throws RuntimeException
	 *             if {@link #isComplete()} is <code>false</code>
	 */
	public void sign(HttpRequest request) {
		if (!isComplete()) {
			throw new RuntimeException(
					"Cannot sign "
							+ request
							+ ". At least one of application tokens or access token is missing");
		}
		sign(request, appTokens, accessTokens);
	}

	/**
	 * Get the singleton instance of this class. Complete the authentication
	 * process if {@link #isComplete()} is <code>false</code>
	 * 
	 * @throws RuntimeException
	 *             if any invalid or missing application config file or any
	 *             error occurs during the authentication process
	 */
	private DropboxAuth() {
		initTokens();
		if (!isComplete()) {
			TokenPair authTokens = retrieveAuthorizationTokens();
			accessTokens = retrieveAccesTokens(authTokens);
			save(accessTokens);
		}
	}

	/**
	 * Store the {@link #accessTokens} into the app config file (append the
	 * access tokens at the end of the dropbox configuration line)
	 * 
	 * @param accessToken
	 *            the access tokens received by the app to access the user
	 *            dropbox account
	 */
	private void save(TokenPair accessTokens) {
		try {
			Vector lines = IOUtils
					.parse(new InputStreamReader(FileSystemStorage
							.getInstance().openInputStream("app.conf")));

			for (int i = 0; i < lines.size(); i++) {
				String line = (String) lines.elementAt(i);
				if (line.startsWith("dropbox&")) {
					line = line + "accessToken=\"" + accessTokens.getKey()
							+ "\"" + "&" + "accessSecret=\""
							+ accessTokens.getSecret() + "\"";
					lines.setElementAt(line, i);
				}
			}
			IOUtils.write(lines, "app.conf");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Authorization is complete when both {@link #appTokens} and
	 * {@link #accessTokens} are set
	 * 
	 * @return
	 */
	private boolean isComplete() {
		// TODO Auto-generated method stub
		return appTokens != null && accessTokens != null;
	}

	/**
	 * Initialize tokens from the application configuration file
	 */
	private void initTokens() {
		// try {
		// Reader r = new InputStreamReader(FileSystemStorage.getInstance()
		// .openInputStream("app.conf"));
		// Vector lines = IOUtils.parse(r);
		// for (int i = 0; i < lines.size(); i++) {
		// String line = (String) lines.elementAt(i);
		// if (line.startsWith("dropbox")) {
		// setupTokens(line);
		// return;
		// }
		// }
		// throw new RuntimeException("No dropbox entry found in app.conf.");
		// } catch (IOException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		//dropbox&appToken=d15asees4r8bw3i&appSecret=lkcyv9yf7aybs04&accessToken=bc1ubkw57v9541a&accessSecret=0qmwmko91o9gr0u
	    this.appTokens = new TokenPair("d15asees4r8bw3i", "lkcyv9yf7aybs04");
	    this.accessTokens = new TokenPair("bc1ubkw57v9541a", "0qmwmko91o9gr0u");
	    
	}

	/**
	 * Parse the app and access tokens
	 * <p>
	 * Input format: dropbox&appToken=d15asees4r8bw3i&appSecret=lkcyv9yf7aybs04
	 * 
	 * @param line
	 */
	private void setupTokens(String line) {
		line = line.substring("dropbox&".length());
		Vector tokens = CommonUtils.split(line, '&');
		if (tokens.isEmpty()) {
			throw new RuntimeException("Invalid configuration line: " + line);
		}
		Enumeration e = tokens.elements();
		while (e.hasMoreElements()) {
			String token = (String) e.nextElement();
			Vector keyValue = CommonUtils.split(token, '=');
			if (keyValue.size() != 2) {
				throw new RuntimeException(
						"Invalid format for keyValue in app.conf: " + token
								+ "Expected format is 'key=value'");
			}
			String key = (String) keyValue.elementAt(0);
			String value = (String) keyValue.elementAt(1);
			processToken(key, value);

		}
	}

	private void processToken(String key, String value) {
		if (key.equals("appToken")) {
			if (appTokens == null) {
				appTokens = new TokenPair(null, null);
			}
			appTokens.setKey(value);
		} else if (key.equals("appSecret")) {
			if (appTokens == null) {
				appTokens = new TokenPair(null, null);
			}
			appTokens.setSecret(value);
		}
		if (key.equals("accessToken")) {
			if (accessTokens == null) {
				accessTokens = new TokenPair(null, null);
			}
			accessTokens.setKey(value);
		} else if (key.equals("accessSecret")) {
			if (accessTokens == null) {
				accessTokens = new TokenPair(null, null);
			}
			accessTokens.setSecret(value);
		}

	}

	/**
	 * The dropbox {@link TokenPair} assigned to this application
	 * 
	 * @return
	 */
	private TokenPair getApplicationTokens() {
		return appTokens;
	}

	/**
	 * Step 1 of the authorization process.
	 * <ol>
	 * <li>Go to https://api.dropbox.com:443/1/oauth/request_token
	 * <li>Sign the request with the application tokens
	 * <li>Retrieve the authorization token from the response in format:
	 * {oauth_token=sscca3qdjxv14za, oauth_token_secret=7y3yrbynx6wp5ff}
	 * </ol>
	 * 
	 * @return the authorization key and secret
	 */
	private TokenPair retrieveAuthorizationTokens() {
		TokenPair applicationTokens = getApplicationTokens();
		KeyValuePairsRequest conn = new KeyValuePairsRequest(REQUEST_TOKEN_URL,
				false);
		sign(conn, applicationTokens);
		Hashtable response = conn.getResponse(5000);
		String authToken = (String) response.get("oauth_token");
		String authTokenSecret = (String) response.get("oauth_token_secret");
		if (authToken == null || authTokenSecret == null) {
			throw new RuntimeException(
					"Unexpected response from https://api.dropbox.com:443/1/oauth/request_token");
		}
		System.out.println("Please visit the page: "
				+ buildAuthorizationUrl(new TokenPair(authToken,
						authTokenSecret)));
		return new TokenPair(authToken, authTokenSecret);
	}

	/**
	 * The last step of oauth process The application must sent a request,
	 * signed with the application tokens and the specified auth tokens and
	 * receive the access {@link TokenPair}, which will be used by the app to
	 * sign any subsequent API request
	 * <p>
	 * This step will fail if the user did not previously grant access for the
	 * application to his dropbox account (see
	 * {@link #buildAuthorizationUrl(TokenPair)}
	 * 
	 * @param authTokens
	 * @return
	 */
	public TokenPair retrieveAccesTokens(TokenPair authTokens) {
		TokenPair requestTokens = getApplicationTokens();
		KeyValuePairsRequest conn = new KeyValuePairsRequest(ACCESS_TOKEN_URL,
				false);
		sign(conn, requestTokens, authTokens);
		Hashtable response = conn.getResponse(5000);
		String authToken = (String) response.get("oauth_token");
		String authTokenSecret = (String) response.get("oauth_token_secret");
		if (authToken == null || authTokenSecret == null) {
			throw new RuntimeException(
					"Unexpected response from https://api.dropbox.com:443/1/oauth/request_token");
		}
		return new TokenPair(authToken, authTokenSecret);
	}

	/**
	 * Build the url of form
	 * https://www.dropbox.com:443/1/oauth/authorize?oauth_token=sscca3qdjxv14za
	 * This url requires from a dropbox account owner to allow application
	 * access to his account
	 * 
	 * @param authTokens
	 * @return
	 */
	private String buildAuthorizationUrl(TokenPair authTokens) {
		return AUTHORIZE_URL + "?oauth_token=" + authTokens.getKey();
	}

	private void sign(HttpRequest conn, TokenPair applicationTokens) {
		conn.addRequestHeader("Authorization",
				getApplicationAuth(applicationTokens));

	}

	private void sign(HttpRequest conn, TokenPair requestTokens,
			TokenPair authTokens) {
		conn.addRequestHeader("Authorization",
				buildAppAuth(requestTokens, authTokens));
	}

	/**
	 * Add the following header to the underlying request: Authorization: OAuth
	 * oauth_version="1.0", oauth_signature_method="PLAINTEXT",
	 * oauth_consumer_key="<app key>", oauth_signature="<appSecret>"
	 * 
	 * @param requestTokens
	 * @param authTokens
	 * @return
	 */
	private String getApplicationAuth(TokenPair requestTokens) {
		Hashtable h = buildDefaultAuthParams();
		h.put("oauth_consumer_key", requestTokens.getKey());
		h.put("oauth_signature", requestTokens.getSecret() + "&");
		return buildHeader(h);
	}

	/**
	 * Add the following header to the underlying request: Authorization: OAuth
	 * oauth_version="1.0", oauth_signature_method="PLAINTEXT",
	 * oauth_consumer_key="<app key>", oauth_token="authTokens.key",
	 * oauth_signature="<appSecret>&<authsecret>"
	 * 
	 * @param requestTokens
	 * @param authTokens
	 * @return
	 */
	private String buildAppAuth(TokenPair requestTokens, TokenPair authTokens) {
		Hashtable h = buildDefaultAuthParams();
		h.put("oauth_consumer_key", requestTokens.getKey());
		h.put("oauth_token", authTokens.getKey());
		h.put("oauth_signature",
				requestTokens.getSecret() + "&" + authTokens.getSecret());

		return buildHeader(h);
	}

	/**
	 * Builds the oauth header
	 * 
	 * @param h
	 *            the key-value oaut parameter pairs
	 * @return the oauth header; sample OAuth
	 *         oauth_version="1.0",oauth_signature_method
	 *         ="PLAINTEXT",oauth_consumer_key
	 *         ="d15asees4r8bw3i",oauth_signature="lkcyv9yf7aybs04&"
	 */
	private String buildHeader(Hashtable h) {
		//
		StringBuffer buf = new StringBuffer();
		Enumeration e = h.keys();
		while (e.hasMoreElements()) {
			Object key = e.nextElement();
			buf.append(key + "=\"" + h.get(key) + "\"");
			if (e.hasMoreElements()) {
				buf.append(", ");
			}
		}
		return buf.toString();
	}

	/**
	 * @return {@link Hashtable} with the default standard oauth params
	 */
	private Hashtable buildDefaultAuthParams() {
		Hashtable res = new Hashtable();
		res.put("OAuth oauth_version", "1.0");
		res.put("oauth_signature_method", "PLAINTEXT");
		return res;
	}

}
