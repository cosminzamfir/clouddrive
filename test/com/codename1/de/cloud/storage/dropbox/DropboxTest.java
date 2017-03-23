package com.codename1.de.cloud.storage.dropbox;

import com.codename1.de.cloud.TestUtils;
import com.codename1.de.cloud.drive.storage.dropbox.DropBoxDirectory;
import com.codename1.de.cloud.drive.storage.dropbox.DropBoxDrive;
import com.codename1.de.cloud.drive.storage.dropbox.DropboxStorrageItem;
import com.codename1.de.cloud.util.CommonUtils;


import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

public class DropboxTest {

	private static DropBoxDrive drive;

	public static void testList() {
		Hashtable h = drive.getAccountInfo();
		CommonUtils.print(h, 0);
		Vector v = drive.list();
		System.out.println(v);

		DropBoxDirectory photos = (DropBoxDirectory) v.elementAt(0);
		System.out.println(photos.children());
	}

	public static void testMkdir() {
		try {
			drive.mkdir(null, "Personal2");
		} catch (Exception ignore) {
		}
	}

	public static void testDeleteDir() {
		Vector v = drive.list();
		Enumeration e = v.elements();
		while (e.hasMoreElements()) {
			DropboxStorrageItem item = (DropboxStorrageItem) e.nextElement();
			if (item.name().startsWith("Personal")) {
				item.delete();
			}
		}
	}

	public static void main(String[] args) {
		TestUtils.init();
        drive = DropBoxDrive.instance();
		testList();
		testMkdir();
		testDeleteDir();
	}

}
