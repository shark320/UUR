package main.spaceinvaders2;

import javafx.scene.Group;
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

/**
 * Game engine, calculates all moves and collisions, draw all game objects on the game pane
 *
 * @author Volodymyr Pavlov
 * @version 30.07.2022
 */
public class GameEngine {

    //--------------------------------------------------------------------------//
    //                                                                          //
    //                              Static constants                            //
    //                                                                          //
    //--------------------------------------------------------------------------//

    /**
     * Countdown duration in the seconds
     */
    private static final long COUNTDOWN_DURATION = 3;

    /**
     * Zero level enemies count
     */
    private static final int INIT_ENEMY_COUNT = 5;

    /**
     * Zero level enemies type
     */
    private static final ModelType INIT_ENEMY_TYPE = ModelType.SOLDIER_1;

    /**
     * Game pane width
     */
    private static final double PANE_WIDTH = 417.2;

    /**
     * Game pane height
     */
    private static final double PANE_HEIGHT = 673.2;

    /**
     * Down border for the enemies
     */
    private static final double DOWN_BORDER = 3 * PANE_HEIGHT / 5;

    /**
     * Count of explosion animation images count
     */
    private static final int EXPLOSION_IMAGE_COUNT = 16;

    //--------------------------------------------------------------------------//
    //                                                                          //
    //                             Static variables                             //
    //                                                                          //
    //--------------------------------------------------------------------------//

    /**
     * Is debug mode active
     */
    private static final boolean DEBUG = true;

    /**
     * Is god mode active
     */
    private static boolean godMode = false;

    /**
     * Start of the countdown
     */
    private long startCountdown = 0;

    /**
     * The only one GameEngine instance
     */
    private static GameEngine engine;

    //--------------------------------------------------------------------------//
    //                                                                          //
    //                           Instance constants                             //
    //                                                                          //
    //--------------------------------------------------------------------------//

    /**
     * List of enemies models
     */
    private final List<EnemyModel> enemies = new LinkedList<>();

    /**
     * Game pane with all models
     */
    private final Pane gamePane;

    /**
     * Life HBox
     */
    private final HBox lifeBox;

    /**
     * Scores text field
     */
    private final Text scoresField;

    /**
     * Map of the models textures
     */
    private final Map<ModelType, Image> textures = new HashMap<>();

    /**
     * Text field with countdown seconds
     */
    private final Text countdownSeconds = new Text();

    /**
     * Map of the laser textures
     */
    private final Map<LaserType, Image> laserTextures = new HashMap<>();

    /**
     * List with all present lasers
     */
    private final LinkedList<Laser> lasers = new LinkedList<>();

    /**
     * List with all present hit effects
     */
    private final LinkedList<HitEffect> hits = new LinkedList<>();

    /**
     * List with all present explosions effects
     */
    private final LinkedList<ExplosionEffect> explosions = new LinkedList<>();

    /**
     * Map with kill scores for each enemy model
     */
    private final Map<ModelType, Integer> killScores = new HashMap<>();

    /**
     * Pause text
     */
    private final Text pauseText = new Text("PAUSED");

    /**
     * Group of the models
     */
    private final Group modelsGroup = new Group();

    /**
     * Group of the effects
     */
    private final Group effectsGroup = new Group();

    /**
     * Explosion textures array
     */
    private final Image[] explosionTextures = new Image[EXPLOSION_IMAGE_COUNT];

    /**
     * God mode info text
     */
    private final Text godModeText = new Text("God mode activated");

    //--------------------------------------------------------------------------//
    //                                                                          //
    //                           Instance variables                             //
    //                                                                          //
    //--------------------------------------------------------------------------//

    /**
     * Player model
     */
    private PlayerModel player;

    /**
     * Life texture
     */
    private Image lifeTexture;

    /**
     * Hit texture
     */
    private Image hitTexture;

    /**
     * Current level
     */
    private int level = 0;

    /**
     * If models can not fire lasers
     */
    private boolean noLasers = false;

    /**
     * If it's countdown
     */
    private boolean isCountdown = true;

    /**
     * If the game is paused
     */
    private boolean isPaused = false;

    /**
     * Is game over
     */
    private boolean gameOver = false;

    /**
     * Current scores
     */
    private int scores;

    //--------------------------------------------------------------------------//
    //                                                                          //
    //                              Constructors                                //
    //                                                                          //
    //--------------------------------------------------------------------------//

    /**
     * Private class constructor
     *
     * @param controller - Controller of the "Game" scene
     */
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

    //--------------------------------------------------------------------------//
    //                                                                          //
    //                              Static methods                              //
    //                                                                          //
    //--------------------------------------------------------------------------//

    /**
     * Fabric method, creates new instance if there is no instance of this class, or return the exists instance
     *
     * @param controller - Controller of the "Game" scene
     * @return the only one instance of this class
     */
    public static GameEngine getGameEngine(GameController controller) {
        if (engine == null) {
            engine = new GameEngine(controller);
        }
        return engine;
    }

    //--------------------------------------------------------------------------//
    //                                                                          //
    //                              Instance methods                            //
    //                                                                          //
    //--------------------------------------------------------------------------//

    /**
     * Initialize kill scores for each enemy model
     */
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

