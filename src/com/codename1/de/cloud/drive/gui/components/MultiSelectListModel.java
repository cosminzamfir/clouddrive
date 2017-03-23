package com.codename1.de.cloud.drive.gui.components;

import com.codename1.de.cloud.drive.gui.model.DefaultListModel;
import com.codename1.de.cloud.util.CommonUtils;
import com.codename1.ui.CheckBox;


import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

public class MultiSelectListModel extends DefaultListModel {

    public static String ALL = "Select all";
    /**
     * Maps data to true/false based on the user selection
     */
    private Hashtable selection = new Hashtable();

    /**
     * Checkboxes which are displayed in the list view for each list item 
     */
    private Vector checkboxes = new Vector();

    public MultiSelectListModel(Vector items) {
        //create a copy of the input list 
        Vector copy = CommonUtils.copy(items);
        copy.insertElementAt(ALL, 0);
        setItems(copy);
        Enumeration e = items.elements();
        selection.put(ALL, Boolean.FALSE);
        while (e.hasMoreElements()) {
            Object item = (Object) e.nextElement();
            selection.put(item, Boolean.FALSE);
        }
    }

    public Hashtable getSelection() {
        return selection;
    }

    public Vector getCheckboxes() {
        return checkboxes;
    }

    public void addCheckbox(CheckBox checkbox) {
        this.checkboxes.addElement(checkbox);
    }

    public boolean isItemSelected(Object item) {
        Object o = selection.get(item);
        return Boolean.TRUE.equals(o);
    }

    /**
     * When one selection changed
     * @param value
     * @param selected
     */
    public void selectionChanged(Object value, boolean selected) {
        selection.put(value, new Boolean(selected));
    }

    /**
     * When {@link #ALL} item selection changed
     * @param selected
     */
    public void updateAllCheckBoxes(boolean selected) {
        Enumeration e = checkboxes.elements();
        while (e.hasMoreElements()) {
            CheckBox checkBox = (CheckBox) e.nextElement();
            checkBox.setSelected(selected);
        }
        Enumeration e1 = selection.keys();
        while (e1.hasMoreElements()) {
			Object o = (Object) e1.nextElement();
			selection.put(o, new Boolean(selected));
		}
    }

    /**
     * 
     * @return all list items which have been selected
     */
    public Vector getSelectedItems() {
        Vector res = CommonUtils.filter(selection, Boolean.TRUE);
        res.removeElement(ALL);
        return res;
    }
}
