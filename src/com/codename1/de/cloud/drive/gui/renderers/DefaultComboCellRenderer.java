package com.codename1.de.cloud.drive.gui.renderers;

import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Font;
import com.codename1.ui.Label;
import com.codename1.ui.List;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.list.ListCellRenderer;
import com.codename1.ui.plaf.Border;

public class DefaultComboCellRenderer extends Container implements ListCellRenderer {

    private Label name = new Label("");
    private Label focus = new Label("");

    public DefaultComboCellRenderer() {
        setLayout(new BorderLayout());
        name.getStyle().setBgTransparency(0);
        name.getStyle().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_MEDIUM));
        name.getStyle().setMargin(3, 3, 0, 0);

        addComponent(BorderLayout.CENTER, name);
        focus.getStyle().setBgTransparency(100);
        Border upper = Border.createBevelRaised();
        upper.setThickness(1);
        Border border =
                Border.createCompoundBorder(upper, Border.createEmpty(), Border.createEmpty(), Border.createEmpty());
        getStyle().setBorder(border);

    }

    public Component getListCellRendererComponent(List list, Object value, int index, boolean isSelected) {
        name.setText(value.toString());
        return this;
    }

    public Component getListFocusComponent(List list) {
        return focus;
    }

}
