package Zaadni_2;

/**
 * abstract class, presents some animals in the game
 *
 * @author Volodymyr Pavlov
 * @version 21-02-2022
 */
public abstract class AbstractAnimal {
    //x-coordinate
    protected int x;
    //y-coordinate
    protected int y;
    //life energy
    protected int energy;
    //animal name
    protected final String name;

    /**
     * constructor
     *
     * @param x      x-coordinate
     * @param y      y-coordinate
     * @param energy life energy
     * @param name   animal name
     */
    protected AbstractAnimal(int x, int y, int energy, String name) {
        if (x < 0 || y < 0 || energy < 0) {
            throw new IllegalArgumentException();
        }
        this.x = x;
        this.y = y;
        this.energy = energy;
        this.name = name;
    }

    /**
     * x-coordinate getter
     *
     * @return x-coordinate
     */
    public int getX() {
        return x;
    }

    /**
     * y-coordinate getter
     *
     * @return y-coordinate
     */
    public int getY() {
        return y;
    }

    /**
     * animal name getter
     *
     * @return animal name
     */
    public String getName() {
        return name;
    }

    /**
     * energy getter
     *
     * @return energy
     */
    public int getEnergy() {
        return energy;
    }

    /**
     * help method, animal moving
     *
     * @param x        destination x-coordinate
     * @param y        destination y-coordinate
     * @param moveType moving type (different animals have different moving type)
     */
    protected void moveTo(int x, int y, String moveType) {
        if (x < 0 || y < 0) {
            throw new IllegalArgumentException();
        }
        if (energy < 1) {
            System.out.println("Not enough energy!\n");
        } else {
            System.out.printf("%s is %s from [%d,%d] to [%d,%d], after has %d energy\n", name, moveType, this.x, this.y, x, y, --energy);
            this.x = x;
            this.y = y;
        }
    }

    /**
     * animal moving in the world
     *
     * @param x destination x-coordinate
     * @param y destination y-coordinate
     */
    public abstract void moveTo(int x, int y);

    /**
     * eating help method
     *
     * @param addEnergy adding energy amount
     * @param food      specific food
     */
    protected void eat(int addEnergy, String food) {
        if (addEnergy <= 0) {
            throw new IllegalArgumentException();
        }
        this.energy += addEnergy;
        System.out.printf("%s haas eaten %s and has %d energy\n", name, food, energy);
    }

    /**
     * eating method
     *
     * @param addEnergy adding energy amount
     */
    public abstract void eat(int addEnergy);
}
