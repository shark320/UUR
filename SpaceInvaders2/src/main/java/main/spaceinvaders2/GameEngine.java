package main.spaceinvaders2;

import javafx.scene.image.Image;
import javafx.scene.layout.*;
import main.spaceinvaders2.gamemodels.EnemyModel;
import main.spaceinvaders2.gamemodels.Laser;
import main.spaceinvaders2.gamemodels.ModelType;
import main.spaceinvaders2.gamemodels.PlayerModel;

import java.util.*;

public class GameEngine {

    private static final int INIT_ENEMY_COUNT = 5;

    private static final ModelType INIT_ENEMY_TYPE = ModelType.SOLDIER_1;

    private static final double PANE_WIDTH = 417.2;

    private static final double PANE_HEIGHT = 619.2;

    private static double playerStartX;

    private static double playerStartY;

    private static GameEngine engine;

    private PlayerModel player;

    private List<EnemyModel> enemies;

    private final Pane gamePane;

    private final Map<ModelType, Image> textures = new HashMap<>();

    private final LinkedList<Laser> enemyLasers = new LinkedList<>();

    private final LinkedList<Laser> playerLasers = new LinkedList<>();


/*    private GameEngine(PlayerModel player, List<EnemyModel> enemies, Pane gamePane){
        this.player=player;
        this.enemies=enemies;
        this.gamePane=gamePane;
    }*/

    private GameEngine(Pane gamePane) {
        this.gamePane = gamePane;
    }

    public static GameEngine getGameEngine(Pane gamePane) {
        if (engine == null) {
            engine = new GameEngine(gamePane);
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
    }


    public void init() {
        Image img = new Image(String.valueOf(SpaceInvaders2App.class.getResource("textures/background.png")));
        gamePane.setBackground(new Background(new BackgroundImage(img, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
        loadTextures(String.valueOf(SpaceInvaders2App.class.getResource("textures")));

        player = new PlayerModel(0, 0, textures.get(ModelType.PLAYER));
        player.setBorders(0, PANE_HEIGHT, 0, PANE_WIDTH);

        playerStartX = PANE_WIDTH / 2;
        playerStartY = PANE_HEIGHT - player.getHeight();

        player.setX(playerStartX);
        player.setY(playerStartY);

        player.scale(0.5);

        gamePane.getChildren().add(player.getView());

        enemies = new LinkedList<>();

        Image defImg = textures.get(INIT_ENEMY_TYPE);
        double delta = PANE_WIDTH / INIT_ENEMY_COUNT;
        double start_x = delta / 2;
        double start_y = PANE_HEIGHT / 5;

        for (int i = 0; i < INIT_ENEMY_COUNT; i++) {
            EnemyModel enemy = new EnemyModel(textures.get(INIT_ENEMY_TYPE));
            enemy.setX(start_x + delta * i);
            enemy.setY(start_y);
            enemy.scale(0.5);
            enemy.setMoveDistance(1.5);
            enemy.setBorders(0,PANE_HEIGHT/2,0,PANE_WIDTH);
            gamePane.getChildren().add(enemy.getView());
            enemies.add(enemy);
        }

        gamePane.setFocusTraversable(true);

        gamePane.setOnKeyPressed(event -> {
            player.setBorders(0, gamePane.getHeight(), 0, gamePane.getWidth());
            switch (event.getCode()) {
                case UP -> player.up();
                case DOWN -> player.down();
                case LEFT -> player.left();
                case RIGHT -> player.right();
            }
/*            System.out.println(player.getHeight() + " " + player.getWidth());
            System.out.println(gamePane.getHeight() + " " + gamePane.getWidth());*/
        });
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
                System.out.println(enemy.getMovingVector());
            }
            enemy.decMovingTime();
        }
    }

    private

    public void update (long time){

    }



}
