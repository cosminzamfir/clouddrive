package com.codename1.de.cloud.drive.storage;

import java.util.Vector;


/**
 * 
 * @author Cosmin Zamfir
 *
 */
public interface Directory extends StorageItem {

    Vector children();

    /**
     * The name of the icon to be used when displaying this Directory instance 
     * @return
     */
    String iconName();

}
