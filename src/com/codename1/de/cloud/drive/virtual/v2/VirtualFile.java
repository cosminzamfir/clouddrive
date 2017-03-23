package com.codename1.de.cloud.drive.virtual.v2;

import com.codename1.de.cloud.drive.DriveFactory;
import com.codename1.de.cloud.drive.storage.Directory;
import com.codename1.de.cloud.drive.storage.Drive;
import com.codename1.de.cloud.drive.storage.File;
import com.codename1.de.cloud.drive.virtual.v1.VirtualDirectory;
import com.codename1.de.cloud.drive.virtual.v1.VirtualDrive;
import com.codename1.de.cloud.util.CommonUtils;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;

/**
 * An implementation which should consume less memory
 * @author Cosmin Zamfir
 *
 */
public class VirtualFile implements File {

    private VirtualDrive drive;
    private Drive handle;
    private Integer id;
    /**
     * Format: <absoluteName>:<driveId>:<size>:<lastModified>
     */
    private String metadata;
    /**
     * The 'parent' of this file - dependent on the
     * current view of the drive
     */
    private VirtualDirectory parent;

    public Directory parent() {
        return parent;
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

    public long size() {
        return getSize();
    }

    private long getSize() {
        return Long.parseLong((String) (CommonUtils.split(metadata, ':').elementAt(0)));
    }

    private Drive getDrive() {
        return DriveFactory.getLFSDriveInstance();
    }


    public InputStream getInputStream() {
        return null;
    }

    public byte[] content() {
        return null;
    }

    public String uri() {
        return null;
    }

    public void stream(OutputStream os) {
    }

    public String name() {
        return null;
    }

    public String absolutePath() {
        return null;
    }

    public void rename(String newName) {
    }
}
