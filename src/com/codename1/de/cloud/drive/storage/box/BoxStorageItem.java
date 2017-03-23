package com.codename1.de.cloud.drive.storage.box;

import com.codename1.de.cloud.drive.exception.CloudSyncException;
import com.codename1.de.cloud.drive.storage.Directory;
import com.codename1.de.cloud.drive.storage.StorageItem;

import java.util.Date;
import java.util.Hashtable;

/**
 * 
 * @author Cosmin Zamfir
 *
 */
public abstract class BoxStorageItem implements StorageItem {

    private final static String file = "file";
    private final static String directory = "folder";

    private String path;
	private String name;
	private String description;
	private int size;
	private Date createdAt;
	private Date modifiedAt;
	private Directory parent;
	private BoxDrive drive;
    private Integer id;

    /**
     * Box directory listing provides only the following info about the children:
     *      *    "items":[
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
     *    All other attributes are returned when the item content is listed.
     *    <p>
     *    This is quite different compared to the local file system or DropBox, where all 
     *    children metadata are provided when the parent content is listed. 
     *    
     *    @see BoxDrive#list() for more details about the box response
     * @param parent
     * @param drive
     * @param json
     */
	public BoxStorageItem(BoxDirectory parent, BoxDrive drive, Hashtable json) {
		this.drive = drive;
		this.parent = parent;
        this.id = (Integer) json.get("id");
        this.name = (String) json.get("name");
	}

    /**
     * 
     * @param parent
     * @param drive
     * @param json
     * @return a {@link BoxDirectory} or {@link BoxFile} based on the "type" json attribute value
     */
    public static BoxStorageItem instance(BoxDirectory parent, BoxDrive drive, Hashtable json) {
        if (json.get("type").equals(file)) {
            return new BoxFile(parent, drive, json);
        } else {
            return new BoxDirectory(parent, drive, json);
        }
    }


	public String name() {
		return name;
	}

	public String absolutePath() {
		return path;
	}

	public Directory parent() {
		return parent;
	}

	public Date lastModified() {
		return modifiedAt;
	}

	public void delete() {
		drive.delete(this);
	}

	public void move(Directory parent) {
		drive.move(this, parent);
	}

	public void copy(Directory parent) {
		drive.copy(this, parent);
	}

    /**
     * 
     * @return the unique number which identifies each storage item in a box account  
     */
    public Integer getId() {
        return id;
    }

    public String uri() {
        throw new CloudSyncException("Box uri() not implemented yet");
    }

    public void rename(String newName) {
    }

}
