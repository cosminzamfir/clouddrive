package com.codename1.de.cloud.drive.gui.components;

import com.codename1.de.cloud.drive.gui.renderers.DefaultComboCellRenderer;
import com.codename1.de.cloud.util.CommonUtils;
import com.codename1.ui.ComboBox;

import java.util.Vector;

/**
 * Sets the default layout
 * Add an "All" entry, selected by default
 * @author Cosmin Zamfir
 *
 */
public class DefaultComboBox extends ComboBox {

    private static final String ALL = "Everything";
    private boolean createSelectAllEntry;

    public static DefaultComboBox newInstance(Vector items, boolean createSelectAllEntry) {
        if (createSelectAllEntry) {
            Vector res = new Vector();
            res.addElement(ALL);
            CommonUtils.addElements(res, items);
            return new DefaultComboBox(res, true);
        } else {
            return new DefaultComboBox(items, true);
        }

    }

    private DefaultComboBox(Vector items, boolean createSelectAllEntry) {
        super(items);
        this.createSelectAllEntry = createSelectAllEntry;
        applyDefaultStyle();
    }

    private void applyDefaultStyle() {
        getStyle().setBgTransparency(0);
        setSelectedStyle(getUnselectedStyle());
        setPressedStyle(getUnselectedStyle());
        if (createSelectAllEntry) {
            setSelectedItem(ALL);
        } else {
            setSelectedIndex(0);
        }
        setRenderer(new DefaultComboCellRenderer());

    }

    public boolean isAll() {
        return createSelectAllEntry && this.getSelectedItem().equals(ALL);
    }
}
