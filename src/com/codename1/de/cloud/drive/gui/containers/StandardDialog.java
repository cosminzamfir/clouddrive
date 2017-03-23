package com.codename1.de.cloud.drive.gui.containers;

import com.codename1.de.cloud.drive.gui.actions.navigation.NavigationManager;
import com.codename1.de.cloud.drive.gui.factory.ComponentsFactory;
import com.codename1.de.cloud.util.Colors;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Font;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.plaf.Border;


public class StandardDialog extends Dialog {

    private Container header;

    public StandardDialog(Image icon, String header, int backgroudColor) {
        setLayout(new BorderLayout());
        setDialogBackground(backgroudColor);
        setRoundCorners();
        addTopPanel(icon, header);
    }

    /**
     * Dialog header and dialog button bellow
     * @param d
     * @param header2 
     * @param icon 
     */
    private void addTopPanel(Image icon, String headerText) {
        //header: icon and text
        BorderLayout borderLayout = new BorderLayout();
        borderLayout.setCenterBehavior(BorderLayout.CENTER_BEHAVIOR_CENTER);
        header = new Container(borderLayout);
        header.getStyle().setBgTransparency(255);
        header.getStyle().setBgColor(Colors.BLUE);

        Label label1 = new Label(icon);
        label1.setText(headerText);
        label1.getStyle().setBgTransparency(0);
        label1.getStyle().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_LARGE));
        label1.getStyle().setFgColor(Colors.WHITE);
        header.addComponent(BorderLayout.CENTER, label1);
        //header.getStyle().setBorder(ComponentsFactory.roundBorder());
        header.getStyle().setBorder(ComponentsFactory.roundBorder());

        getContentPane().addComponent(BorderLayout.NORTH, header);
    }

    private void setRoundCorners() {
        getStyle().setBorder(Border.createEmpty());
        getContentPane().getStyle().setBorder(Border.createRoundBorder(20, 20, Colors.BLACK, true));
    }

    private void setDialogBackground(int color) {
        getStyle().setBgTransparency(0);
        getContentPane().getStyle().setBgTransparency(255);
        getContentPane().getStyle().setBgColor(color);
        getStyle().setMargin(0, 0, 0, 0);
        getContentPane().getStyle().setMargin(0, 0, 0, 0);
    }

    public void repaint() {
        this.setPreferredH(NavigationManager.getRoot().getPreferredH() * 8 / 10);
        this.setPreferredW(NavigationManager.getRoot().getPreferredW() * 8 / 10);
        super.repaint();
    }

}
