package main.spaceinvaders2.gamemodels;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.List;

public abstract class AbstractModel {

    protected static final double DEF_MOVE_DISTANCE = 5;
    protected double x;

    protected double y;

    protected double upBorder;

    protected double downBorder;

    protected double leftBorder;

    protected double rightBorder;

    protected double moveDistance = DEF_MOVE_DISTANCE;

    protected double width;

    protected double height;

    protected Image texture;

    protected ImageView view;


    protected Image laserTexture;


    protected long fireRate = 1500;

    protected long previousShot = fireRate;


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

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
        view.setX(x - width / 2);
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
        view.setY(y - height / 2);
    }

    public Image getTexture() {
        return texture;
    }

    public void setTexture(Image texture) {
        this.texture = texture;
    }

    public double getWidth() {
        return texture.getWidth();
    }

    public double getHeight() {
        return texture.getHeight();
    }

    public void setBorders(double up, double down, double left, double right) {
        this.upBorder = up;
        this.downBorder = down;
        this.leftBorder = left;
        this.rightBorder = right;
    }



    public void setMoveDistance(double moveDistance) {
        this.moveDistance = moveDistance;
    }

    protected boolean checkMove (double x, double y){
        if ((x - moveDistance < leftBorder + width / 2)||(x + moveDistance > rightBorder - width / 2))
            return false;
        if ((y - moveDistance < upBorder + height / 2)||(y + moveDistance > downBorder - height / 2))
            return false;
        return true;
    }

    public boolean move (double moveX, double moveY){
        if(!checkMove(moveX, moveY))
            return false;
        setX(moveX);
        setY(moveY);
        return true;
    }

    public void scale(double scale) {
        view.setPreserveRatio(true);

        width = width * scale;
        height = height * scale;

        view.setFitWidth(width);
        view.setFitHeight(height);

        view.setX(x - width / 2);
        view.setY(y - height / 2);
    }

    public abstract List<Laser> shot(long elapsed);

    public ImageView getView() {
        return view;
    }
}
