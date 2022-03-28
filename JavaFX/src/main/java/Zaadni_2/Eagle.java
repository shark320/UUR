package Zaadni_2;

/**
 * class that represents eagles
 *
 * @author Volodymyr Pavlov
 * @version 21-02-2022
 */
public class Eagle extends AbstractBird {
    //egg type
    private static final String eggType = "eggs";
    //egg count
    private static final int eggsCount = 3;
    //voice type
    private static final String defVoice = "loud screaming";
    //food type
    private static final String defFood = "meat";
    //flying type
    private static final String flyType = "high";

    /**
     * constructor
     *
     * @param x      x-coordinate
     * @param y      y-coordinate
     * @param energy life energy
     * @param name   animal name
     */
    public Eagle(int x, int y, int energy, String name) {
        super(x, y, energy, name);
    }

    /**
     * laying 3 eggs
     */
    @Override
    public void layEggs() {
        layEggs(eggType, eggsCount);
    }

    /**
     * male loud noise
     */
    @Override
    public void cry() {
        cry(defVoice);
    }

    /**
     * eat meat
     *
     * @param addEnergy adding energy amount
     */
    @Override
    public void eat(int addEnergy) {
        eat(addEnergy, defFood);
    }

    /**
     * flying high
     *
     * @param x destination x-coordinate
     * @param y destination y-coordinate
     */
    @Override
    public void moveTo(int x, int y) {
        moveTo(x, y, flyType);
    }

    @Override
    public String toString() {
        return "[Eagle " +name + " X: " + x + " Y: "+y+" energy "+energy+']';
    }
}
