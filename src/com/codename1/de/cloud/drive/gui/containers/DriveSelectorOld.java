package com.codename1.de.cloud.drive.gui.containers;

import com.codename1.de.cloud.drive.EnumDriveType;
import com.codename1.de.cloud.drive.gui.factory.ComponentsFactory;
import com.codename1.de.cloud.drive.gui.factory.ImageFactory;
import com.codename1.de.cloud.drive.storage.Drive;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.Label;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.GridLayout;


/**
 * Select a cloud to display it as a {@link Drive}
 * 
 * @author Cosmin Zamfir
 * 
 */
public class DriveSelectorOld extends Container {

    private static final String name = "driveSelector";
    private static DriveSelectorOld instance;

    public static DriveSelectorOld instance() {
        if (instance == null) {
            instance = new DriveSelectorOld();
        }
        return instance;
    }

    DriveSelectorOld() {
        System.out.println("Display size: " + Display.getInstance().getDisplayHeight() + ", "
                + Display.getInstance().getDisplayWidth());
        setLayout(new GridLayout(4, 2));
        setPreferredSize(new Dimension(Display.getInstance().getDisplayWidth(), Display.getInstance()
                .getDisplayHeight()));
        for (int i = 0; i < ComponentsFactory.driveTypes.length; i++) {

            final EnumDriveType type = ComponentsFactory.driveTypes[i];
            final String image = ComponentsFactory.driveIcons[i];
            final Button b =
                    ComponentsFactory.getImageButton(ImageFactory.instance(ComponentsFactory.driveIcons[i], Display
                            .getInstance().getDisplayHeight() / 5));
            //b.setPressedStyle(b.getUnselectedStyle());
            b.setSelectedStyle(b.getUnselectedStyle());
            b.setName("selectDrive");
            b.getStyle().setBgTransparency(0);
            Container container = new Container();
            BorderLayout l = new BorderLayout();
            l.setCenterBehavior(BorderLayout.CENTER_BEHAVIOR_CENTER);
            container.setLayout(l);
            //			if (i == 0 || i == 3 || i== 4 || i == 7 ) {
            //				container.getStyle().setBgColor(com.codename1.de.cloud.util.Colors.SLATE_BLUE);
            //			} else {
            //				container.getStyle().setBgColor(ComponentsFactory.SLATE_GRAY);
            //			}
            container.getStyle().setBgTransparency(255);

            container.addComponent(BorderLayout.CENTER, b);

            Container waitContainer = new Container();
            BorderLayout l1 = new BorderLayout();
            l1.setCenterBehavior(BorderLayout.CENTER_BEHAVIOR_CENTER);
            waitContainer.setLayout(l1);
            Label wait = new Label(ImageFactory.getWait(ImageFactory.LARGE, 10));
            wait.setVisible(false);
            waitContainer.addComponent(BorderLayout.CENTER, wait);
            container.addComponent(BorderLayout.SOUTH, waitContainer);
            addComponent(container);
            //b.addActionListener(new DriveSelectActionListener(type, image, wait, this));
        }
    }

    public String getName() {
        return name;
    }
}
