package com.example.test;

import javafx.scene.shape.Rectangle;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class ObservingRectangle extends Rectangle implements PropertyChangeListener {
    @Override
    public void propertyChange(PropertyChangeEvent evt) {

    }
}
