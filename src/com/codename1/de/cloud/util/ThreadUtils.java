package com.codename1.de.cloud.util;

import java.util.Enumeration;
import java.util.Vector;

import com.codename1.io.Log;
import com.codename1.ui.Display;

public class ThreadUtils {

    private static Vector threads = new Vector();

    /**
     * All Gui tasks must be executed non-blocking. This is similar to
     * Swing.invokeLater(). If run synchronously, they would e.g. block the http
     * listeners which are executed in the main EDT thread
     */
	public static void runBackgroundTask(Runnable runnable) {
        runnable.run();
        //Display.getInstance().invokeAndBlock(runanble);
	}

    /**
     * Todo - implement a simple ThreadPool
     * @param runnable
     * @param name set the name of the new thread/good names are good for logging
     */
    public static void run(Runnable runnable, String name) {
        Log.p("Starting a new thread for: " + name);
        Thread t = new Thread(runnable, name);
        t.start();
        threads.addElement(t);
    }

    /**
     * For testing only - prevent the tests to exit until all thread finished
     */
    public static void waitForBackgroundThreads() {
        Enumeration e = threads.elements();
        while (e.hasMoreElements()) {
            Thread thread = (Thread) e.nextElement();
            if (thread.isAlive()) {
                try {
                    thread.join();
                } catch (InterruptedException ignore) {
                    ignore.printStackTrace();
                }
            }
        }
    }

    /**
     * Convention is to use class name of the class performing the task as thread name
     * @param threadName
     */
    public static void interrupt(String threadName) {
        Enumeration e = threads.elements();
        while (e.hasMoreElements()) {
            Thread t = (Thread) e.nextElement();
            if (t.getName().equals(threadName)) {
                t.interrupt();
            }
        }
    }
}
