package com.codename1.de.cloud.drive.gui.components;

import com.codename1.de.cloud.drive.gui.containers.StandardDialog;
import com.codename1.de.cloud.drive.gui.factory.ComponentsFactory;
import com.codename1.de.cloud.drive.gui.factory.ImageFactory;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BorderLayout;

/**
 * Shows an auto-disposable dialog with a specified info message
 * @author zamfcos
 *
 */
public class ShortInfoDialog extends StandardDialog {

    public ShortInfoDialog(String text, int time) {
        super(ImageFactory.instance(ImageFactory.info, ImageFactory.MEDIUM), "Info", com.codename1.de.cloud.util.Colors.SLATE_BLUE);
        layout(text);
        setTimeout(time);
        show();
    }

    private void layout(String text) {
        Label l = new Label(text);
        l.setIcon(ImageFactory.instance(ImageFactory.info, ImageFactory.MEDIUM));
        getContentPane().addComponent(BorderLayout.CENTER, l);
    }
}
