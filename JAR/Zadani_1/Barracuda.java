package Zadani_1;

/**
 * class that represents fish barracuda
 *
 * @author Volodymyr Pavlov
 * @version 21-02-2022
 */
public class Barracuda extends AbstractFish {
    //egg type
    private static final String eggType = "caviar";
    //egg count
    private static final int eggsCount = 1000;
    //food type
    private static final String defFood = "meat";
    //swimming type
    private static final String swimType = "swimming in the depth";

    /**
     * constructor
     *
     * @param x      x-coordinate
     * @param y      y-coordinate
     * @param energy life energy
     * @param name   animal name
     */
    public Barracuda(int x, int y, int energy, String name) {
        super(x, y, energy, name);
    }

    /**
     * lays 1000 caviar
     */
    @Override
    public void layEggs() {
        layEggs(eggType, eggsCount);
    }

    /**
     * swims in the depth
     *
     * @param x destination x-coordinate
     * @param y destination y-coordinate
     */
    @Override
    public void moveTo(int x, int y) {
        moveTo(x, y, swimType);
    }

    /**
     * eats meat
     *
     * @param addEnergy adding energy amount
     */
    @Override
    public void eat(int addEnergy) {
        eat(addEnergy, defFood);
    }
}
