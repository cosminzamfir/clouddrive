package com.codename1.de.cloud.drive.gui.components;

import com.codename1.de.cloud.util.Colors;
import com.codename1.ui.Button;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.plaf.Style;

import java.util.Vector;


public class HorizontalComponentsContainer extends Container {

	private Vector components = new Vector();
	private int rows;
	private int columns;
	/**
	 * Rows/columns no are recalculated if components added/removed only if
	 * defaults are used
	 */
	private boolean recalculateLayout;

	/**
	 * Default layout of 1 row/n columns
	 * 
	 * @param components
	 */
	public HorizontalComponentsContainer(Vector components) {
		super();
		this.components = components;
		rows = 1;
		columns = components.size();
		recalculateLayout = true;
		layout();
	}


	/**
	 * Layout with specified rows/columns
	 * 
	 * @param components
	 * @param rows
	 * @param columns
	 */
	public HorizontalComponentsContainer(Vector components, int rows, int columns) {
		super();
		this.components = components;
		this.rows = rows;
		this.columns = columns;
		recalculateLayout = false;
		layout();
	}

	private void layout() {
		setLayout(new GridLayout(rows, columns));
		int n = components.size();
		for (int i = 0; i < n; i++) {
			Component b = (Component) components.elementAt(i);
			applyDefaults(b.getStyle(), (i % 2 == 0 && rows > 1));
			applyDefaultsWhenPressed(b.getPressedStyle());
            b.getPressedStyle().setBgColor(Colors.BLACK);
			b.setUnselectedStyle(b.getStyle());
			b.setSelectedStyle(b.getStyle());
			addComponent(b);
			if (rows == 1 && i < n - 1) {
				Border empty = Border.createEmpty();
				Border upper = Border.createBevelRaised();
				upper.setThickness(1);
				Border border = Border.createCompoundBorder(empty, empty, empty, upper);
				b.getStyle().setBorder(border);
			}
		}

	}

	public void delete(Component b) {
		components.removeElement(b);
		removeComponent(b);
		if (recalculateLayout) {
			setLayout(new GridLayout(1, components.size()));
		}
	}

	public void add(Component b) {
		components.addElement(b);
		if (recalculateLayout) {
			setLayout(new GridLayout(1, components.size()));
		}
		applyDefaults(b.getStyle(), false);
		applyDefaults(b.getSelectedStyle(), false);
		applyDefaults(b.getPressedStyle(), false);
		addComponent(b);
		// create border for the second to last component
		Border empty = Border.createEmpty();
		Border upper = Border.createBevelRaised();
		upper.setThickness(1);
		Border border = Border.createCompoundBorder(empty, empty, empty, upper);
		if (rows == 1) {
			((Button) components.elementAt(components.size() - 2)).getStyle().setBorder(border);
		}
	}

	private void applyDefaults(Style style, boolean inverseGradient) {
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
		style.setAlignment(CENTER);
		style.setMargin(0, 0, 0, 0);
	}

	private void applyDefaultsWhenPressed(Style style) {
		style.setBorder(Border.createEmpty());
		style.setBackgroundGradientStartColor(com.codename1.de.cloud.util.Colors.BLUE_GRADIENT_START);
		style.setBackgroundGradientEndColor(com.codename1.de.cloud.util.Colors.BLUE_GRADIENT_END);
		style.setBackgroundType(Style.BACKGROUND_GRADIENT_LINEAR_VERTICAL);
		style.setBgTransparency(255);
		style.setAlignment(CENTER);
		style.setMargin(0, 0, 0, 0);
	}

}
