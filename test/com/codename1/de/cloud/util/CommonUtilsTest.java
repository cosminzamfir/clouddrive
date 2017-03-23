package com.codename1.de.cloud.util;

import java.util.Vector;

public class CommonUtilsTest {

    public static void wrappTest() {
        String s = "123456789";
        System.out.println(CommonUtils.wrapp(s, 7, 30));
        System.out.println(CommonUtils.wrapp(s, 9, 30));
        System.out.println(CommonUtils.wrapp(s, 2, 30));

    }

    public static void testSortArray() {
        Integer[] a =
                new Integer[] { new Integer(10), new Integer(9), new Integer(8), new Integer(7), new Integer(6),
                        new Integer(5), new Integer(4), new Integer(3), new Integer(2), new Integer(1),
                        new Integer(0)};
        CommonUtils.quickSort(a, new Comparator() {
            
            public int compare(Object o1, Object o2) {
                Integer i1 = (Integer) o1;
                Integer i2 = (Integer) o2;
                if (i1.intValue() < i2.intValue()) {
                    return -1;
                }
                return 1;
            }
        });
        System.out.println(CommonUtils.asVector(a));
    }

    public static void testSortVector() {
        Vector v = new Vector();
        v.addElement(new Integer(10));
        v.addElement(new Integer(9));
        v.addElement(new Integer(8));
        v.addElement(new Integer(7));
        v.addElement(new Integer(6));
        v.addElement(new Integer(5));
        v.addElement(new Integer(4));
        v.addElement(new Integer(3));
        v.addElement(new Integer(2));
        v.addElement(new Integer(1));

        CommonUtils.quickSort(v, new Comparator() {

            public int compare(Object o1, Object o2) {
                Integer i1 = (Integer) o1;
                Integer i2 = (Integer) o2;
                if (i1.intValue() < i2.intValue()) {
                    return -1;
                }
                return 1;
            }
        });
        System.out.println(v);

    }

    public static void main(String[] args) {
        //wrappTest();
        testSortArray();
        testSortVector();
    }
}
