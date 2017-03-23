package com.codename1.de.cloud.util;

import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

public class CommonUtils {

	private static int _1MB = 1024 * 1024;
	private static int _1KB = 1024;

	private static Hashtable URL_ENCODING = new Hashtable();

	static {
		URL_ENCODING.put("$", "%24");
		URL_ENCODING.put(" ", "%20");
		URL_ENCODING.put("&", "%26");
		URL_ENCODING.put(",", "%2C");
		// URL_ENCODING.put(":", "%3A");
	}

	public static Vector split(String line, char separator) {
		Vector res = new Vector();
		StringBuffer tmp = new StringBuffer();
		int len = line.length();
		for (int i = 0; i < len; i++) {
			char ch = line.charAt(i);
			;
			if (ch != separator) {
				tmp.append(ch);
			} else {
				if (tmp.length() > 0) {
					res.addElement(tmp.toString());
					tmp = new StringBuffer();
				}
			}
		}
		if (tmp.length() > 0) {
			res.addElement(tmp.toString());
		}
		return res;
	}

	public static Date parseDropBoxDate(String s) {
		return new Date();
	}

	public static boolean getBoolean(String s) {
		if (s == null) {
			return false;
		}
		if (s.equalsIgnoreCase("true")) {
			return true;
		}
		return false;
	}

	public static String buildHttpHeader(Hashtable requestHeaders) {
		StringBuffer buf = new StringBuffer();
		Enumeration e = requestHeaders.keys();
		if (e.hasMoreElements()) {
			buf.append("?");
		}
		while (e.hasMoreElements()) {
			String key = (String) e.nextElement();
			String value = (String) requestHeaders.get(key);
			buf.append(key + "=" + value);
			if (e.hasMoreElements()) {
				buf.append("&");
			}

		}
		return buf.toString();
	}

	public static void print(Hashtable h, int indent) {
		Enumeration e = h.keys();
		while (e.hasMoreElements()) {
			Object key = e.nextElement();
			Object value = h.get(key);
			if (value instanceof Hashtable) {
				for (int i = 0; i < indent; i++) {
					System.out.print("   ");
				}
				System.out.println(key + "=");
				print((Hashtable) value, indent + 1);
			} else if (value instanceof Vector) {
				for (int i = 0; i < indent; i++) {
					System.out.print("   ");
				}
				System.out.println(key + "=");
				print((Vector) value, indent + 1);
			} else {
				for (int i = 0; i < indent; i++) {
					System.out.print("   ");
				}
				System.out.println(key + "=" + value);

			}
		}
	}

	private static void print(Vector key, int indent) {
		Enumeration e = key.elements();
		while (e.hasMoreElements()) {
			Object o = e.nextElement();
			if (o instanceof Hashtable) {
				print((Hashtable) o, indent + 1);
			} else if (o instanceof Vector) {
				print((Vector) o, indent++);
			} else {
				for (int i = 0; i < indent; i++) {
					System.out.print("   ");
				}
				System.out.println(o);

			}
		}
	}

	public static Date parseBoxDate(String s) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 
	 * @return the part in the file name after the latest dot or
	 *         <code>null</code> if there is no dot in the file name
	 */
	public static String extension(String fileName) {
		int lastDotIndex = fileName.lastIndexOf('.');
		if (lastDotIndex < 0) {
			return null;
		}
		return fileName.substring(lastDotIndex + 1);
	}

	/**
	 * Add the specified element to the {@link Vector} which is stored in the
	 * specified {@link Hashtable} under the given key. Create the Vector if it
	 * does not exist yet under that key
	 * 
	 * @param h
	 * @param key
	 * @param element
	 */
	public static void addToMappedList(Hashtable h, Object key, Object element) {
		Vector v = (Vector) h.get(key);
		if (v == null) {
			v = new Vector();
			v.addElement(element);
			h.put(key, v);
		} else {
			v.addElement(element);
		}
	}

