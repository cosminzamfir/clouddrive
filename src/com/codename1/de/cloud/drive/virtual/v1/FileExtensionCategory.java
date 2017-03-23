package com.codename1.de.cloud.drive.virtual.v1;


import com.codename1.de.cloud.util.CommonUtils;
import com.codename1.io.Log;

import java.util.Hashtable;
import java.util.Vector;

/**
 * 
 * @author Cosmin Zamfir
 *
 */
public class FileExtensionCategory extends Category {

    /**
     * Maps extensions to the {@link VirtualDirectory} objects
     */
    private Hashtable extensions = new Hashtable();
    
    private Vector directories = new Vector();
    
    private static FileExtensionCategory instance;

    private FileExtensionCategory() {
    }

    public static FileExtensionCategory instance() {
        if (instance == null) {
            instance = new FileExtensionCategory();
        }
        return instance;
    }


    public Object value(VirtualFile file) {
        return file.absolutePath();
    }

    public String getName() {
        return "FileExtension";
    }

    public boolean isExclusive() {
        return false;
    }

    public Byte getId() {
        return new Byte((byte) 5);
    }

    public String getIcon() {
        return "icons/categories/absolute-path.png";
    }

    /**
     * Tags are not predefined as for the other categories; they are built while files are being processed
     */
    public boolean process(VirtualItem item) {
    	String extension = (String) value(item);
    	if (extensions.containsKey(extension)) {
            VirtualDirectory vDir = (VirtualDirectory) extensions.get(extension);
            Log.p(item + " matched by " + vDir + " for category " + this, Log.DEBUG);
            vDir.addChild(item);
		} else {
			VirtualDirectory vDir = new VirtualDirectory(VirtualDrive.instance(), extension, this);
			vDir.addChild(item);
            Log.p(item + " matched by " + vDir + " for category " + this, Log.DEBUG);
			extensions.put(extension, vDir);
			directories.addElement(vDir);
		}
    	return true;
    	
    }

    public Object value(VirtualItem file) {
        return CommonUtils.extension(file.name());
    }

    public Vector directories() {
    	return directories;
    }

    /**
     * We provide own process() implementation  where match() is not relevant
     */
    public boolean match(VirtualDirectory vDir, VirtualItem item) {
        return true;
    }

}
