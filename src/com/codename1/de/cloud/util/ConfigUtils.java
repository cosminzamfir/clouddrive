package com.codename1.de.cloud.util;

import com.codename1.io.FileSystemStorage;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

public class ConfigUtils {

    /**
     * Read the tokens stored in the application config for the specified cloud provider 
     * @param provider
     * @return
     */
    public static Hashtable readTokens(String provider) {
        try {
            Reader r = new InputStreamReader(FileSystemStorage.getInstance().openInputStream("app.conf"));
            Vector lines = IOUtils.parse(r);
            for (int i = 0; i < lines.size(); i++) {
                String line = (String) lines.elementAt(i);
                if (line.startsWith(provider)) {
                    return getTokens(line, provider);
                }
            }
            throw new RuntimeException("No entry found in application config for provider " + provider);
        } catch (IOException e) {
            throw new RuntimeException("Cannot read app config file: " + e.getMessage());
        }
    }

    private static Hashtable getTokens(String line, String provider) {
        line = line.substring((provider + "&").length());
        Vector tokens = CommonUtils.split(line, '&');
        if (tokens.isEmpty()) {
            throw new RuntimeException("Invalid configuration line: " + line);
        }
        Hashtable res = new Hashtable();
        Enumeration e = tokens.elements();
        while (e.hasMoreElements()) {
            String token = (String) e.nextElement();
            Vector keyValue = CommonUtils.split(token, '=');
            if (keyValue.size() != 2) {
                throw new RuntimeException("Invalid format for keyValue in app.conf: " + token
                        + "Expected format is 'key=value'");
            }
            String key = (String) keyValue.elementAt(0);
            String value = (String) keyValue.elementAt(1);
            res.put(key, value);
        }
        return res;

    }
}
