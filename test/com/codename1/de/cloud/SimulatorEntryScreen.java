package com.codename1.de.cloud;

import com.codename1.components.WebBrowser;
import com.codename1.de.cloud.drive.gui.actions.navigation.NavigationManager;
import com.codename1.de.cloud.drive.gui.components.HttpRequestProgressIndicator;
import com.codename1.de.cloud.net.HttpRequest;
import com.codename1.de.cloud.util.Colors;
import com.codename1.io.Log;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BorderLayout;

public class SimulatorEntryScreen {

    public static Display d;

    public static void init() {
        d = Display.getInstance();
        Display.init(new Object());
        Form f = new Form();
        f.setPreferredSize(new Dimension(400, 700));
        Display.init(f);
    }

    public static void cloud() {

        Form f = new Form();
		f.getContentPane().setLayout(new BorderLayout());
        f.setScrollable(false);
        f.getStyle().setBgColor(Colors.BACKGROUND);
        NavigationManager.setRoot(f);

        Log.setLevel(Log.DEBUG);
        WebBrowser browser = new WebBrowser();
        browser.setURL("http://intranet2.deutsche-boerse.de/");
        f.addComponent(BorderLayout.CENTER, browser);

        //        StartScreen startScreen = ContainerFactory.startScreen();
        //        f.addComponent(BorderLayout.CENTER, startScreen);
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
