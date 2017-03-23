package com.codename1.de.cloud.drive.gui.components;

import com.codename1.ui.List;

import java.util.Vector;

public class MultiSelectList extends List {

    public MultiSelectList(Vector items) {
        super(new MultiSelectListModel(items));
        setRenderer(new MultiSelectListCellRenderer((MultiSelectListModel) getModel(), this));
    }

    public MultiSelectList(Vector items, SelectAction action) {
        super(new MultiSelectListModel(items));
        setRenderer(new MultiSelectListCellRenderer((MultiSelectListModel) getModel(), this, action));
    }

    /**
     * 
     * @return all list items which have been selected
     */
    public Vector getSelectedItems() {
        return ((MultiSelectListModel) getModel()).getSelectedItems();
    }
}
