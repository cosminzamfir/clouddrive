package com.codename1.de.cloud.drive.storage.dropbox;

import com.codename1.de.cloud.drive.EnumDriveType;
import com.codename1.de.cloud.drive.gui.factory.ImageFactory;
import com.codename1.de.cloud.drive.storage.Directory;
import com.codename1.de.cloud.drive.storage.Drive;
import com.codename1.de.cloud.drive.storage.StorageItem;
import com.codename1.de.cloud.drive.storage.dropbox.auth.DropboxAuth;
import com.codename1.de.cloud.net.HttpRequest;
import com.codename1.de.cloud.net.JSONRequest;
import com.codename1.de.cloud.net.NetworkUtils;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

/**
 * 
 * @author Cosmin Zamfir
 *
 */
public class DropBoxDrive implements Drive {

    private static final String ROOT = "https://api.dropbox.com";
    private static final String ROOT_CONTENT = "https://api-content.dropbox.com";

    //private static final String ROOT = "http://localhost:8000";
    //private static final String ROOT_CONTENT = "http://localhost:8000";

    private static final String ACCOUNT_INFO_URL = ROOT + "/1/account/info";
    private static final String FILES_METADATA_URL = ROOT + "/1/metadata/dropbox";
    private static final String FILES_DELETE_URL = ROOT + "/1/fileops/delete";
    private static final String FILES_MOVE_URL = ROOT + "/1/fileops/move";
    private static final String FILES_COPY_URL = ROOT + "/1/fileops/copy";
    private static final String CREATE_DIRECTORY_URL = ROOT + "/1/fileops/create_folder";
    private static final String GET_FILE_URL = ROOT_CONTENT + "/1/files/dropbox";

    private DropboxAuth auth;

    private static DropBoxDrive instance;

    public static DropBoxDrive instance() {
        if (instance == null) {
            instance = new DropBoxDrive();
        }
        return instance;
    }

    private DropBoxDrive() {
        super();
        auth = DropboxAuth.instance();
    }

    public Hashtable getAccountInfo() {
        JSONRequest request = new JSONRequest(ACCOUNT_INFO_URL);
        auth.sign(request);
        return NetworkUtils.getJsonResponse(request);
    }

    /**
     * Retrieve the content metadata of the root in dropbox json format
     * <p>
     * Sample response: {"hash": "2347e29324d1c56af8d187cb3b3d94af",
     * "thumb_exists": false, "bytes": 0, "path": "/", "is_dir": true, "size":
     * "0 bytes", "root": "dropbox", "contents": [{"revision": 1, "rev":
     * "107b1336b", "thumb_exists": false, "bytes": 0, "modified":
     * "Sun, 13 May 2012 22:11:07 +0000", "path": "/Photos", "is_dir": true,
     * "icon": "folder_photos", "root": "dropbox", "size": "0 bytes"}], "icon":
     * "folder"}
     * 
     * 
     * @return
     */
    public Hashtable listNative() {
        JSONRequest request = new JSONRequest(FILES_METADATA_URL);
        auth.sign(request);
        // list The strings true and false are valid values. true is the
        // default.
        // If true, the folder's metadata will include a contents field with a
        // list of metadata entries for the
        // contents of the folder. If false, the contents field will be omitted.
        request.addRequestHeader("list", "true");
        return NetworkUtils.getJsonResponse(request);
    }

    /**
     * Retrieve the content metadata of the specified path relative to the
     * dropbox root in dropbox json format
     * <p>
     * Sample response: {"hash": "2347e29324d1c56af8d187cb3b3d94af",
     * "thumb_exists": false, "bytes": 0, "path": "/", "is_dir": true, "size":
     * "0 bytes", "root": "dropbox", "contents": [{"revision": 1, "rev":
     * "107b1336b", "thumb_exists": false, "bytes": 0, "modified":
     * "Sun, 13 May 2012 22:11:07 +0000", "path": "/Photos", "is_dir": true,
     * "icon": "folder_photos", "root": "dropbox", "size": "0 bytes"}], "icon":
     * "folder"}
     * 
     * 
     * @return
     */
    public Hashtable listNative(String path) {
        JSONRequest request = new JSONRequest(FILES_METADATA_URL + "/" + path);
        auth.sign(request);
        return NetworkUtils.getJsonResponse(request);
    }

