package com.codename1.de.cloud.drive.gui.containers;

import com.codename1.de.cloud.drive.gui.actions.BaseActionListener;
import com.codename1.de.cloud.drive.gui.factory.ComponentsFactory;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Font;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.table.TableLayout;
import com.codename1.ui.table.TableLayout.Constraint;

/**
 * A {@link StandardDialog} extension which display a key-value collection; e.g.
 * a file properties dialog
 * 
 * @author zamfcos
 * 
 */
public class MappingInfoDialog extends StandardDialog {

	public MappingInfoDialog(Image icon, String headerText, String[] keys, String[] values) {
		super(icon, headerText, com.codename1.de.cloud.util.Colors.SLATE_BLUE);
		addContent(keys, values);
		addExitButton();
	}

	private void addExitButton() {
		Button b = new Button("OK");
		b.addActionListener(new BaseActionListener() {

			protected void execute(ActionEvent evt) {
				dispose();
			}
		});
		getContentPane().addComponent(BorderLayout.SOUTH, b);
	}

	private void addContent(String[] keys, String[] values) {
		Container c = new Container();
		c.setLayout(new TableLayout(keys.length, 2));
		for (int i = 0; i < keys.length; i++) {
			Label label1 = new Label(keys[i].toString());
			label1.getStyle().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_MEDIUM));
			label1.getStyle().setBgTransparency(0);
			Constraint right = new Constraint();
			right.setHorizontalAlign(RIGHT);
			c.addComponent(right, label1);

			Label label2 = new Label(values[i].toString());
			label2.getStyle().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_MEDIUM));
			label2.getStyle().setBgTransparency(0);
			Constraint left = new Constraint();
			left.setHorizontalAlign(LEFT);
			c.addComponent(left, label2);
		}
		getContentPane().addComponent(BorderLayout.CENTER, c);

	}
}
