package com.codename1.de.cloud;

import com.codename1.de.cloud.drive.gui.actions.navigation.NavigationManager;
import com.codename1.de.cloud.drive.gui.factory.ComponentsFactory;
import com.codename1.de.cloud.util.Colors;
import com.codename1.io.Log;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.geom.Dimension;

public class TestUtils {

    /**
     * Call this method for standalone programs to instantiate the codename resources
     */
    public static void init() {
		Display d = Display.getInstance();
        d.init(new Object());
        Log.setLevel(Log.DEBUG);
	}

    public static Form initDisplay() {
        Display d = Display.getInstance();
        Display.init(new Object());
        Form f = new Form();
        f.setPreferredSize(new Dimension(200, 200));
        f.getStyle().setBgColor(Colors.BLACK);
        f.getStyle().setBgTransparency(255);
        Display.init(f);
        ComponentsFactory.setDefaults(f);
        NavigationManager.setRoot(f);
        f.show();
        return f;
    }
}
