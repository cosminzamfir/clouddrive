package com.codename1.de.cloud.drive.gui.actions;

import com.codename1.de.cloud.drive.gui.actions.navigation.NavigationManager;
import com.codename1.de.cloud.drive.gui.components.NavigationButton;
import com.codename1.de.cloud.drive.gui.containers.NamedContainer;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;


/**
 * 
 * @author Cosmin Zamfir
 *
 */
public class NavigationActionListener implements ActionListener {
    private NamedContainer parent;
    private NavigationButton source;

    public NavigationActionListener(NavigationButton source, NamedContainer parent) {
		this.parent = parent;
        this.source = source;
	}

	public void actionPerformed(ActionEvent evt) {
        source.beforeNavigation();
        NavigationManager.navigate(parent, evt.getComponent());
        source.afterNavigation();
	}
}
