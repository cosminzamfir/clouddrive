package com.codename1.de.cloud.drive.virtual.v1;

import com.codename1.de.cloud.drive.exception.CloudSyncException;
import com.codename1.de.cloud.drive.storage.Directory;
import com.codename1.de.cloud.drive.storage.StorageItem;
import com.codename1.io.Log;

import java.util.Enumeration;
import java.util.Vector;

/**
 * A hierarchical category view of a {@link VirtualDrive}
 * <p>
 * It consists of a {@link Vector} of categories which are applied on the virtual drive content.
 * <p>
 * At any element in the category chain until the last one, the list of {@link VirtualDirectory}s for the respective category is displayed
 * When the current position is the latest category, display the VirtualFiles which are matched by all {@link VirtualDirectory}s up to the root
 * <p>
 * TODO Add optional filters for each {@link Category} - e.g. 'Pictures' for {@link FileTypeCategory}; '2006' for {@link TimeCategory}
 * 
 * @author Cosmin Zamfir
 *
 */
public class View {

    /**
     * All {@link Category}s in the chain
     */
    private Vector categories;

    /**
     * The path of {@link VirtualDirectory}s being traversed up to the current category
     * Only the files which are contained by all VirtualDirectories in this Vector are displayed
     */
    private Vector path = new Vector();

    public View(Vector categories) {
        super();
        if (categories == null || categories.isEmpty()) {
            throw new CloudSyncException("Attempt to build empty VirtualDrive view");
        }
        this.categories = categories;
    }

    public View(Category category) {
        super();
        this.categories = new Vector();
        categories.addElement(category);
    }

    public View(Category first, Category second) {
        super();
        this.categories = new Vector();
        categories.addElement(first);
        categories.addElement(second);
    }

    public View(Category first, Category second, Category third) {
        this(first, second);
        categories.addElement(third);
    }

    public Vector getCategories() {
        return categories;
    }

    /**
     * List the content of the given {@link VirtualDirectory} in this chain context
     * <p>
     * It filters out all children of the specified VirtualDirectories which are not matched by the previous Category/Virtual Directory pair in the chain
     * @param vDir
     * @return
     */
    public Vector list(VirtualDirectory vDir) {
        Log.p("Listing " + vDir + "; " + status());
        Category category = vDir.getCategory();
        if (!isLeafCategory(category)) {
            Category next = nextCategory(category);
            Vector res = filter(next);
            return res;
        } else {
            if (depth() == 1) {
                return vDir.getChildren();
            }
            Vector res = new Vector();
            Vector items = vDir.getChildren();
            Enumeration e = items.elements();
            while (e.hasMoreElements()) {
                StorageItem item = (StorageItem) e.nextElement();
                boolean matched = true;
                Enumeration f = path.elements();
                while (f.hasMoreElements()) {
                    VirtualDirectory v = (VirtualDirectory) f.nextElement();
                    if (!v.getChildren().contains(item)) {
                        //item is filtered out by one of the directories; exit the loop 
                        matched = false;
                        break;
                    }
                }
                if (matched) {
                    res.addElement(item);
                }
            }
            return res;
        }
    }

    /**
     * Filter out directories in the given {@link Category} which do not have any {@link StorageItem} wchich is matched 
     * by all previous {@link VirtualDirectory} in the {@link #path}
     * @param c
     * @return
     */
    private Vector filter(Category c) {
        //hide all directories in the current directory whose content is filtered out 
        //by the upper directories in the current navigation path
        if (path.isEmpty()) {
            Vector res = new Vector();
            Category category = ((Category) categories.elementAt(0));
            Vector roots = category.filter(category.directories());
            Enumeration e = roots.elements();
            while (e.hasMoreElements()) {
                VirtualDirectory dir = (VirtualDirectory) e.nextElement();
                if (!dir.getChildren().isEmpty()) {
                    res.addElement(dir);
                }
            }
            return res;

        }
        Vector res = new Vector();
        Vector dirs = c.filter(c.directories());
        Enumeration e = dirs.elements();
        while (e.hasMoreElements()) {
            VirtualDirectory subDir = (VirtualDirectory) e.nextElement();
            Vector items = subDir.getChildren();
            Enumeration eItems = items.elements();
            while (eItems.hasMoreElements()) {
                StorageItem item = (StorageItem) eItems.nextElement();
                boolean matched = true;
                for (int i = 0; i < path.size() ; i++) { //all dirs on the path up to curr dir must contain the item
                    if (!((VirtualDirectory) path.elementAt(i)).getChildren().contains(item)) {
                        matched = false;
                        break;
                    }
                }
                if (matched) {
                    res.addElement(subDir);
                    break; //if one item in this subDir has been matched by all upper dirs in the path => the subDir is part of the view
                }
            }
        }
        return res;
    }

    private Category nextCategory(Category category) {
        int index = categories.indexOf(category);
        return (Category) categories.elementAt(index + 1);
    }

    /**
     * The depth of category tree
     */
    public int depth() {
        return categories.size();
    }

    /**
     * Navigate one level up 
     * <p>
     * Remove the last directory in the filter Vector and move the currentDirectory and currentCategory left by one
     */
    public void up() {
        Log.p("Up one level." + status());
        if (path.isEmpty()) {
            return;
        }
        path.removeElementAt(path.size() - 1);
    }

    private boolean isLeafCategory(Category category) {
        return category == categories.lastElement();
    }

    /**
     * List the 'root' of this view. 
     * @return the {@link VirtualDirectory}s included in the first category of the chain
     */
    public Vector list() {
        return filter(((Category) categories.elementAt(0)));

    }

    private String status() {
        StringBuffer s = new StringBuffer();
        s.append(";Current directory " + getCurrentDirectory());
        return s.toString();
    }

    public Directory getCurrentDirectory() {

        if (path.isEmpty()) {
            return null; //root
        }
        return (Directory) path.lastElement();
    }

    public String toString() {
        return categories.toString();
    }

    /**
     * Add the specified {@link Directory} to the current {@link #path}
     * @param vDir
     */
    public void setCurrentDirectory(VirtualDirectory vDir) {
        if (vDir == null) {
            path.removeAllElements(); //navigate to the root
        } else if (!path.contains(vDir)) {
            path.addElement(vDir);
        } else {
            //go up until the VDir
            int index = path.indexOf(vDir);
            while (path.size() > index + 1) {
                path.removeElementAt(path.size() - 1);
            }
        }
    }

    /**
     * Set the full navigation path. 
     * @param path
     */
    public void setPath(Vector path) {
        this.path = path;
    }

	public String getCurrentPath() {
		if (path == null  || path.isEmpty()) {
			return "/";
		}
		StringBuffer res = new StringBuffer("/");
		Enumeration e = path.elements();
		while (e.hasMoreElements()) {
			VirtualDirectory vDir = (VirtualDirectory) e.nextElement();
			res.append(vDir.name());
			if (e.hasMoreElements()) {
				res.append("/");
			}
		}
		return res.toString();
	}
}
