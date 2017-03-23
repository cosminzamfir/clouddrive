package com.codename1.de.cloud.drive.storage.dropbox;

import com.codename1.de.cloud.drive.gui.factory.ImageFactory;
import com.codename1.de.cloud.drive.storage.Directory;

import java.util.Hashtable;
import java.util.Vector;

/**
 * 
 * @author Cosmin Zamfir
 *
 */
public class DropBoxDirectory extends DropboxStorrageItem implements Directory {

    /**
     * Extract directory specific attributes from the json data
     * <p>
     * Json format for a dropbox directory:
     * "contents": [{"revision": 1, "rev": "107b1336b", "thumb_exists": false, "bytes": 0, 
     * "modified": "Sun, 13 May 2012 22:11:07 +0000", "path": "/Photos", "is_dir": true, "icon": "folder_photos", 
     * "root": "dropbox", "size": "0 bytes"}], "icon": "folder"}
     * @param response
     * @param json
     * @param parent
     * @param drive
     */
    public DropBoxDirectory(Hashtable json, Directory parent, DropBoxDrive drive) {
        super(json, parent, drive);
        //no directory specific attributes yet
    }

    public Vector children() {
        return getDrive().list(this);
    }
    
    public String toString() {
    	return name();
    }

    public String iconName() {
        return ImageFactory.folder;
    }

}
