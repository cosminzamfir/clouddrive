package com.codename1.de.cloud.drive.virtual.v1;

import com.codename1.de.cloud.drive.storage.Directory;
import com.codename1.de.cloud.drive.storage.File;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;

/**
 * 
 * @author Cosmin Zamfir
 *
 */
public class VirtualFile extends VirtualItem implements File {

    private File handle;
    private VirtualDirectory parent;

    public VirtualFile(VirtualDrive drive, File handle) {
        super(drive, handle.name());
        this.handle = handle;
    }

    public String name() {
        return handle.name();
    }

    public String absolutePath() {
        return parent.absolutePath() + "/" + handle.name();
    }

    public Directory parent() {
        return parent;
    }

    public boolean isDirectory() {
        return false;
    }

    public Date lastModified() {
        return handle.lastModified();
    }

    public void delete() {
        handle.delete();
        //remove it from the virtual drive as well
    }

    public void move(Directory parent) {
        //virtual drives should be read-only
    }

    public void copy(Directory parent) {
    }

    public long size() {
        return handle.size();
    }

    public InputStream getInputStream() {
        return null;
    }

    public byte[] content() {
        return null;
    }

    public String uri() {
        return handle.uri();
    }

    public void stream(OutputStream os) {
    }

    public String toString() {
        return name();
    }

    public void rename(String newName) {
    }
}
