package com.codename1.de.cloud.drive.gui.components;

import com.codename1.de.cloud.drive.gui.actions.BaseActionListener;
import com.codename1.de.cloud.drive.gui.actions.FileClickedActionListener;
import com.codename1.de.cloud.drive.gui.containers.DriveManager;
import com.codename1.de.cloud.drive.gui.containers.MappingInfoDialog;
import com.codename1.de.cloud.drive.gui.containers.VerticalButtonsDialog;
import com.codename1.de.cloud.drive.gui.factory.ImageFactory;
import com.codename1.de.cloud.drive.gui.model.DefaultListModel;
import com.codename1.de.cloud.drive.storage.Directory;
import com.codename1.de.cloud.drive.storage.File;
import com.codename1.de.cloud.drive.storage.StorageItem;
import com.codename1.de.cloud.util.CommonUtils;
import com.codename1.ui.Button;
import com.codename1.ui.Dialog;
import com.codename1.ui.List;
import com.codename1.ui.events.ActionEvent;

import java.util.Vector;

/**
 * A {@link List} extension to manager a {@link Vector} of {@link StorageItem}
 * @author Cosmin Zamfir
 *
 */
public class FileList extends List {

    private DriveManager driveManager;

    public FileList(DriveManager driveManager) {
        super(new DefaultListModel());
        this.driveManager = driveManager;
        addActionListener(new FileClickedActionListener(this.driveManager));
    }

    public FileList(Vector items, DriveManager driveManager) {
        super(new DefaultListModel(items));
        this.driveManager = driveManager;
        addActionListener(new FileClickedActionListener(this.driveManager));
    }

    public void longPointerPress(int x, int y) {
        super.longPointerPress(x, y);
        //super implementation sets the selected model index of the pressed item
        StorageItem item = (StorageItem) getModel().getItemAt(getModel().getSelectedIndex());
        showFileOperations(item);
    }

    /**
     * Display various info about the specified {@link StorageItem} together with the supported operations, e.g. delete, move, copy, synch to cloud etc
     * @param item
     */
    private void showFileOperations(StorageItem item) {
        Dialog d =
                new VerticalButtonsDialog((item.isDirectory() ? ImageFactory.instance(ImageFactory.folder,
                        ImageFactory.MEDIUM) : ImageFactory.instanceForFileType(item.name(), ImageFactory.MEDIUM)),
                        "File operations", getFileOperationsButtons(item));
        d.show();
    }

    private Vector getFileOperationsButtons(StorageItem item) {
        //file operations
        Vector buttons = new Vector();
        OButton delete = new OButton("Delete", item);
        OButton rename = new OButton("Rename", item);
        OButton toCloud = new OButton("Copy To Me.Everywhere", item);
        OButton toLocal = new OButton("Copy To the local device", item);
        OButton properties = new OButton("Properties", item);
        OButton share = new OButton("Share", item);
        Button cancel = new Button("Cancel");
        buttons.addElement(delete);
        buttons.addElement(rename);
        buttons.addElement(toCloud);
        buttons.addElement(toLocal);
        buttons.addElement(share);
        buttons.addElement(properties);
        buttons.addElement(cancel);

        delete.addActionListener(new BaseActionListener() {
            protected void execute(ActionEvent evt) {
                ConfirmDialog d =
                        new ConfirmDialog("Are you sure you want to delete "
                                + ((StorageItem) ((OButton) evt.getComponent()).getO()).name());
                d.show();
                if (d.proceed()) {
                    ((StorageItem) ((OButton) evt.getComponent()).getO()).delete();
                    new ShortInfoDialog("Deleted.", 500);
                    driveManager.refresh();
                }
            }
        });

        rename.addActionListener(new BaseActionListener() {
            protected void execute(ActionEvent evt) {
                StorageItem item = ((StorageItem) ((OButton) evt.getComponent()).getO());
                InputTextDialog d = new InputTextDialog("Please enter the new name;", item.name(), false);
                d.show();
                if (!d.isCanceled()) {
                    String newName = d.getValue();
                    item.rename(newName);
                }
            }
        });

        toCloud.addActionListener(new BaseActionListener() {
            protected void execute(ActionEvent evt) {
                //copy item to local device
            }
        });

        toLocal.addActionListener(new BaseActionListener() {
            protected void execute(ActionEvent evt) {
                //copy item to the cloud 
            }
        });

        cancel.addActionListener(new BaseActionListener() {
            protected void execute(ActionEvent evt) {
            }
        });

        properties.addActionListener(new BaseActionListener() {
            protected void execute(ActionEvent evt) {
                StorageItem item = ((StorageItem) ((OButton) evt.getComponent()).getO());
                String[] keys;
                String[] values;

                if (!item.isDirectory()) {
                    keys = new String[5];
                    values = new String[5];
                    keys[0] = "Name:";
                    keys[1] = "Path:";
                    keys[2] = "Size:";
                    keys[3] = "Last modified:";
                    keys[4] = "Drive:";
                    values[0] = item.name();
                    if (item.parent() != null) {
                        values[1] = item.parent().absolutePath();
                    } else {
                        values[1] = "/";
                    }
                    values[2] = CommonUtils.formatFileSize(((File) item).size());
                    values[3] = CommonUtils.format(item.lastModified());
                    values[4] = "To be implemented";
                    
                } else {
                    keys = new String[4];
                    values = new String[4];
                    keys[0] = "Name:";
                    keys[1] = "Path:";
                    keys[2] = "Children:";
                    keys[3] = "Last modified: ";
                    values[0] = item.name();
                    if (item.parent() != null) {
                        values[1] = item.parent().absolutePath();
                    } else {
                        values[1] = "/";
                    }
                    values[2] = String.valueOf(((Directory) item).children().size());
                    values[3] = CommonUtils.format(item.lastModified());
                }
                MappingInfoDialog d =
                        new MappingInfoDialog(ImageFactory.instanceForFileType(item.name(), ImageFactory.MEDIUM),
                                "Properties", keys, values);
                d.show();
            }
        });
        return buttons;
    }
}
