package main.spaceinvaders2;

import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import main.spaceinvaders2.effects.ExplosionEffect;
import main.spaceinvaders2.effects.HitEffect;
import main.spaceinvaders2.gamemodels.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.*;

import static javafx.scene.input.KeyCode.ESCAPE;
import static javafx.scene.input.KeyCode.W;


public class GameEngine {

    private static boolean debug = true;

    private static boolean godMode = false;

    private static final long COUNTDOWN_DURATION = 3;

    private static final int INIT_ENEMY_COUNT = 5;

    private static final ModelType INIT_ENEMY_TYPE = ModelType.SOLDIER_1;

    private static final double PANE_WIDTH = 417.2;

    private static final double PANE_HEIGHT = 673.2;

    private static final double DOWN_BORDER = 3 * PANE_HEIGHT / 5;

    private static double playerStartX;

    private static double playerStartY;

    private long startCountdown = 0;

    private static GameEngine engine;

    private PlayerModel player;

    private List<EnemyModel> enemies = new LinkedList<>();

    private final Pane gamePane;

    private final HBox lifeBox;

    private final Text scoresField;

    private final Map<ModelType, Image> textures = new HashMap<>();

    private final Text countdownSeconds = new Text();

    private final Map<LaserType, Image> laserTextures = new HashMap<>();

    private final LinkedList<Laser> lasers = new LinkedList<>();

    private final LinkedList<HitEffect> hits = new LinkedList<>();

    private final LinkedList<ExplosionEffect> explosions = new LinkedList<>();

    private final Map<ModelType, Integer> killScores = new HashMap<>();

    private Image lifeTexture;

    private Image hitTexture;

    private int level = 0;

    private boolean noLasers = false;

    private boolean isCountdown = true;

    private boolean isPaused = false;

    private final Text pauseText = new Text("PAUSED");

    private final Group group = new Group();

    private final Group effectsGroup = new Group();

    private boolean gameOver = false;

    private int scores = 30;

    private static final int EXPLOSION_IMAGE_COUNT = 16;

    private Image[] explosionTextures = new Image[EXPLOSION_IMAGE_COUNT];

    private Text godModeText = new Text("God mode activated");


