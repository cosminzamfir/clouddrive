package com.codename1.de.cloud.drive.storage.lfs;

import com.codename1.de.cloud.drive.storage.Directory;
import com.codename1.de.cloud.drive.storage.File;
import com.codename1.de.cloud.util.CommonUtils;
import com.codename1.io.FileSystemStorage;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;

/**
 * A local file system {@link File} implementation
 * 
 * @author Cosmin Zamfir
 * 
 */
public class LFSFile implements File {

	private final static String uriPrefix = "file://";
	private LFSDrive drive;
	private String name;
	private String absolutePath;
	private Directory parent;
	private Date lastModified;
	private long size;

	public LFSFile(LFSDrive drive, String name, String absolutePath, Directory parent) {
		super();
		this.name = name;
		this.absolutePath = absolutePath;
		this.parent = parent;
		this.drive = drive;
	}

	public String name() {
		return name;
	}

	public String absolutePath() {
		return absolutePath;
	}

	public void delete() {
		drive.delete(this);
	}

	public void move(Directory parent) {
	}

	public Date lastModified() {
        if (lastModified == null) {
            lastModified = drive.lastModified(this);
        }
        return lastModified;
	}

	public Directory parent() {
		return parent;
	}

	public long size() {
        if (size == 0) {
            size = drive.size(this);
        }
        return size;
	}

	public boolean isDirectory() {
		return false;
	}

	public String toString() {
		return name;
	}

	public void copy(Directory parent) {
	}

	public InputStream getInputStream() {
		return drive.inputStream(this);
	}

	public String uri() {
		return CommonUtils.uriEncode(uriPrefix + CommonUtils.removeAll('\\', absolutePath()));
		//return uriPrefix + CommonUtils.removeAll('\\', absolutePath());
	}

	public byte[] content() {
		return null;
	}

	public InputStream getOutputStream() {
		return null;
	}

	public void stream(OutputStream os) {
		FileSystemStorage fs = FileSystemStorage.getInstance();
		return;
	}

	public void rename(String newName) {
	}

}
