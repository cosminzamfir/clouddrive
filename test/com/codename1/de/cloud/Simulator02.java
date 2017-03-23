package com.codename1.de.cloud;

import com.codename1.de.cloud.drive.gui.components.OButton;
import com.codename1.de.cloud.drive.gui.components.VerticalComponentsContainer;
import com.codename1.de.cloud.drive.gui.containers.StandardDialog;
import com.codename1.de.cloud.drive.gui.factory.ComponentsFactory;
import com.codename1.de.cloud.drive.gui.factory.ImageFactory;
import com.codename1.ui.Display;
import com.codename1.ui.Font;
import com.codename1.ui.Form;
import com.codename1.ui.layouts.BorderLayout;

import java.util.Vector;

public class Simulator02 {

    public static Display d;
    public static Form f;

    public static void main(String[] args) {
        f = TestUtils.initDisplay();
        testVerticalComponentsContainer();
    }

    private static void testVerticalComponentsContainer() {
        StandardDialog d =
                new StandardDialog(ImageFactory.instance(ImageFactory.folder, ImageFactory.MEDIUM), "File Operations",
                        com.codename1.de.cloud.util.Colors.SLATE_BLUE);

        Vector buttons = new Vector();
        OButton delete = new OButton("Delete", null);
        OButton rename = new OButton("Rename", null);
        OButton toCloud = new OButton("Copy To Me.Everywhere", null);
        OButton toLocal = new OButton("Copy To the local device", null);
        buttons.addElement(delete);
        buttons.addElement(rename);
        buttons.addElement(toCloud);
        buttons.addElement(toLocal);
        VerticalComponentsContainer c = new VerticalComponentsContainer(buttons, Font.SIZE_LARGE);
        d.getContentPane().addComponent(BorderLayout.CENTER, c);
        d.show();

    }

}
