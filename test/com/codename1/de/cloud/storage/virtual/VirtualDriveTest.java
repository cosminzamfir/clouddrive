package com.codename1.de.cloud.storage.virtual;

import com.codename1.de.cloud.TestUtils;
import com.codename1.de.cloud.drive.storage.lfs.LFSDrive;
import com.codename1.de.cloud.drive.virtual.v1.Category;
import com.codename1.de.cloud.drive.virtual.v1.FileTypeCategory;
import com.codename1.de.cloud.drive.virtual.v1.SizeCategory;
import com.codename1.de.cloud.drive.virtual.v1.View;
import com.codename1.de.cloud.drive.virtual.v1.VirtualDirectory;
import com.codename1.de.cloud.drive.virtual.v1.VirtualDrive;
import com.codename1.de.cloud.util.Observable;
import com.codename1.de.cloud.util.Observer;
import com.codename1.io.Log;
import com.codename1.ui.Display;


import java.util.Enumeration;
import java.util.Vector;

public class VirtualDriveTest {

    public static void test1() {
        VirtualDrive d = VirtualDrive.instance();
        d.initialize();
        d.add(LFSDrive.instance(), "/home/zamfcos/test");
        d.addObserver(new Observer() {

            public void update(Observable o, Object arg) {
                //                Log.p("VirtualDrive notification received ");
                //                System.out.println(asString((VirtualDrive) o));
            }
        });
        d.setView(new View(FileTypeCategory.instance()));
        Vector roots = d.list();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
        //list root content
        System.out.println(roots);

        //list content of the root sub-directories 
        Enumeration e = roots.elements();
        while (e.hasMoreElements()) {
            VirtualDirectory v = (VirtualDirectory) e.nextElement();
            Vector items = d.list(v);
            System.out.println(items);
        }

        //set a 2 levels depth view
        View view = new View(FileTypeCategory.instance(), SizeCategory.instance());
        d.setView(view);
        //list root
        roots = d.list();
        System.out.println(roots);

        //list all second and third level tags
        Enumeration en = roots.elements();
        while (en.hasMoreElements()) {
            d.setCurrentDirectory(null);
            VirtualDirectory flVDir = (VirtualDirectory) en.nextElement();
            d.setCurrentDirectory(flVDir);
            Vector secondLevel = d.list(flVDir);
            System.out.println(secondLevel);

            Enumeration slEn = secondLevel.elements();
            while (slEn.hasMoreElements()) {
                VirtualDirectory slVDir = (VirtualDirectory) slEn.nextElement();
                d.setCurrentDirectory(slVDir);
                Vector thirdLevel = d.list(slVDir);
                System.out.println(thirdLevel);
            }

        }

    }

    public static void main(String[] args) {
        TestUtils.init();
        Log.setLevel(Log.INFO);
        test1();
        Display.getInstance().exitApplication();
    }

    private static String asString(VirtualDrive drive) {
        StringBuffer res = new StringBuffer();
        Vector categories = drive.getCategories();
        Enumeration e = categories.elements();
        while (e.hasMoreElements()) {
            Category cat = (Category) e.nextElement();
            res.append("\n");
            res.append(cat).append("\n");
            Vector tags = cat.directories();
            Enumeration e1 = tags.elements();
            while (e1.hasMoreElements()) {
                VirtualDirectory tag = (VirtualDirectory) e1.nextElement();
                res.append(tag.name() + ":" + tag.getChildren().size()).append("||");
            }
        }
        return res.toString();
    }
}
