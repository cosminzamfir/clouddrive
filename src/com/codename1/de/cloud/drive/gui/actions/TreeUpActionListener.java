package com.codename1.de.cloud.drive.gui.actions;

import com.codename1.de.cloud.drive.gui.containers.DriveManager;
import com.codename1.de.cloud.util.ThreadUtils;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;


/**
 * 
 * @author Cosmin Zamfir
 *
 */
public class TreeUpActionListener implements ActionListener, Runnable {

	private DriveManager driveManager;
	
	public TreeUpActionListener(DriveManager drive) {
		super();
		this.driveManager = drive;
	}

	public void actionPerformed(ActionEvent evt) {
		ThreadUtils.runBackgroundTask(this);
	}
	
	public void run() {
		driveManager.up();
	}
}
