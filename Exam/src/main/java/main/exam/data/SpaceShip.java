package main.exam.data;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.paint.Color;

/**
 * Class represents spaceship with separated components
 *
 * @author Volodymyr Pavlov
 * @version 23.05.2022
 */
public class SpaceShip {

    //////////////////////////////////////////////////////////////////////////////
    //                                                                          //
    //                            Static constants                              //
    //                                                                          //
    //////////////////////////////////////////////////////////////////////////////

    /**
     * Default ship main color
     */
    private static final Color DEF_MAIN_COLOR = Color.YELLOW;

    /**
     * Default ship additional color
     */
    private static final Color DEF_ADDITION_COLOR = Color.BLACK;

    //////////////////////////////////////////////////////////////////////////////
    //                                                                          //
    //                           Instance variables                             //
    //                                                                          //
    //////////////////////////////////////////////////////////////////////////////

    /**
     * Ship name
     */
    private final StringProperty name = new SimpleStringProperty();

    /**
     * Ship id
     */
    private final IntegerProperty id = new SimpleIntegerProperty();

    /**
     * main color property
     */
    private final ObjectProperty<Color> mainColor = new SimpleObjectProperty<>();

    /**
     * additional color property
     */
    private final ObjectProperty<Color> additionalColor = new SimpleObjectProperty<>();

    /**
     * total weight property [kg]
     */
    private final IntegerProperty totalWeight = new SimpleIntegerProperty(0);

    /**
     * total power consumption property [MW]
     */
    private final IntegerProperty totalPowerConsumption = new SimpleIntegerProperty(0);

    /**
     * total power production property [MW]
     */
    private final IntegerProperty totalPower = new SimpleIntegerProperty(0);

    /**
     * Added components
     */
    private final ListProperty<Component> components = new SimpleListProperty<>(FXCollections.observableArrayList());

    /**
     * Default constructor, init name, id and colors
     */
    public SpaceShip() {
        name.setValue("DEFAULT NAME");
        id.setValue(1);
        mainColor.setValue(DEF_MAIN_COLOR);
        additionalColor.setValue(DEF_ADDITION_COLOR);
    }

    //////////////////////////////////////////////////////////////////////////////
    //                                                                          //
    //                           Getters and Setters                            //
    //                                                                          //
    //////////////////////////////////////////////////////////////////////////////

    /**
     * Name getter
     *
     * @return ship name
     */
    public String getName() {
        return name.get();
    }

    /**
     * Name property getter
     *
     * @return name property
     */
    public StringProperty nameProperty() {
        return name;
    }

    /**
     * Name setter
     *
     * @param name - new ship name
     */
    public void setName(String name) {
        this.name.set(name);
    }

    /**
     * ID getter
     *
     * @return ship ID
     */
    public int getId() {
        return id.get();
    }

    /**
     * ID property getter
     *
     * @return ship ID property
     */
    public IntegerProperty idProperty() {
        return id;
    }

    /**
     * ID setter
     *
     * @param id new ship ID
     */
    public void setId(int id) {
        this.id.set(id);
    }

    /**
     * Main color getter
     *
     * @return main color
     */
    public Color getMainColor() {
        return mainColor.get();
    }

    /**
     * Main color property getter
     *
     * @return main color property
     */
    public ObjectProperty<Color> mainColorProperty() {
        return mainColor;
    }

    /**
     * Main color setter
     *
     * @param mainColor new main color
     */
    public void setMainColor(Color mainColor) {
        this.mainColor.set(mainColor);
    }

    /**
     * Additional color getter
     *
     * @return additional color
     */
    public Color getAdditionalColor() {
        return additionalColor.get();
    }

    /**
     * Additional color property getter
     *
     * @return additional property getter
     */
    public ObjectProperty<Color> additionalColorProperty() {
        return additionalColor;
    }

    /**
     * additional color setter
     *
     * @param additionalColor -new additional color
     */
    public void setAdditionalColor(Color additionalColor) {
        this.additionalColor.set(additionalColor);
    }

    /**
     * Components List property getter
     *
     * @return ListProperty with components
     */
    public ListProperty<Component> componentsProperty() {
        return components;
    }

    /**
     * total weight property getter
     *
     * @return total weight property
     */
    public IntegerProperty totalWeightProperty() {
        return totalWeight;
    }

    /**
     * total power consumption property getter
     *
     * @return total power consumption property
     */
    public IntegerProperty totalPowerConsumptionProperty() {
        return totalPowerConsumption;
    }

    /**
     * total power production property getter
     *
     * @return total power production property
     */
    public IntegerProperty totalPowerProperty() {
        return totalPower;
    }

    /**
     * total weight getter
     *
     * @return total weight [kg]
     */
    public int getTotalWeight() {
        int total = 0;
        for (Component c : components) {
            total += c.getWeight();
        }

        return total;
    }

    /**
     * total power consumption getter
     *
     * @return total power consumption [MW]
     */
    public int getTotalConsumption() {
        int totalConsumption = 0;
        for (Component c : components) {
            if (c.getType() != ComponentType.REACTOR) {
                totalConsumption += c.getPower();
            }
        }
        return totalConsumption;
    }

    /**
     * total power production getter
     *
     * @return total power production [MW]
     */
    public int getTotalPower() {
        Component reactor = getComponent(ComponentType.REACTOR);
        if (reactor != null) {
            return reactor.getPower();
        }
        return 0;
    }

    /**
     * check if there is power shortage
     *
     * @return true if there is, else false
     */
    public boolean isPowerShortage() {

        return getTotalPower() < getTotalConsumption();

    }

    /**
     * Get component by type
     *
     * @param type -component type
     * @return component with specified type or null if there is no component with this type
     */
    public Component getComponent(ComponentType type) {
        for (Component c : components) {
            if (c.getType() == type) return c;
        }
        return null;
    }

    /**
     * Check if component with this type is present
     *
     * @param component -component to check
     * @return true if is present, else false
     */
    public boolean isPresentComponent(Component component) {
        for (Component c : components) {
            if (c.getType() == component.getType()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Adding new component, if there is already component with this type, replace it with new one,
     * recalculate weight and power
     *
     * @param newComponent -components to add
     */
    public void addComponent(Component newComponent) {
        components.removeIf(c -> c.getType() == newComponent.getType());
        components.add(newComponent);
        totalWeight.setValue(getTotalWeight());
        totalPowerConsumption.setValue(getTotalConsumption());
        totalPower.setValue(getTotalPower());
    }

    /**
     * Delete specified component form the ship, recalculate weight and power
     *
     * @param component -component to delete
     */
    public void deleteComponent(Component component) {
        components.remove(component);
        totalWeight.setValue(getTotalWeight());
        totalPowerConsumption.setValue(getTotalConsumption());
        totalPower.setValue(getTotalPower());
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Component c : components) {
            sb.append(c).append('\n');
        }

        return sb.toString();
    }


}
