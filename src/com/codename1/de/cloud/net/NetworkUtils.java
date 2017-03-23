package com.codename1.de.cloud.net;

import com.codename1.de.cloud.drive.exception.HttpErrorCodeException;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.Log;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;


import java.util.Enumeration;
import java.util.Hashtable;

/**
 * 
 * @author Cosmin Zamfir
 *
 */
public class NetworkUtils {

	private static Hashtable neCodes = new Hashtable();

	static {
		neCodes.put(new Integer(NetworkEvent.PROGRESS_TYPE_INITIALIZING),
				"Initializing");
		neCodes.put(new Integer(NetworkEvent.PROGRESS_TYPE_INPUT),
				"Processing input");
		neCodes.put(new Integer(NetworkEvent.PROGRESS_TYPE_OUTPUT),
				"Processing output");
		neCodes.put(new Integer(NetworkEvent.PROGRESS_TYPE_COMPLETED),
				"Request completed");
	}

	public static void testConnect() {
		ConnectionRequest request = new ConnectionRequest();
		request.setUrl("http://intranet2.deutsche-boerse.de/");
		request.setPost(false);
		NetworkManager nm = NetworkManager.getInstance();
		nm.addToQueue(request);
		nm.addProgressListener(new MyActionListener());
		nm.start();
	}

	private static class MyActionListener implements ActionListener {

		public void actionPerformed(ActionEvent evt) {
			NetworkEvent ne = (NetworkEvent) evt;
			System.out.println("Connection request: "
					+ neCodes.get(new Integer(ne.getProgressType())));
			System.out.println("Connection request progress: "
					+ ne.getProgressPercentage());
			if (ne.getProgressType() == NetworkEvent.PROGRESS_TYPE_COMPLETED) {
				if (((ConnectionRequest) ne.getSource()).getResponseData() != null) {
					System.out.println(new String(((ConnectionRequest) ne
							.getSource()).getResponseData()));
				}
			}
		}

	}

	/**
	 * TODO workaround for Android - each second {@link HttpRequest} fails with
	 * error code -1. It fails fast, i.e. after several milliseconds. If request
	 * fails with {@link HttpErrorCodeException#getErrorCode()}=-1, retry
	 * 
	 * @param request
	 * @return
	 */
	public static Hashtable getJsonResponse(JSONRequest request) {
		int retries = 4;
		int index = 0;
		Hashtable h = new Hashtable();
		long t1 = 0;
		long t2;
		Hashtable res;
		while (true) {
			try {
				t1 = System.currentTimeMillis();
				res = request.getJsonResponse(5000);
				t2 = System.currentTimeMillis();
				h.put("Response code : 200 ", new Integer((int) (t2 - t1)));
				//Dialog.show("HttpRequest statistics", getText(h), "OK", null);
				return res;

			} catch (HttpErrorCodeException e) {
				t2 = System.currentTimeMillis();
				if (e.getErrorCode() <= 0) {
					if (index++ < retries) {
						Log.p("Error code " + e.getErrorCode()
								+ "received for " + request
								+ "Resending request.", Log.ERROR);
					} else {
						throw e;
					}
				}
				h.put("Error code: " + e.getErrorCode(), new Integer(
						(int) (t2 - t1)));
			}
		}
	}

	private static String getText(Hashtable h) {
		StringBuffer buf = new StringBuffer();
		Enumeration e = h.keys();
		while (e.hasMoreElements()) {
			String key = (String) e.nextElement();
			buf.append(key + " - Ellapse time in ms: " + h.get(key));
			buf.append("\n");
		}
		return buf.toString();
	}

	/**
	 * TODO workaround for Android - each second {@link HttpRequest} fails with
	 * error code -1. It fails fast, i.e. after several milliseconds. If request
	 * fails with {@link HttpErrorCodeException#getErrorCode()}=-1, retry
	 * 
	 * @param request
	 * @return
	 */
	public static byte[] sendReques(HttpRequest request) {
		int retries = 4;
		int index = 0;
		while (true) {
			try {
				return request.run(5000);
			} catch (HttpErrorCodeException e) {
				if (e.getErrorCode() <= 0) {
					if (index++ < retries) {
						Log.p("Error code received for " + request
								+ "Resending request.", Log.DEBUG);
					} else {
						throw e;
					}
				}
			}
		}
	}

}
