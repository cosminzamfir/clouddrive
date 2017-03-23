package com.codename1.de.cloud.drive.virtual.v1;

import com.codename1.de.cloud.drive.EnumDriveType;
import com.codename1.de.cloud.drive.gui.containers.DriveManager;
import com.codename1.de.cloud.drive.gui.factory.ImageFactory;
import com.codename1.de.cloud.drive.storage.Directory;
import com.codename1.de.cloud.drive.storage.Drive;
import com.codename1.de.cloud.drive.storage.File;
import com.codename1.de.cloud.drive.storage.StorageItem;
import com.codename1.de.cloud.util.Observable;
import com.codename1.de.cloud.util.ThreadUtils;
import com.codename1.io.Log;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

/**
 * This is the entry point in the application and the structure the user
 * interacts with
 * <p>
 * It aggregates a collection of 'real' {@link Drive}s into a data structure
 * which should allow: fast searches, various orthogonal views, save/restore on
 * disk etc
 * <p>
 * Unlike the other drives, the current path must be always known. Clients must
 * use {@link #setCurrentDirectory(VirtualDirectory)} when navigating a
 * VirtualDrive (see {@link DriveManager#setCurrentDirectory(Directory)}
 * <p>
 * This is the first simple implementation which uses a collection of
 * {@link Hashtable}s with {@link File} objects as values
 * <p>
 * On long term some light database should be used; SQL Lite is the default
 * supported database on both iOS and Android and should be used to store the
 * virtual drive
 * 
 * @author Cosmin Zamfir
 * 
 */
public class VirtualDrive extends Observable implements Drive {

	/**
	 * Max files - for testing only. Stop when max size reached
	 */
	private final int maxFiles = 50000;

	private final Vector files = new Vector();

	/**
	 * All {@link Category} supported by this instance
	 */
	private final Vector categories = new Vector();

	/**
	 * Initial implementation - all {@link View}s supported
	 */
	private final Vector views = new Vector();

	/**
	 * The {@link View} which controls the display/structure of the
	 * {@link VirtualDrive}. A VirtualDrive cannot be navigated if the
	 * viewController is not set
	 */
	private View view;

	private VirtualDriveBuilder builder;

	private static VirtualDrive instance;

	/**
	 * Get the singleton instance. Start the {@link VirtualDriveBuilder} thread
	 * 
	 * @return
	 */
	public static VirtualDrive instance() {
		if (instance == null) {
			instance = new VirtualDrive();
		}
		return instance;
	}

	private VirtualDrive() {
	}

	public void initializeMetadata() {
		initializeCategories();
		initializeViews();
	}
	/**
	 * Initialize the default categories
	 * <p>
	 * Start builder in background
	 * <p>
	 * Set default view to {@link FileTypeCategory}
	 */
	public void initialize() {
		startBuilder();
		setView(new View(FileTypeCategory.instance()));
	}

	private void initializeViews() {
		views.addElement(new View(FileTypeCategory.instance()));
		views.addElement(new View(SizeCategory.instance()));
		views.addElement(new View(TimeCategory.instance()));
		views.addElement(new View(FileTypeCategory.instance(),
				FileExtensionCategory.instance()));
		views.addElement(new View(FileTypeCategory.instance(),
				TimeCategory.instance));
		views.addElement(new View(FileTypeCategory.instance(),
				SizeCategory.instance));
		views.addElement(new View(TimeCategory.instance(),
				SizeCategory.instance));
		views.addElement(new View(FileTypeCategory.instance(),
				TimeCategory.instance, SizeCategory.instance));
		views.addElement(new View(FileTypeCategory.instance(),
				SizeCategory.instance, TimeCategory.instance));
		// a view with 4 levels
		Vector v = new Vector();
		v.addElement(FileTypeCategory.instance());
		v.addElement(FileExtensionCategory.instance());
		v.addElement(SizeCategory.instance());
		v.addElement(TimeCategory.instance());
		views.addElement(new View(v));
	}

	private void startBuilder() {
		builder = new VirtualDriveBuilder(this);
		ThreadUtils.run(builder, "VirtualDriveBuilder");
	}

	/**
	 * Add the specified physical {@link Drive} to this virtual drive
	 * 
	 * @param drive
	 * @param path
	 *            the path from where the given physical drive is explored; root
	 *            if <code>null</code>
	 */
	public void add(final Drive drive, final String path) {
		builder.addDrive(drive, path);
	}

	private void initializeCategories() {
		categories.addElement(FileTypeCategory.instance());
		categories.addElement(SizeCategory.instance());
		categories.addElement(TimeCategory.instance());
		categories.addElement(FileExtensionCategory.instance());
	}

	/**
	 * The {@link View} which controls the display/structure of the
	 * {@link VirtualDrive}. A VirtualDrive cannot be navigated if the
	 * viewController is not set
	 */
	public void setView(View view) {
		this.view = view;
	}

	/**
	 * @return all {@link View}s
	 */
	public Vector getViews() {
		return views;
	}

	/**
	 * Assign the given {@link StorageItem} to categories/tags
	 * 
	 * @param item
	 */
	void process(StorageItem item) {
		Log.p("Processing " + item);
		if (files.size() > maxFiles) {
			return;
		}
		boolean matched = false;
		VirtualItem vf = VirtualItem.instance(this, item);
		Enumeration e = categories.elements();
		while (e.hasMoreElements()) {
			Category c = (Category) e.nextElement();
			boolean b = c.process(vf);
			if (b) {
				matched = true;
			} else {
				if (c.isExclusive()) {
					matched = false;
					break;
				}
			}
		}
		if (matched) {
			files.addElement(vf);
			// Log.p("New item added to at least one Category. Notifying observers");
			setChanged();
			notifyObservers(this);
		}

		if (item.isDirectory()) {
			Vector items = ((Directory) item).children();
			e = items.elements();
			while (e.hasMoreElements()) {
				StorageItem child = (StorageItem) e.nextElement();
				process(child);
			}
		}
	}

	/**
	 * Listing depends on the current view
	 * 
	 */
	public Vector list() {
		Log.p("Listing root. Current view: " + view, Log.INFO);
		return view.list();
	}

	public Vector list(VirtualDirectory directory) {
		Log.p("Listing " + directory + ". Current view: " + view, Log.INFO);
		return view.list(directory);
	}

	public long totalSpace() {
		return 0;
	}

	public long availableSpace() {
		return 0;
	}

	public long usedSpace() {
		return 0;
	}

	public void mkdir(Directory parent, String name) {
	}

	public String shortName() {
		return "Me.Everywhere";
	}

	public EnumDriveType type() {
		return EnumDriveType.VIRTUAL;
	}

	public Vector list(String path) {
		return null;
	}

	public Vector getCategories() {
		return categories;
	}

	public Directory parent(VirtualDirectory virtualDirectory) {
		view.up();
		return view.getCurrentDirectory();
	}

	/**
	 * Set the current virtual directory in the navigation view.
	 * 
	 * @param vDir
	 */
	public void setCurrentDirectory(VirtualDirectory vDir) {
		view.setCurrentDirectory(vDir);
	}

	public String absolutePath(VirtualDirectory virtualDirectory) {
		return view.getCurrentPath();
	}

	/**
	 * Reset all filters which are set on the VritualDrive categories
	 */
	public void resetAllFilters() {
		Enumeration e = categories.elements();
		while (e.hasMoreElements()) {
			Category category = (Category) e.nextElement();
			category.resetFilter();
		}
	}

    public String iconName() {
        return ImageFactory.virtualDrive;
    }
}
