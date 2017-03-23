package com.codename1.de.cloud.util;

import com.codename1.io.BufferedOutputStream;
import com.codename1.io.FileSystemStorage;

import java.io.IOException;
import java.io.Reader;
import java.util.Enumeration;
import java.util.Vector;

public class IOUtils {

    private final static String newLine = System.getProperty("line.separator");
    private static char nLC = '\n';
    private static char cRc = '\r';
	/**
     * Get the lines into a vector
     * 
     * @param r
     * @return
     * @throws IOException
     */
    public static Vector parse(Reader r) throws IOException {
        Vector res = new Vector();
        char[] buf = new char[1024];
        while (r.read(buf) >= 0) {
            StringBuffer s = new StringBuffer();
            for (int i = 0; i < buf.length; i++) {
                if (buf[i] != nLC && buf[i] != cRc) {
                    s.append(buf[i]);
                } else {
                    if (s.length() > 0) {
                    	res.addElement(s.toString());
                        s = new StringBuffer();
					}
                }
            }
            res.addElement(s.toString());
        }
        return res;
    }

    /**
     * Write the given Vector to the specified file name
     */
    public static void write(Vector lines, String file) {
        try {
            BufferedOutputStream bos = new BufferedOutputStream(FileSystemStorage.getInstance().openOutputStream(file));
            Enumeration e = lines.elements();
            while (e.hasMoreElements()) {
                String line = (String) e.nextElement();
                bos.write(line.getBytes());
                bos.write('\n');
            }
            bos.close();
        } catch (IOException e) {
            throw new RuntimeException("Cant write to " + file + ": " + e.getMessage());
        }
    }


}
