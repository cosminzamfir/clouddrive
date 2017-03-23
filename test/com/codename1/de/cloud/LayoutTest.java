package com.codename1.de.cloud;

import com.codename1.de.cloud.drive.gui.components.HttpRequestProgressIndicator;
import com.codename1.de.cloud.net.HttpRequest;
import com.codename1.de.cloud.util.Colors;
import com.codename1.io.Log;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BorderLayout;


public class LayoutTest {

    public static Display d;

    public static void init() {
        d = Display.getInstance();
        Display.init(new Object());
        Form f = new Form();
        Display.init(f);
    }

    public static void cloud() {
        Log.setLevel(Log.INFO);
        Form f = new Form();
        f.setScrollable(false);
        f.getStyle().setBgColor(Colors.BLACK);

        Container container = getContainer(f);
        
        f.addComponent(container);
        Display.init(f);
        f.show();
    }

    private static Container getContainer(Form f) {
    	Container res = new Container();
    	res.setPreferredH(Display.getInstance().getDisplayHeight());
    	res.setPreferredW(Display.getInstance().getDisplayWidth());
    	res.setLayout(new BorderLayout());
    	Label l1 = new Label("North");
    	Label l2 = new Label("Center");
    	Label l3 = new Label("South");
    	res.addComponent(BorderLayout.NORTH, l1);
    	res.addComponent(BorderLayout.CENTER, l2);
    	res.addComponent(BorderLayout.SOUTH, l3);
    	return res;
    }

	public static void testProgress() {
        HttpRequest req =
                new HttpRequest(
                        "http://gts.xeop.de/cmqa/repos/gts/ise/001.000/x86_64/environment/RPMS/gts-oscar-0.112.38-1.el5.x86_64.rpm");
        NetworkManager.getInstance().addToQueue(req.getUnderlying());
        HttpRequestProgressIndicator progress = new HttpRequestProgressIndicator(req);
        Form f = new Form();
        f.addComponent(progress);
        f.show();
    }

    public static void main(String[] args) {
        init();
        //testProgress();
        cloud();
    }

}
