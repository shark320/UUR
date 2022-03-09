package Zadani_1;

/**
 * Fish abstract class, can lay eggs
 *
 * @author Volodymyr Pavlov
 * @version 21-02-2022
 */
public abstract class AbstractFish extends AbstractAnimal implements IEggPlanty {

    /**
     * constructor
     *
     * @param x      x-coordinate
     * @param y      y-coordinate
     * @param energy life energy
     * @param name   animal name
     */
    protected AbstractFish(int x, int y, int energy, String name) {
        super(x, y, energy, name);
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
