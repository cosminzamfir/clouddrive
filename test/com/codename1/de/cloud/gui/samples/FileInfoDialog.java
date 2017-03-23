package com.codename1.de.cloud.gui.samples;

import com.codename1.de.cloud.TestUtils;
import com.codename1.de.cloud.drive.gui.actions.BaseActionListener;
import com.codename1.de.cloud.drive.gui.factory.ComponentsFactory;
import com.codename1.de.cloud.drive.gui.factory.ImageFactory;
import com.codename1.de.cloud.util.Colors;
import com.codename1.ui.Button;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Font;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.table.TableLayout;
import com.codename1.ui.table.TableLayout.Constraint;

public class FileInfoDialog {

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
        addTopPanel(d, ImageFactory.instance(ImageFactory.info, ImageFactory.MEDIUM), "FileOperations");
        addFileInfo(d);
        addExitButton(d);
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

    private static void addExitButton(Dialog d) {
        Button b = new Button("OK");
        b.addActionListener(new BaseActionListener() {

            protected void execute(ActionEvent evt) {
                Display.getInstance().exitApplication();
            }
        });
        d.getContentPane().addComponent(BorderLayout.SOUTH, b);
    }

    private static void addFileInfo(Dialog d) {
        String[] keys = new String[] { "Name", "Path", "Size", "Last modified", "Tags" };
        String[] values =
                new String[] { "everest.jpg", "/Pictures/2005/Average(<5MB)", "3.5 MB", "21/12/2012 08:00:00",
                        "Mountains, Pictures, 2005, Small, Above 8000" };
        Container c = new Container();
        c.setLayout(new TableLayout(keys.length, 2));
        for (int i = 0; i < keys.length; i++) {
            Label label1 = new Label(keys[i].toString());
            label1.getStyle().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_MEDIUM));
            label1.getStyle().setBgTransparency(0);
            Constraint right = new Constraint();
            right.setHorizontalAlign(Component.RIGHT);
            c.addComponent(right, label1);

            Label label2 = new Label(values[i].toString());
            label2.getStyle().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_MEDIUM));
            label2.getStyle().setBgTransparency(0);
            Constraint left = new Constraint();
            left.setHorizontalAlign(Component.LEFT);
            c.addComponent(left, label2);
        }
        d.getContentPane().addComponent(BorderLayout.CENTER, c);

    }

}
