package main.exam.data;


import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * Class represents spaceship component
 *
 * @author Volodymyr Pavlov
 * @version 23.05.2022
 */
public class Component {

    /**
     * Component type
     */
    private final ComponentType type;

    /**
     * Component name
     */
    private final String name;

    /**
     * Component producer
     */
    private final Producer producer;

    /**
     * Component weight property [kg]
     */
    private final IntegerProperty weight = new SimpleIntegerProperty();

    /**
     * Component power consumption (or production in case with REACTOR) [MW]
     */
    private final IntegerProperty power = new SimpleIntegerProperty();

    /**
     * Component constructor
     *
     * @param type     -component type
     * @param name     -component name
     * @param producer -component producer
     * @param weight   -component weight [kg]
     * @param power    -power consumption (or production) [MW]
     */
    public Component(ComponentType type, String name, Producer producer, int weight, int power) {
        this.type = type;
        this.name = name;
        this.producer = producer;
        this.weight.setValue(weight);
        this.power.setValue(power);
    }

    /**
     * Component type getter
     *
     * @return component type
     */
    public ComponentType getType() {
        return type;
    }

    /**
     * Name getter
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Producer getter
     *
     * @return producer
     */
    public Producer getProducer() {
        return producer;
    }

    /**
     * Weight getter
     *
     * @return weight [kg]
     */
    public int getWeight() {
        return weight.get();
    }

    /**
     * Weight property getter
     *
     * @return weight property
     */
    public IntegerProperty weightProperty() {
        return weight;
    }

    /**
     * Weight setter
     *
     * @param weight -new weight [kg]
     */
    public void setWeight(int weight) {
        this.weight.set(weight);
    }

    /**
     * Power consumption (production) getter
     *
     * @return power consumption (production) [MW]
     */
    public int getPower() {
        return power.get();
    }

    /**
     * Power consumption (production) property getter
     *
     * @return power consumption (production) property
     */
    public IntegerProperty powerProperty() {
        return power;
    }

    /**
     * Power consumption (production) setter
     *
     * @param power -power consumption (production) [MW]
     */
    public void setPower(int power) {
        this.power.set(power);
    }

    /**
     * Name and producer getter
     *
     * @return string with name and producer
     */
    public String getProducerAndName() {
        return type + " - [" + producer + "] " + name;
    }

    @Override
    public String toString() {
        return type + " - [" + producer + "] " + name + " {" + weight.get() + " kg , " + power.get() + " MW}";
    }
}
