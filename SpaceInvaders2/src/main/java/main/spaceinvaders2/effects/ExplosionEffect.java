package main.spaceinvaders2.effects;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import main.spaceinvaders2.gamemodels.AbstractModel;
import main.spaceinvaders2.gamemodels.Direction;

import javax.imageio.ImageIO;

public class ExplosionEffect {

    private static final int DURATION = 1000;

    private static final double IMAGE_SCALE = 0.5;

    private double x;

    private double y;

    private double width;

    private double height;

    private int currentImg=0;

    private Image[] textures;

    private int imageCount;

    private ImageView view;

    private long startTime;


    public ExplosionEffect (AbstractModel model, Image[] explosionTextures, long startTime){
        this.x=model.getX();
        this.y=model.getY();
        this.textures = explosionTextures;

        this.view = new ImageView();

        this.imageCount = explosionTextures.length;

        if (this.imageCount == 0 ){
            throw new IllegalArgumentException("Textures array can not be empty");
        }

        width = textures[0].getWidth()*IMAGE_SCALE;
        height = textures[0].getHeight()*IMAGE_SCALE;
        this.view.setX(this.x-width/2);
        this.view.setY(this.y-height/2);
        view.setImage(textures[0]);
        view.setPreserveRatio(true);
        view.setFitWidth(width);
        view.setFitHeight(height);
        this.startTime=startTime;
    }

    public ImageView getView(){
        return view;
    }

    public boolean update (long elapsed){
        if (startTime+DURATION<elapsed) return  true;

        //System.out.println(currentImg);
        currentImg = (int)(elapsed-startTime)/(DURATION/imageCount);

        if (currentImg<imageCount) {
            //System.out.println(textures[currentImg].getHeight());
            if (view.getImage() != textures[currentImg]) {
                view.setImage(textures[currentImg]);
                //System.out.println(view.getImage());
            }
        }

        return false;
    }


}
