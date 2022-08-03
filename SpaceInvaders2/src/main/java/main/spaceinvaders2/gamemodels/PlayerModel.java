package main.spaceinvaders2.gamemodels;

import javafx.scene.image.Image;

import java.util.LinkedList;
import java.util.List;

/**
 * Player game model with texture, coordinates and borders
 *
 * @author Volodymyr Pavlov
 * @version 30.07.2022
 */
public class PlayerModel extends AbstractModel {

    //--------------------------------------------------------------------------//
    //                                                                          //
    //                              Static constants                            //
    //                                                                          //
    //--------------------------------------------------------------------------//

    /**
     * Players default start health
     */
    private static final int INIT_HEALTH = 5;

    //--------------------------------------------------------------------------//
    //                                                                          //
    //                            Instance variables                            //
    //                                                                          //
    //--------------------------------------------------------------------------//

    /**
     * Current player health
     */
    private int health = INIT_HEALTH;

    //--------------------------------------------------------------------------//
    //                                                                          //
    //                                Constructors                              //
    //                                                                          //
    //--------------------------------------------------------------------------//

    /**
     * Class constructor
     *
     * @param x            -start X-coordinate
     * @param y            -start Y-coordinate
     * @param texture      -model texture
     * @param laserTexture -laser texture
     */
    public PlayerModel(int x, int y, Image texture, Image laserTexture) {
        super(x, y, texture, laserTexture);
    }

    //--------------------------------------------------------------------------//
    //                                                                          //
    //                           Getters and Setters                            //
    //                                                                          //
    //--------------------------------------------------------------------------//

    /**
     * Player health getter
     *
     * @return current player health
     */
    public int getHealth() {
        return health;
    }

    //--------------------------------------------------------------------------//
    //                                                                          //
    //                              Instance methods                            //
    //                                                                          //
    //--------------------------------------------------------------------------//

    /**
     * Decrement player health
     *
     * @return true if health <= 0, else - false
     */
    public boolean decHealth() {
        return --health <= 0;
    }

    /**
     * Move player up on move distance
     *
     * @return true if it has moves, else false
     */
    public boolean up() {
        return move(x, y - moveDistance);
    }

    /**
     * Move player down on move distance
     *
     * @return true if it has moves, else false
     */
    public boolean down() {
        return move(x, y + moveDistance);
    }


    /**
     * Move player left on move distance
     *
     * @return true if it has moves, else false
     */
    public boolean left() {
        return move(x - moveDistance, y);
    }

    /**
     * Move player right on move distance
     *
     * @return true if it has moves, else false
     */
    public boolean right() {
        return move(x + moveDistance, y);
    }

    @Override
    public List<Laser> shot(long elapsed) {
        if (previousShot + fireRate < elapsed) {
            previousShot = elapsed;
            Laser laser1 = new Laser(x + width / 6, y, laserTexture, Direction.UP);
            Laser laser2 = new Laser(x - width / 6, y, laserTexture, Direction.UP);

            laser1.setSpeed(laserSpeed);
            laser2.setSpeed(laserSpeed);

            LinkedList<Laser> lasers = new LinkedList<>();
            lasers.add(laser1);
            lasers.add(laser2);
            return lasers;
        }
        return null;
    }
}
