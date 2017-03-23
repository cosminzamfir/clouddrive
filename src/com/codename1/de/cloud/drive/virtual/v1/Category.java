package com.codename1.de.cloud.drive.virtual.v1;

import com.codename1.de.cloud.drive.storage.Directory;
import com.codename1.de.cloud.util.CommonUtils;

import java.util.Enumeration;
import java.util.Vector;

/**
 * Concrete instances of this class provide various categorizations of the {@link VirtualDrive} content.
 * <p>
 * Each concrete implementation represents one classification based on one or several criteria.
 * <p>
 * An AbstractCategory contains a number of discrete Tags.  A {@link VirtualFile} processed by one Category is assigned to zero or one 
 * (and possibly more - however in most cases there should be a 1-to-0/1 assignment from Files to Tags) tags.
 * <p>If the file is not assigned to any tag, it is not part of this classification instance 
 * <p>Main logic of tag assignments and other concerns is implemented in this class. The extensions must provide the #value(VirtualFile) implementation
 * and the collection of tags.
 * <p>
 * A concrete example of a Category is by FileType, with a collection of Tags like: Documents,Photos,Videos,Libraries etc; the value() method returns the 
 * file extension, which is used to assign a file to tags.
 * <p>
 * In the {@link VirtualDrive} world, a Category is similar to a root directory; tags are similar directories.
 * The current Category assigned to a VirtualDrive determines the 'directory' structure
 * <p>
 * In a second phase, several Categories can be combined in a hierarchy : For instance FileType.TimeLine hierarchy will 
 * organize the files first based on FileType then, inside each FileType {@link Tag}, they will be organized by years.  
 *  
 * @author Cosmin Zamfir
 *
 */
public abstract class Category {

    /**
     * Display only the {@link VirtualDirectory}s contained in the filter, if filter is not empty
     */
    private Vector filter = new Vector();

    /**
     * The value of the {@link VirtualFile} according to this category
     * <p>
     * The return of this method determines the assignment of the file to the containing tags.
     * <p>
     * If the return value does not match any Tag, the file will not be part of this classification 
     * (e.g. system files are ignored by the FileType category)
     * @param file
     * @return the value of the file in the context of this category. It must properly implement the equal() method - being used 
     * for lookups in the {@link Tag#getValues()} {@link Vector}
     */
    public abstract Object value(VirtualItem file);

    /**
     * The display name of this instance
     * @return
     */
    public abstract String getName();

    /**
     * If a Category is exclusive, a file which not matched by that category is not part of the {@link VirtualDrive}
     * <p>
     * Use this to filter-out files which are not interesting for our app
     * @return
     */
    public abstract boolean isExclusive();

    /**
     * The unique identifier
     * @return
     */
    public abstract Byte getId();

    /**
     * The {@link Vector} of {@link VirtualDirectory}s contained in this Category instance
     */
    public abstract Vector directories();

    /**
     * @param vDir
     * @param item
     * @return <code>true</code> if the specified {@link VirtualItem} should be included in the given {@link VirtualDirectory}
     */
    public abstract boolean match(VirtualDirectory vDir, VirtualItem item);

    public void setFilter(Vector filter) {
        this.filter = filter;
    }

    public void setFilter(VirtualDirectory vDir) {
        this.filter = new Vector();
        filter.addElement(vDir);
    }

    public void resetFilter() {
        this.filter.removeAllElements();
    }

    /**
     * Process the item only if not a {@link Directory}. Assign it to the matching {@link VirtualDirectory}s, if any
     * <p>
     * Default non-hierarchical categories do not process directories
     * @param file
     */
    public boolean process(VirtualItem item) {
        if (item.isDirectory()) {
            //default behavior - categories do not care about the VirtualDirs
            return false;
        }
        boolean matched = false;
        VirtualFile file = (VirtualFile) item;
        Enumeration e = directories().elements();
        while (e.hasMoreElements()) {
            VirtualDirectory v = (VirtualDirectory) e.nextElement();
            if (match(v, file)) {
                v.addChild(file);
                matched = true;
            }
        }
        return matched;
    }

    public String toString() {
        return getName();
    }

    /**
     * Retrieve the {@link Tag} with the specified name
     * @param name 
     * @return the Tag or <code>null</code> if does not exist
     */
    public VirtualDirectory getTagForName(String name) {
        Enumeration e = directories().elements();
        while (e.hasMoreElements()) {
            VirtualDirectory tag = (VirtualDirectory) e.nextElement();
            if (tag.name().equals(name)) {
                return tag;
            }
        }
        return null;
    }

    /**
     * Filter-out all {@link VirtualDirectory}s which are not part of the current filter set to this category
     * @param vDirs
     * @return
     */
    public Vector filter(Vector vDirs) {
        if (filter.isEmpty() || filter.size() == vDirs.size()) {
            return vDirs;
        }
        return CommonUtils.intersect(vDirs, filter);
    }

}
