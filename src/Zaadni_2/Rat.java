package Zaadni_2;

public class Rat extends AbstractMammal {
    //voice type
    private static final String defVoice = "whistling";
    //food type
    private static final String defFood = "everything";
    //running type
    private static final String runType = "running";

    /**
     * constructor
     *
     * @param x      x-coordinate
     * @param y      y-coordinate
     * @param energy life energy
     * @param name   animal name
     */
    public Rat(int x, int y, int energy, String name) {
        super(x, y, energy, name);
    }

    /**
     * whistling
     */
    @Override
    public void cry() {
        cry(defVoice);
    }

    /**
     * running
     *
     * @param x destination x-coordinate
     * @param y destination y-coordinate
     */
    @Override
    public void moveTo(int x, int y) {
        moveTo(x, y, runType);
    }

    /**
     * eat everything
     *
     * @param addEnergy adding energy amount
     */
    @Override
    public void eat(int addEnergy) {
        eat(addEnergy, defFood);
    }

    @Override
    public String toString() {
        return "[Rat " +name + " X: " + x + " Y: "+y+" energy "+energy+']';
    }
}
