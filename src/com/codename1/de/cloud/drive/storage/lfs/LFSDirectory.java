package com.codename1.de.cloud.drive.storage.lfs;

import com.codename1.de.cloud.drive.gui.factory.ImageFactory;
import com.codename1.de.cloud.drive.storage.Directory;

import java.util.Date;
import java.util.Vector;

/**
 * A local file system directory implementation
 * @author Cosmin Zamfir
 *
 */
public class LFSDirectory implements Directory {

    private LFSDrive drive;
    private String name;
    private String absolutePath;
    private Directory parent;
    private Date lastModified;

    public LFSDirectory(LFSDrive drive, String name, String absoutePath, Directory parent) {
        super();
        this.name = name;
        this.absolutePath = absoutePath;
        this.parent = parent;
        this.drive = drive;
    }

    public boolean isDirectory() {
        return true;
    }

    public String name() {
        return name;
    }

    public String absolutePath() {
        return absolutePath;
    }

    public void delete() {
        drive.delete(this);
    }

    public void move(Directory parent) {
    }

    public Date lastModified() {
        if (lastModified == null) {
            lastModified = drive.lastModified(this);
        }
        return lastModified;
    }

    public Directory parent() {
        return parent;
    }

    public Vector children() {
        return drive.list(this);
    }

    public String toString() {
        return name;
    }

    public void copy(Directory parent) {
    }

    public void rename(String newName) {
    }

    public String iconName() {
        return ImageFactory.folder;
    }

}
