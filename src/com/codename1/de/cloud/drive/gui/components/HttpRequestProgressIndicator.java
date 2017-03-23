package com.codename1.de.cloud.drive.gui.components;

import com.codename1.de.cloud.net.HttpRequest;
import com.codename1.io.Log;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Container;
import com.codename1.ui.Slider;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;


/**
 * A progress indicator for a {@link HttpRequest}
 * <p>
 * This class relies on {@link NetworkEvent#getProgressPercentage()} which does not guarantee a 
 * valid percentage value in all cases. 
 * @author Cosmin Zamfir
 *
 */
public class HttpRequestProgressIndicator extends Container implements ActionListener {

    private HttpRequest request;
    private Slider slider;

    public HttpRequestProgressIndicator(HttpRequest request) {
        super();
        this.request = request;
        NetworkManager.getInstance().addProgressListener(this);
        setLayout(new BoxLayout(BoxLayout.X_AXIS));
        slider = new Slider();
        addComponent(slider);

    }

    public void actionPerformed(ActionEvent evt) {
        NetworkEvent ne = (NetworkEvent) evt;
        if (ne.getConnectionRequest() != request.getUnderlying()) {
            return;
        }
        Log.p(request + " NetworkEvent received: " + HttpRequest.neCodes.get(new Integer(ne.getProgressType())) + "; "
                + ne.getProgressPercentage(), Log.DEBUG);
        if (ne.getProgressType() == NetworkEvent.PROGRESS_TYPE_COMPLETED) {
            setVisible(false);
            revalidate();
        }
        if (ne.getProgressPercentage() >= 0) {
            slider.setProgress(ne.getProgressPercentage());
        }

    }

}
