package com.codename1.de.cloud.utils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Hashtable;

import com.codename1.io.JSONParser;

public class JSonParserTest {

	public static void test1() {
		String s = "{oauth_token_secret=9rrxouqxek0lays&oauth_token=kj7ja66kw2fbeif}";
		JSONParser parser = new JSONParser();
		Hashtable h;
		try {
			parser.parse(
					new InputStreamReader(
							new ByteArrayInputStream(s.getBytes())),
					new JsonCallback());
			// System.out.println(h);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		String s = new String(new byte[] {111, 97, 117, 116, 104, 95, 116, 111, 107, 101, 110, 95, 115, 101, 99, 114, 101, 116, 61, 57, 101, 114, 121, 54, 112, 108, 53, 114, 113, 54, 112, 111, 50, 50, 38, 111, 97, 117, 116, 104, 95, 116, 111, 107, 101, 110, 61, 54, 110, 101, 103, 121, 49, 98, 109, 108, 101, 49, 104, 109, 112, 108});
		System.out.println(s);
		//test1();
	}
}
