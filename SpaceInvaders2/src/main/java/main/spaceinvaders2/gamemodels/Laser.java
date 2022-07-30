package main.spaceinvaders2.gamemodels;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Laser {

    private Image texture;
    private double x;

    private double y;

    private double speed=5;

    private double width;

    private double height;

    private ImageView view;

    private final Direction direction;

    public Laser(double x, double y,Image texture, Direction direction){
        this.x=x;
        this.y=y;
        this.direction = direction;
        this.width = texture.getWidth();
        this.height = texture.getHeight();
        this.view = new ImageView(texture);
        this.view.setX(x-width/2);
        this.view.setY(y-height/2);

    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }


    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public ImageView getView() {
        return view;
    }

    public Direction getDirection() {
        return direction;
    }

    public void scale (double scale){
        view.setPreserveRatio(true);

        width = width * scale;
        height = height * scale;

        view.setFitWidth(width);
        view.setFitHeight(height);

        view.setX(x - width / 2);
        view.setY(y - height / 2);
    }

    public void move(){
        if (direction == Direction.UP){
            y=y-speed;
        }else {
            y=y+speed;
        }
        view.setY(y-view.getImage().getHeight()/2);
    }
}
