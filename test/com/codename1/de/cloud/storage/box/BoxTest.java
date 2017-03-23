package com.codename1.de.cloud.storage.box;

import com.codename1.de.cloud.TestUtils;
import com.codename1.de.cloud.drive.storage.box.BoxDrive;


public class BoxTest {

	public static void main(String[] args) {
		TestUtils.init();
        BoxDrive drive = BoxDrive.instance();
		drive.list();

	}

}
