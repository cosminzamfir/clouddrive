package com.codename1.de.cloud.drive.gui.components;

import com.codename1.de.cloud.drive.gui.actions.BaseActionListener;
import com.codename1.de.cloud.drive.gui.containers.StandardDialog;
import com.codename1.de.cloud.drive.gui.factory.ComponentsFactory;
import com.codename1.de.cloud.drive.gui.factory.ImageFactory;
import com.codename1.ui.Button;
import com.codename1.ui.Dialog;
import com.codename1.ui.Label;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.layouts.BorderLayout;

import java.util.Vector;

/**
 * A confirmation {@link Dialog}. Requires the confirmation text to be displayed.
 * Returns the answer given by a user as a boolean value: <code>true</code> = proceed; <code>false</code>=cancel 
 * @author zamfcos
 *
 */
public class ConfirmDialog extends StandardDialog {

    private boolean proceed;

    public ConfirmDialog(String text) {
        super(ImageFactory.instance(ImageFactory.stop, ImageFactory.MEDIUM), "Confirm", com.codename1.de.cloud.util.Colors.SLATE_BLUE);
        addContent(text);
    }

    private void addContent(String text) {
        Label l = new Label(text);
        l.setIcon(ImageFactory.instance(ImageFactory.question, ImageFactory.MEDIUM));
        getContentPane().addComponent(BorderLayout.CENTER, l);

        Vector buttons = new Vector();
        Button ok = new Button("OK");
        ok.setPreferredH(ImageFactory.MEDIUM);
        ok.addActionListener(new BaseActionListener() {
            
            protected void execute(ActionEvent evt) {
                proceed = true;
                dispose();
            }
        });
        Button cancel = new Button("Cancel");
        cancel.setPreferredH(ImageFactory.MEDIUM);
        cancel.addActionListener(new BaseActionListener() {

            protected void execute(ActionEvent evt) {
                proceed = false;
                dispose();
            }
        });
        buttons.addElement(ok);
        buttons.addElement(cancel);
        getContentPane().addComponent(BorderLayout.SOUTH, new HorizontalComponentsContainer(buttons));
    }

    public boolean proceed() {
        return proceed;
    }
}
