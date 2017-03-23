package com.codename1.de.cloud.drive.gui.containers;

import com.codename1.de.cloud.drive.DriveFactory;
import com.codename1.de.cloud.drive.EnumDriveType;
import com.codename1.de.cloud.drive.gui.actions.navigation.NavigationManager;
import com.codename1.de.cloud.drive.gui.components.ComponentsTable;
import com.codename1.de.cloud.drive.gui.components.NavigationButton;
import com.codename1.de.cloud.drive.gui.factory.ImageFactory;
import com.codename1.ui.layouts.BorderLayout;

import java.util.Vector;

/**
 * The application entry screen;
 * 
 * @author zamfcos
 * 
 */
public class StartScreen extends NamedContainer {

    public static final String NAME = "startScreen";
    private NavigationButton virtualDrive;
    private NavigationButton storageProviders;
    private NavigationButton storageManagement;
    private NavigationButton help;
    private String virtualDriveText;
    private String storageProvidersText;
    private String storageManagementText;
    private String helpText;
    private String virtualDriveIcon;
    private String storageProvidersIcon;
    private String storageManagementIcon;
    private String helpIcon;

    StartScreen() {
        setLayout(new BorderLayout());
        setPreferredSize(NavigationManager.getRoot().getPreferredSize());
        build();
    }

    private void build() {
        Vector buttons = new Vector();
        Vector texts = new Vector();
        Vector icons = new Vector();
        virtualDrive = new NavigationButton(this, "virtualDrive") {
            public void afterNavigation() {
                ContainerFactory.driveManager(DriveFactory.getInstance(EnumDriveType.VIRTUAL));
            }
        };
        virtualDriveText = "My Virtual Drive";
        virtualDriveIcon = ImageFactory.virtualDrive;
        buttons.addElement(virtualDrive);
        texts.addElement(virtualDriveText);
        icons.addElement(virtualDriveIcon);

        storageProviders = new NavigationButton(this, "driveExplorer");
        storageProvidersText = "My Clouds";
        storageProvidersIcon = "icons/storage-spaces.png";
        buttons.addElement(storageProviders);
        texts.addElement(storageProvidersText);
        icons.addElement(storageProvidersIcon);

        storageManagement = new NavigationButton(this, "storageManager");
        storageManagementText = "Cloud Management and Statistics";
        storageManagementIcon = "icons/storage-manager.png";
        buttons.addElement(storageManagement);
        texts.addElement(storageManagementText);
        icons.addElement(storageManagementIcon);

        help = new NavigationButton(this, "help");
        helpText = "Help/Tutorial";
        helpIcon = "icons/help.png";
        buttons.addElement(help);
        texts.addElement(helpText);
        icons.addElement(helpIcon);

        ComponentsTable table = new ComponentsTable(buttons, icons, texts, 2);
        this.addComponent(BorderLayout.CENTER, table);

    }

    public void repaint() {
        setPreferredSize(NavigationManager.getRoot().getPreferredSize());
        super.repaint();
    }

    public String getContainerName() {
        return NAME;
    }
}
