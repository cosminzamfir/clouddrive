package com.codename1.de.cloud.drive.gui.containers;

import com.codename1.de.cloud.drive.gui.actions.BaseActionListener;
import com.codename1.de.cloud.drive.gui.components.HorizontalComponentsContainer;
import com.codename1.de.cloud.drive.gui.components.MultiSelectList;
import com.codename1.de.cloud.drive.gui.components.SelectAction;
import com.codename1.de.cloud.drive.gui.factory.ComponentsFactory;
import com.codename1.de.cloud.drive.gui.factory.ImageFactory;
import com.codename1.de.cloud.drive.virtual.v1.Category;
import com.codename1.de.cloud.drive.virtual.v1.View;
import com.codename1.de.cloud.drive.virtual.v1.VirtualDirectory;
import com.codename1.de.cloud.drive.virtual.v1.VirtualDrive;
import com.codename1.de.cloud.util.Colors;
import com.codename1.ui.Button;
import com.codename1.ui.Dialog;
import com.codename1.ui.Font;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.plaf.Border;

import java.util.Vector;

public class ViewBuilder extends StandardDialog {

    private DriveManager driveManager;

    public ViewBuilder(DriveManager driveManager) {
        super(ImageFactory.instance(ImageFactory.virtualViews, ImageFactory.MEDIUM), "View Builder",
                com.codename1.de.cloud.util.Colors.SLATE_BLUE);
        this.driveManager = driveManager;
        addContent();
    }


    private void addContent() {
        Vector categories = VirtualDrive.instance().getCategories();

        final MultiSelectList list = new MultiSelectList(categories, new SelectAction() {

            public void execute(Object value) {
                showCategoryFilter((Category) value);
            }
        });
        getContentPane().addComponent(BorderLayout.CENTER, list);

        Button apply = new Button("Apply");
        apply.getStyle().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_MEDIUM));

        Button cancel = new Button("Cancel");
        apply.getStyle().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_MEDIUM));
        apply.addActionListener(new BaseActionListener() {

            protected void execute(ActionEvent evt) {
                dispose();
                VirtualDrive.instance().setView(new View(list.getSelectedItems()));
                driveManager.setCurrentDirectory(null);
            }
        });
        cancel.addActionListener(new BaseActionListener() {

            protected void execute(ActionEvent evt) {
                dispose();
            }
        });
        Vector buttons = new Vector();
        buttons.addElement(apply);
        buttons.addElement(cancel);
        HorizontalComponentsContainer south = new HorizontalComponentsContainer(buttons);
        south.getStyle().setBorder(Border.createRoundBorder(20, 20, Colors.BLACK, true));
        getContentPane().addComponent(BorderLayout.SOUTH, south);
    }

    /**
     * Show a dialog to choose the {@link VirtualDirectory}s to be displayed for
     * a given category
     */
    private void showCategoryFilter(final Category category) {
        final Dialog dialog =
                ComponentsFactory.createStandardDialog(
                        ImageFactory.instance(ImageFactory.categoryFilter, ImageFactory.MEDIUM), "Select Tags");

        final MultiSelectList list = new MultiSelectList(category.directories());
        dialog.getContentPane().addComponent(BorderLayout.CENTER, list);
        Button apply = new Button("Apply");
        apply.addActionListener(new BaseActionListener() {
            protected void execute(ActionEvent evt) {
                dialog.dispose();
                Vector filter = list.getSelectedItems();
                category.setFilter(filter);
            }
        });
        Button cancel = new Button("Cancel");
        cancel.addActionListener(new BaseActionListener() {
            protected void execute(ActionEvent evt) {
                dialog.dispose();
            }
        });
        Vector buttons = new Vector();
        buttons.addElement(apply);
        buttons.addElement(cancel);
        HorizontalComponentsContainer south = new HorizontalComponentsContainer(buttons);
        dialog.getContentPane().addComponent(BorderLayout.SOUTH, south);
        dialog.show();
    }
}