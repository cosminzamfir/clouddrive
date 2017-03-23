package com.codename1.de.cloud.drive.storage.box;

import com.codename1.de.cloud.drive.storage.File;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Hashtable;

/**
 * 
 * @author Cosmin Zamfir
 *
 */
public class BoxFile extends BoxStorageItem implements File {

	public BoxFile(BoxDirectory parent, BoxDrive drive, Hashtable json) {
		super(parent, drive, json);
	}

	public long size() {
		return 0;
	}

    public boolean isDirectory() {
        return false;
    }

    public InputStream getInputStream() {
        return null;
    }

    public byte[] content() {
        return null;
    }

    public void stream(OutputStream os) {
    }


}
