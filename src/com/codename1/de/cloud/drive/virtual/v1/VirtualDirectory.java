package com.codename1.de.cloud.drive.virtual.v1;

import com.codename1.de.cloud.drive.gui.factory.ImageFactory;
import com.codename1.de.cloud.drive.storage.Directory;
import com.codename1.de.cloud.drive.storage.StorageItem;
import com.codename1.io.Log;

import java.util.Date;
import java.util.Hashtable;
import java.util.Vector;

/**
 * 
 * @author Cosmin Zamfir
 *
 */
public class VirtualDirectory extends VirtualItem implements Directory {

    private static Hashtable iconNames = new Hashtable();

    static {
        iconNames.put(FileTypeCategory.documents, ImageFactory.documents);
        iconNames.put(FileTypeCategory.music, ImageFactory.music);
        iconNames.put(FileTypeCategory.videos, ImageFactory.videos);
        iconNames.put(FileTypeCategory.pictures, ImageFactory.pictures);
        iconNames.put(FileTypeCategory.programming, ImageFactory.programming);
    }
    /**
     * Optional, the VirtualDirectory does not have to be associated with a physical directory.
     * Only if it is assigned, directory operations are available on it 
     */
    private Directory handle;

    /**
     * A storage container for any additional data required by other components to
     * organize/display a VirtualDrive
     * @see Category#match(VirtualDirectory, VirtualItem)
     */
    private Object metadata;

    /**
     * The Vector of {@link VirtualItem}s
     */
    private Vector children = new Vector();

    private Category category;

    /**
     * Create a first level directory
     * @param virtualDrive
     * @param key a key value in the VirtualDrive.currentView {@link Hashtable} 
     */
    public VirtualDirectory(VirtualDrive virtualDrive, String name, Category category) {
        super(virtualDrive, name);
        this.category = category;
    }

    /**
     * Create a first level directory
     * @param virtualDrive
     * @param key a key value in the VirtualDrive.currentView {@link Hashtable} 
     */
    public VirtualDirectory(VirtualDrive virtualDrive, String name, Object metadata, Category category) {
        super(virtualDrive, name);
        this.metadata = metadata;
        this.category = category;
    }

    /**
     * Create a virtual directory mapped to a physical directory
     * @param virtualDrive
     * @param key a key value in the VirtualDrive.currentView {@link Hashtable} 
     */
    public VirtualDirectory(VirtualDrive virtualDrive, Directory directory, Category category) {
        super(virtualDrive, directory.name());
        handle = directory;
        this.category = category;
    }

    public Category getCategory() {
        return category;
    }

    /**
     * 
     * @return <code>true</code> if this instance maps a physical directory
     */
    public boolean mapPhisicalDirectory() {
        return handle != null;
    }

    public boolean isDirectory() {
        return true;
    }

    /**
     * The children returned depend on the currnet {@link View}
     */
    public Vector children() {
        return getDrive().list(this);
    }

    /**
     * Standard getter for the children Vector; do not confuse with {@link #children()}
     * @return
     */
    public Vector getChildren() {
        return children;
    }

    public void addChild(StorageItem item) {
        Log.p("Adding " + item + " to VirtualDirectory " + this);
        children.addElement(item);
    }

    public Date lastModified() {
        return new Date();
    }

    public Object getMetadata() {
        return metadata;
    }

    public Directory parent() {
        return getDrive().parent(this);
    }
    
    public String absolutePath() {
    	// TODO Auto-generated method stub
    	return getDrive().absolutePath(this);
    }

    public void rename(String newName) {
    }

    public String iconName() {
        String s = (String) iconNames.get(name());
        if (s != null) {
            return s;
        }
        return ImageFactory.folder;
    }
}
