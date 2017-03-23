package com.codename1.de.cloud.drive.gui.containers;

import com.codename1.ui.Container;

/**
 * 
 * @author Cosmin Zamfir
 *
 */
public class ScrollableContainer extends Container {

	public boolean isScrollableY() {
		setAlwaysTensile(false);
		return true;
	}
}
