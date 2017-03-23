package com.codename1.de.cloud.util;

import com.codename1.de.cloud.drive.storage.File;
import com.codename1.de.cloud.drive.virtual.v1.VirtualDirectory;

import java.util.Vector;

/**
 * A filter to search inside a VirtualDrive
 * It consists of an optional name filter and a chain of {@link VirtualDirectory}s 
 * <p>
 * An example: name='abc'; chain={"Documents","2012","Huge"} returns all files whose name is like 'abc' and belongs to "Documents","2012" and "Huge" {@link VirtualDirectory}s
 * @author Cosmin Zamfir
 *
 */
public class VirtualFileFilter {

    private String name;
    private Vector virtualDirectories;

    public VirtualFileFilter(String name) {
        super();
        this.name = name;
    }

    public VirtualFileFilter(Vector virtualDirectories) {
        super();
        this.virtualDirectories = virtualDirectories;
    }

    public VirtualFileFilter(String name, Vector virtualDirectories) {
        super();
        this.name = name;
        this.virtualDirectories = virtualDirectories;
    }

    /**
     * Match based only on the name filter. The {@link VirtualDirectory} matching has been already applied
     * @param file
     * @return
     */
    public boolean match(File file) {
        if (this.name != null && file.name().toLowerCase().indexOf(name.toLowerCase()) < 0) {
            return false;
        }
        return true;
    }

    public Vector getVirtualDirectories() {
        return virtualDirectories;
    }

    public String getName() {
        return name;
    }

}
