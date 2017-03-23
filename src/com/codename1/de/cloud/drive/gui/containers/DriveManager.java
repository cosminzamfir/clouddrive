package com.codename1.de.cloud.drive.gui.containers;

import com.codename1.de.cloud.drive.EnumDriveType;
import com.codename1.de.cloud.drive.gui.actions.BaseActionListener;
import com.codename1.de.cloud.drive.gui.actions.TreeUpActionListener;
import com.codename1.de.cloud.drive.gui.actions.navigation.NavigationManager;
import com.codename1.de.cloud.drive.gui.components.FileList;
import com.codename1.de.cloud.drive.gui.components.HorizontalComponentsContainer;
import com.codename1.de.cloud.drive.gui.components.OButton;
import com.codename1.de.cloud.drive.gui.components.SearchVirtualDrive;
import com.codename1.de.cloud.drive.gui.factory.ComponentsFactory;
import com.codename1.de.cloud.drive.gui.factory.ImageFactory;
import com.codename1.de.cloud.drive.gui.model.DefaultListModel;
import com.codename1.de.cloud.drive.gui.renderers.FileListCellRenderer;
import com.codename1.de.cloud.drive.storage.Directory;
import com.codename1.de.cloud.drive.storage.Drive;
import com.codename1.de.cloud.drive.virtual.v1.View;
import com.codename1.de.cloud.drive.virtual.v1.VirtualDirectory;
import com.codename1.de.cloud.drive.virtual.v1.VirtualDrive;
import com.codename1.de.cloud.util.Colors;
import com.codename1.de.cloud.util.CommonUtils;
import com.codename1.de.cloud.util.Observable;
import com.codename1.de.cloud.util.Observer;
import com.codename1.de.cloud.util.ThreadUtils;
import com.codename1.io.Log;
import com.codename1.ui.Button;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.Font;
import com.codename1.ui.Form;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.plaf.Border;

import java.util.Enumeration;
import java.util.Vector;

/**
 * A basic file manager
 * 
 * @author Cosmin Zamfir
 * 
 */
public class DriveManager extends NamedContainer implements Observer {

    /**
     * The selected {@link Drive}
     */
    private Drive drive;
    public static final String NAME = "driveManager";

    /**
     * The current directory being displayed
     */
    private Directory currentDirectory;

    /**
     * The files/directories in the {@link #currentDirectory}
     */
    private FileList fileList;
    /**
     * Up one level
     */
    private Button back;

    /**
     * Switch between list and icons view for the currentDirectory content
     */
    private Button viewType;

    /**
     * Search drive content
     */
    private Button search;

    /**
     * Open the view builder dialog. A selection of categories/filters
     */
    private Button virtualViews;

    private boolean isListViewType = true;
    /**
     * Display the {@link #currentDirectory} content
     */
    private Container contentPanel;
    /**
     * Contains the {@link #back}, {@link #backToDrives} and {@link #home}
     * buttons
     */
    private HorizontalComponentsContainer navigationPanel;
    /**
     * Contains the {@link #currentDirectoryLabel}
     */
    private Container labelPanel;

    /**
     * Last view update from the {@link VirtualDrive}; see {@link Observable}
     */
    private long lastUpdate = System.currentTimeMillis();
    /**
     * Contains the available {@link View}s if the {@link #drive} is the
     * {@link VirtualDrive}
     */
    private Container viewsPanel = new Container(new BoxLayout(BoxLayout.X_AXIS));

    Container labelAndContent;
    private Container toolbar;

    private static int BUTTON_SIZE = CommonUtils.min(Display.getInstance().getDisplayHeight(), Display.getInstance()
            .getDisplayWidth()) / 10;

    DriveManager(Drive drive) {
        super();
        this.drive = drive;
        if (drive.type().equals(EnumDriveType.VIRTUAL)) {
            ((VirtualDrive) drive).addObserver(this);
        }
        build();
    }

