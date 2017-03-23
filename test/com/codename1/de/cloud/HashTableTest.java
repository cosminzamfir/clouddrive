package com.codename1.de.cloud;

import java.util.Enumeration;
import java.util.Hashtable;

/**
 * Test if {@link HashTableTest} maintains the insertion order
 * @author Cosmin Zamfir
 *
 */
public class HashTableTest {

    public static void main(String[] args) {
        Hashtable h = new Hashtable();
        for (int i = 0; i < 20; i++) {
            Integer integer = new Integer(i);
            h.put(integer, integer);
        }
        Enumeration e = h.keys();
        while (e.hasMoreElements()) {
            Object integer = (Object) e.nextElement();
            System.out.println(integer);
        }
    }
}
