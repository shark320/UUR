package main.spaceinvaders2.gamemodels;

import javafx.scene.image.Image;

import java.util.LinkedList;
import java.util.List;

import static main.spaceinvaders2.gamemodels.ModelType.SOLDIER_1;

public class EnemyModel extends AbstractModel {

    private static final int DEF_HP = 2;

    private int HP = DEF_HP;

    private int movingTime = 0;

    private double movingVector = Math.PI;

    private ModelType modelType;

    public EnemyModel(double x, double y, Image texture, Image laserTexture) {
        super(x, y, texture, laserTexture);
    }

    public EnemyModel(Image texture,ModelType modelType, Image laserTexture) {
        super(texture,laserTexture);
        this.modelType = modelType;

        switch (modelType){
            case SOLDIER_1  -> HP = 1;
            case SOLDIER_2  -> HP = 2;
            case SOLDIER_3  -> HP = 3;
            case BOSS_0     -> {
                HP = 5;
                moveDistance = 1;
            }
            case BOSS_1     -> HP = 6;
            case BOSS_2     -> HP = 7;
            case BOSS_3     -> HP = 8;
            case BOSS_4     -> HP = 9;
            case BOSS_5     -> HP = 10;
            case BOSS_6     -> HP = 15;
            case BOSS_7     -> HP = 20;
        }
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

    public ModelType getModelType(){
        return modelType;
    }

    @Override
    public List<Laser> shot(long elapsed) {

            switch (modelType) {
                case SOLDIER_1, SOLDIER_2, SOLDIER_3 -> {
                    return null;
                }
                default-> {
                    if (previousShot + fireRate < elapsed) {
                        LinkedList<Laser> lasers = new LinkedList<>();
                        Laser laser1 = new Laser(x + width / 3, y, laserTexture, Direction.DOWN);
                        Laser laser2 = new Laser(x - width / 3, y, laserTexture, Direction.DOWN);
                        laser1.setSpeed(laserSpeed);
                        laser2.setSpeed(laserSpeed);
                        lasers.add(laser1);
                        lasers.add(laser2);
                        previousShot = elapsed;
                        return lasers;
                    }
                }
            }
        return null;
    }
}
