package com.codename1.de.cloud.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.codename1.io.ConnectionRequest;
import com.codename1.io.NetworkManager;
import com.codename1.io.Util;

/**
 * A {@link ConnectionRequest} extension used to download large files. 
 * Does not store the response in the memory bytes array; instead it streams the response to the specified {@link OutputStream}
 * @author Cosmin Zamfir
 *
 */
public class DownloadStreamRequest extends ConnectionRequest {

	/**
	 * The {@link OutputStream} to write the response to 
	 */
	private OutputStream os;
	
	public DownloadStreamRequest(OutputStream os) {
		this.os = os;
	}
	
	protected void readResponse(InputStream is) throws IOException {
		Util.copy(is, os, 9012);
	}
	
	public void runSync() {
		NetworkManager.getInstance().addToQueueAndWait(this);
	}
	
}
