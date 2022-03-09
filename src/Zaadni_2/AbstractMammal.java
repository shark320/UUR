package Zaadni_2;

/**
 * mammal abstract class, can cry
 *
 * @author Volodymyr Pavlov
 * @version 21-02-2022
 */
public abstract class AbstractMammal extends AbstractAnimal implements IVoicy {
    /**
     * constructor
     *
     * @param x      x-coordinate
     * @param y      y-coordinate
     * @param energy life energy
     * @param name   animal name
     */
    protected AbstractMammal(int x, int y, int energy, String name) {
        super(x, y, energy, name);
    }

    /**
     * crying helper method
     *
     * @param voice -specific voice of crying
     */
    protected void cry(String voice) {
        if (energy < 1) {
            System.out.printf("%s is tired and can not sing!\n", name);
        } else {
            energy -= 1;
            System.out.printf("%s is singing: %s\n", name, voice);
        }
    }
}
