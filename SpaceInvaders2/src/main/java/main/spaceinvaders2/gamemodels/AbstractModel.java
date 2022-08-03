package main.spaceinvaders2.gamemodels;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.List;

/**
 * Abstract game model with texture, coordinates and borders
 *
 * @author Volodymyr Pavlov
 * @version 30.07.2022
 */
public abstract class AbstractModel {

    //--------------------------------------------------------------------------//
    //                                                                          //
    //                              Static constants                            //
    //                                                                          //
    //--------------------------------------------------------------------------//

    /**
     * Default models moving distance
     */
    protected static final double DEF_MOVE_DISTANCE = 5;

    //--------------------------------------------------------------------------//
    //                                                                          //
    //                           Instance variables                             //
    //                                                                          //
    //--------------------------------------------------------------------------//

    /**
     * X-coordinate of the model
     */
    protected double x;

    /**
     * Y-coordinate of the model
     */
    protected double y;

    /**
     * Upper moving area border
     */
    protected double upBorder;

    /**
     * Lower moving area border
     */
    protected double downBorder;

    /**
     * Left moving area border
     */
    protected double leftBorder;

    /**
     * Right moving area border
     */
    protected double rightBorder;

    /**
     * Moving distance
     */
    protected double moveDistance = DEF_MOVE_DISTANCE;

    /**
     * Model width
     */
    protected double width;

    /**
     * Model height
     */
    protected double height;

    /**
     * Model texture
     */
    protected Image texture;

    /**
     * Model ImageView (sprite)
     */
    protected ImageView view;

    /**
     * Models laser texture
     */
    protected Image laserTexture;

    /**
     * Model fire rate (milliseconds)
     */
    protected long fireRate = 1500;

    /**
     * Laser speed
     */
    protected double laserSpeed = 5;

    /**
     * Time of the previous shot
     */
    protected long previousShot = fireRate;

    //--------------------------------------------------------------------------//
    //                                                                          //
    //                              Constructors                                //
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
    protected AbstractModel(double x, double y, Image texture, Image laserTexture) {
        this.x = x;
        this.y = y;
        this.texture = texture;
        this.laserTexture = laserTexture;
        width = texture.getWidth();
        height = texture.getHeight();
        view = new ImageView(texture);
        view.setX(x - width / 2);
        view.setY(y - height / 2);
    }

    /**
     * Class constructor
     *
     * @param texture      -model texture
     * @param laserTexture -laser texture
     */
    protected AbstractModel(Image texture, Image laserTexture) {
        this.x = 0;
        this.y = 0;
        this.texture = texture;
        this.laserTexture = laserTexture;
        width = texture.getWidth();
        height = texture.getHeight();
        view = new ImageView(texture);
        view.setX(x - width / 2);
        view.setY(y - height / 2);
    }

    //--------------------------------------------------------------------------//
    //                                                                          //
    //                           Getters and Setters                            //
    //                                                                          //
    //--------------------------------------------------------------------------//

    /**
     * X-coordinate getter
     *
     * @return current X-coordinate
     */
    public double getX() {
        return x;
    }

    /**
     * X-coordinate setter
     *
     * @param x -new X-coordinate
     */
    public void setX(double x) {
        this.x = x;
        view.setX(x - width / 2);
    }

    /**
     * Y-coordinate getter
     *
     * @return current Y-coordinate
     */
    public double getY() {
        return y;
    }

    /**
     * Y-coordinate setter
     *
     * @param y -new Y-coordinate
     */
    public void setY(double y) {
        this.y = y;
        view.setY(y - height / 2);
    }

    /**
     * Model width getter
     *
     * @return model width
     */
    public double getWidth() {
        return width;
    }

    /**
     * Model height getter
     *
     * @return model height
     */
    public double getHeight() {
        return texture.getHeight();
    }

    /**
     * Laser speed setter
     *
     * @param laserSpeed -new laser speed
     */
    public void setLaserSpeed(double laserSpeed) {
        if (!(laserSpeed > 0)) {
            throw new IllegalArgumentException("Laser speed should be a positive double");
        }
        this.laserSpeed = laserSpeed;
    }

    /**
     * Fire rate getter
     *
     * @return current fire rate (milliseconds)
     */
    public long getFireRate() {
        return fireRate;
    }

    /**
     * Fire rate setter
     *
     * @param fireRate -new fire rate (milliseconds)
     */
    public void setFireRate(long fireRate) {
        if (!(fireRate > 0)) {
            throw new IllegalArgumentException("Fire rate should be a positive long");
        }
        this.fireRate = fireRate;
    }

    /**
     * Borders setter
     *
     * @param up    -upper moving area border
     * @param down  -lower moving area border
     * @param left  -left moving area border
     * @param right -right moving area border
     */
    public void setBorders(double up, double down, double left, double right) {
        this.upBorder = up;
        this.downBorder = down;
        this.leftBorder = left;
        this.rightBorder = right;
    }

    /**
     * Move distance setter
     *
     * @param moveDistance -new move distance
     */
    public void setMoveDistance(double moveDistance) {
        this.moveDistance = moveDistance;
    }

    /**
     * Model ImageView (sprite) getter
     *
     * @return model ImageView
     */
    public ImageView getView() {
        return view;
    }

    //--------------------------------------------------------------------------//
    //                                                                          //
    //                              Instance methods                            //
    //                                                                          //
    //--------------------------------------------------------------------------//

    /**
     * Checks if model can move to specified coordinates
     *
     * @param x -X-move coordinate
     * @param y -Y-move coordinate
     * @return true if canned, false - if not
     */
    protected boolean checkMove(double x, double y) {
        if ((x - moveDistance < leftBorder + width / 2) || (x + moveDistance > rightBorder - width / 2))
            return false;
        return (!(y - moveDistance < upBorder + height / 2)) && (!(y + moveDistance > downBorder - height / 2));
    }

    /**
     * Move model to specified coordinates
     *
     * @param moveX -new X-coordinate
     * @param moveY -new Y-coordinate
     * @return true - if model have moved, else - false
     */
    public boolean move(double moveX, double moveY) {
        if (!checkMove(moveX, moveY))
            return false;
        setX(moveX);
        setY(moveY);
        return true;
    }

    /**
     * Scales model
     *
     * @param scale -scale parameter
     */
    public void scale(double scale) {
        view.setPreserveRatio(true);

        width = width * scale;
        height = height * scale;

        view.setFitWidth(width);
        view.setFitHeight(height);

        view.setX(x - width / 2);
        view.setY(y - height / 2);
    }

    /**
     * Make model shot
     *
     * @param elapsed -time from the game start
     * @return list of lasers or null if model has not shot
     */
    public abstract List<Laser> shot(long elapsed);

}
