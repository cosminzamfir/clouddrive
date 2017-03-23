package com.codename1.de.cloud.util;

import com.codename1.de.cloud.drive.storage.File;

import java.util.Date;

public class FileFilter {

    private String name;
    private long minSize;
    private long maxSize;
    private Date minLastModifiedDate;
    private Date maxLaxtModifiedDate;

    public FileFilter(String name) {
        super();
        this.name = name;
    }

    public FileFilter(long minSize, long maxSize) {
        super();
        this.minSize = minSize;
        this.maxSize = maxSize;
    }

    public FileFilter(Date minLastModifiedDate, Date maxLaxtModifiedDate) {
        super();
        this.minLastModifiedDate = minLastModifiedDate;
        this.maxLaxtModifiedDate = maxLaxtModifiedDate;
    }

    public FileFilter(String name, long minSize, long maxSize, Date minLastModifiedDate, Date maxLaxtModifiedDate) {
        super();
        this.name = name;
        this.minSize = minSize;
        this.maxSize = maxSize;
        this.minLastModifiedDate = minLastModifiedDate;
        this.maxLaxtModifiedDate = maxLaxtModifiedDate;
    }

    public boolean match(File file) {
        if (this.name != null && file.name().indexOf(name.toLowerCase()) < 0) {
            return false;
        }
        if (minSize > 0 && file.size() < minSize) {
            return false;
        }
        if (maxSize > 0 && file.size() > maxSize) {
            return false;
        }
        if (minLastModifiedDate != null && file.lastModified().getTime() < minLastModifiedDate.getTime()) {
            return false;
        }
        if (maxLaxtModifiedDate != null && file.lastModified().getTime() > maxLaxtModifiedDate.getTime()) {
            return true;
        }
        return true;
    }

}
