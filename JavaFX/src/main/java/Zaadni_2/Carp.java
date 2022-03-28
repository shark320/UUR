package Zaadni_2;

public class Carp extends AbstractFish {
    //egg type
    private static final String eggType = "caviar";
    //egg count
    private static final int eggsCount = 500;
    //food type
    private static final String defFood = "seaweed";
    //swimming type
    private static final String swimType = "swimming at the surface";

    /**
     * constructor
     *
     * @param x      x-coordinate
     * @param y      y-coordinate
     * @param energy life energy
     * @param name   animal name
     */
    public Carp(int x, int y, int energy, String name) {
        super(x, y, energy, name);
    }

    /**
     * lay 500 caviar
     */
    @Override
    public void layEggs() {
        layEggs(eggType, eggsCount);
    }

    /**
     * swim under at the surface
     *
     * @param x destination x-coordinate
     * @param y destination y-coordinate
     */
    @Override
    public void moveTo(int x, int y) {
        moveTo(x, y, swimType);
    }

    /**
     * eat seaweed
     *
     * @param addEnergy adding energy amount
     */
    @Override
    public void eat(int addEnergy) {
        eat(addEnergy, defFood);
    }

    @Override
    public String toString() {
        return "[Carp " +name + " X: " + x + " Y: "+y+" energy "+energy+']';
    }
}
