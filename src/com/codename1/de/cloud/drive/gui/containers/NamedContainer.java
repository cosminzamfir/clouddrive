package com.codename1.de.cloud.drive.gui.containers;

import com.codename1.de.cloud.drive.gui.actions.navigation.NavigationManager;
import com.codename1.ui.Container;
import com.codename1.ui.layouts.Layout;

/**
 * Any {@link Container} which needs to be the target of the {@link NavigationManager}, must extends this class
 * @author zamfcos
 *
 */
public abstract class NamedContainer extends Container {

    public NamedContainer() {
        super();

    }

    public NamedContainer(Layout layout) {
        super(layout);

    }

    /**
     * The container identifier in the navigation manager
     * @return
     */
    public abstract String getContainerName();
}
