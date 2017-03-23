package com.codename1.de.cloud.drive.gui.components;

import com.codename1.de.cloud.drive.DriveFactory;
import com.codename1.de.cloud.drive.gui.actions.BaseActionListener;
import com.codename1.de.cloud.drive.gui.actions.navigation.NavigationManager;
import com.codename1.de.cloud.drive.gui.containers.ContainerFactory;
import com.codename1.de.cloud.drive.gui.containers.DriveManager;
import com.codename1.de.cloud.drive.gui.model.DefaultListModel;
import com.codename1.de.cloud.drive.storage.Drive;
import com.codename1.de.cloud.drive.storage.StorageItem;
import com.codename1.ui.List;
import com.codename1.ui.events.ActionEvent;

/**
 * A {@link List} extension which displays all available {@link Drive}s
 * @author Cosmin Zamfir
 *
 */
public class DrivesList extends List {

    public DrivesList() {
        super(new DefaultListModel(DriveFactory.getAllDrives()));
        addActionListener(new BaseActionListener() {

            protected void execute(ActionEvent evt) {
                List list = (List) evt.getSource();
                Drive drive = (Drive) list.getSelectedItem();

                DriveManager container = ContainerFactory.driveManager(drive);
                NavigationManager.navigateTo(container);
            }
        });
    }


    public void longPointerPress(int x, int y) {
        super.longPointerPress(x, y);
        //super implementation sets the selected model index of the pressed item
        StorageItem item = (StorageItem) getModel().getItemAt(getModel().getSelectedIndex());
        //do smth here
    }
}
