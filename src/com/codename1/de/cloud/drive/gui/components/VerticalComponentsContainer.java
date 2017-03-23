package com.codename1.de.cloud.drive.gui.components;

import com.codename1.de.cloud.drive.gui.factory.ComponentsFactory;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Font;
import com.codename1.ui.List;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.plaf.Border;

import java.util.Enumeration;
import java.util.Vector;

/**
 * Arrange a number of components vertically and left aligned.
 * The layout is similar with a {@link List}
 * @author Cosmin Zamfir
 *
 */
public class VerticalComponentsContainer extends Container {

    public Vector components;

    public VerticalComponentsContainer(Vector components, int fontSize) {
        super();
        this.components = components;
        layout(fontSize);
    }

    private void layout(int fontSize) {
        setLayout(new GridLayout(components.size() + 1, 1));
        Enumeration e = components.elements();
        while (e.hasMoreElements()) {
            Component cmp = (Component) e.nextElement();
            Font font = Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, fontSize);
            Border b = Border.createBevelRaised();
            b.setThickness(1);
            Border border =
                    Border.createCompoundBorder(Border.createEmpty(), b, Border.createEmpty(), Border.createEmpty());

            ComponentsFactory.applySelectionStyle(cmp, font, b, 0, 0, 0, 0);
            addComponent(cmp);
        }
    }

}
