package com.codename1.de.cloud.drive;

import com.codename1.de.cloud.drive.storage.Drive;
import com.codename1.de.cloud.drive.storage.box.BoxDrive;
import com.codename1.de.cloud.drive.storage.dropbox.DropBoxDrive;
import com.codename1.de.cloud.drive.storage.googledrive.GoogleDrive;
import com.codename1.de.cloud.drive.storage.lfs.LFSDrive;
import com.codename1.de.cloud.drive.virtual.v1.VirtualDrive;

import java.util.Vector;

/**
 * Each {@link Drive} is a singleton
 * 
 * @author Cosmin Zamfir
 * 
 */
public class DriveFactory {

	/**
	 * For testing only - the path of the local drive where the virtual drive
	 * building starts
	 */
    private static final String VIRTUAL_DRIVE_PATH = "/home/zamfcos/training";

	private static DropBoxDrive dropBoxDrive;
	private static BoxDrive boxDrive;
	private static LFSDrive lfsDrive;
	private static VirtualDrive virtualDrive;
    private static GoogleDrive googleDrive;

	public static DropBoxDrive getDropBoxDriveInstance() {
		if (dropBoxDrive == null) {
			dropBoxDrive = DropBoxDrive.instance();
		}
		return dropBoxDrive;
	}

	public static LFSDrive getLFSDriveInstance() {
		if (lfsDrive == null) {
			lfsDrive = LFSDrive.instance();
		}
		return lfsDrive;
	}

	public static BoxDrive getBoxDriveInstance() {
		if (boxDrive == null) {
			boxDrive = BoxDrive.instance();
		}
		return boxDrive;
	}

    public static GoogleDrive getGoogleDriveInstance() {
        if (googleDrive == null) {
            googleDrive = GoogleDrive.instance();
        }
        return googleDrive;
    }

	public static VirtualDrive getVirtualDriveInstance() {
		if (virtualDrive == null) {
			virtualDrive = VirtualDrive.instance();
			virtualDrive.initializeMetadata();
			virtualDrive.initialize();
			virtualDrive.add(getLFSDriveInstance(), VIRTUAL_DRIVE_PATH);
		}
		return virtualDrive;
	}

	public static Drive getInstance(EnumDriveType type) {
		if (type == EnumDriveType.DROPBOX) {
			return getDropBoxDriveInstance();
		} else if (type == EnumDriveType.LFS) {
			return getLFSDriveInstance();
		} else if (type == EnumDriveType.VIRTUAL) {
			return getVirtualDriveInstance();
		} else {
			return getBoxDriveInstance();
		}
	}
	
    /**
     * @return all available drives, except the {@link VirtualDrive}
     */
    public static Vector getAllDrives() {
        Vector res = new Vector();
        res.addElement(getLFSDriveInstance());
        res.addElement(getDropBoxDriveInstance());
        res.addElement(getBoxDriveInstance());
        res.addElement(getGoogleDriveInstance());
        return res;
    }

}
