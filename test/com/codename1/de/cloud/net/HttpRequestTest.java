package com.codename1.de.cloud.net;

import com.codename1.de.cloud.TestUtils;
import com.codename1.de.cloud.net.HttpRequest;


public class HttpRequestTest {

	public static void test1() {
		HttpRequest request = new HttpRequest("http://www.google.com");
		byte[] response = request.run(5000);
		String s = new String(response);
		System.out.println(s);
	}
	
	public static void main(String[] args) {
		TestUtils.init();
		test1();
	}
}