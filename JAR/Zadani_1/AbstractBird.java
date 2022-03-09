package Zadani_1;


/**
 * bird abstract class, can cry and lay eggs
 *
 * @author Volodymyr Pavlov
 * @version 21-02-2022
 */
public abstract class AbstractBird extends AbstractAnimal implements IEggPlanty, IVoicy {

    /**
     * constructor
     *
     * @param x      x-coordinate
     * @param y      y-coordinate
     * @param energy life energy
     * @param name   animal name
     */
    protected AbstractBird(int x, int y, int energy, String name) {
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

    /**
     * helper laying eggs method
     *
     * @param type  type of eggs
     * @param count amount of eggs
     */
    protected void layEggs(String type, int count) {
        if (energy < 5) {
            System.out.printf("%s can not lay eggs!\n", name);
        } else {
            energy -= 5;
            System.out.printf("%s has laid %d %s\n", name, count, type);
        }
    }

}
