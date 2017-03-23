package com.codename1.de.cloud.drive.gui.containers;

import com.codename1.de.cloud.drive.gui.actions.navigation.NavigationManager;
import com.codename1.de.cloud.drive.gui.components.DrivesList;
import com.codename1.de.cloud.drive.gui.renderers.DrivesListCellRenderer;
import com.codename1.ui.layouts.BorderLayout;

public class DriveExplorer extends NamedContainer {

    public static final String NAME = "DriveExplorer";

    public String getContainerName() {
        return NAME;
    }

    public DriveExplorer() {
        setLayout(new BorderLayout());
        setPreferredW(NavigationManager.getRoot().getPreferredW());
        build();
    }

    private void build() {
        //DrivesTable drives = new DrivesTable();
        DrivesList drives = new DrivesList();
        drives.setRenderer(new DrivesListCellRenderer());
        addComponent(BorderLayout.CENTER, drives);
    }

}
