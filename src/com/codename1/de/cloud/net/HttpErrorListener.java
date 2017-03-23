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
public class HttpErrorListener implements ActionListener {

	HttpRequest connection;

	public HttpErrorListener(HttpRequest connection) {
		this.connection = connection;
	}

	public void actionPerformed(ActionEvent evt) {
		NetworkEvent ne = (NetworkEvent) evt;
        Log.p(HttpRequest.neCodes.get(new Integer(ne.getProgressType())) + "; " + ne.getMessage(), Log.DEBUG);
	}

}
