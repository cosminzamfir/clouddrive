package com.codename1.de.cloud.drive.virtual.v1;

import com.codename1.io.Log;

import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

/**
 * 
 * @author Cosmin Zamfir
 *
 */
public class TimeCategory extends Category {

    private static final Calendar c = Calendar.getInstance();
    private Vector directories = new Vector();

    public static TimeCategory instance;

    public static TimeCategory instance() {
        if (instance == null) {
            instance = new TimeCategory();
        }
        return instance;
    }

    private TimeCategory() {
        buildTags();
    }

    public Object value(VirtualItem file) {
        if (file.isDirectory()) {
            return null;
        }
        Date d = file.lastModified();
        if (d == null) {
            return null;
        }
        c.setTime(d);
        return new Integer(c.get(Calendar.YEAR));
    }

    public String getName() {
        return "Time";
    }

    public Byte getId() {
        return new Byte((byte) 3);
    }

    public Vector directories() {
        return directories;
    }

    private void buildTags() {
        int year = 2000;
        int end = Calendar.getInstance().get(Calendar.YEAR);
        while (year <= end) {
            directories.addElement(new VirtualDirectory(VirtualDrive.instance(), String.valueOf(year),
                    new Integer(year), this));
            year++;

        }
    }

    public boolean isExclusive() {
        return false;
    }

    public boolean match(VirtualDirectory vDir, VirtualItem item) {
        Integer year = (Integer) value(item);
        Integer y = (Integer) vDir.getMetadata();
        if (y.equals(year)) {
            Log.p(item + " matched by " + vDir + " for category " + this, Log.DEBUG);
            return true;
        }
        return false;
    }
}
