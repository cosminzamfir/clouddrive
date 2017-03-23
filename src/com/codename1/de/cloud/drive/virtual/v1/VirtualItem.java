package com.codename1.de.cloud.drive.virtual.v1;

import com.codename1.de.cloud.drive.storage.Directory;
import com.codename1.de.cloud.drive.storage.File;
import com.codename1.de.cloud.drive.storage.StorageItem;

/**
 * 
 * @author Cosmin Zamfir
 *
 */
public abstract class VirtualItem implements StorageItem {

    private VirtualDrive drive;
    private String name;

    public VirtualItem(VirtualDrive virtualDrive, String name) {
        this.drive = virtualDrive;
        this.name = name;
    }

    public static VirtualItem instance(VirtualDrive drive, StorageItem item) {
        if (item.isDirectory()) {
            return new VirtualDirectory(drive, (Directory) item, null);
        } else {
            return new VirtualFile(drive, (File) item);
        }
    }

    public String name() {
        return name;
    }

    public String absolutePath() {
        return null;
    }

    public Directory parent() {
        return null;
    }

    public String toString() {
        return name();
    }

    public void delete() {
    }

    public void move(Directory parent) {
    }

    public void copy(Directory parent) {
    }

    public VirtualDrive getDrive() {
        return drive;
    }

}
