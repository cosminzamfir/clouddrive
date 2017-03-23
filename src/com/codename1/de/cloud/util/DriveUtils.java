package com.codename1.de.cloud.util;

import com.codename1.de.cloud.drive.storage.Directory;
import com.codename1.de.cloud.drive.storage.Drive;
import com.codename1.de.cloud.drive.storage.File;
import com.codename1.de.cloud.drive.storage.StorageItem;
import com.codename1.de.cloud.drive.virtual.v1.Category;
import com.codename1.de.cloud.drive.virtual.v1.FileTypeCategory;
import com.codename1.de.cloud.drive.virtual.v1.View;
import com.codename1.de.cloud.drive.virtual.v1.VirtualDirectory;
import com.codename1.de.cloud.drive.virtual.v1.VirtualItem;

import java.util.Enumeration;
import java.util.Vector;

public class DriveUtils {

    public static Vector searchPhisicalDrive(Drive drive, String path, FileFilter filter, boolean recursive) {
        Vector res = new Vector();
        Vector content;
        if (path == null) {
            content = drive.list();
        } else {
            content = drive.list(path);
        }
        Enumeration e = content.elements();
        while (e.hasMoreElements()) {
            StorageItem item = (StorageItem) e.nextElement();
            if (item.isDirectory()) {
                if (recursive) {
                    Vector v = searchPhisicalDrive(drive, item.absolutePath(), filter, recursive);
                    CommonUtils.addElements(res, v);
                }
            } else {
                if (filter.match((File) item)) {
                    res.addElement(item);
                }
            }
        }
        return res;
    }

    public static Vector searchVirtualDrive(Directory dir, VirtualFileFilter filter) {
        //create a view object with the VirtualDirectories in the filter as navigation path
        Vector res = new Vector();
        Vector categories = new Vector();
        Enumeration e = filter.getVirtualDirectories().elements();
        while (e.hasMoreElements()) {
            VirtualDirectory vDir = (VirtualDirectory) e.nextElement();
            Category c = vDir.getCategory();
            c.setFilter(vDir);
            categories.addElement(c);
        }
        Vector items = new Vector();
        if (categories.isEmpty()) { //when no VDirectory in the filter, set any Category in the view, without any filter on it
            View view = new View(FileTypeCategory.instance());
            Vector vDirs = view.list();
            Enumeration e1 = vDirs.elements();
            while (e1.hasMoreElements()) {
				VirtualDirectory vDir = (VirtualDirectory) e1.nextElement();
				CommonUtils.addElements(items, vDir.getChildren());
			}
        } else {
            View view = new View(categories);
            VirtualDirectory leaf = (VirtualDirectory) filter.getVirtualDirectories().lastElement();
            view.setPath(filter.getVirtualDirectories());
            items = view.list(leaf);
        }
        Enumeration e1 = items.elements();
        while (e1.hasMoreElements()) {
            VirtualItem item = (VirtualItem) e1.nextElement();
            if (!item.isDirectory() && filter.match((File) item)) {
                res.addElement(item);
            }

        }
        return res;
    }

}
