package com.codename1.de.cloud.drive.storage.dropbox;

import com.codename1.de.cloud.drive.exception.CloudSyncException;
import com.codename1.de.cloud.drive.storage.Directory;
import com.codename1.de.cloud.drive.storage.StorageItem;
import com.codename1.de.cloud.util.CommonUtils;

import java.util.Date;
import java.util.Hashtable;

/**
 * 
 * @author Cosmin Zamfir
 *
 */
public abstract class DropboxStorrageItem implements StorageItem {

    private DropBoxDrive drive;
    private Directory parent;
    private Double revision;
    /**Size in bytes*/
    private Double size;
    /**
     * The last time the file was modified on Dropbox, in the standard date format (not included for the root folder)
     */
    private Date  modified;
    /**
     * For files, this is the modification time set by the desktop client when the file was added to Dropbox, 
     * in the standard date format. 
     * Since this time is not verified (the Dropbox server stores whatever the desktop client sends up), 
     * this should only be used for display purposes (such as sorting) and not, for example, 
     * to determine if a file has changed or not.
     */
    private Date clientMtime;
    /**The canonical path to the file or directory.*/
    private String absolutePath;
    private String name;
    private boolean isDirectory;
    /**
     * JSon format for a dropbox file:
     * "contents": [{"revision": 993, "rev": "3e107b1336b", "thumb_exists": true, "bytes": 1603531, 
     * "modified": "Thu, 07 Jun 2012 05:55:19 +0000", "client_mtime": "Thu, 07 Jun 2012 05:55:19 +0000", 
     * "path": "/Photos/2012-05-20 12.19.03.jpg", "is_dir": false, "icon": "page_white_picture", 
     * "root": "dropbox", "mime_type": "image/jpeg", "size": "1.5 MB"}
     * <p>
     * Json format for a dropbox directory:
     * "contents": [{"revision": 1, "rev": "107b1336b", "thumb_exists": false, "bytes": 0, 
     * "modified": "Sun, 13 May 2012 22:11:07 +0000", "path": "/Photos", "is_dir": true, "icon": "folder_photos", 
     * "root": "dropbox", "size": "0 bytes"}], "icon": "folder"}
     * <p>
     * Extract the common file/directory attributes from JSon
     * @param response
     */
    public DropboxStorrageItem(Hashtable json, Directory parent, DropBoxDrive drive) {
        this.revision = (Double) json.get("revision");
        this.size = (Double) json.get("bytes");
        this.modified = CommonUtils.parseDropBoxDate((String) json.get("modified"));
        this.absolutePath = (String) json.get("path");
        this.name = absolutePath.substring(absolutePath.lastIndexOf('/') + 1);
        this.isDirectory = CommonUtils.getBoolean((String) json.get("is_dir"));
        this.parent = parent;
        this.drive = drive;
    }

    public static DropboxStorrageItem newInstance(Hashtable json, Directory parent, DropBoxDrive drive) {
        if (CommonUtils.getBoolean((String) json.get("is_dir"))) {
            return new DropBoxDirectory(json, parent, drive);
        } else {
            return new DropboxFile(json, parent, drive);
        }

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
        drive.move(this, parent);
    }

    public void copy(Directory parent) {
        drive.copy(this, parent);
    }

    public Date lastModified() {
        return modified;
    }

    public Directory parent() {
        return parent;
    }

    public boolean isDirectory() {
        return isDirectory;
    }

    public DropBoxDrive getDrive() {
        return drive;
    }
    
    public long size() {
    	return size.longValue();
    }

    public String uri() {
        throw new CloudSyncException("DropBox uri() not implemented yet");
    }

    public void rename(String newName) {
    }

}
