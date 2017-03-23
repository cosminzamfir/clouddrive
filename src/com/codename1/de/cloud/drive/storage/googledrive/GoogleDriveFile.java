package com.codename1.de.cloud.drive.storage.googledrive;

import com.codename1.de.cloud.drive.storage.Directory;
import com.codename1.de.cloud.drive.storage.File;
import com.codename1.de.cloud.util.CommonUtils;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.Hashtable;
import java.util.Vector;

public class GoogleDriveFile implements File {

    private String name;
    private Date lastModified;
    private long size;
    private Vector parents;

    public GoogleDriveFile(Hashtable jsonItem, GoogleDrive googleDrive) {
        lastModified = CommonUtils.parseGoogleDate((String) jsonItem.get("modifiedDate"));
        name = (String) jsonItem.get("originalFilename");
        size = ((Long) jsonItem.get("fileSize")).longValue();
    }

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

    public long size() {
        return 0;
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

}
