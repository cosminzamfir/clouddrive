package com.codename1.de.cloud.drive.gui.components;

import com.codename1.de.cloud.drive.gui.actions.BaseActionListener;
import com.codename1.de.cloud.drive.gui.containers.StandardDialog;
import com.codename1.de.cloud.drive.gui.factory.ComponentsFactory;
import com.codename1.de.cloud.drive.gui.factory.ImageFactory;
import com.codename1.ui.Button;
import com.codename1.ui.Dialog;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.layouts.BorderLayout;

import java.util.Vector;

/**
 * A {@link Dialog} which displays one input text.
 * It requires the message to be displayed; it returns the text which has been entered in the input text or <code>null</code>
 * if the cancel button has been pressed 
 * input text. 
 * @author zamfcos
 *
 */
public class InputTextDialog extends StandardDialog {

    private boolean allowEmptyValue;
    private String value;
    private boolean canceled;

    public InputTextDialog(String message, String initialValue, boolean allowEmptyValue) {
        super(ImageFactory.instance(ImageFactory.question, ImageFactory.MEDIUM), message, com.codename1.de.cloud.util.Colors.SLATE_BLUE);
        this.allowEmptyValue = allowEmptyValue;
        layout(initialValue);
    }

    private void layout(String initialValue) {
        final TextField text = new TextField(20);
        text.setPreferredH(ImageFactory.MEDIUM);
        getContentPane().addComponent(BorderLayout.CENTER, text);
        Vector buttons = new Vector();
        Button ok = new Button("OK");
        ok.setPreferredH(ImageFactory.MEDIUM);
        ok.addActionListener(new BaseActionListener() {

            protected void execute(ActionEvent evt) {
                value = text.getText();
                if (!allowEmptyValue && value.trim().length() == 0) {
                    ShortInfoDialog d = new ShortInfoDialog("Please enter something in the text field", 500);
                    return;
                }
                canceled = false;
                dispose();
            }
        });
        Button cancel = new Button("Cancel");
        cancel.setPreferredH(ImageFactory.MEDIUM);
        cancel.addActionListener(new BaseActionListener() {

            protected void execute(ActionEvent evt) {
                canceled = true;
                value = null;
                dispose();
            }
        });
        buttons.addElement(ok);
        buttons.addElement(cancel);
        getContentPane().addComponent(BorderLayout.SOUTH, new HorizontalComponentsContainer(buttons));

    }

    public boolean isCanceled() {
        return canceled;
    }

    public String getValue() {
        return value;
    }

}