    public Vector list() {
        Hashtable json = listNative();
        return storageItems(json, null);
    }

    public Vector list(Directory directory) {
        Hashtable json = listNative(directory.absolutePath());
        return storageItems(json, directory);
    }

    public void delete(StorageItem item) {
        HttpRequest request = new HttpRequest(FILES_DELETE_URL, true);
        auth.sign(request);
        request.getUnderlying().addArgument("root", "dropbox");
        request.getUnderlying().addArgument("path", item.absolutePath());
        // ignore json response with metadata of the deleted item
        NetworkUtils.sendReques(request);
    }

    public void move(StorageItem item, Directory parent) {
        HttpRequest request = new HttpRequest(FILES_MOVE_URL, true);
        auth.sign(request);
        request.getUnderlying().addArgument("root", "dropbox");
        String fromPath;
        if (item.parent() == null) { // root
            fromPath = "/";
        } else {
            fromPath = item.parent().absolutePath();
        }
        request.getUnderlying().addArgument("from_path", fromPath);
        request.getUnderlying().addArgument("to_path", parent.absolutePath());
        // ignore json response with metadata of the moved item
        NetworkUtils.sendReques(request);
    }

    public void copy(StorageItem item, Directory parent) {
        HttpRequest request = new HttpRequest(FILES_COPY_URL, true);
        auth.sign(request);
        request.addRequestHeader("root", "dropbox");
        String fromPath;
        if (item.parent() == null) { // root
            fromPath = "/";
        } else {
            fromPath = item.parent().absolutePath();
        }
        request.getUnderlying().addArgument("from_path", fromPath);
        request.getUnderlying().addArgument("to_path", parent.absolutePath());
        // ignore json response with metadata of the copied item
        NetworkUtils.sendReques(request);
    }

    public void mkdir(Directory parent, String name) {
        HttpRequest request = new HttpRequest(CREATE_DIRECTORY_URL);
        String path;
        if (parent == null) {
            path = "/" + name;
        } else {
            path = parent.absolutePath() + "/" + name;
        }
        auth.sign(request);
        request.getUnderlying().addArgument("root", "dropbox");
        request.getUnderlying().addArgument("path", path);
        NetworkUtils.sendReques(request);
    }

    /**
     * Build a {@link Vector} of {@link StorageItem}s from a 'metadata' drop-box
     * response
     * 
     * @see DropBoxDrive#listNative()
     * @param json
     * @return
     */
    private Vector storageItems(Hashtable json, Directory parent) {
        Vector contents = (Vector) json.get("contents");
        Vector res = new Vector();
        Enumeration e = contents.elements();
        // the 'contents Vector contains one Hashtable for each storage item in
        // the parent directory
        while (e.hasMoreElements()) {
            Hashtable jsonStorageItem = (Hashtable) e.nextElement();
            res.addElement(DropboxStorrageItem.newInstance(jsonStorageItem, parent, this));
        }
        return res;
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

    public String shortName() {
        return "Dropbox";
    }

    public EnumDriveType type() {
        return EnumDriveType.DROPBOX;
    }

    public byte[] get(DropboxFile file) {
        HttpRequest request = new HttpRequest(GET_FILE_URL + file.absolutePath());
        return request.run(10000);
    }

    public Vector list(String path) {
        return null;
    }

    public Vector search(Directory dir, StorageItem filter, boolean recursive) {
        return null;
    }

    public String iconName() {
        return ImageFactory.dropbox;
    }

}
