package Zaadni_2;

/**
 * class that represents sloths
 *
 * @author Volodymyr Pavlov
 * @version 21-02-2022
 */
public class Sloth extends AbstractMammal {
    //voice type
    private static final String defVoice = "growling";
    //food type
    private static final String defFood = "leaves";
    //running type
    private static final String runType = "crawling";

    /**
     * constructor
     *
     * @param x      x-coordinate
     * @param y      y-coordinate
     * @param energy life energy
     * @param name   animal name
     */
    public Sloth(int x, int y, int energy, String name) {
        super(x, y, energy, name);
    }

    /**
     * growling
     */
    @Override
    public void cry() {
        cry(defVoice);
    }

    /**
     * crawling
     *
     * @param x destination x-coordinate
     * @param y destination y-coordinate
     */
    @Override
    public void moveTo(int x, int y) {
        moveTo(x, y, runType);
    }

    /**
     * eat leaves
     *
     * @param addEnergy adding energy amount
     */
    @Override
    public void eat(int addEnergy) {
        eat(addEnergy, defFood);
    }

    @Override
    public String toString() {
        return "[Sloth " +name + " X: " + x + " Y: "+y+" energy "+energy+']';
    }
}
