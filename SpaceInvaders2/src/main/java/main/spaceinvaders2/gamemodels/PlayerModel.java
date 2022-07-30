package main.spaceinvaders2.gamemodels;

import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class PlayerModel extends AbstractModel {

    private static final int INIT_HEALTH = 5;

    private int health = INIT_HEALTH;

    public PlayerModel(int x, int y, Image texture, Image laserTexture) {
        super(x, y, texture, laserTexture);
    }

    public int getHealth() {
        return health;
    }



    public void setHealth(int health) {
        if (health <= 0) throw new IllegalArgumentException("Health should be a positive integer");
        this.health = health;
    }

    public boolean decHealth(){
        return --health==0;
    }

    public boolean up() {
        return move(x, y - moveDistance);
    }

    public boolean down() {
        return move(x, y + moveDistance);
    }

    public boolean left() {
        return move(x - moveDistance, y);
    }

    public boolean right() {
        return move(x + moveDistance, y);
    }

    @Override
    public List<Laser> shot(long elapsed) {
        if (previousShot + fireRate < elapsed) {
            previousShot = elapsed;
            Laser laser1 = new Laser(x + width / 6, y, laserTexture, Direction.UP);
            Laser laser2 = new Laser(x - width / 6, y, laserTexture, Direction.UP);

            laser1.setSpeed(laserSpeed);
            laser2.setSpeed(laserSpeed);

            LinkedList<Laser> lasers = new LinkedList<>();
            lasers.add(laser1);
            lasers.add(laser2);
            return lasers;
        }
        return null;
    }
}