	/**
	 * 
	 * @return number of occurrences of c in s
	 */
	public static int count(String s, char c) {
		int res = 0;
		char[] chars = s.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			if (chars[i] == c) {
				res++;
			}
		}
		return res;
	}

	public static int min(int a, int b) {
		if (a > b) {
			return b;
		}
		return a;
	}

	public static int max(int a, int b) {
		if (a > b) {
			return a;
		}
		return b;
	}

	/**
	 * @return a subset of the given {@link Hashtable} keys, whose mapped value
	 *         equal the specified filter value
	 */
	public static Vector filter(Hashtable hashtable, Object filter) {
		Vector res = new Vector();
		Enumeration e = hashtable.keys();
		while (e.hasMoreElements()) {
			Object key = e.nextElement();
			Object o = hashtable.get(key);
			if (o != null && o.equals(filter)) {
				res.addElement(key);
			}
		}
		return res;
	}

	/**
	 * Return all elements of v1 which are contained in v2
	 */
	public static Vector intersect(Vector v1, Vector v2) {
		Enumeration e1 = v1.elements();
		Vector res = new Vector();
		while (e1.hasMoreElements()) {
			Object elem = e1.nextElement();
			if (v2.contains(elem)) {
				res.addElement(elem);
			}
		}
		return res;
	}

	public static Vector copy(Vector v) {
		Vector res = new Vector();
		Enumeration e = v.elements();
		while (e.hasMoreElements()) {
			Object o = e.nextElement();
			res.addElement(o);
		}
		return res;
	}

	/**
	 * Pad the given {@link String} with spaces up to the specified length if
	 * it's length is less then the given length. Otherwise, return a substring
	 * of it up to the specified length
	 * 
	 * @param s
	 * @param length
	 * @return
	 */
	public static String pad(String s, int length) {
		int sLength = s.length();
		if (sLength == length) {
			return s;
		} else if (sLength <= length) {
			int pad = length - sLength;
			int leftPad = pad / 2;
			int rightPad = length - leftPad;
			return repeat(" ", leftPad) + s + repeat(" ", rightPad);
		} else {
			return s.substring(0, length - 3) + "...";
		}
	}

	public static String[] wrapp(String s, int lineLength, int maxSize) {
		if (s.length() > maxSize) {
			s = s.substring(0, maxSize);
		} else {
			s = s + repeat(" ", maxSize - s.length());
		}
		Vector res = new Vector();
		int currentPosition = 0;
		while (currentPosition <= s.length()) {
			if (s.length() - currentPosition < lineLength) {
				res.addElement(s.substring(currentPosition));
				currentPosition = s.length() + 1;
			} else {
				res.addElement(s.substring(currentPosition, currentPosition
						+ lineLength));
				currentPosition += lineLength;
			}
		}
		String[] tokens = new String[res.size()];
		Enumeration e = res.elements();
		int index = 0;
		while (e.hasMoreElements()) {
			String token = (String) e.nextElement();
			tokens[index++] = token;
		}
		return tokens;
	}

	private static String repeat(String s, int n) {
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < n; i++) {
			buf.append(s);
		}
		return buf.toString();
	}

	public static String removeAll(char c, String s) {
		char[] chars = s.toCharArray();
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < chars.length; i++) {
			if (chars[i] != c) {
				buf.append(chars[i]);
			}
		}
		return buf.toString();
	}

	public static String uriEncode(String s) {
		return replaceAll(s, URL_ENCODING);
	}

	public static String replaceAll(String s, Hashtable replacements) {
		StringBuffer buf = new StringBuffer();
		int len = s.length();
		for (int i = 0; i < len; i++) {
			String si = s.substring(i, i + 1);
			String enc = (String) URL_ENCODING.get(si);
			if (enc != null) {
				buf.append(enc);
			} else {
				buf.append(si);
			}
		}
		return buf.toString();
	}

	public static String format(Date d) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		StringBuffer buf = new StringBuffer();
		buf.append(cal.get(Calendar.DAY_OF_MONTH)).append("-");
		buf.append(cal.get(Calendar.MONTH)).append("-");
		buf.append(cal.get(Calendar.YEAR)).append(" at ");
		buf.append(cal.get(Calendar.HOUR_OF_DAY)).append(":");
		buf.append(cal.get(Calendar.MINUTE)).append(":");
		buf.append(cal.get(Calendar.SECOND));
		return buf.toString();
	}

	public static String formatFileSize(long size) {
		long mbs = size / _1MB;
		long kbs = (size - mbs * _1MB) / _1KB;
		if (mbs > 0) {
			return (kbs < 512 ? mbs + " MB" : (mbs + 1) + " MB");
		} else {
			if (kbs < 1) {
				return "Less than 1 KB";
			} else {
				return kbs + " KB";
			}
		}
	}

	/**
	 * Add all elements in v to res
	 */
	public static void addElements(Vector res, Vector v) {
		Enumeration e = v.elements();
		while (e.hasMoreElements()) {
			Object o = e.nextElement();
			res.addElement(o);
		}
	}

	public static Object[][] matrix(Vector items, int columns) {
		int r = items.size() % columns;
		int n;
		if (r == 0) {
			n = items.size() / columns;
		} else {
			n = items.size() / columns + 1;
		}
		Object[][] res = new Object[n][columns];
		Enumeration e = items.elements();
		int index = 0;
		while (e.hasMoreElements()) {
            Object item = e.nextElement();
			int i = index / columns;
			int j = index % columns;
			res[i][j] = item;
			index++;
		}
		return res;
	}

	public static void quickSort(Object[] a, Comparator c) {
		quickSort(a, c, 0, a.length - 1);
	}

	private static void quickSort(Object[] a, Comparator c, int lo, int hi) {
		if (hi <= lo)
			return;
		int lt = lo, gt = hi;
		Object v = a[lo];
		int i = lo;
		while (i <= gt) {
			int cmp = c.compare(a[i], v);
			if (cmp < 0)
				exch(a, lt++, i++);
			else if (cmp > 0)
				exch(a, i, gt--);
			else
				i++;
		}

		quickSort(a, c, lo, lt - 1);
		quickSort(a, c, gt + 1, hi);
	}

	// exchange a[i] and a[j]
	private static void exch(Object[] a, int i, int j) {
		Object swap = a[i];
		a[i] = a[j];
		a[j] = swap;
	}

	public static void quickSort(Vector a, Comparator c) {
		quickSort(a, c, 0, a.size() - 1);
	}

	private static void quickSort(Vector a, Comparator c, int lo, int hi) {
		if (hi <= lo)
			return;
		int lt = lo, gt = hi;
		Object v = a.elementAt(lo);
		int i = lo;
		while (i <= gt) {
			int cmp = c.compare(a.elementAt(i), v);
			if (cmp < 0)
				exch(a, lt++, i++);
			else if (cmp > 0)
				exch(a, i, gt--);
			else
				i++;
		}

		quickSort(a, c, lo, lt - 1);
		quickSort(a, c, gt + 1, hi);
	}

	private static void exch(Vector a, int i, int j) {
		Object swap = a.elementAt(i);
		a.setElementAt(a.elementAt(j), i);
		a.setElementAt(swap, j);
	}

	public static Vector asVector(Object[] o) {
		Vector res = new Vector();
		for (int i = 0; i < o.length; i++) {
			res.addElement(o[i]);
		}
		return res;
	}

	public static Date parseGoogleDate(String s) {
		return parseBoxDate(s);
	}

}
