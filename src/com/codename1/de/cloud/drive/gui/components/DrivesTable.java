package com.codename1.de.cloud.drive.gui.components;

import com.codename1.de.cloud.drive.DriveFactory;
import com.codename1.de.cloud.drive.gui.actions.BaseActionListener;
import com.codename1.de.cloud.drive.gui.actions.navigation.NavigationManager;
import com.codename1.de.cloud.drive.gui.containers.ContainerFactory;
import com.codename1.de.cloud.drive.gui.containers.DriveManager;
import com.codename1.de.cloud.drive.gui.factory.ComponentsFactory;
import com.codename1.de.cloud.drive.gui.factory.ImageFactory;
import com.codename1.de.cloud.drive.storage.Drive;
import com.codename1.de.cloud.util.CommonUtils;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Font;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.table.DefaultTableModel;
import com.codename1.ui.table.Table;

import java.util.Vector;

/**
 * Display all available {@link Drive}s in a grid layout
 * 
 * @author zamfcos
 * 
 */
public class DrivesTable extends Table {

    public static int CELL_WIDTH_TOPSCREEN = ImageFactory.LARGE * 3 / 2;

    private int width;
    /**
     * One of {@link DrivesTable#CELL_WIDTH} or
     * {@link DrivesTable#CELL_WIDTH_TOPSCREEN} depending whether it is showing
     * the root of a drive or not
     */
    private int cellWidth;
    private Vector items;
    private boolean initialized;
    private int columns = 2;

    public DrivesTable() {
        Vector allDrives = DriveFactory.getAllDrives();
        Object[][] data = (Object[][]) CommonUtils.matrix(allDrives, columns);
        String[] columnNames = new String[columns];
        for (int i = 0; i < columns; i++) {
            columnNames[i] = "";
        }
        cellWidth = CELL_WIDTH_TOPSCREEN;
        setDrawBorder(false);
        getStyle().setBgTransparency(0);
        setIncludeHeader(false);
        this.width = NavigationManager.getRoot().getWidth();
        DefaultTableModel model = new DefaultTableModel(columnNames, data);
        initialized = true;
        setModel(model);
    }

    protected Component createCell(Object value, int row, int column, boolean editable) {
        if (!initialized) {
            return super.createCell(value, row, column, editable);
        }
        Container res = ComponentsFactory.getDefaultBorderLayoutContainer();
        res.getStyle().setBgTransparency(0);
        Drive drive = (Drive) value;
        Image icon = ImageFactory.instance(drive.iconName(), ImageFactory.LARGE);

        OButton iconLabel = new OButton(icon);
        iconLabel.setO(drive);
        iconLabel.getStyle().setBgTransparency(0);
        iconLabel.getStyle().setBorder(Border.createEmpty());
        iconLabel.addActionListener(new BaseActionListener() {

            protected void execute(ActionEvent evt) {
                Drive drive = (Drive) evt.getSource();
                DriveManager container = ContainerFactory.driveManager(drive);
                NavigationManager.navigateTo(container);
            }
        });

        res.addComponent(BorderLayout.CENTER, iconLabel);
        Label text = new Label();
        try {
            text.setText(drive.shortName() + " (" + drive.list().size() + " items)");
        } catch (Exception e) {
            text.setText(drive.shortName() + " (unknown number of items)");
        }

        text.getStyle().setBgTransparency(0);
        text.getStyle().setBorder(Border.createEmpty());
        text.getStyle().setAlignment(CENTER);
        text.getStyle().setFgColor(com.codename1.de.cloud.util.Colors.YELOW);
        text.getStyle().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_MEDIUM));
        // text.setSelectedStyle(text.getUnselectedStyle());
        res.addComponent(BorderLayout.SOUTH, text);
        res.setPreferredW(NavigationManager.getRoot().getPreferredW() / getModel().getColumnCount());
        return res;
    }

    public void repaint() {
        if (!initialized) {
            super.repaint();
            return;
        }
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
