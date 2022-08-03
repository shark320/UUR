package main.spaceinvaders2.effects;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import main.spaceinvaders2.gamemodels.Direction;
import main.spaceinvaders2.gamemodels.Laser;

/**
 * Hit effect animation class
 *
 * @author Volodymyr Pavlov
 * @version 30.07.2022
 */
public class HitEffect {

    //--------------------------------------------------------------------------//
    //                                                                          //
    //                           Static constants                               //
    //                                                                          //
    //--------------------------------------------------------------------------//

    /**
     * Animation duration
     */
    private static final int DURATION = 1000;

    /**
     * Animation images scale
     */
    private static final double IMAGE_SCALE = 0.5;

    //--------------------------------------------------------------------------//
    //                                                                          //
    //                     Instance constants and variables                     //
    //                                                                          //
    //--------------------------------------------------------------------------//

    /**
     * X-coordinate of the animation
     */
    private final double x;

    /**
     * Y-coordinate of the animation
     */
    private double y;

    /**
     * Animation width
     */
    private final double width;

    /**
     * Animation height
     */
    private final double height;

    /**
     * Animation ImageView instance
     */
    private final ImageView view;

    /**
     * Start time of the animation
     */
    private final long startTime;

    //--------------------------------------------------------------------------//
    //                                                                          //
    //                              Constructors                                //
    //                                                                          //
    //--------------------------------------------------------------------------//


    /**
     * Class constructor
     *
     * @param laser     -laser animation hit to make
     * @param texture   -hit texture
     * @param startTime -start time of the animation
     */
    public HitEffect(Laser laser, Image texture, long startTime) {
        this.startTime = startTime;
        this.x = laser.getX();
        if (laser.getDirection() == Direction.UP) {
            this.y = laser.getY() - laser.getHeight() / 2;
        } else if (laser.getDirection() == Direction.DOWN) {
            this.y = laser.getY() + laser.getHeight() / 2;
        }
        view = new ImageView(texture);
        view.setPreserveRatio(true);
        height = texture.getHeight();
        this.y = y - (height / 2) * IMAGE_SCALE;
        width = texture.getWidth();
        view.setX(this.x - width / 2);
        view.setY(this.y - height);
        scale(IMAGE_SCALE);
    }

    //--------------------------------------------------------------------------//
    //                                                                          //
    //                           Getters and Setters                            //
    //                                                                          //
    //--------------------------------------------------------------------------//


    /**
     * ImageView of the effect getter
     *
     * @return ImageView of the effect
     */
    public ImageView getView() {
        return view;
    }

    //--------------------------------------------------------------------------//
    //                                                                          //
    //                           Instance methods                               //
    //                                                                          //
    //--------------------------------------------------------------------------//

    /**
     * Scales animation view
     *
     * @param scale -scale parameter
     * @throws IllegalArgumentException - if scale argument <= 0
     */
    private void scale(double scale) throws IllegalArgumentException {
        if (scale <= 0) {
            throw new IllegalArgumentException("Scale factor should be a positive value");
        }
        double curWidth = width * scale;
        double curHeight = height * scale;
        view.setFitWidth(curWidth);
        view.setFitHeight(curHeight);
        view.setX(this.x - curWidth / 2);
        view.setY(this.y - curHeight / 2);
    }


    /**
     * Redraw the effect, change current effect image
     *
     * @param elapsed -current time in the game
     * @return true - if the animation is ended, false - if it's still processing
     */
    public boolean update(long elapsed) {
        if (startTime + DURATION > elapsed) {
            scale((1 - (elapsed - startTime) / (double) DURATION) * IMAGE_SCALE);
            return false;
        }
        return true;
    }
}
