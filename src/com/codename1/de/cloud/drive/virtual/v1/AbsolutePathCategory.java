package com.codename1.de.cloud.drive.virtual.v1;

import com.codename1.de.cloud.drive.exception.CloudSyncException;

import java.util.Hashtable;
import java.util.Vector;

/**
 * 
 * @author Cosmin Zamfir
 *
 */
public class AbsolutePathCategory extends Category {

    /**
     * Maps paths to the Tag objects
     * Tags are stores both as embedded in the parent tags as well as first level Hashtable elements (for faster lookup
     * while building the VirtualDrive)
     */
    private Hashtable paths = new Hashtable();

    public Object value(VirtualFile file) {
        return file.absolutePath();
    }

    public String getName() {
        return "FileLocation";
    }

    public boolean isExclusive() {
        return false;
    }

    public Byte getId() {
        return new Byte((byte) 4);
    }

    public Vector tags() {
        return null;
    }

    public String getIcon() {
        return "icons/categories/absolute-path.png";
    }

    /**
     * Tags are not predefined as for the other categories; they are built while files are being processed
     * <p>
     * Each file is added to the already existing parent Tag; each directory is added to the parent tag and to the {@link #paths}
     * mapping for faster lookup
     * <p>
     * Directories are mapped to physical directory, see {@link VirtualDirectory#mapPhisicalDirectory()}, only if 
     * we are part of a chain having DriveTypeCategory previous to this category (Only then it maps to a physical directory
     * or a subset of it and directory operations can be performed safely.
     */
    public boolean process(VirtualItem item) {
        String path = item.absolutePath();
        if (isRootItem(path)) {
            VirtualDirectory tag = new VirtualDirectory(VirtualDrive.instance(), "/", "/", this);
            paths.put("/", tag);
        } else {
            VirtualDirectory parent = getParent(item.absolutePath());
            if (!item.isDirectory()) {
                //add the file to the parent tag
                parent.addChild(item);
            } else {
                parent.children().addElement(item);
                paths.put(item.absolutePath(), item);
            }
        }
        return true;
    }

    /**
     * Retrieve the tag which is the direct ascendant for the given path; i.e. /local/data/temp/ parent is /local/data/
     * Note that /local/data/ is not directly accessible; it is available under /local/ tag
     * <p>
     * The assumption is (based on the way a physical drive is traversed) that when /local/data/temp is processed, /local/data/ is already
     * available, under the /local/ tag 
     * @param path
     * @return
     */
    private VirtualDirectory getParent(String path) {
        if (isRootItem(path)) {
            return null;
        }
        String key = path.substring(0, path.lastIndexOf('/'));
        VirtualDirectory res = (VirtualDirectory) paths.get(key);
        if (res == null) {
            throw new CloudSyncException("Application error: " + key + " have not been traversed before " + path);
        }
        return res;
    }

    private boolean isRootItem(String path) {
        //TODO - different impl for the windows file system
        return path.equals("/");
    }

    public Object value(VirtualItem file) {
        return null;
    }

    public Vector directories() {
        return null;
    }

    /**
     * We provide own process() implementation  where match() is not relevant
     */
    public boolean match(VirtualDirectory vDir, VirtualItem item) {
        return true;
    }

}
