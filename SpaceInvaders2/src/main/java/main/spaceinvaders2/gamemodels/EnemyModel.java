package main.spaceinvaders2.gamemodels;

import javafx.scene.image.Image;

public class EnemyModel extends AbstractModel {

    private static final int DEF_HP = 2;

    private int HP = DEF_HP;

    private int movingTime = 0;

    private double movingVector = Math.PI;

    public EnemyModel(double x, double y, Image texture) {
        super(x, y, texture);
    }

    public EnemyModel(Image texture) {
        super(texture);
    }

    public boolean hit() {
        return --HP == 0;
    }

    public void setHP(int HP) {
        this.HP = HP;
    }

    public int getHP() {
        return HP;
    }

    public boolean move(){
        double moveX = x+moveDistance*Math.cos(movingVector);
        double moveY = y+moveDistance*Math.sin(movingVector);

        return move(moveX,moveY);
    }

    public void setMovingTime(int movingTime) {
        if (movingTime < 0)
            throw new IllegalArgumentException("Moving time should be non negative integer");

        this.movingTime = movingTime;
    }

    public int getMovingTime() {
        return movingTime;
    }

    public void decMovingTime(){
        setMovingTime(movingTime-1);
    }

    public void setMovingVector(double movingVector) {
        if (movingVector < 0 || movingVector > (2 * Math.PI))
            throw new IllegalArgumentException("Moving vector should be in range [0, 2*PI]");
        this.movingVector=movingVector;
    }

    public double getMovingVector() {
        return movingVector;
    }
}
