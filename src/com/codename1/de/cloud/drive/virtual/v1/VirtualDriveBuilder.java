package com.codename1.de.cloud.drive.virtual.v1;

import java.util.Enumeration;
import java.util.Vector;

import com.codename1.de.cloud.drive.storage.Drive;
import com.codename1.de.cloud.drive.storage.StorageItem;
import com.codename1.io.Log;

/**
 * Builds the {@link VirtualDrive} content in the background. Processes the
 * queue of physical drives; waits if not {@link Drive} is in the queue
 * 
 * @author Cosmin Zamfir
 * 
 */
public class VirtualDriveBuilder implements Runnable {

	private final VirtualDrive virtualDrive;
	private final Vector queue = new Vector();

	/**
	 * Wait time to check if new drives are availble in the queue
	 */
	private static final long waitTime = 1000;

	public VirtualDriveBuilder(VirtualDrive virtualDrive) {
		super();
		this.virtualDrive = virtualDrive;
	}

	/**
	 * Add this {@link Drive} for processing
	 * 
	 * @param drive
	 * @param path
	 */
	public synchronized void addDrive(Drive drive, String path) {
		Log.p(drive + " added to the queue");
		queue.addElement(new DriveWrapper(drive, path));
	}

	/**
	 * Poll the next available drive to be processed
	 * 
	 * @return the {@link Drive} or
	 */
	public synchronized DriveWrapper popDrive() {
		if (queue.size() == 0) {
			return null;
		}
		DriveWrapper res = (DriveWrapper) queue.elementAt(0);
		// TODO - implement and use a queue for this
		queue.removeElementAt(0);
		return res;
	}

	public void run() {
		Thread.currentThread().setPriority(1);
		while (true) {
			DriveWrapper wrapper = null;
			while ((wrapper = popDrive()) == null) {
				try {
					Thread.sleep(waitTime);
				} catch (InterruptedException ignore) {
					Log.p("Building VirtualDrive interrupted while waiting for new drives to process",
							Log.INFO);
					return;
				}
			}

			Log.p("Start processing " + wrapper.getDrive().type());
			processDrive(wrapper);
			Log.p("Finished processing " + wrapper.getDrive().type());
		}
	}

	private void processDrive(DriveWrapper wrapper) {
		Vector items;
		if (wrapper.getPath() == null) {
			items = wrapper.getDrive().list();
		} else {
			items = wrapper.getDrive().list(wrapper.getPath());
		}
		Enumeration e = items.elements();
		while (e.hasMoreElements()) {
			StorageItem item = (StorageItem) e.nextElement();
			virtualDrive.process(item);
		}
	}

	private class DriveWrapper {
		private final Drive drive;
		private final String path;

		public DriveWrapper(Drive drive, String path) {
			super();
			this.drive = drive;
			this.path = path;
		}

		public Drive getDrive() {
			return drive;
		}

		public String getPath() {
			return path;
		}
	}

}
