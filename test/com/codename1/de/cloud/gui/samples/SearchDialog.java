package com.codename1.de.cloud.gui.samples;

import com.codename1.de.cloud.TestUtils;
import com.codename1.de.cloud.drive.gui.actions.BaseActionListener;
import com.codename1.de.cloud.drive.gui.components.DefaultComboBox;
import com.codename1.de.cloud.drive.gui.components.HorizontalComponentsContainer;
import com.codename1.de.cloud.drive.gui.factory.ComponentsFactory;
import com.codename1.de.cloud.drive.gui.factory.ImageFactory;
import com.codename1.de.cloud.drive.virtual.v1.Category;
import com.codename1.de.cloud.drive.virtual.v1.VirtualDrive;
import com.codename1.de.cloud.util.Colors;
import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Font;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.table.TableLayout;

import java.util.Enumeration;
import java.util.Vector;

public class SearchDialog {

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
        addTopPanel(d, ImageFactory.instance(ImageFactory.search, ImageFactory.MEDIUM), "Search virtual drive");
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
        VirtualDrive drive = VirtualDrive.instance();
        drive.initializeMetadata();
        Vector categories = drive.getCategories();
        Enumeration e = categories.elements();
        Container c1 = new Container(new TableLayout(categories.size() + 1, 2));
        Label l = getLabel("Name");
        c1.addComponent(ComponentsFactory.rightConstraint(), l);
        final TextField text = new TextField(20);

        text.setPreferredH(ImageFactory.MEDIUM / 3);
        c1.addComponent(text);
        final Vector combos = new Vector();
        while (e.hasMoreElements()) {
            Category category = (Category) e.nextElement();
            Label label = getLabel(category.getName());
            c1.addComponent(ComponentsFactory.rightConstraint(), label);
            ComboBox combo = DefaultComboBox.newInstance(category.directories(), true);
            combos.addElement(combo);
            c1.addComponent(combo);

        }
        d.getContentPane().addComponent(BorderLayout.CENTER, c1);

        Vector buttons = new Vector();
        Button ok = new Button("OK");
        ok.setPreferredH(ImageFactory.MEDIUM);
        Button cancel = new Button("Cancel");
        cancel.setPreferredH(ImageFactory.MEDIUM);
        cancel.addActionListener(new BaseActionListener() {
            protected void execute(ActionEvent evt) {
                d.dispose();
                Display.getInstance().exitApplication();
            }
        });
        buttons.addElement(ok);
        buttons.addElement(cancel);
        d.getContentPane().addComponent(BorderLayout.SOUTH, new HorizontalComponentsContainer(buttons));
    }

    private static Label getLabel(String text) {
        Label res = new Label(text);
        res.getStyle().setBgTransparency(0);
        res.getStyle().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_MEDIUM));
        res.getStyle().setFgColor(com.codename1.de.cloud.util.Colors.WHITE);
        return res;
    }

}
