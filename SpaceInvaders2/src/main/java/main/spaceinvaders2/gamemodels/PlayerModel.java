package main.spaceinvaders2.gamemodels;

import javafx.scene.image.Image;

public class PlayerModel extends AbstractModel{
    public PlayerModel(int x, int y, Image texture) {
        super(x, y, texture);
    }

    public boolean up() {
        return move(x,y-moveDistance);
    }

    public boolean down() {
        return move(x,y+moveDistance);
    }

    public boolean left() {
        return move(x-moveDistance,y);
    }

    public boolean right() {
        return move(x+moveDistance,y);
    }
}
