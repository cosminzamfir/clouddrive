package com.codename1.de.cloud.utils;

import com.codename1.de.cloud.TestUtils;
import com.codename1.de.cloud.util.ConfigUtils;

import java.util.Hashtable;


public class ConfigUtilsTest {

	public static void test1() {
		Hashtable tokens = ConfigUtils.readTokens("dropbox");
		System.out.println(tokens);
	}
	
	public static void main(String[] args) {
		TestUtils.init();
		test1();
		System.exit(0);
	}
}
