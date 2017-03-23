package com.codename1.de.cloud.drive.gui.components;

import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Image;

public class OButton extends Button {

    private Object o;

    public OButton() {
    }

    public OButton(String text) {
        super(text);
    }

    public OButton(String text, Object o) {
        super(text);
        this.o = o;
    }

    public OButton(Command cmd) {
        super(cmd);
    }

    public OButton(Image icon) {
        super(icon);

    }

    public OButton(String text, Image icon) {
        super(text, icon);

    }

    public Object getO() {
        return o;
    }

    public void setO(Object o) {
        this.o = o;
    }
}
