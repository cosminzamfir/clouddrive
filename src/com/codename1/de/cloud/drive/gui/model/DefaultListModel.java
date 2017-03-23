package com.codename1.de.cloud.drive.gui.model;

import com.codename1.io.Log;
import com.codename1.ui.events.DataChangedListener;
import com.codename1.ui.events.SelectionListener;
import com.codename1.ui.list.ListModel;
import com.codename1.ui.util.EventDispatcher;

import java.util.Vector;

/**
 * The model behind the file/directory listing
 * @author Cosmin Zamfir
 *
 */
public class DefaultListModel implements ListModel {

    private Vector items = new Vector();

    private EventDispatcher dataListener = new EventDispatcher();
    private EventDispatcher selectionListener = new EventDispatcher();

    private int selectedIndex = 0;

    public DefaultListModel() {
    }

    public DefaultListModel(Vector items) {
        this.items = items;
    }

    public void setItems(Vector items) {
        Log.p("List - items set: " + items, Log.DEBUG);
        this.items = items;
    }

    public Vector getItems() {
        return items;
    }

    /**
     * @inheritDoc
     */
    public Object getItemAt(int index) {
    	if (index < getSize() && index >= 0) {
            return items.elementAt(index);
        }
        return null;
    }

    /**
     * @inheritDoc
     */
    public int getSize() {
        return items.size();
    }

    /**
     * @inheritDoc
     */
    public int getSelectedIndex() {
        return selectedIndex;
    }

    /**
     * @inheritDoc
     */
    public void addItem(Object item) {
        items.addElement(item);
        fireDataChangedEvent(DataChangedListener.ADDED, items.size());
    }

    /**
     * Change the item at the given index
     * 
     * @param index the offset for the item
     * @param item the value to set
     */
    public void setItem(int index, Object item) {
        items.setElementAt(item, index);
        fireDataChangedEvent(DataChangedListener.CHANGED, index);
    }

    /**
     * Adding an item to list at given index
     * @param item - the item to add
     * @param index - the index position in the list
     */
    public void addItemAtIndex(Object item, int index) {
        if (index <= items.size()) {
            items.insertElementAt(item, index);
            fireDataChangedEvent(DataChangedListener.ADDED, index);
        }
    }

    /**
     * @inheritDoc
     */
    public void removeItem(int index) {
        if (index < getSize() && index >= 0) {
            items.removeElementAt(index);
            if (index != 0) {
                setSelectedIndex(index - 1);
            }
            fireDataChangedEvent(DataChangedListener.REMOVED, index);
        }
    }

    /**
     * Removes all elements from the model
     */
    public void removeAll() {
        while (getSize() > 0) {
            removeItem(0);
        }
    }

    /**
     * @inheritDoc
     */
    public void setSelectedIndex(int index) {
        int oldIndex = selectedIndex;
        this.selectedIndex = index;
        selectionListener.fireSelectionEvent(oldIndex, selectedIndex);
    }

    /**
     * @inheritDoc
     */
    public void addDataChangedListener(DataChangedListener l) {
        dataListener.addListener(l);
    }

    /**
     * @inheritDoc
     */
    public void removeDataChangedListener(DataChangedListener l) {
        dataListener.removeListener(l);
    }

    private void fireDataChangedEvent(final int status, final int index) {
        dataListener.fireDataChangeEvent(index, status);
    }

    /**
     * @inheritDoc
     */
    public void addSelectionListener(SelectionListener l) {
        selectionListener.addListener(l);
    }

    /**
     * @inheritDoc
     */
    public void removeSelectionListener(SelectionListener l) {
        selectionListener.removeListener(l);
    }
    
    public String toString() {
     	return items.toString();
    }
}
