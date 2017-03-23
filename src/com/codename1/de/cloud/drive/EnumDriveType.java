package com.codename1.de.cloud.drive;

import java.util.Vector;

/**
 * 
 * @author Cosmin Zamfir
 *
 */
public class EnumDriveType {

    private String type;
    private static String lfs = "lfs";
    private static String dropbox = "dropbox";
    private static String box = "box";
    private static String virtual = "virtual";
    private static Vector types = new Vector();

    static {
        types.addElement(lfs);
        types.addElement(dropbox);
        types.addElement(box);
        types.addElement(virtual);
    }

    public static EnumDriveType LFS = new EnumDriveType(lfs);
    public static EnumDriveType DROPBOX = new EnumDriveType(dropbox);
    public static EnumDriveType BOX = new EnumDriveType(box);
    public static EnumDriveType VIRTUAL = new EnumDriveType(virtual);

    private EnumDriveType(String type) {
        this.type = type;
    }

    public String type() {
        return type;
    }

    public String toString() {
        return type;
    }

    public static EnumDriveType fromString(String type) {
        if (!types.contains(type)) {
            throw new RuntimeException("Unsupported drive type: " + type);
        }
        if (type.equals(lfs)) {
            return LFS;
        } else if (type.equals(dropbox)) {
            return DROPBOX;
        } else if (type.equals(virtual)) {
            return VIRTUAL;
        } else {
            return BOX;
        }
    }
}
