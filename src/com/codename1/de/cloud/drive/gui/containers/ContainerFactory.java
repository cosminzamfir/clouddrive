package com.codename1.de.cloud.drive.gui.containers;

import com.codename1.de.cloud.drive.DriveFactory;
import com.codename1.de.cloud.drive.gui.actions.navigation.NavigationManager;
import com.codename1.de.cloud.drive.storage.Drive;


/**
 * Serve each container type as a singleton
 * @author Cosmin Zamfir
 *
 */
public class ContainerFactory {

    public static final String DRIVE_MANAGER = "DriveManager";
    public static final String SRIVE_SELECTOR = "DriveSelector";
    public static final String DRIVE_EXPLORER = "DriveExplorer";
    public static final String STORAGE_MANAGER = "StorageManager";
    public static final String HELP_SCREEN = "HelpScreen";
    public static final String START_SCREEN = "StartScreen";

    private static DriveManager driveManager;
    private static DriveSelector driveSelector;
    private static StartScreen startScreen;
    private static DriveExplorer driveExplorer;

    public static DriveExplorer driveExplorer() {
        if (driveExplorer == null) {
            driveExplorer = new DriveExplorer();
        }
        return driveExplorer;
    }

    public static DriveManager driveManager(Drive drive) {
        if (driveManager == null) {
            driveManager = new DriveManager(drive);
        } else {
            driveManager.setDrive(drive);
        }
        return driveManager;
    }

    public static DriveSelector driveSelector() {
        if (driveSelector == null) {
            driveSelector = new DriveSelector();
        }
        return driveSelector;
    }
    
    public static StartScreen startScreen() {
        if (startScreen == null) {
            startScreen = new StartScreen();
        }
        return startScreen;
    }

    /**
     * good enough as long as we don't have too many containers
     * <p> 
     * Mainly used by the {@link NavigationManager}
     * @param name
     * @return
     */
    public static NamedContainer getContainer(String name) {
        if (name.equals(DriveManager.NAME)) {
            return driveManager(DriveFactory.getLFSDriveInstance());
        }
        if (name.equals(DriveExplorer.NAME)) {
            return driveExplorer();
        }
        if (name.equals(STORAGE_MANAGER)) {
            return null;
        }
        if (name.equals(StartScreen.NAME)) {
            return startScreen();
        }
        if (name.equals(HELP_SCREEN)) {
            return null;
        }

        throw new RuntimeException("No container named " + name + " available");
    }
}
