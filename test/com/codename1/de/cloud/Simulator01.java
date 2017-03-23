package com.codename1.de.cloud;

import com.codename1.de.cloud.drive.gui.components.MultiSelectList;
import com.codename1.de.cloud.drive.gui.factory.ComponentsFactory;
import com.codename1.de.cloud.drive.gui.factory.ImageFactory;
import com.codename1.de.cloud.drive.virtual.v1.VirtualDrive;
import com.codename1.de.cloud.util.Colors;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Font;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.plaf.Style;


public class Simulator01 {

    public static Display d;
    public static Form f;

    public static void main(String[] args) {
        f = TestUtils.initDisplay();
        createStandardDialog();
    }

    private static void createStandardDialog() {
        ComponentsFactory.setDefaults(f);
        Dialog d = new Dialog();
        d.setLayout(new BorderLayout());
        setBackgrounfColor(d, com.codename1.de.cloud.util.Colors.SLATE_BLUE);
        setRoundCorners(d);
        addTopPanel(d);
        addMiddlePanel(d);
        d.show();
    }

    private static void addMiddlePanel(Dialog d) {
        BoxLayout layout = new BoxLayout(BoxLayout.Y_AXIS);
        Container res = new Container(layout);
        VirtualDrive drive = VirtualDrive.instance();
        drive.initializeMetadata();
        MultiSelectList list = new MultiSelectList(drive.getCategories());
        res.addComponent(list);
        d.getContentPane().addComponent(BorderLayout.CENTER, res);

    }

    /**
     * Dialog header and dialog button bellow
     * @param d
     */
    private static void addTopPanel(Dialog d) {
        //header
        BorderLayout borderLayout = new BorderLayout();
        borderLayout.setCenterBehavior(BorderLayout.CENTER_BEHAVIOR_CENTER);
        Container header = new Container(borderLayout);
        header.getStyle().setBgTransparency(255);
        header.getStyle().setBgColor(com.codename1.de.cloud.util.Colors.BLUE);
        //header.getStyle().setBackgroundType(Style.BACKGROUND_GRADIENT_LINEAR_VERTICAL);
        //header.getStyle().setBackgroundGradientEndColor(com.codename1.de.cloud.util.Colors.BLUE_GRADIENT_START);
        //header.getStyle().setBackgroundGradientStartColor(com.codename1.de.cloud.util.Colors.BLUE_GRADIENT_END);
        header.getStyle().setBorder(Border.createRoundBorder(20, 20));

        Label label1 = new Label(ImageFactory.instance(ImageFactory.search, ImageFactory.MEDIUM));
        label1.setText("Search Me.Everywhere");
        label1.getStyle().setBgTransparency(0);
        label1.getStyle().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_LARGE));
        label1.getStyle().setFgColor(com.codename1.de.cloud.util.Colors.WHITE);
        header.addComponent(BorderLayout.CENTER, label1);

        //buttons
        int n = 5;
        Container buttons = new Container(new GridLayout(1, n));

        for (int i = 0; i < n; i++) {
            Container c = new Container(new GridLayout(1, 1));
            Button b1 = new Button();
            b1.getStyle().setBgTransparency(0);
            b1.setIcon(ImageFactory.instance(ImageFactory.search, ImageFactory.MEDIUM));
            b1.getStyle().setBorder(Border.createEmpty());
            c.getStyle().setBackgroundGradientStartColor(Colors.GREY_GRADIENT_START);
            c.getStyle().setBackgroundGradientEndColor(Colors.GREY_GRADIENT_END);
            c.getStyle().setBackgroundType(Style.BACKGROUND_GRADIENT_LINEAR_HORIZONTAL);
            c.getStyle().setBgTransparency(255);
            c.addComponent(b1);
            buttons.addComponent(c);
            if (i < n - 1) {
                Border empty = Border.createEmpty();
                Border upper = Border.createBevelRaised();
                upper.setThickness(1);
                Border border = Border.createCompoundBorder(empty, empty, empty, upper);
                c.getStyle().setBorder(border);

            }
        }
        
        Container res = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        res.addComponent(header);
        res.addComponent(buttons);
        d.getContentPane().addComponent(BorderLayout.NORTH, res);

    }

    private static void setRoundCorners(Dialog d) {
        d.getStyle().setBorder(Border.createRoundBorder(10, 10));
        d.getContentPane().getStyle().setBorder(Border.createRoundBorder(20, 20, Colors.BLACK, true));
    }

    private static void setBackgrounfColor(Dialog d, int color) {
        d.getContentPane().getStyle().setBgTransparency(255);
        d.getContentPane().getStyle().setBgColor(color);
    }

}
