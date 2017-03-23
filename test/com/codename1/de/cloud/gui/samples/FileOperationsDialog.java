package com.codename1.de.cloud.gui.samples;

import com.codename1.de.cloud.TestUtils;
import com.codename1.de.cloud.drive.gui.actions.BaseActionListener;
import com.codename1.de.cloud.drive.gui.components.OButton;
import com.codename1.de.cloud.drive.gui.factory.ComponentsFactory;
import com.codename1.de.cloud.drive.gui.factory.ImageFactory;
import com.codename1.de.cloud.util.Colors;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Font;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.plaf.Border;

import java.util.Enumeration;
import java.util.Vector;

public class FileOperationsDialog {

    public static void main(String[] args) {
        TestUtils.initDisplay();
        Dialog d = build();
        d.show();
    }

    private static Dialog build() {
        Dialog d = new Dialog();
        d.setLayout(new BorderLayout());
        setDialogBackground(d, com.codename1.de.cloud.util.Colors.SLATE_BLUE);
        setRoundCorners(d);
        addTopPanel(d, ImageFactory.instance(ImageFactory.file, ImageFactory.MEDIUM), "FileOperations");
        addContent(d);
        return d;
    }

    /**
     * Dialog header and dialog button bellow
     * @param d
     * @param header2 
     * @param icon 
     */
    private static void addTopPanel(Dialog d, Image icon, String headerText) {
        //header: icon and text
        BorderLayout borderLayout = new BorderLayout();
        borderLayout.setCenterBehavior(BorderLayout.CENTER_BEHAVIOR_CENTER);
        Container header = new Container(borderLayout);
        header.getStyle().setBgTransparency(255);
        header.getStyle().setBgColor(com.codename1.de.cloud.util.Colors.BLUE);

        Label label1 = new Label(icon);
        label1.setText(headerText);
        label1.getStyle().setBgTransparency(0);
        label1.getStyle().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_LARGE));
        label1.getStyle().setFgColor(com.codename1.de.cloud.util.Colors.WHITE);
        header.addComponent(BorderLayout.CENTER, label1);
        //header.getStyle().setBorder(ComponentsFactory.roundBorder());
        header.getStyle().setBorder(ComponentsFactory.roundBorder());

        d.getContentPane().addComponent(BorderLayout.NORTH, header);
    }

    private static void setRoundCorners(Dialog d) {
        d.getStyle().setBorder(Border.createEmpty());
        d.getContentPane().getStyle().setBorder(Border.createRoundBorder(20, 20, Colors.BLACK, true));
    }

    private static void setDialogBackground(Dialog d, int color) {
        d.getStyle().setBgTransparency(0);
        d.getContentPane().getStyle().setBgTransparency(255);
        d.getContentPane().getStyle().setBgColor(color);
        d.getStyle().setMargin(0, 0, 0, 0);
        d.getContentPane().getStyle().setMargin(0, 0, 0, 0);
    }

    private static void addContent(final Dialog d) {
        Vector buttons = new Vector();
        Button delete = new OButton("Delete");
        Button rename = new OButton("Rename");
        Button toCloud = new OButton("Copy To Me.Everywhere");
        Button toLocal = new OButton("Copy To the local device");
        Button properties = new OButton("Properties");
        Button share = new OButton("Share");
        Button cancel = new Button("Cancel");
        buttons.addElement(delete);
        buttons.addElement(rename);
        buttons.addElement(toCloud);
        buttons.addElement(toLocal);
        buttons.addElement(share);
        buttons.addElement(properties);
        buttons.addElement(cancel);
        cancel.addActionListener(new BaseActionListener() {

            protected void execute(ActionEvent evt) {
                Display.getInstance().exitApplication();
            }
        });

        Container container = new Container();
        container.setLayout(new GridLayout(buttons.size() + 1, 1));
        Enumeration e = buttons.elements();
        while (e.hasMoreElements()) {
            Button button = (Button) e.nextElement();
            Font font = Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_MEDIUM);
            Border b = Border.createBevelRaised();
            b.setThickness(1);
            Border border =
                    Border.createCompoundBorder(Border.createEmpty(), b, Border.createEmpty(), Border.createEmpty());

            ComponentsFactory.applySelectionStyle(button, font, b, 0, 0, 0, 0);
            container.addComponent(button);
        }

        d.getContentPane().addComponent(BorderLayout.CENTER, container);

    }

}
