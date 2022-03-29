package main.zadani_7;

import javafx.beans.property.*;

public class Animal {

    private DoubleProperty weight = new SimpleDoubleProperty();

    private StringProperty species = new SimpleStringProperty();

    private StringProperty sound = new SimpleStringProperty();

    private IntegerProperty legsCount = new SimpleIntegerProperty();

    public Animal (){

    }

    public Animal(double weight, String species, String sound, int legsCount) {
        this();

        setWeight(weight);
        setSpecies(species);
        setSound(sound);
        setLegsCount(legsCount);
    }

    public double getWeight() {
        return weight.get();
    }

    public DoubleProperty weightProperty() {
        return weight;
    }

    public void setWeight(double weight) {
        if (weight>0) {
            this.weight.set(weight);
        }else{
            throw new IllegalArgumentException("Weight must be positive number");
        }
    }

    public String getSound() {
        return sound.get();
    }

    public StringProperty soundProperty() {
        return sound;
    }

    public void setSound(String sound) {
        this.sound.set(sound);
    }

    public int getLegsCount() {
        return legsCount.get();
    }

    public IntegerProperty legsCountProperty() {
        return legsCount;
    }

    public String getSpecies() {
        System.out.println("Getting species of " + species.get());
        return species.get();
    }

    public StringProperty speciesProperty() {
        System.out.println("Getting property species of " + species.get());
        return species;
    }

    public void setSpecies(String species) {
        this.species.set(species);
    }

    public void setLegsCount(int legsCount) {
        this.legsCount.set(legsCount);
    }
}
