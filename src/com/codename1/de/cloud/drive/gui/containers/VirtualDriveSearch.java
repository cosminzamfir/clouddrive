package com.codename1.de.cloud.drive.gui.containers;

import com.codename1.de.cloud.drive.gui.factory.ComponentsFactory;
import com.codename1.de.cloud.drive.gui.factory.ImageFactory;

/**
 * Search by name and all defined categories 
 * @author zamfcos
 *
 */
public class VirtualDriveSearch extends StandardDialog {

    public VirtualDriveSearch() {
        super(ImageFactory.instance(ImageFactory.search, ImageFactory.MEDIUM), "Search for file",
                com.codename1.de.cloud.util.Colors.SLATE_BLUE);
        layout();
    }

    private void layout() {
    }

}
