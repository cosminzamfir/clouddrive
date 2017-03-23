package com.codename1.de.cloud.drive.storage;

import java.util.Date;


/**
 * Common interface for {@link File} and {@link Directory}
 * @author Cosmin Zamfir
 *
 */
public interface StorageItem {

    String name();

    String absolutePath();

    /**
     * Root directory has null parent
     * @return
     */
    Directory parent();

    boolean isDirectory();

    Date lastModified();

    void delete();

    void move(Directory parent);

    void copy(Directory parent);

    /**
     * Rename this {@link StorageItem} instance inside its existing parent direcotryu
     * @param newName
     */
    void rename(String newName);

}
