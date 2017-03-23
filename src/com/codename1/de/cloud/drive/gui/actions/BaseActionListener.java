package com.codename1.de.cloud.drive.gui.actions;

import com.codename1.de.cloud.util.ThreadUtils;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;


/**
 * 
 * @author Cosmin Zamfir
 *
 */
public abstract class BaseActionListener implements ActionListener, Runnable {

    private ActionEvent evt;

    public void actionPerformed(ActionEvent evt) {
        this.evt = evt;
        ThreadUtils.runBackgroundTask(this);
    }

    public void run() {
        execute(evt);
    }

    protected abstract void execute(ActionEvent evt);
}
