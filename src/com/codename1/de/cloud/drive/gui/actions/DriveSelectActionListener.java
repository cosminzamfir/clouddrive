package com.codename1.de.cloud.drive.gui.actions;

import com.codename1.de.cloud.drive.DriveFactory;
import com.codename1.de.cloud.drive.EnumDriveType;
import com.codename1.de.cloud.drive.gui.actions.navigation.NavigationManager;
import com.codename1.de.cloud.drive.gui.containers.ContainerFactory;
import com.codename1.de.cloud.drive.gui.containers.DriveSelector;
import com.codename1.de.cloud.drive.storage.Drive;
import com.codename1.de.cloud.util.ThreadUtils;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;


/**
 * Change the current {@link Drive}
 * 
 * @author Cosmin Zamfir
 * 
 */
public class DriveSelectActionListener implements ActionListener, Runnable {

    private EnumDriveType driveType;
    private DriveSelector selector;
    private ActionEvent evt;

    public DriveSelectActionListener(EnumDriveType driveType, String driveIcon, DriveSelector selector) {
        super();
        this.driveType = driveType;
        this.selector = selector;
    }

    public void run() {
        ContainerFactory.driveManager(DriveFactory.getInstance(driveType));
        NavigationManager.navigate(selector, evt.getComponent());
    }

    public void actionPerformed(ActionEvent evt) {
        this.evt = evt;
        ThreadUtils.runBackgroundTask(this);
    }

}
