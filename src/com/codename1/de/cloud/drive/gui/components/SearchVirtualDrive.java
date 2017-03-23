package com.codename1.de.cloud.drive.gui.components;

import com.codename1.de.cloud.drive.gui.actions.BaseActionListener;
import com.codename1.de.cloud.drive.gui.actions.navigation.NavigationManager;
import com.codename1.de.cloud.drive.gui.containers.DriveManager;
import com.codename1.de.cloud.drive.gui.containers.StandardDialog;
import com.codename1.de.cloud.drive.gui.factory.ComponentsFactory;
import com.codename1.de.cloud.drive.gui.factory.ImageFactory;
import com.codename1.de.cloud.drive.virtual.v1.Category;
import com.codename1.de.cloud.drive.virtual.v1.VirtualDrive;
import com.codename1.de.cloud.util.DriveUtils;
import com.codename1.de.cloud.util.VirtualFileFilter;
import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Container;
import com.codename1.ui.Font;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.table.TableLayout;

import java.util.Enumeration;
import java.util.Vector;

public class SearchVirtualDrive extends StandardDialog {

    private VirtualDrive virtualDrive;
    private DriveManager driveManager;

    public SearchVirtualDrive(VirtualDrive virtualDrive, DriveManager driveManager) {
        super(ImageFactory.instance(ImageFactory.search, ImageFactory.MEDIUM), "Search", com.codename1.de.cloud.util.Colors.SLATE_BLUE);
        this.virtualDrive = virtualDrive;
        this.driveManager = driveManager;
        addContent();
    }

    private void addContent() {
        Vector categories = virtualDrive.getCategories();
        Enumeration e = categories.elements();
        Container c1 = new Container(new TableLayout(categories.size() + 1, 2));
        Label l = getLabel("Name");
        c1.addComponent(ComponentsFactory.rightConstraint(), l);
        final TextField text = new TextField(20);
        
        text.setPreferredH(ImageFactory.MEDIUM/3);
        c1.addComponent(text);
        final Vector combos = new Vector();
        while (e.hasMoreElements()) {
            Category category = (Category) e.nextElement();
            Label label = getLabel(category.getName());
            c1.addComponent(ComponentsFactory.rightConstraint(), label);
            ComboBox combo = DefaultComboBox.newInstance(category.directories(), true);
            combos.addElement(combo);
            c1.addComponent(combo);

        }
        getContentPane().addComponent(BorderLayout.CENTER, c1);

        Vector buttons = new Vector();
        Button ok = new Button("OK");
        ok.setPreferredH(ImageFactory.MEDIUM);
        ok.addActionListener(new BaseActionListener() {

            protected void execute(ActionEvent evt) {
                Vector vDirs = new Vector();
                Enumeration e = combos.elements();
                while (e.hasMoreElements()) {
                    DefaultComboBox combo = (DefaultComboBox) e.nextElement();
                    if (!combo.isAll()) {
                        vDirs.addElement(combo.getSelectedItem());
                    }
                }
                VirtualFileFilter filter = new VirtualFileFilter(text.getText(), vDirs);
                Vector items = DriveUtils.searchVirtualDrive(null, filter);
                driveManager.setItems(items);
                dispose();
            }
        });
        Button cancel = new Button("Cancel");
        cancel.setPreferredH(ImageFactory.MEDIUM);
        cancel.addActionListener(new BaseActionListener() {

            protected void execute(ActionEvent evt) {
                dispose();
            }
        });
        buttons.addElement(ok);
        buttons.addElement(cancel);
        getContentPane().addComponent(BorderLayout.SOUTH, new HorizontalComponentsContainer(buttons));
    }

    private Label getLabel(String text) {
        Label res = new Label(text);
        res.getStyle().setBgTransparency(0);
        res.getStyle().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_MEDIUM));
        res.getStyle().setFgColor(com.codename1.de.cloud.util.Colors.WHITE);
        return res;
    }
    
    public void repaint() {
    	this.setPreferredH(NavigationManager.getRoot().getPreferredH() * 8 / 10);
    	this.setPreferredW(NavigationManager.getRoot().getPreferredW() * 8 / 10);
    	super.repaint();
    }

}
