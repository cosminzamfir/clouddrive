package com.codename1.de.cloud.utils;

import com.codename1.io.JSONParseCallback;
import com.codename1.io.JSONParser;

public class JsonCallback extends JSONParser implements JSONParseCallback {

	public void startBlock(String blockName) {
		super.startBlock(blockName);
	}

	public void endBlock(String blockName) {
		super.endBlock(blockName);
	}

	public void startArray(String arrayName) {
		super.startArray(arrayName);
	}

	public void endArray(String arrayName) {
		super.endArray(arrayName);

	}

	public void stringToken(String tok) {
		super.stringToken(tok);
	}

	public void numericToken(double tok) {
		super.numericToken(tok);
	}

	public void keyValue(String key, String value) {
		super.keyValue(key, value);

	}

	public boolean isAlive() {
		return super.isAlive();
	}

}
