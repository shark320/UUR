package Zadani_1;

/**
 * class that represents eagles
 *
 * @author Volodymyr Pavlov
 * @version 21-02-2022
 */
public class Pigeon extends AbstractBird {
    //egg type
    private static final String eggType = "eggs";
    //egg count
    private static final int eggsCount = 2;
    //voice type
    private static final String defVoice = "beeping";
    //food type
    private static final String defFood = "seed";
    //flying type
    private static final String flyType = "low";

    /**
     * constructor
     *
     * @param x      x-coordinate
     * @param y      y-coordinate
     * @param energy life energy
     * @param name   animal name
     */
    public Pigeon(int x, int y, int energy, String name) {
        super(x, y, energy, name);
    }

    /**
     * lay 2 eggs
     */
    @Override
    public void layEggs() {
        layEggs(eggType, eggsCount);
    }

    /**
     * make beep noise
     */
    @Override
    public void cry() {
        cry(defVoice);
    }

    /**
     * eat seeds
     *
     * @param addEnergy adding energy amount
     */
    @Override
    public void eat(int addEnergy) {
        eat(addEnergy, defFood);
    }

    /**
     * flying low
     *
     * @param x destination x-coordinate
     * @param y destination y-coordinate
     */
    @Override
    public void moveTo(int x, int y) {
        moveTo(x, y, flyType);
    }
}
