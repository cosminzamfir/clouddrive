package com.codename1.de.cloud.drive.storage.box;

import com.codename1.de.cloud.drive.EnumDriveType;
import com.codename1.de.cloud.drive.gui.factory.ImageFactory;
import com.codename1.de.cloud.drive.storage.Directory;
import com.codename1.de.cloud.drive.storage.Drive;
import com.codename1.de.cloud.drive.storage.StorageItem;
import com.codename1.de.cloud.drive.storage.box.auth.BoxAuth;
import com.codename1.de.cloud.net.JSONRequest;
import com.codename1.de.cloud.util.CommonUtils;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

/**
 * 
 * @author Cosmin Zamfir
 *
 */
public class BoxDrive implements Drive {

    private static String LIST_FOLDER_URL = "https://www.box.com/api/2.0/folders/";
    private BoxAuth auth;

    private static BoxDrive instance;

    public static BoxDrive instance() {
        if (instance == null) {
            instance = new BoxDrive();
        }
        return instance;
    }

    private BoxDrive() {
        auth = BoxAuth.newInstance();
    }

    /**
     * List the content of the root folder curl
     * https://www.box.com/api/2.0/folders/FOLDER_ID \ -H
     * "Authorization: BoxAuth api_key=API_KEY&auth_token=AUTH_TOKEN"
     * <p>
     * Sample JSON response:
     * {
     *      "type":"folder",
     *      "id":"11008856",
     *      "name":"My Awesome Files",
     *      "created_at":"2012-02-09T18:04:02-08:00",
     *      "modified_at":"2012-02-09T18:04:02-08:00",
     *      "description":"",
     *      "size":"24124124",
     *      "created_by": "17046989"
     *      {
     *          "type":"user",
     *          "id":"17046989",
     *          "name":"Donald Glover"
     *      },
     *      "modified_by":
     *      {
     *         "type":"user",
     *         "id":"17046989",
     *         "name":"Donald Glover"
     *      },
     *      "owned_by":
     *      {
     *         "type":"user",
     *         "id":"17046989",
     *         "name":"Donald Glover"
     *      },
     *      "parent_folder":
     *      {
     *          "type":"folder",
     *           "id":0,
     *           "name":"All Files"
     *},
     *    "items":[
     *    [
     *        {
     *            "type":"folder",
     *             "id":"10941646",
     *            "name":"Updated Folder"
     *        },
     *        {
     *            "type":"file",
     *             "id":"69952066",
     *            "name":"file.html"
     *        }
     *    ]
     *]
     *}
     */

    public Vector list() {
        BoxDirectory d = null;
        return list(d);
    }

    public Vector list(BoxDirectory directory) {
        JSONRequest request;
        if (directory == null) {
            // root folder ID always 0
            request = new JSONRequest(LIST_FOLDER_URL + "0");
        } else {
            request = new JSONRequest(LIST_FOLDER_URL + directory.getId());
        }
        auth.sign(request);
        Hashtable json = request.getJsonResponse(5000);
        CommonUtils.print(json, 0);
        Vector res = new Vector();
        Vector items = (Vector) json.get("items");
        Enumeration e = items.elements();
        while (e.hasMoreElements()) {
            Hashtable jsonItem = (Hashtable) e.nextElement();
            BoxStorageItem boxItem = BoxStorageItem.instance(directory, this, jsonItem);
            res.addElement(boxItem);
        }
        return res;
    }

    public void delete(StorageItem item) {
    }

    public void move(StorageItem item, Directory parent) {
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

    public void copy(BoxStorageItem boxStorageItem, Directory parent) {
    }

    public String shortName() {
        return "Box";
    }

    public EnumDriveType type() {
        return EnumDriveType.BOX;
    }

    public Vector list(String path) {
        return null;
    }

    public Vector search(Directory dir, StorageItem filter, boolean recursive) {
        return null;
    }

    public String iconName() {
        return ImageFactory.box;
    }

}
