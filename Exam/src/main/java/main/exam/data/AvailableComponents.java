package main.exam.data;

import java.util.Arrays;
import java.util.List;

/**
 * Abstract class represents all available components
 *
 * @author Volodymyr Pavlov
 * @version 23.05.2022
 */
public abstract class AvailableComponents {
    /**
     * List with available components
     */
    private static final List<Component> components = Arrays.asList(
            new Component(ComponentType.ENGINE, "Mark 1", Producer.Bebra, 1_500, 2),
            new Component(ComponentType.ENGINE, "Python", Producer.Anaconda, 1_000, 1),
            new Component(ComponentType.ENGINE, "Skoda", Producer.ZCU, 4_000, 10),
            new Component(ComponentType.ENGINE, "Death Engine", Producer.DarthVader, 3_000, 5),

            new Component(ComponentType.REACTOR, "Uranium 10", Producer.Bebra, 4_000, 50),
            new Component(ComponentType.REACTOR, "Naja", Producer.Anaconda, 3_000, 30),
            new Component(ComponentType.REACTOR, "Skoda nuclear", Producer.ZCU, 10_000, 100),
            new Component(ComponentType.REACTOR, "Princess Lea", Producer.DarthVader, 5_000, 60),

            new Component(ComponentType.SHIELDS, "SteelArmor 3", Producer.Bebra, 500, 15),
            new Component(ComponentType.SHIELDS, "Titanoboa", Producer.Anaconda, 2_000, 20),
            new Component(ComponentType.SHIELDS, "Kasperk", Producer.ZCU, 5_000, 50),
            new Component(ComponentType.SHIELDS, "Black Mask", Producer.DarthVader, 3_000, 30),


            new Component(ComponentType.WEAPONS, "BangBang 3000", Producer.Bebra, 1_000, 10),
            new Component(ComponentType.WEAPONS, "Cobra", Producer.Anaconda, 1_700, 17),
            new Component(ComponentType.WEAPONS, "Menza", Producer.ZCU, 10_000, 15),
            new Component(ComponentType.WEAPONS, "The Death Star", Producer.DarthVader, 2_000, 20),

            new Component(ComponentType.CARGO, "Cargo 5", Producer.Bebra, 1_500, 2),
            new Component(ComponentType.CARGO, "Mamba", Producer.Anaconda, 3_300, 10),
            new Component(ComponentType.CARGO, "Tatra", Producer.ZCU, 2_000, 4),
            new Component(ComponentType.CARGO, "Cruiser", Producer.DarthVader, 5_000, 20),

            new Component(ComponentType.LIFE, "Life 2", Producer.Bebra, 700, 1),
            new Component(ComponentType.LIFE, "Viper", Producer.Anaconda, 1_400, 2),
            new Component(ComponentType.LIFE, "VZP", Producer.ZCU, 3_000, 3),
            new Component(ComponentType.LIFE, "Jedi Power", Producer.DarthVader, 2_000, 2)
    );

    /**
     * Components list getter
     *
     * @return components list
     */
    public static List<Component> getComponents() {
        return components;
    }

}
