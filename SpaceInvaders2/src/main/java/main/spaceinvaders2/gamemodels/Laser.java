package main.spaceinvaders2.gamemodels;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Laser model with texture, coordinates
 *
 * @author Volodymyr Pavlov
 * @version 30.07.2022
 */
public class Laser {

    //--------------------------------------------------------------------------//
    //                                                                          //
    //                            Instance constants                            //
    //                                                                          //
    //--------------------------------------------------------------------------//

    /**
     * X-coordinate
     */
    private final double x;

    /**
     * Laser model ImageView (sprite)
     */
    private final ImageView view;

    //--------------------------------------------------------------------------//
    //                                                                          //
    //                            Instance variables                            //
    //                                                                          //
    //--------------------------------------------------------------------------//

    /**
     * Y-coordinate
     */
    private double y;

    /**
     * Laser speed (default = 5)
     */
    private double speed = 5;

    /**
     * Laser model width
     */
    private double width;

    /**
     * Laser model height
     */
    private double height;

    /**
     * Laser direction
     */
    private final Direction direction;

    //--------------------------------------------------------------------------//
    //                                                                          //
    //                                Constructors                              //
    //                                                                          //
    //--------------------------------------------------------------------------//

    /**
     * Class constructor
     *
     * @param x         - X-coordinate
     * @param y         - Y-coordinate
     * @param texture   -laser texture
     * @param direction -laser direction
     */
    public Laser(double x, double y, Image texture, Direction direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.width = texture.getWidth();
        this.height = texture.getHeight();
        this.view = new ImageView(texture);
        this.view.setX(x - width / 2);
        this.view.setY(y - height / 2);

    }

    //--------------------------------------------------------------------------//
    //                                                                          //
    //                           Getters and Setters                            //
    //                                                                          //
    //--------------------------------------------------------------------------//

    /**
     * Laser speed getter
     *
     * @param speed laser speed
     */
    public void setSpeed(double speed) {
        this.speed = speed;
    }

    /**
     * X-coordinate getter
     *
     * @return X-coordinate
     */
    public double getX() {
        return x;
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
     * Laser model width getter
     *
     * @return width
     */
    public double getWidth() {
        return width;
    }

    /**
     * Laser model height getter
     *
     * @return height
     */
    public double getHeight() {
        return height;
    }

    /**
     * Laser ImageView getter
     *
     * @return laser ImageView
     */
    public ImageView getView() {
        return view;
    }

    /**
     * Laser direction getter
     *
     * @return laser direction
     */
    public Direction getDirection() {
        return direction;
    }

    /**
     * Scale laser model
     *
     * @param scale -scale factor
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
     * Move laser according to the direction
     */
    public void move() {
        if (direction == Direction.UP) {
            y = y - speed;
        } else {
            y = y + speed;
        }
        view.setY(y - view.getImage().getHeight() / 2);
    }
}