    private GameEngine(GameController controller) {

        this.gamePane = controller.gamePane;
        this.lifeBox = controller.lifeBox;
        this.scoresField = controller.scores;

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

    public static GameEngine getGameEngine(GameController controller) {
        if (engine == null) {
            engine = new GameEngine(controller);
        }
        return engine;
    }

    private void killScoresInit() {
        killScores.put(ModelType.SOLDIER_1, 10);
        killScores.put(ModelType.SOLDIER_2, 20);
        killScores.put(ModelType.SOLDIER_3, 30);
        killScores.put(ModelType.BOSS_0, 100);
        killScores.put(ModelType.BOSS_1, 150);
        killScores.put(ModelType.BOSS_2, 200);
        killScores.put(ModelType.BOSS_3, 250);
        killScores.put(ModelType.BOSS_4, 300);
        killScores.put(ModelType.BOSS_5, 350);
        killScores.put(ModelType.BOSS_6, 500);
        killScores.put(ModelType.BOSS_7, 700);
    }

    private void loadTextures(String url) throws IOException {

        //SpaceInvaders2App.showError("TEST");

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

        laserTextures.put(LaserType.RED_LASER, new Image(url + "lasers/red_laser.png"));
        laserTextures.put(LaserType.BLUE_LASER, new Image(url + "lasers/blue_laser.png"));
        laserTextures.put(LaserType.RED_BULLET, new Image(url + "lasers/red_bullet.png"));
        laserTextures.put(LaserType.BLUE_BULLET, new Image(url + "lasers/blue_bullet.png"));

        lifeTexture = new Image(url + "life.png");
        hitTexture = new Image(url + "laser_hit.png");

        splitImage(url + "explosion.png");
    }


    public void init() throws IOException {
        isPaused = false;
        gameOver = false;
        noLasers = false;
        isCountdown = true;
        startCountdown = 0;
        level = 0;
        scores = 0;
        enemies.clear();
        lasers.clear();
        gamePane.getChildren().clear();
        group.getChildren().clear();
        effectsGroup.getChildren().clear();
        explosions.clear();
        lifeBox.getChildren().clear();


        Image img = new Image(String.valueOf(SpaceInvaders2App.class.getResource("textures/background.png")));
        gamePane.setBackground(new Background(new BackgroundImage(img, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
        loadTextures(String.valueOf(SpaceInvaders2App.class.getResource("textures")));

        player = new PlayerModel(0, 0, textures.get(ModelType.PLAYER), laserTextures.get(LaserType.BLUE_LASER));
        player.setBorders(0, PANE_HEIGHT, 0, PANE_WIDTH);

        playerStartX = PANE_WIDTH / 2;
        playerStartY = PANE_HEIGHT - player.getHeight();

        player.setX(playerStartX);
        player.setY(playerStartY);

        player.scale(0.5);

        player.setLaserSpeed(10);

        player.setFireRate(500);

        player.getView().toFront();

        gamePane.getChildren().add(player.getView());

        generateLevel();

        killScoresInit();

        gamePane.setFocusTraversable(true);

        gamePane.setOnKeyPressed(this::keyEvent);

        gamePane.setOnKeyTyped(this::keyEvent);

        gamePane.toBack();

        gamePane.getChildren().add(group);
        group.toBack();

        gamePane.getChildren().add(effectsGroup);
        effectsGroup.toFront();

        gamePane.getChildren().add(countdownSeconds);
        countdownSeconds.setVisible(false);
        countdownSeconds.setFont(new Font(70));
        countdownSeconds.setFill(Color.RED);

        for (int i = 0; i < player.getHealth(); ++i) {
            lifeBox.getChildren().add(new ImageView(lifeTexture));
        }

        pauseText.setFont(new Font(70));
        pauseText.setFill(Color.RED);
        pauseText.setX(PANE_WIDTH / 2 - pauseText.getLayoutBounds().getWidth() / 2);
        pauseText.setY(PANE_HEIGHT / 2);
        pauseText.setVisible(false);
        gamePane.getChildren().add(pauseText);

        scoresField.setText(String.valueOf(scores));

        godModeText.setFill(Color.YELLOW);
        godModeText.setVisible(godMode);
        godModeText.setX(0);
        godModeText.setY(PANE_HEIGHT-20);
        gamePane.getChildren().add(godModeText);

    }

    private void generateLevel() {
        //double delta = PANE_WIDTH / INIT_ENEMY_COUNT;

        if (level == 0) {
            spawnEnemies(INIT_ENEMY_TYPE, INIT_ENEMY_COUNT);
        } else {
            int index = (level / 10);
            switch (level % 10) {
                case 1 -> {
                    spawnEnemies(ModelType.SOLDIER_1, 5 + 5 * index);
                    spawnEnemies(ModelType.BOSS_0, 1 + index);
                }
                case 2 -> {
                    spawnEnemies(ModelType.SOLDIER_1, 5 + 5 * index);
                    spawnEnemies(ModelType.BOSS_1, 1 + index);
                }
                case 3 -> {
                    spawnEnemies(ModelType.SOLDIER_1, 5 + 5 * index);
                    spawnEnemies(ModelType.BOSS_2, 1 + index);
                }
                case 4 -> {
                    spawnEnemies(ModelType.SOLDIER_2, 5 + 5 * index);
                    spawnEnemies(ModelType.BOSS_3, 1 + index);
                }
                case 5 -> {
                    spawnEnemies(ModelType.SOLDIER_2, 5 + 5 * index);
                    spawnEnemies(ModelType.BOSS_4, 1 + index);
                }
                case 6 -> {
                    spawnEnemies(ModelType.SOLDIER_2, 5 + 5 * index);
                    spawnEnemies(ModelType.BOSS_5, 1 + index);
                }
                case 7 -> {
                    spawnEnemies(ModelType.SOLDIER_3, 5 + 5 * index);
                    spawnEnemies(ModelType.BOSS_6, 1 + index);
                }
                case 8 -> {
                    spawnEnemies(ModelType.SOLDIER_3, 5 + 5 * index);
                    spawnEnemies(ModelType.BOSS_7, 1 + index);
                }
                case 9 -> {
                    spawnEnemies(ModelType.SOLDIER_3, 5 + 5 * index);
                    spawnEnemies(ModelType.BOSS_7, 1 + index);
                    spawnEnemies(ModelType.BOSS_1, 1 + index);
                }
            }
        }
    }

    private void spawnEnemies(ModelType model, int count) {
        double start_x = PANE_WIDTH / 2;
        double start_y = PANE_HEIGHT / 5;
        for (int i = 0; i < count; i++) {
            EnemyModel enemy = new EnemyModel(textures.get(model), model, laserTextures.get(LaserType.RED_BULLET));
            enemy.setX(start_x);
            enemy.setY(start_y);
            enemy.scale(0.4);
            enemy.setMoveDistance(1.5);
            enemy.setBorders(0, DOWN_BORDER, 0, PANE_WIDTH);
            gamePane.getChildren().add(enemy.getView());
            enemies.add(enemy);
        }
    }

    private void splitImage(String url) throws IOException {
        BufferedImage buffer = ImageIO.read(new URL(url));

        int counter = 0;

        File tmp = new File("tmp.png");
        for (int x = 0; x < buffer.getWidth(); x += buffer.getWidth() / EXPLOSION_IMAGE_COUNT) {

            ImageIO.write(buffer.getSubimage(x, 0, buffer.getWidth() / EXPLOSION_IMAGE_COUNT, buffer.getHeight()), "png", tmp);
            explosionTextures[counter] = new Image(new FileInputStream(tmp));
            ++counter;

        }

        tmp.deleteOnExit();
    }

    private void keyEvent(KeyEvent event) {
        player.setBorders(0, gamePane.getHeight(), 0, gamePane.getWidth());
        if (debug){
            switch (event.getCode()) {
                case ESCAPE -> gameOver=true;
                case PAGE_UP -> player.setFireRate(player.getFireRate()-50);
                case PAGE_DOWN -> player.setFireRate(player.getFireRate()+50);
                case G -> {
                    godMode = !godMode;
                    System.out.println(godMode);
                    godModeText.setVisible(godMode);
                }
                case D -> {
                    group.getChildren().removeAll(enemies);
                    enemies.clear();
                }
            }
        }
        if (!isPaused) {
            switch (event.getCode()) {
                case UP -> player.up();
                case DOWN -> player.down();
                case LEFT -> player.left();
                case RIGHT -> player.right();
                case SPACE -> {
                    if (gameOver) {
                        gamePane.getChildren().clear();
                        SpaceInvaders2App.currentPlayer.setMaxScores(scores);
                        SpaceInvaders2App.showStartMenu();
                    } else {
                        if (!isCountdown) {
                            isPaused = true;
                            pauseText.setVisible(true);
                        }
                    }
                }
            }
        } else if (event.getCode() == KeyCode.SPACE) {
            isPaused = false;
            pauseText.setVisible(false);
        }
    }

    private void showGameOver() {
        Text gameOverMessage = new Text("Game Over");
        gameOverMessage.setFont(new Font(50));
        gameOverMessage.setFill(Color.RED);
        gameOverMessage.setX(PANE_WIDTH / 2 - gameOverMessage.getLayoutBounds().getWidth() / 2);
        gameOverMessage.setY(2 * PANE_HEIGHT / 5);

        Text scoresMessage = new Text("You have " + scores + " scores");
        scoresMessage.setFont(new Font(25));
        scoresMessage.setFill(Color.YELLOW);
        scoresMessage.setX(PANE_WIDTH / 2 - scoresMessage.getLayoutBounds().getWidth() / 2);
        scoresMessage.setY(gameOverMessage.getY() + gameOverMessage.getLayoutBounds().getHeight());

        Text spaceMessage = new Text("Press Space to exit");
        spaceMessage.setFont(new Font(12));
        spaceMessage.setFill(Color.YELLOW);
        spaceMessage.setX(PANE_WIDTH / 2 - spaceMessage.getLayoutBounds().getWidth() / 2);
        spaceMessage.setY(scoresMessage.getY() + scoresMessage.getLayoutBounds().getHeight());

        gamePane.getChildren().addAll(gameOverMessage, spaceMessage, scoresMessage);
    }


    private boolean deleteLife() {
        if (!godMode) {
            if (player.getHealth() > 0) {
                lifeBox.getChildren().remove(lifeBox.getChildren().get(0));
            }
            return player.decHealth();
        }else{
            return false;
        }

    }

    private void moveEnemies() {
        Random r = new Random();
        for (EnemyModel enemy : enemies) {
            if (enemy.getMovingTime() == 0) {
                enemy.setMovingTime(r.nextInt(50, 200));
                enemy.setMovingVector(r.nextDouble(0, 2 * Math.PI));
            }
            while (!enemy.move()) {
                enemy.setMovingVector(r.nextDouble(0, 2 * Math.PI));
                //System.out.println(enemy.getMovingVector());
            }
            enemy.decMovingTime();
        }
    }

    private void moveLasers(long time) {
        if (!noLasers) {
            List<Laser> playerShot = player.shot(time);
            if (playerShot != null) {
                lasers.addAll(playerShot);
                for (Laser laser : playerShot) {
                    group.getChildren().add(laser.getView());
                    laser.scale(0.5);
                }
            }
            for (EnemyModel enemy : enemies) {
                List<Laser> shot = enemy.shot(time);
                if (shot != null) {
                    lasers.addAll(shot);
                    for (Laser laser : shot) {
                        laser.scale(0.5);
                        group.getChildren().add(laser.getView());
                    }
                }
            }
        }

        Iterator<Laser> iter = lasers.iterator();
        while (iter.hasNext()) {
            Laser laser = iter.next();
            laser.move();
            //double x = laser.getX();
            double y = laser.getY();
            //double width = laser.getWidth();
            double height = laser.getHeight();
            if (y - height > PANE_HEIGHT || y + height / 2 < 0) {
                iter.remove();
                laser.getView().toBack();
                group.getChildren().remove(laser.getView());
                //gamePane.getChildren().remove(laser.getView());
            }
        }
    }

    private void checkCollision(long elapsed) {
        Iterator<Laser> iter = lasers.iterator();

        LinkedList<Object> toRemove = new LinkedList<>();

        while (iter.hasNext()) {
            Laser laser = iter.next();
            if (laser.getDirection() == Direction.UP) {
                for (EnemyModel enemy : enemies) {
                    if (enemy.getView().intersects(laser.getView().getLayoutBounds())) {
                        //System.out.println("HIT");
                        toRemove.add(laser);
                        HitEffect hit = new HitEffect(laser, hitTexture, elapsed);
                        hits.add(hit);
                        effectsGroup.getChildren().add(hit.getView());
                        if (enemy.hit()) {
                            toRemove.add(enemy);
                            scores += killScores.get(enemy.getModelType());
                            scoresField.setText(String.valueOf(scores));
                            ExplosionEffect explosion = new ExplosionEffect(enemy, explosionTextures, elapsed);
                            explosions.add(explosion);
                            effectsGroup.getChildren().add(explosion.getView());
                        }
                    }
                }
            } else if (laser.getDirection() == Direction.DOWN) {
                if (player.getView().intersects(laser.getView().getLayoutBounds())) {
                    toRemove.add(laser);
                    HitEffect hit = new HitEffect(laser, hitTexture, elapsed);
                    hits.add(hit);
                    effectsGroup.getChildren().add(hit.getView());
                    if (deleteLife()) {
                        gameOver = true;
                    }
                }
            }
        }

        for (Object obj : toRemove) {
            if (obj instanceof Laser) {
                group.getChildren().remove(((Laser) obj).getView());
                lasers.remove(obj);
            } else if (obj instanceof EnemyModel) {
                gamePane.getChildren().remove(((EnemyModel) obj).getView());
                enemies.remove(obj);
            }
        }
    }

    private void effectsUpdate(long elapsed) {
        Iterator<HitEffect> iter = hits.iterator();

        while (iter.hasNext()) {
            HitEffect hit = iter.next();
            if (hit.update(elapsed)) {
                iter.remove();
                effectsGroup.getChildren().remove(hit.getView());
            }
        }

        Iterator<ExplosionEffect> iter2 = explosions.iterator();
        while (iter2.hasNext()) {
            ExplosionEffect explosion = iter2.next();
            if (explosion.update(elapsed)) {
                iter2.remove();
                effectsGroup.getChildren().remove(explosion.getView());
            }
        }
    }

    private boolean countdown(long elapsed) {
        int current = (int) (COUNTDOWN_DURATION - (elapsed - startCountdown) / 1000);

        if (elapsed - startCountdown > COUNTDOWN_DURATION * 1000) {
            countdownSeconds.setVisible(false);
            return false;
        }

        countdownSeconds.setVisible(true);
        countdownSeconds.setText(String.valueOf(current));
        countdownSeconds.setX(PANE_WIDTH / 2 - countdownSeconds.getLayoutBounds().getWidth() / 2);
        countdownSeconds.setY(PANE_HEIGHT / 2);


        return true;
    }

    public void update(long time) {
        if (gameOver) {
            showGameOver();
        } else {
            if (enemies.isEmpty()) {
                noLasers = true;
            }
            if (enemies.isEmpty() && explosions.isEmpty() && lasers.isEmpty()) {
                ++level;
                generateLevel();
                isCountdown = true;
                noLasers = false;
                startCountdown = time;
            }
            if (isCountdown) {
                isCountdown = countdown(time);
            } else {
                if (!isPaused) {
                    moveLasers(time);
                    checkCollision(time);
                    effectsUpdate(time);
                    moveEnemies();
                }
            }

        }
    }


}
