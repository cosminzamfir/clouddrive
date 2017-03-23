package com.codename1.de.cloud.drive.gui.factory;

import com.codename1.de.cloud.drive.EnumDriveType;
import com.codename1.de.cloud.drive.gui.actions.navigation.NavigationManager;
import com.codename1.de.cloud.drive.gui.components.FileList;
import com.codename1.de.cloud.drive.gui.components.FileTable;
import com.codename1.de.cloud.drive.gui.components.HorizontalComponentsContainer;
import com.codename1.de.cloud.drive.gui.components.OButton;
import com.codename1.de.cloud.drive.gui.containers.DriveManager;
import com.codename1.de.cloud.drive.gui.containers.StandardDialog;
import com.codename1.de.cloud.drive.storage.Drive;
import com.codename1.de.cloud.util.Colors;
import com.codename1.de.cloud.util.CommonUtils;
import com.codename1.ui.Button;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Font;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.List;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.plaf.DefaultLookAndFeel;
import com.codename1.ui.plaf.LookAndFeel;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.table.DefaultTableModel;
import com.codename1.ui.table.TableLayout;
import com.codename1.ui.table.TableLayout.Constraint;

import java.util.Hashtable;
import java.util.Vector;

/**
 * 
 * @author Cosmin Zamfir
 *
 */
public class ComponentsFactory {

    /**
     * Map {@link EnumDriveType} to drive {@link Image}
     * TODO implement a Hashtable which keeps the insertion order
     */
    public static EnumDriveType[] driveTypes = new EnumDriveType[8];
    public static String[] driveIcons = new String[8];
    public static String[] driveShortNames = new String[8];

    static {
        driveTypes[0] = EnumDriveType.LFS;
        driveTypes[1] = EnumDriveType.VIRTUAL;
        driveTypes[2] = EnumDriveType.DROPBOX;
        driveTypes[3] = EnumDriveType.DROPBOX;
        driveTypes[4] = EnumDriveType.DROPBOX;
        driveTypes[5] = EnumDriveType.DROPBOX;
        driveTypes[6] = EnumDriveType.DROPBOX;
        driveTypes[7] = EnumDriveType.DROPBOX;
        //driveTypes[8] = EnumDriveType.DROPBOX;
        driveIcons[0] = ImageFactory.lfs;
        driveIcons[1] = ImageFactory.virtualDrive;
        driveIcons[2] = ImageFactory.dropbox;
        driveIcons[3] = ImageFactory.box;
        driveIcons[4] = ImageFactory.sugarsync;
        driveIcons[5] = ImageFactory.googledrive;
        driveIcons[6] = ImageFactory.wuala;
        driveIcons[7] = ImageFactory.cloudme;
        //driveIcons[8] = ImageFactory._4shared;
        driveShortNames[0] = "Local";
        driveShortNames[1] = "Me.Everywhere";
        driveShortNames[2] = "Dropbox";
        driveShortNames[3] = "Box";
        driveShortNames[4] = "SugarSync";
        driveShortNames[5] = "GoogleDrive";
        driveShortNames[6] = "Wuala";
        driveShortNames[7] = "CloudMe";
        //driveShortNames[8] = "_4Shared";
    }

    /**
     * Retrieve the {@link Image} logo for this drive/cloud provider
     * @param drive
     * @param size
     * @return
     */
    public static Image getLogo(Drive drive, int size) {
        for (int i = 0; i < driveTypes.length; i++) {
            if (driveTypes[i].equals(drive.type())) {
                return ImageFactory.instance(driveIcons[i], size);
            }
        }
        throw new RuntimeException("Logo missing for " + drive.type());
    }

    public static Label getDefaultLabel(String text, int size) {
        Label label = new Label(text);
        applyDefaultStyle(label, size);
        return label;
    }

    public static List getList() {
        List l = new List();
        applyDefaultStyle(l, Font.SIZE_MEDIUM);
        return l;
    }

    public static FileList getFileList(DriveManager driveManager) {
        FileList l = new FileList(driveManager);
        applyDefaultStyle(l, Font.SIZE_MEDIUM);
        return l;
    }

    public static Button getButton(String name) {
        Button b = new Button(name);
        applyDefaultStyle(b);
        return b;
    }

    /**
     * Create a {@link Button} with the specified background image and scales it, if width and height are specified
     * @param image
     * @param width
     * @param height
     * @return
     */
    public static OButton getImageButton(Image icon) {
        OButton b = new OButton(icon);
        applyDefaultStyle(b);
        b.getStyle().setBorder(Border.createEmpty());
        return b;
    }

    public static Container getContainerTableLayout(int rows, int columns) {
        Container container = new Container();
        container.setLayout(new TableLayout(rows, columns));
        applyDefaultStyle(container, Font.SIZE_MEDIUM);
        return container;
    }

    public static void applyDefaultStyle(Component c, int fontSize) {
        Style s = c.getStyle();
        s.setBgColor(Colors.BLACK);
        s.setFgColor(Colors.WHITE);
        s.setFont(Font.createSystemFont(Font.FACE_PROPORTIONAL, Font.STYLE_PLAIN, fontSize));
        s.setBgTransparency(0);
    }

