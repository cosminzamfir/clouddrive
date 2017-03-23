package com.codename1.de.cloud.drive.gui.renderers;

import com.codename1.de.cloud.drive.gui.factory.ImageFactory;
import com.codename1.de.cloud.drive.storage.Drive;
import com.codename1.de.cloud.util.Colors;
import com.codename1.de.cloud.util.ImageUtils;
import com.codename1.io.Log;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Font;
import com.codename1.ui.Label;
import com.codename1.ui.List;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.list.ListCellRenderer;
import com.codename1.ui.plaf.Border;

/**
 * Drive icon + {@link Drive#shortName()}; with upper and lower margins 
 * @author Cosmin Zamfir
 *
 */
public class DrivesListCellRenderer extends Container implements ListCellRenderer {

    private Label name = new Label("");
    private Label driveAttributes = new Label("");
    private Label icon = new Label("");

    private Label focus = new Label("");

    public DrivesListCellRenderer() {
        setLayout(new BorderLayout());

        //drive icon on the left
        addComponent(BorderLayout.WEST, icon);
        icon.getStyle().setBgTransparency(0);
        icon.getStyle().setMargin(10, 10, 0, 0);

        //next some drive info
        Container c = new Container(new GridLayout(2, 1));
        name.getStyle().setBgTransparency(0);
        name.getStyle().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_MEDIUM));
        name.getStyle().setMargin(10, 5, 0, 0);
        name.setPreferredW(1200);
        driveAttributes.getStyle().setBgTransparency(0);
        driveAttributes.getStyle().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_SMALL));
        driveAttributes.getStyle().setMargin(5, 10, 0, 0);
        driveAttributes.setPreferredW(1200);
        c.addComponent(name);
        c.addComponent(driveAttributes);
        addComponent(BorderLayout.CENTER, c);

        //on the left, the free space chart
        Label label = new Label();
        label.setIcon(ImageUtils.createColouredRectangle(Colors.RED_AA, 20, 400));
        addComponent(BorderLayout.EAST, label);
        label.getStyle().setBgTransparency(0);

        focus.getStyle().setBgTransparency(100);
        Border upper = Border.createBevelRaised();
        upper.setThickness(1);
        Border border =
                Border.createCompoundBorder(upper, Border.createEmpty(), Border.createEmpty(), Border.createEmpty());
        getStyle().setBorder(border);

    }

    public Component getListCellRendererComponent(List list, Object value, int index, boolean isSelected) {
        if (value == null) {
            name.setText("");
            driveAttributes.setText("");
            Log.p("CellRenderer callled with null value", Log.WARNING);
        } else {
            Drive drive = (Drive) value;
            name.setText(drive.shortName());
            try {
                driveAttributes.setText(drive.list().size() + " items");
            } catch (Exception e) {
                driveAttributes.setText(99 + " items");
            }
            driveAttributes.getStyle().setFgColor(com.codename1.de.cloud.util.Colors.WHITE);
            name.getStyle().setFgColor(com.codename1.de.cloud.util.Colors.YELOW);
            icon.setIcon(ImageFactory.instance(drive.iconName(), ImageFactory.MEDIUM));
        }
        return this;
    }

    public Component getListFocusComponent(List list) {
        return focus;
    }

}
