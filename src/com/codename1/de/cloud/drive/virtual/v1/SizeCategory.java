package com.codename1.de.cloud.drive.virtual.v1;

import com.codename1.io.Log;

import java.util.Vector;

/**
 * Classify file based on the creation year
 * 2000 to present
 * @author Cosmin Zamfir
 *
 */
public class SizeCategory extends Category {

    private static final String small = "Small (<1MB)";
    private static final String average = "Average (<5MB)";
    private static final String large = "Large (<20MB)";
    private static final String xxl = "XXL (<1GB)";
    private static final String huge = "Huge!(>1GB)";

    private static int _1mb = 1024 * 1024;
    private static int _5mb = 5 * _1mb;
    private static int _20mb = 20 * _1mb;
    private static int _1gb = 1024 * _1mb;

    private Vector directories = new Vector();

    public static SizeCategory instance;

    public static SizeCategory instance() {
        if (instance == null) {
            instance = new SizeCategory();
        }
        return instance;
    }

    private SizeCategory() {
        buildTags();
    }

    public Object value(VirtualItem item) {
        if (item.isDirectory()) {
            return null;
        }
        long size = ((VirtualFile) item).size();
        if (size < _1mb) {
            return small;
        } else if (size < _5mb) {
            return average;
        } else if (size < _20mb) {
            return large;
        } else if (size < _1gb) {
            return xxl;
        } else {
            return huge;
        }
    }

    public String getName() {
        return "Size";
    }

    public Byte getId() {
        return new Byte((byte) 2);
    }

    public Vector directories() {
        return directories;
    }

    private void buildTags() {
        directories.addElement(new VirtualDirectory(VirtualDrive.instance(), small, small, this));
        directories.addElement(new VirtualDirectory(VirtualDrive.instance(), average, average, this));
        directories.addElement(new VirtualDirectory(VirtualDrive.instance(), large, large, this));
        directories.addElement(new VirtualDirectory(VirtualDrive.instance(), xxl, xxl, this));
        directories.addElement(new VirtualDirectory(VirtualDrive.instance(), huge, huge, this));
    }

    public boolean isExclusive() {
        return false;
    }

    public boolean match(VirtualDirectory vDir, VirtualItem item) {
        String size = (String) value(item);
        String s = (String) vDir.getMetadata();
        if (size.equals(s)) {
            Log.p(item + " matched by " + vDir + " for category " + this, Log.DEBUG);
            return true;
        }
        return false;
    }

}
