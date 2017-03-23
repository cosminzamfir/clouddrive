package com.codename1.de.cloud.drive;

import com.codename1.de.cloud.drive.gui.CloudView;
import com.codename1.io.Log;


/**
 * 
 * @author Cosmin Zamfir
 *
 */
public class CloudSync {

	public void init(Object context) {
		System.out.println("init");
	}

	public void start() {
		System.out.println("started");
        Log.setLevel(Log.INFO);
		new CloudView().init();
	}

	public void stop() {
		System.out.println("stopped");
	}

	public void destroy() {
		System.out.println("destroyed");

	}

}
