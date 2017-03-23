package com.codename1.de.cloud.drive.storage;

import java.io.InputStream;
import java.io.OutputStream;


/**
 * 
 * @author Cosmin Zamfir
 *
 */
public interface File extends StorageItem {

    long size();

    /**
     * Retrieve an {@link InputStream} to read the content of this file instance
     */
    InputStream getInputStream();

    /**
     * Retrieve the {@link File} content as an array of bytes
     * <p>
     * Care with this method. We should not load entire files in memory, do streaming instead.
     * This method should be removed or used only for small files,
     * @return
     */
    byte[] content();

    /**
     * Get the URI which identifies this item on the running platform
     * @return
     */
    String uri();

    /**
     * Stream the content of this instance to the specified {@link OutputStream}
     * <p>
     * Use this method to download the file to the local file system (to FilseSystemStorage. 
     * stream it to a media application etc
     * <p>
     * The implementations of this method should create 2 piped streams. 
     * The PipedInputSream must run in a different thread to avoid deadlocking
     * @param os
     */
    void stream(OutputStream os);

}
