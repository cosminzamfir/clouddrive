package com.codename1.de.cloud;

import com.codename1.de.cloud.drive.gui.containers.VerticalButtonsDialog;
import com.codename1.de.cloud.drive.gui.factory.ImageFactory;
import com.codename1.ui.Button;
import com.codename1.ui.Display;
import com.codename1.ui.Form;

import java.util.Vector;

public class Simulator03 {

    public static Display d;
    public static Form f;

    public static void main(String[] args) {
        f = TestUtils.initDisplay();
        testVerticalComponentsContainer();
    }

    private static void testVerticalComponentsContainer() {
        Vector v = new Vector();
        v.addElement(new Button("Button1"));
        v.addElement(new Button("Button2"));
        v.addElement(new Button("Button3"));
        v.addElement(new Button("Button4"));
        v.addElement(new Button("Button5"));
        v.addElement(new Button("Button6"));

        VerticalButtonsDialog d =
                new VerticalButtonsDialog(ImageFactory.instance(ImageFactory.file, ImageFactory.SMALL), "Test", v);
        d.show();
    }


}
