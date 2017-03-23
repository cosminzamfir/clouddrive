package com.codename1.de.cloud.drive.storage.dropbox.auth;

/**
 * A key-secret pair used for the oauth process.
 * <p>
 * See {@link oauth.net}
 * @author Cosmin Zamfir
 *
 */
public class TokenPair {

	private String key;
	private String secret;
	public TokenPair(String key, String secret) {
		super();
		this.key = key;
		this.secret = secret;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getSecret() {
		return secret;
	}
	public void setSecret(String secret) {
		this.secret = secret;
	}
	
	
}
