package com.codename1.de.cloud.drive.storage.lfs;

import com.codename1.de.cloud.drive.EnumDriveType;
import com.codename1.de.cloud.drive.exception.CloudSyncException;
import com.codename1.de.cloud.drive.gui.factory.ImageFactory;
import com.codename1.de.cloud.drive.storage.Directory;
import com.codename1.de.cloud.drive.storage.Drive;
import com.codename1.de.cloud.drive.storage.File;
import com.codename1.de.cloud.drive.storage.StorageItem;
import com.codename1.de.cloud.util.CommonUtils;
import com.codename1.de.cloud.util.StandardItemComparator;
import com.codename1.io.FileSystemStorage;
import com.codename1.io.Log;
import com.codename1.ui.List;

import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

/**
 * A local file system {@link Drive} implementation
 * @author Cosmin Zamfir
 *
 */
public class LFSDrive implements Drive {

    private static Calendar today = Calendar.getInstance();

    private FileSystemStorage delegate;

    private static LFSDrive instance;

    public static LFSDrive instance() {
        if (instance == null) {
            instance = new LFSDrive();
        }
        return instance;
    }

    private LFSDrive() {
        delegate = FileSystemStorage.getInstance();
    }

    public Vector list() {
        String[] roots = delegate.getRoots();
        return getStorageItems(null, roots);
    }

    /**
     * A {@link List} of {@link StorageItem} found in the specified {@link Directory}
     */
    public Vector list(Directory directory) {
        String[] fileNames = null;
        try {
            fileNames = delegate.listFiles(directory.absolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return getStorageItems(directory, fileNames);
    }

    public void delete(StorageItem item) {
        delegate.delete(item.absolutePath());
    }

    public void move(StorageItem item, Directory parent) {
        //TODO - i/o.does not seem right
    }

    /**
     * Order the items first by type (folders first), than by name
     * @param directory
     * @param fileNames
     * @return
     */
    private Vector getStorageItems(Directory directory, String[] fileNames) {
        Vector res = new Vector();
        if (fileNames == null || fileNames.length == 0) {
            return res;
        }
        for (int i = 0; i < fileNames.length; i++) {
            String fileName = fileNames[i];
            String absoluteFileName = fileName;
            if (directory != null) {
                if (!directory.absolutePath().endsWith("/")) {
                    absoluteFileName = directory.absolutePath() + "/" + fileName;
                } else {
                    absoluteFileName = directory.absolutePath() + fileName;
                }
            }

            if (delegate.isDirectory(absoluteFileName)) {
                Directory dir = new LFSDirectory(this, fileName, absoluteFileName, directory);
                res.addElement(dir);
            } else {
                File file = new LFSFile(this, fileName, absoluteFileName, directory);
                res.addElement(file);
            }
        }
        CommonUtils.quickSort(res, new StandardItemComparator());
        return res;
    }

    /**
     * If the drive is not being traversed starting from root
     * @param directoryName
     * @param fileNames
     * @return
     */
    private Vector getStorageItemsForNamedDirectory(String directoryName, String[] fileNames) {
        Vector res = new Vector();
        if (fileNames == null || fileNames.length == 0) {
            return res;
        }

        for (int i = 0; i < fileNames.length; i++) {
            String absoluteFileName;
            String fileName = fileNames[i];
            if (!directoryName.endsWith("/")) {
                absoluteFileName = directoryName + "/" + fileName;
            } else {
                absoluteFileName = directoryName + fileName;
            }

            if (delegate.isDirectory(absoluteFileName)) {
                Directory dir = new LFSDirectory(this, fileName, absoluteFileName, null);
                res.addElement(dir);
            } else {
                File file = new LFSFile(this, fileName, absoluteFileName, null);
                res.addElement(file);
            }
        }
        CommonUtils.quickSort(res, new StandardItemComparator());
        return res;
    }

    public Vector list(String directory) {
        try {
            String[] children = delegate.listFiles(directory);
            if (children == null || children.length == 0) {
                Log.p("No chidren found for " + directory);
                return new Vector();
            }
            return getStorageItemsForNamedDirectory(directory, children);
        } catch (Exception e) {
            throw new CloudSyncException("Cannot list " + directory + " : " + e);
        }
    }

    public boolean isDirectory(String absolutePath) {
        return delegate.isDirectory(absolutePath);
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
        // TODO Auto-generated method stub

    }

    public String shortName() {
        return "Local";
    }

    public EnumDriveType type() {
        return EnumDriveType.LFS;
    }

    public InputStream inputStream(LFSFile file) {
        try {
            return delegate.openInputStream(file.absolutePath());
        } catch (IOException e) {
            throw new CloudSyncException("Cannot open an OutputStream on " + file + " : " + e.getMessage());
        }
    }

    public long size(LFSFile lfsFile) {
        return delegate.getLength(lfsFile.absolutePath());
    }

    public Date lastModified(StorageItem item) {
        //TODO - no API in FileSystemStorage for last modified; need a native interface
        //get some random value

        Date d = new Date();
        //int years = (int) (System.currentTimeMillis() % 6);
        //d.setTime(d.getTime() - 1000 * 60 * 60 * 24 * 365 * years);
        return d;
    }

    public Vector search(Directory dir, StorageItem filter, boolean recursive) {
        return null;
    }

    public String iconName() {
        return ImageFactory.lfs;
    }

}
