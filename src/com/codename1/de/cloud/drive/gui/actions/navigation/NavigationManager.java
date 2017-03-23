package com.codename1.de.cloud.drive.gui.actions.navigation;

import com.codename1.de.cloud.drive.gui.containers.ContainerFactory;
import com.codename1.de.cloud.drive.gui.containers.DriveExplorer;
import com.codename1.de.cloud.drive.gui.containers.DriveManager;
import com.codename1.de.cloud.drive.gui.containers.DriveSelector;
import com.codename1.de.cloud.drive.gui.containers.NamedContainer;
import com.codename1.de.cloud.drive.gui.containers.StartScreen;
import com.codename1.de.cloud.drive.gui.factory.ComponentsFactory;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Form;
import com.codename1.ui.layouts.BorderLayout;

import java.util.Hashtable;

/**
 * This class manage the navigation between screens.
 * <p>
 * Each app container must be uniquely identifiable by it's name, see {@link Container#getName()}
 * The buttons which trigger the navigation must provide the name as well
 * <p>
 * It is allowed for multiple buttons in the same container to share the same name 
 * if they should trigger the same navigation rule
 * @author Cosmin Zamfir
 *
 */
public class NavigationManager {

    /**
     * This is the unique root form of the application.
     * On this form all first level containers are laid out.
     */
    private static Form root;
    
    /**
     * Very basic navigation rule set
     * <p>
     * Map source container name + ":" + trigger component name to target container name
     */
    private static Hashtable rules = new Hashtable();
    
    static {
        rules.put(DriveSelector.NAME + ":selectDrive", DriveManager.NAME);
        rules.put(DriveManager.NAME + ":startScreen", StartScreen.NAME);
        rules.put(StartScreen.NAME + ":virtualDrive", DriveManager.NAME);
        rules.put(StartScreen.NAME + ":driveExplorer", DriveExplorer.NAME);

    }
    
    public static void navigate(NamedContainer source, Component trigger) {
        String key = source.getContainerName() + ":" + trigger.getName();
        String targetContainer = (String) rules.get(key);
        //sanity check
        if (targetContainer == null) {
            throw new RuntimeException("No navigation rule defined for " + source.getContainerName() + ":"
                    + trigger.getName());
        }
        if (!root.contains(source)) {
            throw new RuntimeException("Container " + source.getContainerName() + " is not contained in the root form");
        }
        Container target = ContainerFactory.getContainer(targetContainer);
        root.removeComponent(source);
        root.addComponent(BorderLayout.CENTER, target);
        root.flushReplace();
        root.revalidate();
    }
    
    public static void navigateTo(NamedContainer target) {
        root.removeAll();
        root.addComponent(BorderLayout.CENTER, target);
        root.flushReplace();
        root.revalidate();
    }

    public static void setRoot(Form form) {
    	root = form;
        ComponentsFactory.setDefaults(form);
    }
    
    public static Form getRoot() {
		return root;
	}
}
