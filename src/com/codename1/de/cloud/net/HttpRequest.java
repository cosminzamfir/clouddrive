package com.codename1.de.cloud.net;

import com.codename1.de.cloud.drive.exception.CloudSyncException;
import com.codename1.de.cloud.drive.exception.HttpErrorCodeException;
import com.codename1.de.cloud.util.CommonUtils;
import com.codename1.de.cloud.util.Observable;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.Log;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;


import java.util.Hashtable;

/**
 * A convenience wrapper for a {@link ConnectionRequest} object
 * 
 * @author Cosmin Zamfir
 * 
 */
public class HttpRequest extends Observable {

    private static final boolean sync = true;
    public static final Hashtable neCodes = new Hashtable();
    private static NetworkManager nm;

    /**
     * The number of retries for an {@link HttpErrorCodeException} with an error code -1
     * TODO - why is this happening on Android? 
     */
    private static final int errorCodeRetries = 1;

    static {
        neCodes.put(new Integer(NetworkEvent.PROGRESS_TYPE_INITIALIZING), "Initializing");
        neCodes.put(new Integer(NetworkEvent.PROGRESS_TYPE_INPUT), "Processing input");
        neCodes.put(new Integer(NetworkEvent.PROGRESS_TYPE_OUTPUT), "Processing output");
        neCodes.put(new Integer(NetworkEvent.PROGRESS_TYPE_COMPLETED), "Request completed");
        nm = NetworkManager.getInstance();
    }

    private ConnectionRequestExt underlying;
    private int progress;
    private volatile boolean completed;
    private volatile byte[] responseData;
    private Hashtable requestHeaders = new Hashtable();
    /**
     * Any message that is sent in the response along with the http response
     * code
     */
    private String message;

    public HttpRequest(String url) {
        super();
        underlying = new ConnectionRequestExt();
        underlying.setUrl(url);
        underlying.setPost(false);
    }

    public HttpRequest(String url, boolean post) {
        this(url);
        underlying.setPost(post);
    }

    public void addRequestHeader(String key, String value) {
        underlying.addRequestHeader(key, value);
        requestHeaders.put(key, value);
    }

    /**
     * If the underlying {@link ConnectionRequest} needs to be directly accessed
     * 
     * @return
     */
    public ConnectionRequest getUnderlying() {
        return underlying;
    }

    public byte[] getResponseData() {
        return responseData;
    }

    public void setResponseData(byte[] responseData) {
        this.responseData = responseData;
    }

    public int getProgress() {
        return progress;
    }

    public String getUrl() {
        return underlying.getUrl();
    }

    public byte[] run(long timeout) {
        //underlying.setSilentRetryCount(0);
        if (sync) {
            return runSync();
        } else {
            return runAsync(timeout);
        }
    }

    /**
     * Send this request to the {@link #url}, update all registered Observers
     * with the progress and return the response as an array of bytes
     * 
     * @return the response to this request instance or <code>null</code> if
     *         timeout exceeded
     */
    public byte[] runAsync(long timeout) {
        int index = 0;
        while (true) {
            try {
                Log.p("Adding " + underlying.getUrl() + " to network manager queue", Log.DEBUG);
                underlying.addResponseListener(new HttpResponseListener(this));
                underlying.addResponseCodeListener(new HttpResponseCodeListener(this));
                nm.addToQueue(underlying);
                int waitCycle = 0;
                long waitTime = 30;

                while (!completed) {
                    if (waitCycle++ * waitTime > timeout) {
                        throw new RuntimeException("No response received for " + this + ". Timeout is: " + timeout);

                    }
                    try {
                        Thread.sleep(30);
                    } catch (InterruptedException ignore) {
                    }
                }
                if (getErroResponseCode() != 0) {
                    Log.p("Unexpected error code received from " + underlying.getUrl() + ":" + getErroResponseCode()
                            + ". Response message: " + message, Log.DEBUG);
                    throw new HttpErrorCodeException(getErroResponseCode(), getResponseMessage());
                }
                Log.p("Response received for request " + underlying.getUrl() + ". Response size: "
                        + underlying.getResponseData().length, Log.DEBUG);
                return underlying.getResponseData();
            } catch (HttpErrorCodeException e) {
                if (e.getErrorCode() < 0) {
                    if (index++ < errorCodeRetries) {
                        underlying.setResponseErrorCode(0);
                        continue;
                    } else {
                        underlying.kill();
                        throw e;
                    }
                }
            } catch (CloudSyncException e) {
                underlying.kill();
                throw e;
            } catch (Exception e) {
                underlying.kill();
                throw new RuntimeException("Cannot send " + this + " : " + e.getMessage());
            } finally {
            }
        }
    }

    private String getResponseMessage() {
        return underlying.getResponseMessage();
    }

    public byte[] runSync() {
        int index = 0;
        while (true) {
            try {
                Log.p("Adding " + underlying.getUrl() + " to network manager queue", Log.DEBUG);
                nm.addToQueueAndWait(underlying);
                if (underlying.getErr() != null) {
                    underlying.kill();
                    throw new CloudSyncException("Cannot send " + this + " : " + underlying.getErr().getMessage());
                }
                byte[] response = underlying.getResponseData();
                if (response == null && getErroResponseCode() != 0) {
                    Log.p("Unexpected error code received from " + underlying.getUrl() + ":" + getErroResponseCode()
                            + ". Response message: " + message, Log.DEBUG);
                    throw new HttpErrorCodeException(getErroResponseCode(), getResponseMessage());
                }
                if (response != null) {
                    Log.p("Response received for request " + underlying.getUrl() + ". Response size: "
                            + underlying.getResponseData().length, Log.DEBUG);
                }
                return response;
            } catch (HttpErrorCodeException e) {
                if (e.getErrorCode() < 0) {
                    if (index++ < errorCodeRetries) {
                        underlying.setResponseErrorCode(0);
                        continue;
                    } else {
                        underlying.kill();
                        throw e;
                    }
                } else {
                	underlying.kill();
                	throw e;
                }
            } catch (CloudSyncException e) {
                underlying.kill();
                throw e;
            } catch (Exception e) {
                underlying.kill();
                throw new RuntimeException("Cannot send " + this + " : " + e.getMessage());
            } finally {
            }
        }
    }

    public void completed() {
        completed = true;

    }

    public void setErroResponseCode(int responseCode) {
        this.underlying.setResponseErrorCode(responseCode);
    }

    public int getErroResponseCode() {
        return underlying.getResponseErrorCode();
    }

    public void setProgress(int progressPercentage) {
        this.progress = progressPercentage;
    }

    public String toString() {
        StringBuffer buf = new StringBuffer("HttpConnection: [url=" + this.underlying.getUrl());
        String headers = CommonUtils.buildHttpHeader(requestHeaders);
        buf.append("]");
        return buf.toString();
    }

    public void setMessage(String message) {
        this.underlying.setErrorMessage(message);
    }
}
