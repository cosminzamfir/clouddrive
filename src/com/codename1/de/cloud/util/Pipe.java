package com.codename1.de.cloud.util;

import com.codename1.io.Log;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * Transfer the byte content read from an {@link InputStream} to an {@link OutputStream} 
 * <p>
 * Reading and writing are both performed in new {@link Thread}s
 * TODO - implement a general purpose thread pool.  
 * @author Cosmin Zamfir
 *
 */
public class Pipe {

    private static final int bufferSize = 1024;
    private InputStream is;
    private OutputStream os;
    private byte[] sharedBuffer = new byte[bufferSize];
    private Object lock = new Object();
    private volatile boolean completed = false;

    public Pipe(InputStream is, OutputStream os) {
        super();
        this.is = is;
        this.os = os;
        this.sharedBuffer = new byte[bufferSize];
    }

    /**
     * Transfer the content from the {@link InputStream} to the {@link OutputStream}.
     * <p>
     * <ol>
     * <li>Start a new thread to read from IS
     * <li>After each read, enter a synchronized block on the shared buffer to:
     * <ul>
     * <li>copy the read chunk to the buffer
     * <li>notify the waiting thread that the buffer is available for reading
     * </ul>
     * <li>The current thread waits to get the lock on the shared buffer
     * <li>When the lock is granted, write the buffer content to the os
     * <li>Notify the waiting thread that the buffer is available for writing
     * </ol>
     * 
     */
    public void run() {
        Runnable producer = new Runnable() {

            public void run() {
                produce();
            }
        };
        Runnable consumer = new Runnable() {

            public void run() {
                consume();
            }
        };
        Thread p = new Thread(producer, "Producer");
        Thread c = new Thread(consumer, "Consumer");
        p.start();
        c.start();
        try {
            p.join();
            c.join();
        } catch (InterruptedException e) {
            // TODO: implement to allow a pipe operation to be cancelled on user request
        }
        Log.p("Done");
    }

    private void produce() {
        try {
            byte[] buff = new byte[1024];
            while (is.read(buff) > 0) {
                Log.p("New data read. Waiting to get lock ...", Log.DEBUG);
                synchronized (lock) {
                    Log.p("Copy read data to the shared buffer ", Log.DEBUG);
                    copy(buff, sharedBuffer);
                    lock.notify();
                    Log.p("Notify consumer that new data is available...", Log.DEBUG);
                }
            }
            completed = true;

        } catch (Exception e) {
            completed = true;
        } finally {
            //should we close here the streams? or the caller should do it?
        }
    }

    private void consume() {
        try {
            while (!completed) {
                Log.p("Waiting for new data ...", Log.DEBUG);
                synchronized (lock) {
                    Log.p("New data received. Writing to the output stream...", Log.DEBUG);
                    os.write(sharedBuffer);
                    sharedBuffer = new byte[bufferSize];
                    lock.notify();
                    Log.p("Date written to the output stream. Notify producer that shared buffer is available",
                            Log.DEBUG);
                }
            }
        } catch (Exception e) {
            completed = true;
        } finally {
            //should we close here the streams? or the caller should do it?
        }
    }

    private void copy(byte[] source, byte[] target) {
        for (int i = 0; i < target.length; i++) {
            target[i] = source[i];
        }
    }

}
