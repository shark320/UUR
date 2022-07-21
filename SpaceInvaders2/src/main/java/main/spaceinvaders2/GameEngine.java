package main.spaceinvaders2;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import main.spaceinvaders2.gamemodels.*;

import java.util.*;

public class GameEngine {

    private static final int INIT_ENEMY_COUNT = 5;

    private static final ModelType INIT_ENEMY_TYPE = ModelType.BOSS_6;

    private static final double PANE_WIDTH = 417.2;

    private static final double PANE_HEIGHT = 673.2;

    private static double playerStartX;

    private static double playerStartY;

    private static GameEngine engine;

    private PlayerModel player;

    private List<EnemyModel> enemies;

    private final Pane gamePane;

    private final HBox lifeBox;

    private final Map<ModelType, Image> textures = new HashMap<>();

    private final Map<LaserType, Image> laserTextures = new HashMap<>();

    private final LinkedList<Laser> lasers = new LinkedList<>();

    private final Map<ModelType, Integer> killScores = new  HashMap<>();

    private Image lifeTexture;

    private final Group group = new Group();

    private boolean gameOver = false;

    private int scores = 0;



    private GameEngine(Pane gamePane, HBox lifeBox) {
        this.gamePane = gamePane;
        this.lifeBox = lifeBox;

        killScores.put(ModelType.SOLDIER_1, 50);
        killScores.put(ModelType.SOLDIER_2, 60);
        killScores.put(ModelType.BOSS_0, 100);
        killScores.put(ModelType.BOSS_1, 120);
        killScores.put(ModelType.BOSS_2, 140);
        killScores.put(ModelType.BOSS_3, 160);
        killScores.put(ModelType.BOSS_4, 180);
        killScores.put(ModelType.BOSS_5, 200);
        killScores.put(ModelType.BOSS_6, 300);
        killScores.put(ModelType.BOSS_7, 500);

    }

    public static GameEngine getGameEngine(Pane gamePane, HBox lifeBox) {
        if (engine == null) {
            engine = new GameEngine(gamePane, lifeBox);
        }
        return engine;
    }

    private void loadTextures(String url) {
        textures.put(ModelType.PLAYER, new Image(url + "player.png"));
        textures.put(ModelType.SOLDIER_1, new Image(url + "enemy1.png"));
        textures.put(ModelType.SOLDIER_2, new Image(url + "enemy2.png"));
        textures.put(ModelType.SOLDIER_3, new Image(url + "enemy3.png"));
        textures.put(ModelType.BOSS_0, new Image(url + "bosses/boss1.png"));
        textures.put(ModelType.BOSS_1, new Image(url + "bosses/boss2.png"));
        textures.put(ModelType.BOSS_2, new Image(url + "bosses/boss3.png"));
        textures.put(ModelType.BOSS_3, new Image(url + "bosses/boss4.png"));
        textures.put(ModelType.BOSS_4, new Image(url + "bosses/boss5.png"));
        textures.put(ModelType.BOSS_5, new Image(url + "bosses/boss6.png"));
        textures.put(ModelType.BOSS_6, new Image(url + "bosses/boss7.png"));
        textures.put(ModelType.BOSS_7, new Image(url + "bosses/boss8.png"));

        laserTextures.put(LaserType.RED_LASER, new Image(url+"lasers/red_laser.png"));
        laserTextures.put(LaserType.BLUE_LASER, new Image(url+"lasers/blue_laser.png"));
        laserTextures.put(LaserType.RED_BULLET, new Image(url+"lasers/red_bullet.png"));
        laserTextures.put(LaserType.BLUE_BULLET, new Image(url+"lasers/blue_bullet.png"));

        lifeTexture = new Image(url+"life.png");
    }


