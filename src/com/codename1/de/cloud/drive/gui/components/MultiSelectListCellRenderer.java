package com.codename1.de.cloud.drive.gui.components;

import com.codename1.de.cloud.drive.gui.actions.BaseActionListener;
import com.codename1.de.cloud.drive.gui.factory.ImageFactory;
import com.codename1.io.Log;
import com.codename1.ui.CheckBox;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Font;
import com.codename1.ui.Label;
import com.codename1.ui.List;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.list.ListCellRenderer;
import com.codename1.ui.plaf.Border;


import java.util.Hashtable;

/**
 * Checkbox + Label with item.toString() 
 * <p>
 * There is a 2-way flow required: 
 * <p>
 * view->model: Updating the checkbox updates the {@link MultiSelectListModel#getSelection()} {@link Hashtable}
 * <p>
 * view->model->view: Updating the "Select ALL" check-box updates each element in selection model, which must update the state 
 * of the other check-boxes
 * The update flow is   
 * @author Cosmin Zamfir
 *
 */
public class MultiSelectListCellRenderer extends Container implements ListCellRenderer {

    private MultiSelectListModel listModel;
    private MultiSelectList list;

    private Label focus = new Label("");
    private Label label;
    private CheckBox checkBox;
    private SelectAction selectAction;
    /**
     * The object being displayed in this cell
     */
    private Object value;

    public MultiSelectListCellRenderer(MultiSelectListModel listModel, MultiSelectList list) {
        this.listModel = listModel;
        this.list = list;
        setLayout(new BorderLayout());
        checkBox = new CheckBox();
        checkBox.getStyle().setBgTransparency(0);
        checkBox.addActionListener(new BaseActionListener() {
            protected void execute(ActionEvent evt) {
                checkBoxChanged(evt);
            }
        });
        listModel.addCheckbox(checkBox);
        addComponent(BorderLayout.EAST, checkBox);
        label = new Label();
        label.getStyle().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_MEDIUM));
        label.getStyle().setBgTransparency(0);
        addComponent(BorderLayout.CENTER, label);

        Border upper = Border.createBevelRaised();
        upper.setThickness(1);
        Border border =
                Border.createCompoundBorder(upper, Border.createEmpty(), Border.createEmpty(), Border.createEmpty());
        getStyle().setBorder(border);
    }

    public MultiSelectListCellRenderer(MultiSelectListModel model, MultiSelectList multiSelectList, SelectAction action) {
    	this(model, multiSelectList);
    	this.selectAction = action;
    }

	public Component getListCellRendererComponent(List list, final Object value, int index, boolean isSelected) {
        Log.p("Rendering " + value, Log.DEBUG);
        this.value = value;
        checkBox.setSelected(listModel.isItemSelected(value));

        label.setText(value.toString());
        focus.getStyle().setBgTransparency(0);
        getStyle().setMargin(10, 10, 0, 0);
        return this;
    }

    /**
     * Invoked when the checkBox selection has been changed.
     * Update the model with the new selection; see {@link MultiSelectListModel#getSelection()}
     * <p>
     * If "select ALL" check-box has been selected, update accordingly all other check-boxes in this {@link MultiSelectList}
     * @param evt
     */
    private void checkBoxChanged(ActionEvent evt) {
        boolean selected = ((CheckBox) evt.getComponent()).isSelected();
        if (value == MultiSelectListModel.ALL) {
            listModel.updateAllCheckBoxes(selected);
            return;
        }
        listModel.selectionChanged(value, selected);
        if (selectAction != null) {
			selectAction.execute(value);
		}
    }

    public Component getListFocusComponent(List list) {
        return focus;
    }

}
