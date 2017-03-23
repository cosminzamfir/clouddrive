package com.codename1.de.cloud.drive.gui.containers;

import com.codename1.de.cloud.drive.EnumDriveType;
import com.codename1.de.cloud.drive.gui.actions.DriveSelectActionListener;
import com.codename1.de.cloud.drive.gui.actions.navigation.NavigationManager;
import com.codename1.de.cloud.drive.gui.components.HorizontalComponentsContainer;
import com.codename1.de.cloud.drive.gui.factory.ComponentsFactory;
import com.codename1.de.cloud.drive.gui.factory.ImageFactory;
import com.codename1.de.cloud.drive.storage.Drive;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Font;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BorderLayout;

import java.util.Vector;


/**
 * Select a cloud to display it as a {@link Drive}
 * 
 * @author Cosmin Zamfir
 * 
 */
public class DriveSelector extends NamedContainer {

    public static final String NAME = "driveSelector";
	private static DriveSelector instance;

	public static DriveSelector instance() {
		if (instance == null) {
			instance = new DriveSelector();
		}
		return instance;
	}

	DriveSelector() {
		setLayout(new BorderLayout());
		setPreferredSize(NavigationManager.getRoot().getPreferredSize());
		int drivesNo = ComponentsFactory.driveTypes.length;
		int rows = drivesNo / 2;
		int columns = drivesNo / rows;
		Vector driveButtons = new Vector();
		for (int i = 0; i < ComponentsFactory.driveTypes.length; i++) {

			final EnumDriveType type = ComponentsFactory.driveTypes[i];
			final String image = ComponentsFactory.driveIcons[i];
			final String shortName = ComponentsFactory.driveShortNames[i];

			final Button b = ComponentsFactory.getImageButton(ImageFactory.instance(ComponentsFactory.driveIcons[i],
					ImageFactory.MEDIUM));
			b.setText(shortName);
			b.setTextPosition(Label.BOTTOM);
			b.setName("selectDrive");
			b.addActionListener(new DriveSelectActionListener(type, image, this));
			b.getStyle().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_MEDIUM));
			driveButtons.addElement(b);
		}
		Container c = new HorizontalComponentsContainer(driveButtons, rows, columns);
		this.addComponent(BorderLayout.CENTER, c);

	}

    public String getContainerName() {
        return NAME;
	}
	
	public void repaint() {
		setPreferredSize(NavigationManager.getRoot().getPreferredSize());
		super.repaint();
	}
}
