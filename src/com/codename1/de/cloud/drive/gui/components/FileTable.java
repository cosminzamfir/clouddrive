package com.codename1.de.cloud.drive.gui.components;

import com.codename1.de.cloud.drive.gui.actions.BaseActionListener;
import com.codename1.de.cloud.drive.gui.actions.navigation.NavigationManager;
import com.codename1.de.cloud.drive.gui.containers.DriveManager;
import com.codename1.de.cloud.drive.gui.factory.ComponentsFactory;
import com.codename1.de.cloud.drive.gui.factory.ImageFactory;
import com.codename1.de.cloud.drive.storage.Directory;
import com.codename1.de.cloud.drive.storage.File;
import com.codename1.de.cloud.drive.storage.StorageItem;
import com.codename1.de.cloud.util.CommonUtils;
import com.codename1.io.Log;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.Font;
import com.codename1.ui.Image;
import com.codename1.ui.TextArea;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.table.DefaultTableModel;
import com.codename1.ui.table.Table;

import java.util.Vector;

/**
 * Display a list of files in a grid layout
 * 
 * @author zamfcos
 * 
 */
public class FileTable extends Table {

    public static int CELL_WIDTH = ImageFactory.MEDIUM * 3 / 2;

    /**
     * The screen presenting the root should have bigger icons
     */
    public static int CELL_WIDTH_TOPSCREEN = ImageFactory.LARGE * 3 / 2;

    private final DriveManager driveManager;
    private int width;
    private final Vector items;
    /**
     * One of {@link FileTable#CELL_WIDTH} or
     * {@link FileTable#CELL_WIDTH_TOPSCREEN} depending whether it is showing
     * the root of a drive or not
     */
    private int cellWidth;
    private boolean isDriveRoot;

    public FileTable(Vector items, DefaultTableModel model, int cols, DriveManager manager) {
        super(model, false);
        // FIXME large cells only for drive root
        if (true) {
            cellWidth = CELL_WIDTH_TOPSCREEN;
            isDriveRoot = true;
        } else {
            cellWidth = CELL_WIDTH_TOPSCREEN;
            isDriveRoot = true;
        }
        this.items = items;
        setDrawBorder(false);
        getStyle().setBgTransparency(0);
        this.width = NavigationManager.getRoot().getWidth();
        this.driveManager = manager;
    }

    protected Component createCell(Object value, int row, int column, boolean editable) {
        Container res = ComponentsFactory.getDefaultBorderLayoutContainer();
        res.getStyle().setBgTransparency(0);
        StorageItem item = (StorageItem) value;
        Image icon;
        if (!item.isDirectory()) {
            icon = ImageFactory.instanceForFileType(item.name(), ImageFactory.MEDIUM);
        } else {
            if (isDriveRoot) {
                icon = ImageFactory.instance(((Directory) item).iconName(), ImageFactory.LARGE);
            } else {
                icon = ImageFactory.instance(((Directory) item).iconName(), ImageFactory.MEDIUM);
            }
        }
        OButton iconLabel = new OButton(icon);
        iconLabel.setO(item);
        iconLabel.getStyle().setBgTransparency(0);
        iconLabel.getStyle().setBorder(Border.createEmpty());
        iconLabel.addActionListener(new BaseActionListener() {

            protected void execute(ActionEvent evt) {
                StorageItem item = (StorageItem) ((OButton) evt.getComponent()).getO();
                Log.p("List item clicked for " + item + " which is a " + (item.isDirectory() ? "directory" : " file"),
                        Log.DEBUG);
                if (item.isDirectory()) {
                    Directory dir = (Directory) item;
                    driveManager.setCurrentDirectory(dir);
                } else {
                    Log.p("Opening file " + item.name());
                    Display.getInstance().execute(((File) item).uri());
                }
            }
        });

        res.addComponent(BorderLayout.CENTER, iconLabel);
        TextArea text = new TextArea(3, 10);
        if (item.isDirectory()) {
            text.setText(item.name() + " (" + ((Directory) item).children().size() + " items)");
        } else {
            text.setText(item.name());
        }

        text.getStyle().setBgTransparency(0);
        text.getStyle().setBorder(Border.createEmpty());
        text.getStyle().setAlignment(CENTER);
        text.setEditable(false);
        if (item.isDirectory()) {
            text.getStyle().setFgColor(com.codename1.de.cloud.util.Colors.YELOW);
            text.getStyle().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_MEDIUM));
        } else {
            text.getStyle().setFgColor(com.codename1.de.cloud.util.Colors.WHITE);
            text.getStyle().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_SMALL));
        }
        // text.setSelectedStyle(text.getUnselectedStyle());
        res.addComponent(BorderLayout.SOUTH, text);
        res.setPreferredW(NavigationManager.getRoot().getPreferredW() / getModel().getColumnCount());
        return res;
    }

    public void repaint() {
        if (NavigationManager.getRoot().getWidth() != width) {
            width = NavigationManager.getRoot().getWidth();
            int columns = width / cellWidth;
            Object[][] data = CommonUtils.matrix(items, columns);
            String[] columnNames = new String[columns];
            for (int i = 0; i < columns; i++) {
                columnNames[i] = "";
            }
            DefaultTableModel model = new DefaultTableModel(columnNames, data);
            setModel(model);

        }
        super.repaint();
    }

}
