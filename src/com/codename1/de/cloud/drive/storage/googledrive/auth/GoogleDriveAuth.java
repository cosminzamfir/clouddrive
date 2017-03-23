package com.codename1.de.cloud.drive.storage.googledrive.auth;

import com.codename1.de.cloud.net.JSONRequest;

public class GoogleDriveAuth {

    private static GoogleDriveAuth instance;

    public static GoogleDriveAuth instance() {
        if (instance == null) {
            instance = new GoogleDriveAuth();
        }
        return instance;
    }

    private GoogleDriveAuth() {
    }

    public boolean isComplete() {
        return false;
    }

    public void sign(JSONRequest request) {
    }
}
