package main.spaceinvaders2.gamemodels;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Laser {

    private Image texture;
    private double x;

    private double y;

    private double speed;

    private ImageView view;

    private final Direction direction;

    public Laser(int x, int y,Image texture, Direction direction){
        this.x=x;
        this.y=y;
        this.direction = direction;
        view = new ImageView(texture);
        view.setX(x-texture.getWidth());
        view.setY(y-texture.getHeight());
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public ImageView getView() {
        return view;
    }

    public Direction getDirection() {
        return direction;
    }

    public void move(){
        if (direction == Direction.UP){
            y=y-speed;
        }else {
            y=y+speed;
        }
        view.setY(y-view.getImage().getHeight());
    }
}
