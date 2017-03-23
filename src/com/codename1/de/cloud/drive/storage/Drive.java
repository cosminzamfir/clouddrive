package com.codename1.de.cloud.drive.storage;

import com.codename1.de.cloud.drive.EnumDriveType;
import com.codename1.de.cloud.drive.exception.CloudSyncException;

import java.util.Vector;


/**
 * A source of {@link StorageItem}s
 * @author Cosmin Zamfir
 *
 */
public interface Drive {

    /**
     * 
     * @return the {@link StorageItem} on the root of this instance
     */
    Vector list();

    /**
     * List the content of the directory at the specified path
     * @param path
     * @return
     * @throws CloudSyncException if the path does not exist or if refers to a {@link File} 
     */
    Vector list(String path);
    
    long totalSpace();

    long availableSpace();

    long usedSpace();
    
    /**
     * Create a new {@link Directory} under the specified parent
     * @param parent parent or <code>null</code> if drive root 
     * @param name the name of the new directory
     * TODO - return the new directory object

     */
    void mkdir(Directory parent, String name);

    String shortName();

    EnumDriveType type();

    /**
     * The name of the icon to be used when displaying this Directory instance 
     * @return
     */
    String iconName();
}
