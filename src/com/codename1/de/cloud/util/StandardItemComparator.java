package com.codename1.de.cloud.util;

import com.codename1.de.cloud.drive.storage.StorageItem;

/**
 * Standard sort for {@link StorageItem}: by type (directory first),  then by name
 * @author zamfcos
 *
 */
public class StandardItemComparator implements Comparator {

    public int compare(Object o1, Object o2) {
        StorageItem i1 = (StorageItem) o1;
        StorageItem i2 = (StorageItem) o2;
        if (i1.isDirectory()) {
            if (!i2.isDirectory()) {
                return -1;
            } else {
                return i1.name().compareTo(i2.name());
            }
        } else {
            if (i2.isDirectory()) {
                return 1;
            } else {
                return i1.name().compareTo(i2.name());
            }
        }
    }

}
