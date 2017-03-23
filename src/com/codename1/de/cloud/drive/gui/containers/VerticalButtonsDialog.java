package com.codename1.de.cloud.drive.gui.containers;

import com.codename1.de.cloud.drive.gui.actions.BaseActionListener;
import com.codename1.de.cloud.drive.gui.components.VerticalComponentsContainer;
import com.codename1.de.cloud.drive.gui.factory.ComponentsFactory;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Font;
import com.codename1.ui.Image;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.layouts.BorderLayout;

import java.util.Enumeration;
import java.util.Vector;

public class VerticalButtonsDialog extends StandardDialog {

    private Container header;
    private Container content;
    private Vector buttons = new Vector();

    /**
     * A standard {@link Dialog} with a header and a number of buttons presented in a list form
     * @param icon
     * @param header
     */
    public VerticalButtonsDialog(Image icon, String headerText, Vector buttons) {
        super(icon, headerText, com.codename1.de.cloud.util.Colors.DIRTY_WHITE);
        this.buttons = buttons;
        Enumeration e = buttons.elements();
        while (e.hasMoreElements()) {
            Button b = (Button) e.nextElement();
            b.addActionListener(new BaseActionListener() {

                protected void execute(ActionEvent evt) {
                    dispose();
                }
            });
        }
        addContent(getButtons());
    }

    private Vector getButtons() {
        return buttons;
    }

    private void addContent(Vector buttons) {
        content = new VerticalComponentsContainer(buttons, Font.SIZE_LARGE);
        getContentPane().addComponent(BorderLayout.CENTER, content);
    }

}
