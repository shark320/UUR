package main.spaceinvaders2.effects;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import main.spaceinvaders2.gamemodels.AbstractModel;

/**
 * Explosion effect animation class
 *
 * @author Volodymyr Pavlov
 * @version 30.07.2022
 */
public class ExplosionEffect {

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
    //                           Instance constants                             //
    //                                                                          //
    //--------------------------------------------------------------------------//

    /**
     * Array of explosion textures
     */
    private final Image[] textures;

    /**
     * Number of images in the animation
     */
    private final int imageCount;

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
     * @param model             -model to animate explosion on
     * @param explosionTextures -explosion textures
     * @param startTime         -start time of the animation
     * @throws IllegalArgumentException -if explosionTextures array is empty
     */
    public ExplosionEffect(AbstractModel model, Image[] explosionTextures, long startTime) throws IllegalArgumentException {
        double x = model.getX();
        double y = model.getY();
        this.textures = explosionTextures;

        this.view = new ImageView();

        this.imageCount = explosionTextures.length;

        if (this.imageCount == 0) {
            throw new IllegalArgumentException("Textures array can not be empty");
        }

        double width = textures[0].getWidth() * IMAGE_SCALE;
        double height = textures[0].getHeight() * IMAGE_SCALE;
        this.view.setX(x - width / 2);
        this.view.setY(y - height / 2);
        view.setImage(textures[0]);
        view.setPreserveRatio(true);
        view.setFitWidth(width);
        view.setFitHeight(height);
        this.startTime = startTime;
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
     * Redraw the effect, change current effect image
     *
     * @param elapsed -current time in the game
     * @return true - if the animation is ended, false - if it's still processing
     */
    public boolean update(long elapsed) {
        if (startTime + DURATION < elapsed) return true;

        int currentImg = (int) (elapsed - startTime) / (DURATION / imageCount);
        if (currentImg < imageCount) {
            if (view.getImage() != textures[currentImg]) {
                view.setImage(textures[currentImg]);
            }
        }

        return false;
    }


}