    public void build() {
        setPreferredSize(NavigationManager.getRoot().getPreferredSize());
        setLayout(new BorderLayout());
        buildFileList();
        buildToolbar();
        buildLabelPanel();
        buildContentPanel();

        labelAndContent = new Container(new BorderLayout());
        labelAndContent.addComponent(BorderLayout.NORTH, labelPanel);
        labelAndContent.addComponent(BorderLayout.CENTER, contentPanel);
        this.addComponent(BorderLayout.NORTH, toolbar);
        this.addComponent(BorderLayout.CENTER, labelAndContent);
        // buildViewsPanel();
        // if (this.drive.type().equals(EnumDriveType.VIRTUAL)) {
        // this.addComponent(BorderLayout.SOUTH, viewsPanel);
        // }
        setCurrentDirectory(null);
        this.revalidate();
    }

    public void revalidate() {
        fileList.setShouldCalcPreferredSize(true);
        super.revalidate();
    }

    /**
     * 
     * @return <code>true</code> if the current drive is the
     *         {@link VirtualDrive} instance
     */
    private boolean isVirtual() {
        return drive.type() == EnumDriveType.VIRTUAL;
    }

    private void buildToolbar() {
        virtualViews = ComponentsFactory.getImageButton(ImageFactory.instance(ImageFactory.virtualViews, BUTTON_SIZE));
        virtualViews.getStyle().setBgTransparency(0);
        virtualViews.addActionListener(new BaseActionListener() {

            protected void execute(ActionEvent evt) {
                ViewBuilder viewBuilder = new ViewBuilder(DriveManager.this);
                viewBuilder.show();
                //ViewBuilder2 viewBuilder = new ViewBuilder2(DriveManager.this);
                //viewBuilder.show();
            }
        });

        back = ComponentsFactory.getImageButton(ImageFactory.instance(ImageFactory.back, BUTTON_SIZE));
        back.addActionListener(new TreeUpActionListener(this));
        back.getStyle().setBgTransparency(0);

        viewType = ComponentsFactory.getImageButton(ImageFactory.instance(ImageFactory.iconView, BUTTON_SIZE));
        viewType.getStyle().setBgTransparency(0);
        viewType.addActionListener(new BaseActionListener() {

            protected void execute(ActionEvent evt) {
                if (isListViewType) {
                    contentPanel.removeComponent(fileList);
                    contentPanel.addComponent(BorderLayout.CENTER, buildIconView());
                    isListViewType = false;
                    Log.p("Setting icon to listView", Log.INFO);
                    viewType.setIcon(ImageFactory.instance(ImageFactory.listView, BUTTON_SIZE));
                    viewType.repaint();
                } else {
                    contentPanel.removeAll();
                    contentPanel.addComponent(BorderLayout.CENTER, fileList);
                    isListViewType = true;
                    Log.p("Setting icon to iconView", Log.INFO);
                    viewType.setIcon(ImageFactory.instance(ImageFactory.iconView, BUTTON_SIZE));
                    viewType.repaint();
                }
                revalidate();
            }
        });

        TextField searchText = new TextField();

        search = ComponentsFactory.getImageButton(ImageFactory.instance(ImageFactory.search, BUTTON_SIZE));
        search.getStyle().setBgTransparency(0);
        search.addActionListener(new BaseActionListener() {

            protected void execute(ActionEvent evt) {
                if (isVirtual()) {
                    SearchVirtualDrive d = new SearchVirtualDrive((VirtualDrive) drive, DriveManager.this);
                    d.show();
                }
            }
        });

        Vector navigationButtons = new Vector();
        navigationButtons.addElement(search);
        navigationButtons.addElement(viewType);
        if (isVirtual()) {
            navigationButtons.addElement(virtualViews);
        }
        navigationPanel = ComponentsFactory.getHorizontalButtonsContainer(navigationButtons);
        toolbar = new Container(new GridLayout(1, 2));
        toolbar.addComponent(searchText);
        toolbar.addComponent(navigationPanel);

        searchText.getStyle().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_SMALL));
        int textHeight = searchText.getStyle().getFont().getHeight();
        int toolbaarHeight = toolbar.getPreferredH();
        int margin = (toolbaarHeight - textHeight * 3 / 2) / 2;
        searchText.getStyle().setMargin(Component.TOP, margin);
        searchText.getStyle().setMargin(Component.BOTTOM, margin);
        searchText.setSelectedStyle(searchText.getStyle());
    }

    private void buildLabelPanel() {
        labelPanel = new Container();
    }

    private void buildContentPanel() {
        contentPanel = new Container(new BorderLayout());
        contentPanel.setScrollableY(true);
        contentPanel.setScrollableX(false);
        contentPanel.addComponent(BorderLayout.CENTER, fileList);
        contentPanel.setTensileDragEnabled(false);
    }

    private Container buildIconView() {
        //Vector items = currentDirectory == null ? drive.list() : currentDirectory.children();
        Vector items;
        if (currentDirectory == null) {
            items = drive.list();
        } else {
            items = currentDirectory.children();
        }
        if (!items.isEmpty()) {
            return ComponentsFactory.getFileTable(items, this);
        } else {
            return new Container();
        }

    }

    private void buildFileList() {
        fileList = ComponentsFactory.getFileList(this);
        fileList.setVisible(true);
        fileList.setScrollVisible(true);
        fileList.setRenderer(new FileListCellRenderer());
    }

    /**
     * Set the current {@link Directory}, reload directory content and repaint
     * 
     * @param currentDirectory
     *            new {@link Directory}, if <code>null</code> go to root
     */
    public void setCurrentDirectory(Directory currentDirectory) {
        Directory initial = currentDirectory; // revert if error
        try {
            this.currentDirectory = currentDirectory;
            if (isVirtual()) {
                ((VirtualDrive) drive).setCurrentDirectory((VirtualDirectory) currentDirectory);
            }
            if (currentDirectory == null) {
                if (isListViewType) {
                    ((DefaultListModel) fileList.getModel()).setItems(drive.list());
                } else {
                    contentPanel.removeAll();
                    contentPanel.addComponent(BorderLayout.CENTER, buildIconView());
                }

            } else {
                if (isListViewType) {
                    ((DefaultListModel) fileList.getModel()).setItems(currentDirectory.children());
                } else {
                    contentPanel.removeAll();
                    contentPanel.addComponent(BorderLayout.CENTER, buildIconView());
                }
            }
            labelAndContent.removeComponent(labelPanel);
            this.labelPanel = buildCurrentDirectoryPanel();
            labelAndContent.addComponent(BorderLayout.NORTH, labelPanel);

        } catch (RuntimeException e) {
            this.currentDirectory = initial;
            throw e;
        }
        if (isListViewType) {
            fileList.repaint();
        }
        fileList.repaint();
        revalidate();
    }

    public void refresh() {
        if (currentDirectory == null) {
            ((DefaultListModel) fileList.getModel()).setItems(drive.list());
        } else {
            ((DefaultListModel) fileList.getModel()).setItems(currentDirectory.children());
        }
    }

    /**
     * The current directory panel is a list of buttons which can be used to
     * navigate to any directory in the path
     * 
     * @return
     */
    private Container buildCurrentDirectoryPanel() {
        String rootPath = "/";
        Vector buttons = new Vector();
        OButton b = new OButton("Home");
        b.setO("/");
        buttons.addElement(b);
        // e.g. /a/b results in 3 buttons: "/", "a", "b". Each buttons has as
        // label the directory name
        // and value the directory absolute path

        if (currentDirectory != null) {
            String path = currentDirectory.absolutePath();
            String previousPath;
            if (path.startsWith("/")) { // unix like
                previousPath = "/";
            } else { // windows like
                previousPath = "";
            }
            Vector pathItems = CommonUtils.split(path, '/');
            Enumeration e = pathItems.elements();
            while (e.hasMoreElements()) {
                String dirName = (String) e.nextElement();
                String dirFullPath = previousPath + dirName;
                previousPath = dirFullPath + "/";
                b = new OButton(dirName);
                b.setO(dirFullPath);
                buttons.addElement(b);
            }
        }
        Enumeration e = buttons.elements();
        while (e.hasMoreElements()) {
            b = (OButton) e.nextElement();
            b.getStyle().setBorder(Border.createRoundBorder(5, 5));
            b.getStyle().setBgColor(Colors.BLUE_GRADIENT_START);
            b.addActionListener(new BaseActionListener() {

                /**
                 * Navigate up in the dir hierarchy until the current dir path equals the selected button path
                 */
                protected void execute(ActionEvent evt) {
                    String path = (String) ((OButton) evt.getComponent()).getO();
                    if (path.equals("/")) {
                        setCurrentDirectory(null);
                        return;
                    }
                    if (currentDirectory.absolutePath().equals(path)) {
                        return;
                    }
                    Directory parent = null;
                    while (!(parent = currentDirectory.parent()).absolutePath().equals(path)) {
                        Log.p("not equal: " + parent.absolutePath() + " AND " + path, Log.INFO);
                        currentDirectory = parent;
                    }
                    setCurrentDirectory(parent);
                }
            });
        }
        Container c = new Container();
        Enumeration en = buttons.elements();
        while (en.hasMoreElements()) {
            OButton bt = (OButton) en.nextElement();
            c.addComponent(bt);
        }
        return c;
        //return new HorizontalComponentsContainer(buttons);

    }

    /**
     * (Re)load the roots for the specified {@link Drive}
     */
    public void setDrive(Drive drive) {
        if (drive == this.drive) {
            return;
        }
        Drive initial = this.drive; // revert if error
        try {
            Log.p("Setting current drive: " + drive);
            this.drive = drive;
            setCurrentDirectory(null);
            if (isVirtual()) {
                ((VirtualDrive) drive).resetAllFilters();
                ((VirtualDrive) drive).addObserver(this);
                if (!navigationPanel.contains(virtualViews)) {
                    navigationPanel.add(virtualViews);
                }
                revalidate();
            } else {
                if (navigationPanel.contains(virtualViews)) {
                    navigationPanel.delete(virtualViews);
                }
                revalidate();
            }

        } catch (RuntimeException e) {
            this.drive = initial;
            throw e;
        }
    }

    public void up() {
        if (currentDirectory == null) {
            selectDrive();
            return;
        }
        Directory parent = currentDirectory.parent();
        Log.p("Up button clicked - currentDirectory: " + currentDirectory + " ;parent directory " + parent, Log.DEBUG);
        setCurrentDirectory(parent);
    }

    private void selectDrive() {
        Form root = NavigationManager.getRoot();
        root.removeComponent(this);
        root.addComponent(BorderLayout.CENTER, DriveSelector.instance());
        root.revalidate();
    }

    /**
     * Update display with various notifications received Currently, only
     * {@link VirtualDrive} notifications are implemented
     */
    public void update(Observable o, Object arg) {
        if (System.currentTimeMillis() - lastUpdate < 1000) {
            return;
        }
        if (o instanceof VirtualDrive) {
            virtualDriveUpdated((VirtualDrive) o);
            lastUpdate = System.currentTimeMillis();
        }
    }

    private void virtualDriveUpdated(VirtualDrive virtualDrive) {
        if (drive != virtualDrive) {
            Log.p("Ignoring VirtualDrive notification. Is is not the current displayed drive", Log.DEBUG);
            return;
        }
        // TODO - need to be more fine grained here -
        // change display only if the notification refers to the same
        // category/tag which is being displayed
        Log.p("VirtualDrive notification received. Refreshing display", Log.DEBUG);
        if (isInitialized()) {
            ThreadUtils.runBackgroundTask(new Runnable() {
                public void run() {
                    setCurrentDirectory(currentDirectory);
                }
            });
        }
    }

    public void repaint() {
        setPreferredSize(NavigationManager.getRoot().getPreferredSize());
        super.repaint();
    }

    public void setItems(Vector items) {
        ((DefaultListModel) fileList.getModel()).setItems(items);
        revalidate();
    }

    public String getContainerName() {
        return NAME;
    }
}
