package com.codename1.de.cloud;

import com.codename1.de.cloud.drive.gui.actions.navigation.NavigationManager;
import com.codename1.de.cloud.drive.gui.components.HttpRequestProgressIndicator;
import com.codename1.de.cloud.drive.gui.containers.DriveSelector;
import com.codename1.de.cloud.net.HttpRequest;
import com.codename1.de.cloud.util.Colors;
import com.codename1.io.Log;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BorderLayout;


public class Simulator {

    public static Display d;

    public static void init() {
        d = Display.getInstance();
        Display.init(new Object());
        Form f = new Form();
        f.setPreferredSize(new Dimension(400, 700));
        Display.init(f);
    }

    public static void cloud() {
        Log.setLevel(Log.DEBUG);
        Form f = new Form();
		f.getContentPane().setLayout(new BorderLayout());
        f.setScrollable(false);
        f.getStyle().setBgColor(Colors.BACKGROUND);
        NavigationManager.setRoot(f);
        DriveSelector selector = DriveSelector.instance();
        f.addComponent(BorderLayout.CENTER, selector);
        Display.init(f);
        f.show();
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
