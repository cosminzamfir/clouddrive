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
public class TreeRootActionListener implements ActionListener, Runnable {

    private DriveManager driveManager;

    public TreeRootActionListener(DriveManager driveManager) {
        super();
        this.driveManager = driveManager;
    }

    public void actionPerformed(ActionEvent evt) {
        ThreadUtils.runBackgroundTask(this);
    }

    public void run() {
        driveManager.setCurrentDirectory(null);
    }

}
