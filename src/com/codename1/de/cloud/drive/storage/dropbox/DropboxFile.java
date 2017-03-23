package com.codename1.de.cloud.drive.storage.dropbox;

import com.codename1.de.cloud.drive.exception.CloudSyncException;
import com.codename1.de.cloud.drive.storage.Directory;
import com.codename1.de.cloud.drive.storage.File;
import com.codename1.de.cloud.util.CommonUtils;
import com.codename1.io.FileSystemStorage;
import com.codename1.io.Log;
import com.codename1.io.Storage;
import com.codename1.io.Util;


import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Hashtable;

/**
 * 
 * @author Cosmin Zamfir
 *
 */
public class DropboxFile extends DropboxStorrageItem implements File {

    /**
     * JSon format for a dropbox file:
     * "contents": [{"revision": 993, "rev": "3e107b1336b", "thumb_exists": true, "bytes": 1603531, 
     * "modified": "Thu, 07 Jun 2012 05:55:19 +0000", "client_mtime": "Thu, 07 Jun 2012 05:55:19 +0000", 
     * "path": "/Photos/2012-05-20 12.19.03.jpg", "is_dir": false, "icon": "page_white_picture", 
     * "root": "dropbox", "mime_type": "image/jpeg", "size": "1.5 MB"}
     * <p>
     * Extracts file specific attributes
     * @param json
     * @param parent
     * @param drive
     */
    public DropboxFile(Hashtable json, Directory parent, DropBoxDrive drive) {
        super(json, parent, drive);
    }

    public String toString() {
    	return name();
    }

    public InputStream getInputStream() {
        return new ByteArrayInputStream(content());
    }

    public byte[] content() {
        return getDrive().get(this);
    }

    /**
     * Download the file locally and return the file uri
     * <p>
     * Which means that invoking this method involves that the file download
     * @return the uri or <code>null</code> if the download has been interrupted by user
     */
    public String uri() {
        Log.p("Downloading " + this + " to local storage");
    	Storage storage = Storage.getInstance();
        try {
            String name = String.valueOf(System.currentTimeMillis()) + "." + CommonUtils.extension(name());
        	OutputStream os = storage.createOutputStream(name);
            InputStream is = getInputStream();
            Util.copy(is, os);
            return "file://" + name;
        } catch (IOException e) {
            throw new CloudSyncException("Error trying to retrieve the URI for " + name());
        }
    }

    public void stream(OutputStream os) {
    }

}
