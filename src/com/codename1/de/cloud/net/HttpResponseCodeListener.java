package com.codename1.de.cloud.net;

import com.codename1.io.Log;
import com.codename1.io.NetworkEvent;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;

/**
 * 
 * @author Cosmin Zamfir
 *
 */
public class HttpResponseCodeListener implements ActionListener {

	private HttpRequest connection;
	
	
	public HttpResponseCodeListener(HttpRequest request) {
		this.connection = request;
	}

	public void actionPerformed(ActionEvent evt) {
		NetworkEvent ne = (NetworkEvent) evt;
        Log.p("Response code received for " + connection + ":" + ne.getResponseCode(), Log.DEBUG);
        connection.setErroResponseCode(ne.getResponseCode());
        connection.setMessage(ne.getMessage());
		connection.completed();
	}
}
