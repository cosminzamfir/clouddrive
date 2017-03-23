package com.codename1.de.cloud.drive.virtual.v1;

import com.codename1.de.cloud.util.CommonUtils;
import com.codename1.io.Log;

import java.util.Vector;

/**
 * Classify files based on types
 * @author Cosmin Zamfir
 *
 */
public class FileTypeCategory extends Category {

    private static FileTypeCategory instance;
    public static final String documents = "Documents";
    public static final String music = "Music";
    public static final String videos = "Videos";
    public static final String pictures = "Pictures";
    public static final String programming = "Programming";

    private Vector directories = new Vector();

    private FileTypeCategory() {
        buildTags();
    }

    public static FileTypeCategory instance() {
        if (instance == null) {
            instance = new FileTypeCategory();
        }
        return instance;
    }

    public Object value(VirtualItem file) {
        return CommonUtils.extension(file.name());
    }

    public String getName() {
        return "Type";
    }

    public Byte getId() {
        return new Byte((byte) 1);
    }

    private void buildTags() {
        Vector values = new Vector();
        values.addElement("doc");
        values.addElement("docx");
        values.addElement("xls");
        values.addElement("xlsx");
        values.addElement("pdf");
        values.addElement("ppt");
        values.addElement("pptx");
        directories.addElement(new VirtualDirectory(VirtualDrive.instance(), "Documents", values, this));

        values = new Vector();
        //values.addElement("gif");
        //values.addElement("png");
        values.addElement("jpeg");
        values.addElement("jpg");
        //values.addElement("bmp");
        directories.addElement(new VirtualDirectory(VirtualDrive.instance(), "Pictures", values, this));

        values = new Vector();
        values.addElement("mp3");
        values.addElement("wav");
        values.addElement("mid");
        values.addElement("aif");
        directories.addElement(new VirtualDirectory(VirtualDrive.instance(), "Music", values, this));

        values = new Vector();
        values.addElement("mov");
        values.addElement("mpg");
        values.addElement("3gp");
        values.addElement("mp4");
        directories.addElement(new VirtualDirectory(VirtualDrive.instance(), "Videos", values, this));

        values = new Vector();
        values.addElement("java");
        //values.addElement("xml");
        values.addElement("cpp");
        directories.addElement(new VirtualDirectory(VirtualDrive.instance(), "Programming", values, this));

    }

    public boolean isExclusive() {
        return true;
    }

    public Vector directories() {
        return directories;
    }

    public boolean match(VirtualDirectory vDir, VirtualItem item) {
        String extension = (String) value(item);
        Vector extensions = (Vector) vDir.getMetadata();
        if (extensions.contains(extension)) {
            Log.p(item + " matched by " + vDir + " for category " + this, Log.DEBUG);
            return true;
        }
        return false;
    }

}
