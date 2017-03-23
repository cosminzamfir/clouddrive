package com.codename1.de.cloud.net;

import com.codename1.de.cloud.util.Observer;

/**
 * Argument to be used to notify {@link Observer}s of a {@link HttpRequest} of a new progress event 
 * @author Cosmin Zamfir
 *
 */
public class HttpProgressEvent {

    /**
     * Progress in percentage
     */
    private int progress;

    public HttpProgressEvent(int progress) {
        super();
        this.progress = progress;
    }

    public int getProgress() {
        return progress;
    }

}
