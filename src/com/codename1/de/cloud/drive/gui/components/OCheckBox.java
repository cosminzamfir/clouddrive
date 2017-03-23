package com.codename1.de.cloud.drive.gui.components;

import com.codename1.ui.CheckBox;

public class OCheckBox extends CheckBox {

    private Object o;

    public OCheckBox(String name) {
        super(name);
    }

    public Object getO() {
        return o;
    }

    public void setO(Object o) {
        this.o = o;
    }

}
