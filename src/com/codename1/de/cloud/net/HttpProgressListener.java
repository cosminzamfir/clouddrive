package com.codename1.de.cloud.net;

import com.codename1.io.Log;
import com.codename1.io.NetworkEvent;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;

/**
 * 
 * @author Cosmin Zamfir
 *
 */
public class HttpProgressListener implements ActionListener {

    private HttpRequest request;

    public HttpProgressListener(HttpRequest connection) {
        this.request = connection;
    }

    public void actionPerformed(ActionEvent evt) {
        NetworkEvent ne = (NetworkEvent) evt;
        progress(ne);
    }

    private void progress(NetworkEvent ne) {
        if (ne.getConnectionRequest() != request.getUnderlying()) {
            return;
        }
        request.setChanged();
        request.notifyObservers();
        Log.p(HttpRequest.neCodes.get(new Integer(ne.getProgressType())) + "; " + ne.getProgressPercentage(), Log.DEBUG);
        if (ne.getProgressType() == NetworkEvent.PROGRESS_TYPE_COMPLETED) {
            request.completed();
            request.setResponseData(ne.getConnectionRequest().getResponseData());
        }
        if (ne.getProgressPercentage() >= 0) {
            request.setProgress(ne.getProgressPercentage()); //TODO - HttpRequest.progress attribute should be removed
            request.notifyObservers(new HttpProgressEvent(ne.getProgressPercentage()));
        }
    }

}
