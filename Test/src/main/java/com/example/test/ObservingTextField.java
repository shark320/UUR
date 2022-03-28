package com.example.test;


import javafx.scene.control.TextField;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class ObservingTextField extends TextField implements PropertyChangeListener {
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        setText(String.valueOf(evt.getNewValue()));
    }
}
