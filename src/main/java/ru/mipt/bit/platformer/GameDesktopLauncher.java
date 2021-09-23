package ru.mipt.bit.platformer;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Interpolation;
import ru.mipt.bit.platformer.game.*;

import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;
import static com.badlogic.gdx.math.MathUtils.isEqual;
import static ru.mipt.bit.platformer.util.GdxGameUtils.*;

public class GameDesktopLauncher implements ApplicationListener {
    public static final float MOVEMENT_PROGRESS_START = 0f;
    public static final float MOVEMENT_PROGRESS_END = 1f;
    private static final float MOVEMENT_SPEED = 0.4f;

    private final GameObjectFactory gameObjectFactory = new GameObjectFactory();
    private GameObject player;
    private GameObject treeObstacle;
    private final InputController inputController = new InputController();
    private final ProgressCalculator progressCalculator = new ProgressCalculator();

    private Batch batch;

    private TiledMap level;
    private MapRenderer levelRenderer;
    private TileMovement tileMovement;

    private Texture blueTankTexture;

    // which tile the player want to go next
    private GridPoint2 playerDestinationCoordinates;
    private float playerMovementProgress = MOVEMENT_PROGRESS_END;

    private Texture greenTreeTexture;

    @Override
    public void create() {
        batch = new SpriteBatch();

        // load level tiles
        level = new TmxMapLoader().load("level.tmx");
        levelRenderer = createSingleLayerMapRenderer(level, batch);
        TiledMapTileLayer groundLayer = getSingleLayer(level);
        tileMovement = new TileMovement(groundLayer, Interpolation.smooth);

        // Texture decodes an image file and loads it into GPU memory, it represents a native resource
        blueTankTexture = new Texture("images/tank_blue.png");
        playerDestinationCoordinates = new GridPoint2(1, 1);
        player = gameObjectFactory.createGameObject(blueTankTexture, playerDestinationCoordinates);
        player.rotate(0f);

        greenTreeTexture = new Texture("images/greenTree.png");
        treeObstacle = gameObjectFactory.createGameObject(greenTreeTexture, new GridPoint2(1, 3));

        moveRectangleAtTileCenter(groundLayer, treeObstacle.getRectangle(), treeObstacle.getGridCoordinates());
    }

    @Override
    public void render() {
        // clear the screen
        Gdx.gl.glClearColor(0f, 0f, 0.2f, 1f);
        Gdx.gl.glClear(GL_COLOR_BUFFER_BIT);

        // get time passed since the last render
        float deltaTime = Gdx.graphics.getDeltaTime();

        startMovementIfKeyPressed();

        // calculate interpolated player screen coordinates
        tileMovement.moveGameObjectBetweenTileCenters(player, playerDestinationCoordinates, playerMovementProgress);

        playerMovementProgress = progressCalculator.continueProgress(playerMovementProgress, deltaTime, MOVEMENT_SPEED);
        if (isEqual(playerMovementProgress, MOVEMENT_PROGRESS_END)) {
            // record that the player has reached his/her destination
            player.getGridCoordinates().set(playerDestinationCoordinates);
        }

        // render each tile of the level
        levelRenderer.render();

        // start recording all drawing commands
        batch.begin();

        // render player
        drawTextureRegionUnscaled(batch, player.getGraphics(), player.getRectangle(), player.getRotation());

        // render tree obstacle
        drawTextureRegionUnscaled(batch, treeObstacle.getGraphics(), treeObstacle.getRectangle(), 0f);

        // submit all drawing requests
        batch.end();
    }

    private void startMovementIfKeyPressed() {
        Direction direction = inputController.getDirection();
        if (direction == Direction.NONE) {
            return;
        }

        if (isEqual(playerMovementProgress, MOVEMENT_PROGRESS_END)) {
            // check potential player destination for collision with obstacles
            if (!treeObstacle.hasCollisionInDirection(player.getGridCoordinates(), direction)) {
                playerDestinationCoordinates = direction.getNextCoordinates(playerDestinationCoordinates);
                playerMovementProgress = MOVEMENT_PROGRESS_START;
            }
            player.rotate(direction.getRotation());
        }
    }

    @Override
    public void resize(int width, int height) {
        // do not react to window resizing
    }

    @Override
    public void pause() {
        // game doesn't get paused
    }

    @Override
    public void resume() {
        // game doesn't get paused
    }

    @Override
    public void dispose() {
        // dispose of all the native resources (classes which implement com.badlogic.gdx.utils.Disposable)
        greenTreeTexture.dispose();
        blueTankTexture.dispose();
        level.dispose();
        batch.dispose();
    }

    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        // level width: 10 tiles x 128px, height: 8 tiles x 128px
        config.setWindowedMode(1280, 1024);
        new Lwjgl3Application(new GameDesktopLauncher(), config);
    }
}