    public void init() {
        Image img = new Image(String.valueOf(SpaceInvaders2App.class.getResource("textures/background.png")));
        gamePane.setBackground(new Background(new BackgroundImage(img, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
        loadTextures(String.valueOf(SpaceInvaders2App.class.getResource("textures")));

        player = new PlayerModel(0, 0, textures.get(ModelType.PLAYER),laserTextures.get(LaserType.BLUE_LASER));
        player.setBorders(0, PANE_HEIGHT, 0, PANE_WIDTH);

        playerStartX = PANE_WIDTH / 2;
        playerStartY = PANE_HEIGHT - player.getHeight();

        player.setX(playerStartX);
        player.setY(playerStartY);

        player.scale(0.5);

        player.getView().toFront();

        gamePane.getChildren().add(player.getView());

        enemies = new LinkedList<>();

        double delta = PANE_WIDTH / INIT_ENEMY_COUNT;
        double start_x = delta / 2;
        double start_y = PANE_HEIGHT / 5;

        for (int i = 0; i < INIT_ENEMY_COUNT; i++) {
            EnemyModel enemy = new EnemyModel(textures.get(INIT_ENEMY_TYPE),INIT_ENEMY_TYPE,laserTextures.get(LaserType.RED_BULLET));
            enemy.setX(start_x + delta * i);
            enemy.setY(start_y);
            enemy.scale(0.5);
            enemy.setMoveDistance(1.5);
            enemy.setBorders(0,PANE_HEIGHT/2,0,PANE_WIDTH);
            gamePane.getChildren().add(enemy.getView());
            enemies.add(enemy);
        }

        gamePane.setFocusTraversable(true);

        gamePane.setOnKeyPressed(this::keyEvent);

        gamePane.setOnKeyTyped(this::keyEvent);

        gamePane.toBack();

        gamePane.getChildren().add(group);
        group.toBack();

        for (int i=0;i<player.getHealth();++i){
            lifeBox.getChildren().add(new ImageView(lifeTexture));
        }
    }

    private void keyEvent(KeyEvent event) {
        player.setBorders(0, gamePane.getHeight(), 0, gamePane.getWidth());
        switch (event.getCode()) {
            case UP -> player.up();
            case DOWN -> player.down();
            case LEFT -> player.left();
            case RIGHT -> player.right();
        }
    }

    private boolean deleteLife(){
        lifeBox.getChildren().remove(lifeBox.getChildren().get(0));

        return player.decHealth();
    }

    private void moveEnemies(){
        Random r = new Random();
        for (EnemyModel enemy : enemies){
            if (enemy.getMovingTime()==0){
                enemy.setMovingTime(r.nextInt(50,200));
                enemy.setMovingVector(r.nextDouble(0,2*Math.PI));
            }
            while(!enemy.move()){
                enemy.setMovingVector(r.nextDouble(0,2*Math.PI));
                //System.out.println(enemy.getMovingVector());
            }
            enemy.decMovingTime();
        }
    }

    private void moveLasers(long time){
        /*List<Laser> playerShot = player.shot(time);
        if (playerShot!=null) {
            lasers.addAll(playerShot);
            for (Laser laser: playerShot){
                group.getChildren().add(laser.getView());
                laser.scale(0.5);
            }
        }*/
        for(EnemyModel enemy: enemies){
            List<Laser> shot = enemy.shot(time);
            if (shot!=null) {
                lasers.addAll(shot);
                for (Laser laser: shot){
                    laser.scale(0.5);
                    group.getChildren().add(laser.getView());
                }
            }
        }

        Iterator<Laser> iter = lasers.iterator();
        while(iter.hasNext()){
            Laser laser = iter.next();
            laser.move();
            //double x = laser.getX();
            double y = laser.getY();
            //double width = laser.getWidth();
            double height = laser.getHeight();
            if (y-height > PANE_HEIGHT || y+height/2<0){
                iter.remove();
                laser.getView().toBack();
                group.getChildren().remove(laser.getView());
                //gamePane.getChildren().remove(laser.getView());
            }
        }

    }

    private void checkCollision(){
        Iterator <Laser> iter = lasers.iterator();

        LinkedList<Object> toRemove = new LinkedList<>();

        while(iter.hasNext()){
            Laser laser = iter.next();
            if (laser.getDirection()==Direction.UP){
                for (EnemyModel enemy: enemies){
                    if (enemy.getView().intersects(laser.getView().getLayoutBounds())){
                        /*gamePane.getChildren().remove(enemy.getView());
                        enemies.remove(enemy);

                        group.getChildren().remove(laser.getView());
                        lasers.remove(laser);*/

                        toRemove.add(laser);
                        toRemove.add(enemy);
                    }
                }
            }else if (laser.getDirection()==Direction.DOWN){
                if (player.getView().intersects(laser.getView().getLayoutBounds())){
                    toRemove.add(laser);
                    deleteLife();
                }
            }
        }

        for (Object obj: toRemove){
            if (obj instanceof Laser){
                group.getChildren().remove(((Laser) obj).getView());
                lasers.remove(obj);
            }else if (obj instanceof EnemyModel){
                gamePane.getChildren().remove(((EnemyModel) obj).getView());
                enemies.remove(obj);
            }
        }
    }

    public void update (long time){
        //System.out.println(lasers.size());
        //System.out.println(gamePane.getHeight());
        moveLasers(time);
        checkCollision();
        moveEnemies();
    }



}
