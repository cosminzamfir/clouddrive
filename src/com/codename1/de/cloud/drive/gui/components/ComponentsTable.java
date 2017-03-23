package com.codename1.de.cloud.drive.gui.components;

import com.codename1.de.cloud.drive.gui.actions.navigation.NavigationManager;
import com.codename1.de.cloud.drive.gui.factory.ComponentsFactory;
import com.codename1.de.cloud.drive.gui.factory.ImageFactory;
import com.codename1.de.cloud.util.CommonUtils;
import com.codename1.ui.Button;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Font;
import com.codename1.ui.TextArea;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.table.DefaultTableModel;
import com.codename1.ui.table.Table;
import com.codename1.ui.table.TableModel;

import java.util.Vector;

/**
 * A {@link Table} container which holds a given set of components that needs to
 * be displayed in a grid like structure. The component occupies the entire
 * screen width
 * <p>
 * Each component contains one Button with a specified icon and one piece of text 
 * 
 * @author zamfcos
 * 
 */
public class ComponentsTable extends Table {

    public static int CELL_WIDTH = ImageFactory.MEDIUM * 3 / 2;

    /**
     * The screen presenting the root should have bigger icons
     */
    public static int CELL_WIDTH_TOPSCREEN = ImageFactory.LARGE * 3 / 2;

    /**
     * The width of the table component - equal width
     */
    private int width;
    private final Vector buttons;
    private final Vector texts;
    private final Vector iconNames;
    private int cellWidth;
    private final int columns;
    //FIXME -  the overridden createCell()method is already called by the implicit Table constructor, 
    //at which point not all attributes are set in this instance. Call super.createCell() until this is fully initialized

    private boolean initialized = false;

    /**
     * Build a {@link ComponentsTable} instance with the specified
     * {@link Vector} of {@link OButton}s, the specified text to be displayed
     * for each button
     * 
     * @param buttons
     *            the Vector of {@link OButton} components
     * @param text
     *            the text to be displayed for each button
     * @param cols
     *            the number of table columns
     * @param icons
     *            the image names for each button
     */
    public ComponentsTable(Vector buttons, Vector icons, Vector texts, int cols) {
        super();
        setIncludeHeader(false);
        this.buttons = buttons;
        this.texts = texts;
        setDrawBorder(false);
        getStyle().setBgTransparency(0);
        this.width = NavigationManager.getRoot().getWidth();
        this.columns = cols;
        this.iconNames = icons;
        TableModel model = buildModel(buttons, cols);
        initialized = true;
        setModel(model);
    }

    private TableModel buildModel(Vector items, int cols) {
        String[] columnNames = new String[cols];
        for (int i = 0; i < columnNames.length; i++) {
            columnNames[i] = "";
        }
        Object[][] data = CommonUtils.matrix(items, cols);
        TableModel model = new DefaultTableModel(columnNames, data);
        return model;
    }

    protected Component createCell(Object value, int row, int column, boolean editable) {
        if (!initialized) {
            return super.createCell(value, row, column, editable);
        }

        int cellWidth = width / columns;
        int iconWidth = cellWidth / 2;

        String iconName = (String) CommonUtils.matrix(iconNames, columns)[row][column];
        String text = (String) CommonUtils.matrix(texts, columns)[row][column];
        Button button = (Button) value;
        Container res = ComponentsFactory.getDefaultBorderLayoutContainer();
        //res.setPreferredW(cellWidth);
        res.getStyle().setBgTransparency(0);

        button.setIcon(ImageFactory.instance(iconName, iconWidth));
        button.getStyle().setBgTransparency(0);
        button.getStyle().setBorder(Border.createEmpty());
        button.setText(null);
        button.setSelectedStyle(button.getUnselectedStyle());
        if (button.getParent() != null) {
            button.getParent().removeComponent(button);
        }
        res.addComponent(BorderLayout.CENTER, button);

        TextArea textArea = new TextArea(3, 10);
        textArea.setText(text);
        textArea.getStyle().setBgTransparency(0);
        textArea.getStyle().setBorder(Border.createEmpty());
        textArea.getStyle().setAlignment(CENTER);
        textArea.setEditable(false);
        textArea.getStyle().setFgColor(com.codename1.de.cloud.util.Colors.YELOW);
        textArea.getStyle().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_MEDIUM));
        //text.setSelectedStyle(text.getUnselectedStyle());
        res.addComponent(BorderLayout.SOUTH, textArea);

        res.setPreferredW(NavigationManager.getRoot().getPreferredW() / columns);
        return res;
    }

    public void repaint() {
        if (NavigationManager.getRoot().getWidth() != width) {
            width = NavigationManager.getRoot().getWidth();
        }
        super.repaint();
    }

}
