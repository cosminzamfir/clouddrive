package com.codename1.de.cloud.drive.storage.box;

import com.codename1.de.cloud.drive.gui.factory.ImageFactory;
import com.codename1.de.cloud.drive.storage.Directory;

import java.util.Hashtable;
import java.util.Vector;

/**
 * 
 * @author Cosmin Zamfir
 *
 */
public class BoxDirectory extends BoxStorageItem implements Directory{

	public BoxDirectory(BoxDirectory parent, BoxDrive drive, Hashtable json) {
		super(parent, drive, json);
	}

	public Vector children() {
		return null;
	}

    public boolean isDirectory() {
        return true;
    }

    public String iconName() {
        return ImageFactory.folder;
    }

}
