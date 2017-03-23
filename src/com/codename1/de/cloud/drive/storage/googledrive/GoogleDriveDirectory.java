package com.codename1.de.cloud.drive.storage.googledrive;

import com.codename1.de.cloud.drive.gui.factory.ImageFactory;
import com.codename1.de.cloud.drive.storage.Directory;
import com.codename1.de.cloud.drive.virtual.v1.VirtualDirectory;
import com.codename1.de.cloud.drive.virtual.v1.VirtualDrive;
import com.codename1.de.cloud.drive.virtual.v1.VirtualFile;

import java.util.Date;
import java.util.Vector;

/**
 * A file in google drive can have more than one parent directory.
 * This is a similar concept to a {@link VirtualDrive}, with a {@link VirtualFile} having more
 * than one {@link VirtualDirectory} parent.
 * @author Cosmin Zamfir
 *
 */
public class GoogleDriveDirectory implements Directory {

    public String name() {
        return null;
    }

    public String absolutePath() {
        return null;
    }

    public Directory parent() {
        return null;
    }

    public boolean isDirectory() {
        return false;
    }

    public Date lastModified() {
        return null;
    }

    public void delete() {
    }

    public void move(Directory parent) {
    }

    public void copy(Directory parent) {
    }

    public void rename(String newName) {
    }

    public Vector children() {
        return null;
    }

    public String iconName() {
        return ImageFactory.folder;
    }

}
