package com.codename1.de.cloud.drive.gui.renderers;

import com.codename1.de.cloud.drive.gui.factory.ComponentsFactory;
import com.codename1.de.cloud.drive.gui.factory.ImageFactory;
import com.codename1.de.cloud.drive.storage.Directory;
import com.codename1.de.cloud.drive.storage.File;
import com.codename1.de.cloud.drive.storage.StorageItem;
import com.codename1.de.cloud.util.CommonUtils;
import com.codename1.io.Log;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Font;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.List;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.list.ListCellRenderer;
import com.codename1.ui.plaf.Border;

import java.util.Date;

/**
 * File/directory icon + fileName label; with upper and lower margins 
 * @author Cosmin Zamfir
 *
 */
public class FileListCellRenderer extends Container implements ListCellRenderer {

    private static Image folder;

    static {
        folder = ImageFactory.instance(ImageFactory.folder, ImageFactory.MEDIUM);
    }

    private Label name = new Label("");
    private Label fileAttributes = new Label("");
    private Label icon = new Label("");

    private Label focus = new Label("");

    public FileListCellRenderer() {
        setLayout(new BorderLayout());
        addComponent(BorderLayout.WEST, icon);
        icon.getStyle().setBgTransparency(0);
        icon.getStyle().setMargin(10, 10, 0, 0);
        Container c = new Container(new GridLayout(2, 1));
        name.getStyle().setBgTransparency(0);
        name.getStyle().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_MEDIUM));
        name.getStyle().setMargin(10, 5, 0, 0);
        name.setPreferredW(1200);
        fileAttributes.getStyle().setBgTransparency(0);
        fileAttributes.getStyle().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_SMALL));
        fileAttributes.getStyle().setMargin(5, 10, 0, 0);
        fileAttributes.setPreferredW(1200);
        c.addComponent(name);
        c.addComponent(fileAttributes);

        addComponent(BorderLayout.CENTER, c);
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
            fileAttributes.setText("");
            Log.p("CellRenderer callled with null value");
        } else {
            StorageItem item = (StorageItem) value;
            name.setText(value.toString());
            if (!item.isDirectory()) {
                Date date = item.lastModified();
                String dateS = date == null ? "unknown" : CommonUtils.format(date);
                long size = ((File) item).size();
                String sizeS = CommonUtils.formatFileSize(size);
                fileAttributes.setText("Size: " + sizeS + "; Last modified: " + dateS);
            } else {
                Date date = item.lastModified();
                String dateS = date == null ? "unknown" : CommonUtils.format(date);
                fileAttributes.setText(((Directory) item).children().size() + " items; Last modified: " + dateS);
            }
            fileAttributes.getStyle().setFgColor(com.codename1.de.cloud.util.Colors.WHITE);
            if (item.isDirectory()) {
                name.getStyle().setFgColor(com.codename1.de.cloud.util.Colors.YELOW);
                icon.setIcon(ImageFactory.instance(((Directory) item).iconName(), ImageFactory.MEDIUM));
            } else {
                name.getStyle().setFgColor(com.codename1.de.cloud.util.Colors.WHITE);
                icon.setIcon(ImageFactory.instanceForFileType(item.name(), ImageFactory.MEDIUM));
            }
        }
        return this;
    }

    public Component getListFocusComponent(List list) {
        return focus;
    }

}
