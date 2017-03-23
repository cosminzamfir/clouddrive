package com.codename1.de.cloud.drive.storage.googledrive;

import com.codename1.de.cloud.drive.EnumDriveType;
import com.codename1.de.cloud.drive.gui.factory.ImageFactory;
import com.codename1.de.cloud.drive.storage.Directory;
import com.codename1.de.cloud.drive.storage.Drive;
import com.codename1.de.cloud.drive.storage.googledrive.auth.GoogleDriveAuth;
import com.codename1.de.cloud.net.JSONRequest;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

public class GoogleDrive implements Drive {

    private static final String ROOT = "https://www.googleapis.com/drive/v2/files";

    private static GoogleDrive instance;

    private GoogleDriveAuth auth;

    public static GoogleDrive instance() {
        if (instance == null) {
            instance = new GoogleDrive();
        }
        return instance;
    }

    private GoogleDrive() {
        //        auth = GoogleDriveAuth.instance();
        //        if (!auth.isComplete()) {
        //            throw new CloudSyncException("GoogleDrive authorization is not complete");
        //        }
    }

    public Vector list() {
        Vector res = new Vector();
        JSONRequest request = new JSONRequest(ROOT);
        auth.sign(request);
        Hashtable h = request.getJsonResponse(5000);
        Vector items = (Vector) h.get("items");
        Enumeration e = items.elements();
        while (e.hasMoreElements()) {
            Hashtable jsonItem = (Hashtable) e.nextElement();
            res.addElement(new GoogleDriveFile(jsonItem, this));
        }
        return res;
    }

    public Vector list(String path) {
        return null;
    }

    public long totalSpace() {
        return 0;
    }

    public long availableSpace() {
        return 0;
    }

    public long usedSpace() {
        return 0;
    }

    public void mkdir(Directory parent, String name) {
    }

    public String shortName() {
        return "GoogleDrive";
    }

    public EnumDriveType type() {
        return null;
    }

    public String iconName() {
        return ImageFactory.googledrive;
    }

}
