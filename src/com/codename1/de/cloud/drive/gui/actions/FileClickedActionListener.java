package com.codename1.de.cloud.drive.gui.actions;

import com.codename1.de.cloud.drive.gui.containers.DriveManager;
import com.codename1.de.cloud.drive.storage.Directory;
import com.codename1.de.cloud.drive.storage.File;
import com.codename1.de.cloud.drive.storage.StorageItem;
import com.codename1.de.cloud.util.ThreadUtils;
import com.codename1.io.Log;
import com.codename1.ui.Display;
import com.codename1.ui.List;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;


/**
 * 
 * @author Cosmin Zamfir
 *
 */
public class FileClickedActionListener implements ActionListener, Runnable {

    private DriveManager driveManager;
    private ActionEvent evt;

    public FileClickedActionListener(DriveManager driveManager) {
        super();
        this.driveManager= driveManager;
    }

    public void actionPerformed(ActionEvent evt) {
        this.evt = evt;
        ThreadUtils.runBackgroundTask(this);
    }

	public void run() {
    	List list = (List) evt.getSource();
        StorageItem item = (StorageItem) list.getSelectedItem();
        Log.p("List item clicked for " + item + " which is a " + (item.isDirectory() ? "directory" : " file"),
                Log.DEBUG);
        list.getModel().setSelectedIndex(0);
        if (item.isDirectory()) {
            Directory dir = (Directory) item;
            driveManager.setCurrentDirectory(dir);
        } else {
            Log.p("Opening file " + item.name());
        	Display.getInstance().execute(((File) item).uri());
        }
	}
}
