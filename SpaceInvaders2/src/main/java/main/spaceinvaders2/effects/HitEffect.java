package main.spaceinvaders2.effects;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import main.spaceinvaders2.gamemodels.Direction;
import main.spaceinvaders2.gamemodels.Laser;

public class HitEffect {

    private static final int DURATION = 1000;

    private static final double IMAGE_SCALE = 0.5;

    private double x;

    private double y;

    private double width;

    private double height;

    private ImageView view;

    private long startTime = 0;

    private Direction direction;

    public HitEffect(Laser laser, Image texture, long startTime) {
        this.x = laser.getX();
        //this.y=laser.getY();
        if (laser.getDirection() == Direction.UP) {
            this.y = laser.getY() - laser.getHeight() / 2;
        } else if (laser.getDirection() == Direction.DOWN) {
            this.y = laser.getY() + laser.getHeight() / 2;
        }

        direction = laser.getDirection();
        view = new ImageView(texture);
        view.setPreserveRatio(true);
        height = texture.getHeight();
        this.y=y-(height/2)*IMAGE_SCALE;
        width = texture.getWidth();
        view.setX(this.x - width / 2);
        view.setY(this.y - height);
        scale(IMAGE_SCALE);
        this.startTime = startTime;
    }

    public ImageView getView() {
        return view;
    }

    private void scale(double scale) {
        double curWidth = width * scale;
        double curHeight = height * scale;
        view.setFitWidth(curWidth);
        view.setFitHeight(curHeight);
        view.setX(this.x - curWidth / 2);
        view.setY(this.y - curHeight / 2);
    }

    public boolean update(long elapsed) {
        //System.out.println(startTime + " " + elapsed);
        if (startTime + DURATION > elapsed) {
            scale((1 - (elapsed - startTime) / (double) DURATION) * IMAGE_SCALE);
            return false;
        }
        return true;
    }
}
