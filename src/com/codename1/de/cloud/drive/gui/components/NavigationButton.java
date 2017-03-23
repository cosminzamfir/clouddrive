package com.codename1.de.cloud.drive.gui.components;

import com.codename1.de.cloud.drive.gui.actions.NavigationActionListener;
import com.codename1.de.cloud.drive.gui.containers.DriveManager;
import com.codename1.de.cloud.drive.gui.containers.NamedContainer;
import com.codename1.ui.Button;
import com.codename1.ui.plaf.Border;


/**
 * A {@link Button} which does not perform any action, except navigating to
 * another container
 * <p>
 * The extensions must only provide the name, parent (top level) container and
 * layout.
 * 
 * @author Cosmin Zamfir
 * 
 */
public class NavigationButton extends Button {

	private String name;
    private NamedContainer parent;

    public NavigationButton(NamedContainer parent, String name) {
        this.name = name;
		this.parent = parent;
        getStyle().setBorder(Border.createEmpty());
        this.addActionListener(new NavigationActionListener(this, this.parent));
	}

	public String getName() {
		return name;
	}

    /** 
     * Hook to be invoked bofore navigation
     */
    public void beforeNavigation() {
    }

    /**
     * Hook to be invoked after navigation
     * <p>
     * Example: a NavigationButton to the DriveManager screen must set the {@link DriveManager#setDrive}
     * depending on which drive must be displayed 
     */
    public void afterNavigation() {
    }

}
