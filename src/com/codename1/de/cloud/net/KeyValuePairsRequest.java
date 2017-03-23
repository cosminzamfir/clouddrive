package com.codename1.de.cloud.net;

import com.codename1.de.cloud.util.CommonUtils;
import com.codename1.io.Log;


import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

/**
 * 
 * @author Cosmin Zamfir
 *
 */
public class KeyValuePairsRequest extends HttpRequest {

	public KeyValuePairsRequest(String url, boolean post) {
		super(url, post);
	}

	/**
	 * oauth_token_secret=95grkd9na7hm&oauth_token=ccl4li5n1q9b&uid=100
	 * 
	 * @param timeout
	 * @return
	 */
	public Hashtable getResponse(long timeout) {
		byte[] response = run(timeout);
		if (response == null) {
			return null;
		}
		String s = new String(response);
		Log.p("Key/value response received: " + new String(response));
		Vector pairs = CommonUtils.split(s, '&');
		Hashtable res = new Hashtable();
		Enumeration e = pairs.elements();
		while (e.hasMoreElements()) {
			String pair = (String) e.nextElement();
			res.put(pair.substring(0, pair.indexOf('=')), pair.substring(pair.indexOf('=') + 1));
		}
		return res;
	}
}
