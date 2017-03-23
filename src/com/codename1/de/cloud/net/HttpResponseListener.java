package com.codename1.de.cloud.net;

import com.codename1.io.Log;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;

/**
 * 
 * @author Cosmin Zamfir
 *
 */
public class HttpResponseListener implements ActionListener {

    private HttpRequest connection;

    public HttpResponseListener(HttpRequest connection) {
        this.connection = connection;
    }

    public void actionPerformed(ActionEvent evt) {
        Log.p("Request " + connection.getUnderlying().getUrl() + " completed", Log.DEBUG);
        connection.completed();
    }

}