    /**
     * Load all needed textures from the resources folder
     *
     * @param url - URL of the resource folder
     * @throws IOException -  if there is no FXML file
     */
    private void loadTextures(String url) throws IOException {

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

    /**
     * Initialize the game.
     * Creates player,enemies and reset all variables
     *
     * @throws IOException - if there is problems with files in the resource folder
     */
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
        modelsGroup.getChildren().clear();
        effectsGroup.getChildren().clear();
        explosions.clear();
        lifeBox.getChildren().clear();


        Image img = new Image(String.valueOf(SpaceInvaders2App.class.getResource("textures/background.png")));
        gamePane.setBackground(new Background(new BackgroundImage(img, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
        loadTextures(String.valueOf(SpaceInvaders2App.class.getResource("textures")));

        player = new PlayerModel(0, 0, textures.get(ModelType.PLAYER), laserTextures.get(LaserType.BLUE_LASER));
        player.setBorders(0, PANE_HEIGHT, 0, PANE_WIDTH);

        double playerStartX = PANE_WIDTH / 2;
        double playerStartY = PANE_HEIGHT - player.getHeight();

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
        gamePane.getChildren().add(modelsGroup);
        modelsGroup.toBack();
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
        godModeText.setY(PANE_HEIGHT - 20);
        gamePane.getChildren().add(godModeText);

    }

    /**
     * Generate new level, spawn enemies according to the current level
     */
    private void generateLevel() {

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

    /**
     * Spawn enemies
     *
     * @param model -model to spawn
     * @param count -count to spawn
     */
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

    /**
     * Split explosion textures image
     *
     * @param url -explosion textures image URL
     * @throws IOException -if file can not be read
     */
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

    /**
     * Handle key events
     *
     * @param event -current key event
     */
    private void keyEvent(KeyEvent event) {
        player.setBorders(0, gamePane.getHeight(), 0, gamePane.getWidth());
        if (DEBUG) {
            switch (event.getCode()) {
                case ESCAPE -> gameOver = true;
                case PAGE_UP -> player.setFireRate(player.getFireRate() - 50);
                case PAGE_DOWN -> player.setFireRate(player.getFireRate() + 50);
                case G -> {
                    godMode = !godMode;
                    System.out.println(godMode);
                    godModeText.setVisible(godMode);
                }
                case D -> {
                    for (EnemyModel enemy : enemies){
                        modelsGroup.getChildren().remove(enemy.getView());
                    }
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

    /**
     * Show game over information, after SPACE press returns to the main menu
     */
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

    /**
     * If not god mode, delete one life
     *
     * @return true if players life is zero, else - false
     */
    private boolean deleteLife() {
        if (!godMode) {
            if (player.getHealth() > 0) {
                lifeBox.getChildren().remove(lifeBox.getChildren().get(0));
            }
            return player.decHealth();
        } else {
            return false;
        }

    }

    /**
     * Calculating enemies moves and move models
     */
    private void moveEnemies() {
        Random r = new Random();
        for (EnemyModel enemy : enemies) {
            if (enemy.getMovingTime() == 0) {
                enemy.setMovingTime(r.nextInt(50, 200));
                enemy.setMovingVector(r.nextDouble(0, 2 * Math.PI));
            }
            while (!enemy.move()) {
                enemy.setMovingVector(r.nextDouble(0, 2 * Math.PI));
            }
            enemy.decMovingTime();
        }
    }

    /**
     * Calculating lasers moves and move lasers
     *
     * @param time -elapsed time from the game start
     */
    private void moveLasers(long time) {
        if (!noLasers) {
            List<Laser> playerShot = player.shot(time);
            if (playerShot != null) {
                lasers.addAll(playerShot);
                for (Laser laser : playerShot) {
                    modelsGroup.getChildren().add(laser.getView());
                    laser.scale(0.5);
                }
            }
            for (EnemyModel enemy : enemies) {
                List<Laser> shot = enemy.shot(time);
                if (shot != null) {
                    lasers.addAll(shot);
                    for (Laser laser : shot) {
                        laser.scale(0.5);
                        modelsGroup.getChildren().add(laser.getView());
                    }
                }
            }
        }

        Iterator<Laser> iter = lasers.iterator();
        while (iter.hasNext()) {
            Laser laser = iter.next();
            laser.move();
            double y = laser.getY();
            double height = laser.getHeight();
            if (y - height > PANE_HEIGHT || y + height / 2 < 0) {
                iter.remove();
                laser.getView().toBack();
                modelsGroup.getChildren().remove(laser.getView());
            }
        }
    }

    /**
     * Checks collisions between models and lasers
     *
     * @param elapsed - elapsed time from the game start
     */
    private void checkCollision(long elapsed) {
        Iterator<Laser> iter = lasers.iterator();

        LinkedList<Object> toRemove = new LinkedList<>();

        while (iter.hasNext()) {
            Laser laser = iter.next();
            if (laser.getDirection() == Direction.UP) {
                for (EnemyModel enemy : enemies) {
                    if (enemy.getView().intersects(laser.getView().getLayoutBounds())) {
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
                modelsGroup.getChildren().remove(((Laser) obj).getView());
                lasers.remove(obj);
            } else if (obj instanceof EnemyModel) {
                gamePane.getChildren().remove(((EnemyModel) obj).getView());
                enemies.remove(obj);
            }
        }
    }

    /**
     * Draw effects like hits and explosions
     *
     * @param elapsed -elapsed time from the game start
     */
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

    /**
     * Processing countdown
     *
     * @param elapsed -elapsed time from the game start
     * @return true if it's still countdown, false if countdown is ended
     */
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

    /**
     * Processing one game frame
     *
     * @param time - elapsed time from the game start
     */
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
