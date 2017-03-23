package com.codename1.de.cloud.drive.gui.factory;

import com.codename1.de.cloud.drive.exception.CloudSyncException;
import com.codename1.ui.Image;

import java.io.IOException;
import java.util.Hashtable;

/**
 * 
 * @author Cosmin Zamfir
 *
 */
public class ImageFactory {

    public static final String dropbox = "icons/dropbox.png";
    public static final String box = "icons/box.png";
    public static final String cloudme = "icons/cloudme.png";
    public static final String _4shared = "icons/4shared.png";
    public static final String file = "icons/file.png";
    public static final String folder = "icons/folder.png";
    public static final String lfs = "icons/lfs.png";
    public static final String sugarsync = "icons/sugarsync.png";
    public static final String up = "icons/up.png";
    public static final String wuala = "icons/wuala.png";
    public static final String googledrive = "icons/googledrive.png";
    public static final String home = "icons/home.png";
    public static final String back = "icons/back.png";
    public static final String wait = "icons/wait.gif";
    public static final String virtualDrive = "icons/virtual-drive.png";
    public static final String listView = "icons/list_view.png";
    public static final String iconView = "icons/icon_view.png";
    public static final String virtualViews = "icons/virtual_views.png";
    public static final String categoryFilter = "icons/category-filter.png";
    public static final String apply = "icons/apply.png";
    public static final String checkboxChecked = "icons/checkbox_checked.png";
    public static final String checkboxUnchecked = "icons/checkbox_unchecked.png";
    public static final String search = "icons/search.png";
    public static final String stop = "icons/stop.png";
    public static final String question = "icons/question.png";
    public static final String info = "icons/info.png";

    public static final String pictures = "icons/pictures.png";
    public static final String videos = "icons/videos.png";
    public static final String music = "icons/music.png";
    public static final String documents = "icons/documents.png";
    public static final String programming = "icons/programming.png";

    public static Hashtable fileTypeIcons = new Hashtable();

    public static int SMALL = 40;
    public static int MEDIUM = 60;
    public static int LARGE = 140;
    public static int XXL = 320;

    static {
        fileTypeIcons.put("accdb", "accdb");
        fileTypeIcons.put("avi", "avi");
        fileTypeIcons.put("bmp", "bmp");
        fileTypeIcons.put("css", "css");
        fileTypeIcons.put("doc", "doc");
        fileTypeIcons.put("docx_mac", "docx_mac");
        fileTypeIcons.put("docx", "docx");
        fileTypeIcons.put("eml", "eml");
        fileTypeIcons.put("eps", "eps");
        fileTypeIcons.put("fla", "fla");
        fileTypeIcons.put("gif", "gif");
        fileTypeIcons.put("html", "html");
        fileTypeIcons.put("ind", "ind");
        fileTypeIcons.put("ini", "ini");
        fileTypeIcons.put("jpeg", "jpeg");
        fileTypeIcons.put("jpg", "jpeg");
        fileTypeIcons.put("jsf", "jsf");
        fileTypeIcons.put("midi", "midi");
        fileTypeIcons.put("mov", "mov");
        fileTypeIcons.put("mp4", "mov");
        fileTypeIcons.put("3gp", "mov");
        fileTypeIcons.put("mp3", "mp3");
        fileTypeIcons.put("mpeg", "mpeg");
        fileTypeIcons.put("pdf", "pdf");
        fileTypeIcons.put("png", "png");
        fileTypeIcons.put("ppt", "ppt");
        fileTypeIcons.put("pptx_mac", "pptx_mac");
        fileTypeIcons.put("pptx", "pptx");
        fileTypeIcons.put("proj", "proj");
        fileTypeIcons.put("psd", "psd");
        fileTypeIcons.put("pst", "pst");
        fileTypeIcons.put("pub", "pub");
        fileTypeIcons.put("rar", "rar");
        fileTypeIcons.put("readme", "readme");
        fileTypeIcons.put("settings", "settings");
        fileTypeIcons.put("text", "text");
        fileTypeIcons.put("tiff", "tiff");
        fileTypeIcons.put("url", "url");
        fileTypeIcons.put("vsd", "vsd");
        fileTypeIcons.put("wav", "wav");
        fileTypeIcons.put("wma", "wma");
        fileTypeIcons.put("wmv", "wmv");
        fileTypeIcons.put("xls", "xls");
        fileTypeIcons.put("xlsx_mac", "xlsx_mac");
        fileTypeIcons.put("xlsx", "xlsx");
        fileTypeIcons.put("zip", "zip");

    }

    /**
     * Reuse images 
     * Maps 'imageName+size' to the {@link Image} instances
     */
    private static Hashtable cache = new Hashtable();

    /**
     * Retrieve an instance of the specified image scaled to size*size
     * 
     * @param imageName
     *            file name relative to a classpath element; predefined images
     *            as constants in this class
     * @param size
     *            predefined sizes as constants in this class. If not specified
     *            original size is returned.
     * @return
     */
    public static Image instance(String imageName, int size) {
        try {
            String key = imageName + size;
            Image res = (Image) cache.get(key);
            if (res == null) {
                try {
                    res = Image.createImage(imageName);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (size != 0) {
                    res = res.scaled(size, size);
                }
                cache.put(key, res);
            }
            return res;
        } catch (Exception e) {
            e.printStackTrace();
            throw new CloudSyncException("Error retrieving image with name: " + imageName + " : " + e.getMessage());
        }
    }

    public static Image instance(String imageName, int width, int height) {
        String key = imageName + width + ":" + height;
        Image res = (Image) cache.get(key);
        if (res == null) {
            try {
                res = Image.createImage(imageName);
            } catch (IOException e) {
                e.printStackTrace();
            }
            res = res.scaled(width, height);
            cache.put(key, res);
        }
        return res;
    }

    public static Image getWait(int width, int height) {
        return instance("icons/wait.gif", width, height);
    }

    /**
     * Retrieve the scaled {@link Image} for the given file name
     * @param fileName the file name
     * @param size the scaled size
     * @return the image stored in the icons/filetype directory which has the same name as given @fileName extension
     */
    public static Image instanceForFileType(String fileName, int size) {
        int lastDotIndex = fileName.lastIndexOf('.');
        if (lastDotIndex < 0) { //generic file icon if no extension
            return instance(file, size);
        }
        String extension = fileName.substring(lastDotIndex + 1);
        if (extension.length() == 0) {
            return instance(file, size);
        }
        String icon = (String) fileTypeIcons.get(extension);
        if (icon == null) {
            return instance(file, size);
        }
        return instance("icons/filetype/" + icon + ".png", size);
    }
}
