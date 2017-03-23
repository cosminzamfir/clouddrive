package com.codename1.de.cloud.drive.gui;

import com.codename1.de.cloud.drive.gui.actions.navigation.NavigationManager;
import com.codename1.de.cloud.drive.gui.containers.DriveSelector;
import com.codename1.de.cloud.util.Colors;
import com.codename1.io.Log;
import com.codename1.ui.Command;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.layouts.BorderLayout;


/**
 * 
 * @author Cosmin Zamfir
 * 
 */
public class CloudView {

	public void init() {
		// Form form = ComponentsFactory.getForm("Me.Everywere");
		// Command exitCommand = new Command("Exit") {
		// public void actionPerformed(ActionEvent ev) {
		// Display.getInstance().exitApplication();
		// }
		// };
		// //form.addCommand(exitCommand);
		// form.setBackCommand(exitCommand);
		// form.setScrollable(false);
		// NavigationManager.setRoot(form);
		//
		// DriveSelector selector = DriveSelector.instance();
		// form.setVisible(true);
		// form.addComponent(selector);
		// form.show();
		Log.setLevel(Log.DEBUG);
		Form f = new Form();
		f.getContentPane().setLayout(new BorderLayout());
		Command exitCommand = new Command("Exit") {
			public void actionPerformed(ActionEvent ev) {
				Display.getInstance().exitApplication();
			}
		};
		f.setBackCommand(exitCommand);
		f.setScrollable(false);
        f.getStyle().setBgColor(Colors.BACKGROUND);
		NavigationManager.setRoot(f);
		DriveSelector selector = DriveSelector.instance();
		f.addComponent(BorderLayout.CENTER, selector);
		f.show();

	}
}
