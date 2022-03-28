package com.example.test;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.IntegerPropertyBase;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Alert;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class DataModel {
    private IntegerProperty redComponent = new SimpleIntegerProperty();
    private IntegerProperty greenComponent = new SimpleIntegerProperty();
    private IntegerProperty blueComponent = new SimpleIntegerProperty();

    private int min = 0;
    private int max = 255;

    private final PropertyChangeSupport listenerManager = new PropertyChangeSupport(this);

    public DataModel(int red, int green, int blue) {
        if (red > max || red < min || green > max || green < min || blue > max || blue < min) {
            throw new IllegalArgumentException("Initial data has to be between 0 and 255!");
        }
        redComponent.set(red);
        greenComponent.set(green);
        blueComponent.set(blue);

        redComponent.addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                listenerManager.firePropertyChange("redComponent", oldValue, newValue);
            }
        });
        greenComponent.addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {

                listenerManager.firePropertyChange("greenComponent", oldValue, newValue);
            }
        });
        blueComponent.addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                listenerManager.firePropertyChange("blueComponent", oldValue, newValue);
            }
        });
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        listenerManager.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        listenerManager.removePropertyChangeListener(listener);
    }

    public IntegerProperty redIntegerDataProperty() {
        return redComponent;
    }

    public IntegerProperty greenIntegerDataProperty() {
        return greenComponent;
    }

    public IntegerProperty blueIntegerDataProperty() {
        return blueComponent;
    }
}
