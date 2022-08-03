package main.spaceinvaders2.gamemodels;

import javafx.scene.image.Image;

import java.util.LinkedList;
import java.util.List;

/**
 * Enemy game model with texture, coordinates and borders
 *
 * @author Volodymyr Pavlov
 * @version 30.07.2022
 */
public class EnemyModel extends AbstractModel {

    //--------------------------------------------------------------------------//
    //                                                                          //
    //                            Instance variables                            //
    //                                                                          //
    //--------------------------------------------------------------------------//

    /**
     * Current enemy hit points
     */
    private int HP;

    /**
     * Time from the start of movement by this vector
     */
    private int movingTime = 0;

    /**
     * Current moving vector
     */
    private double movingVector = Math.PI;

    /**
     * Enemy model type
     */
    private final ModelType modelType;

    //--------------------------------------------------------------------------//
    //                                                                          //
    //                                Constructors                              //
    //                                                                          //
    //--------------------------------------------------------------------------//

    /**
     * Class constructor
     *
     * @param texture      -model texture
     * @param laserTexture -laser texture
     * @param modelType    - enemy model type
     */
    public EnemyModel(Image texture, ModelType modelType, Image laserTexture) {
        super(texture, laserTexture);
        this.modelType = modelType;

        switch (modelType) {
            case SOLDIER_1 -> HP = 1;
            case SOLDIER_2 -> HP = 2;
            case SOLDIER_3 -> HP = 3;
            case BOSS_0 -> {
                HP = 5;
                moveDistance = 1;
            }
            case BOSS_1 -> HP = 6;
            case BOSS_2 -> HP = 7;
            case BOSS_3 -> HP = 8;
            case BOSS_4 -> HP = 9;
            case BOSS_5 -> HP = 10;
            case BOSS_6 -> HP = 15;
            case BOSS_7 -> HP = 20;
        }
    }

    //--------------------------------------------------------------------------//
    //                                                                          //
    //                           Getters and Setters                            //
    //                                                                          //
    //--------------------------------------------------------------------------//

    /**
     * Set time from the start of movement by this vector
     *
     * @param movingTime - new moving time
     */
    public void setMovingTime(int movingTime) {
        if (movingTime < 0)
            throw new IllegalArgumentException("Moving time should be non negative integer");

        this.movingTime = movingTime;
    }

    /**
     * Moving time getter
     *
     * @return current time from the start of movement by this vector
     */

    public int getMovingTime() {
        return movingTime;
    }

    /**
     * Moving vector setter
     *
     * @param movingVector -new moving vector
     */
    public void setMovingVector(double movingVector) {
        if (movingVector < 0 || movingVector > (2 * Math.PI))
            throw new IllegalArgumentException("Moving vector should be in range [0, 2*PI]");
        this.movingVector = movingVector;
    }

    /**
     * Model type getter
     *
     * @return this enemy model type
     */
    public ModelType getModelType() {
        return modelType;
    }

    //--------------------------------------------------------------------------//
    //                                                                          //
    //                            Instance methods                              //
    //                                                                          //
    //--------------------------------------------------------------------------//

    /**
     * Decrement HP
     *
     * @return true if HP <= 0, else - false
     */
    public boolean hit() {
        return --HP <= 0;
    }

    /**
     * Move by moving vector on the moving distance
     *
     * @return true if it has moved, else - false
     */

    public boolean move() {
        double moveX = x + moveDistance * Math.cos(movingVector);
        double moveY = y + moveDistance * Math.sin(movingVector);

        return move(moveX, moveY);
    }



    /**
     * decrement moving time
     */
    public void decMovingTime() {
        setMovingTime(movingTime - 1);
    }

    @Override
    public List<Laser> shot(long elapsed) {

        switch (modelType) {
            case SOLDIER_1, SOLDIER_2, SOLDIER_3 -> {
                return null;
            }
            default -> {
                if (previousShot + fireRate < elapsed) {
                    LinkedList<Laser> lasers = new LinkedList<>();
                    Laser laser1 = new Laser(x + width / 3, y, laserTexture, Direction.DOWN);
                    Laser laser2 = new Laser(x - width / 3, y, laserTexture, Direction.DOWN);
                    laser1.setSpeed(laserSpeed);
                    laser2.setSpeed(laserSpeed);
                    lasers.add(laser1);
                    lasers.add(laser2);
                    previousShot = elapsed;
                    return lasers;
                }
            }
        }
        return null;
    }
}
