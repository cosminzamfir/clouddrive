package com.codename1.de.cloud.util;

import com.codename1.de.cloud.TestUtils;
import com.codename1.de.cloud.util.Pipe;
import com.codename1.io.FileSystemStorage;


import java.io.InputStream;
import java.io.OutputStream;

public class PipeTest {

	public static void test1() {
		try {
			InputStream is = FileSystemStorage.getInstance().openInputStream(
					"abc.jar");
			OutputStream os = FileSystemStorage.getInstance().openOutputStream(
					"cde.jar");
			new Pipe(is, os).run();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public static void main(String[] args) {
		TestUtils.init();
		test1();
	}
}