    public static void applyDefaultStyle(Button c) {
        Style s = c.getStyle();
        s.setBgColor(Colors.BLACK);
        s.setFgColor(Colors.WHITE);
        s.setFont(Font.createSystemFont(Font.FACE_PROPORTIONAL, Font.STYLE_PLAIN, Font.SIZE_LARGE));
        c.setSelectedStyle(c.getUnselectedStyle());
    }

    /**
     * Default component style: transparent, slate_blue background when pressed
     * @param l 
     * @param k 
     * @param j 
     * @param i 
     * @param b 
     * @param font 
     * @param c
     */
    public static void applySelectionStyle(Component cmp, Font font, Border b, int topMargin, int bottomMargin,
            int leftMargin, int rightMargin) {
        cmp.getStyle().setBgTransparency(0);

        cmp.getUnselectedStyle().setBgTransparency(0);
        cmp.getSelectedStyle().setBgTransparency(0);
        cmp.getPressedStyle().setBgTransparency(255);
        cmp.getPressedStyle().setBgColor(Colors.SLATE_BLUE);

        cmp.getStyle().setBorder(b);
        cmp.getSelectedStyle().setBorder(b);
        cmp.getUnselectedStyle().setBorder(b);
        cmp.getPressedStyle().setBorder(b);

        cmp.getStyle().setMargin(topMargin, bottomMargin, leftMargin, rightMargin);
        cmp.getStyle().setMargin(topMargin, bottomMargin, leftMargin, rightMargin);
        cmp.getStyle().setMargin(topMargin, bottomMargin, leftMargin, rightMargin);
        cmp.getStyle().setMargin(topMargin, bottomMargin, leftMargin, rightMargin);

        cmp.getStyle().setFont(font);
        cmp.getUnselectedStyle().setFont(font);
        cmp.getSelectedStyle().setFont(font);
        cmp.getPressedStyle().setFont(font);

    }

    public static Form getForm(String string) {
        Form f = new Form(string);
        f.setLayout(new GridLayout(1, 1));
        applyDefaultStyle(f, Font.SIZE_MEDIUM);
        return f;
    }

    public static Container getDefaultBorderLayoutContainer() {
        Container container = new Container();
        BorderLayout l = new BorderLayout();
        l.setCenterBehavior(BorderLayout.CENTER_BEHAVIOR_CENTER);
        container.setLayout(l);
        container.getStyle().setBgTransparency(0);
        return container;
    }

    public static void setDefaults(Form form) {
        LookAndFeel l = form.getUIManager().getLookAndFeel();
        if (l instanceof DefaultLookAndFeel) {
            ((DefaultLookAndFeel) l).setCheckBoxImages(
                    ImageFactory.instance(ImageFactory.checkboxChecked, ImageFactory.MEDIUM),
                    ImageFactory.instance(ImageFactory.checkboxUnchecked, ImageFactory.MEDIUM));
        }
        Hashtable themeProps = new Hashtable();
        themeProps.put("Dialog.transparency", "0");
        themeProps.put("Dialog.bgColor", "00000");
        themeProps.put("Dialog.margin", "0,0,0,0");
        form.getUIManager().addThemeProps(themeProps);
    }

    /**
     * Build a horizontal Container to hold the given list of buttons
     * @return
     */
    public static HorizontalComponentsContainer getHorizontalButtonsContainer(Vector buttons) {
        int n = buttons.size();
        HorizontalComponentsContainer res = new HorizontalComponentsContainer(buttons);
        return res;
    }

    public static Dialog createStandardDialog(Image icon, String header) {
        return new StandardDialog(icon, header, Colors.SLATE_BLUE);
    }

    public static Constraint leftConstraint() {
        Constraint res = new Constraint();
        res.setHorizontalAlign(Component.LEFT);
        return res;
    }

    public static Constraint rightConstraint() {
        Constraint res = new Constraint();
        res.setHorizontalAlign(Component.RIGHT);
        return res;
    }

    public static void applyDefaultGradient(Style style, boolean inverseGradient) {
        style.setBorder(Border.createEmpty());
        if (inverseGradient) {
            style.setBackgroundGradientStartColor(Colors.GREY_GRADIENT_START);
            style.setBackgroundGradientEndColor(Colors.GREY_GRADIENT_END);
        } else {
            style.setBackgroundGradientStartColor(Colors.GREY_GRADIENT_END);
            style.setBackgroundGradientEndColor(Colors.GREY_GRADIENT_START);
        }
        style.setBackgroundType(Style.BACKGROUND_GRADIENT_LINEAR_VERTICAL);
        style.setBgTransparency(255);
        style.setAlignment(Component.CENTER);
        style.setMargin(0, 0, 0, 0);
    }

    public static Border roundBorder() {
        return Border.createRoundBorder(20, 20);
    }

    public static FileTable getFileTable(Vector items, DriveManager driveManager) {

        int columns = NavigationManager.getRoot().getPreferredW() / FileTable.CELL_WIDTH;
        //FIXME - more type safety here; data is actually StorageItem[][]
        Object[][] data = (Object[][]) CommonUtils.matrix(items, columns);
        String[] columnNames = new String[columns];
        for (int i = 0; i < columns; i++) {
            columnNames[i] = "";
        }
        DefaultTableModel model = new DefaultTableModel(columnNames, data);
        return new FileTable(items, model, columns, driveManager);
    }

}
