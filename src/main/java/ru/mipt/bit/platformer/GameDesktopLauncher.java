package ru.mipt.bit.platformer;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Interpolation;
import ru.mipt.bit.platformer.game.CoordinatesCalculator;
import ru.mipt.bit.platformer.game.Direction;
import ru.mipt.bit.platformer.game.LevelLoader;
import ru.mipt.bit.platformer.game.TankMovementScheduler;
import ru.mipt.bit.platformer.game.entity.Level;
import ru.mipt.bit.platformer.game.entity.Player;
import ru.mipt.bit.platformer.game.entity.Tank;
import ru.mipt.bit.platformer.game.input.InputController;
import ru.mipt.bit.platformer.game.renderer.Renderer;
import ru.mipt.bit.platformer.game.renderer.factory.GameRendererFactory;
import ru.mipt.bit.platformer.game.renderer.factory.LibGdxGameRendererFactory;
import ru.mipt.bit.platformer.util.MapUtils;

import java.util.List;

public class GameDesktopLauncher implements ApplicationListener {
    public static final String LEVEL_OBJECT_MAP = "level.txt";
    public static final String LEVEL_TILE_MAP = "level.tmx";
    public static final String TANK_IMAGE_PATH = "images/tank_blue.png";
    public static final String TREE_IMAGE_PATH = "images/greenTree.png";
    public static final int LEVEL_WIDTH = 10;
    public static final int LEVEL_HEIGHT = 8;
    public static final int WINDOW_WIDTH = 1280;
    public static final int WINDOW_HEIGHT = 1024;
    private final LevelLoader levelLoader = new LevelLoader();

    private TiledMap levelTiledMap;
    private Player player;
    private List<Tank> tanks;

    private final InputController inputController = new InputController();
    private final GameRendererFactory gameRendererFactory = new LibGdxGameRendererFactory();
    private final TankMovementScheduler tankMovementScheduler = new TankMovementScheduler();
    private Renderer gameRenderer;
    private Batch batch;
    private Texture blueTankTexture;
    private Texture greenTreeTexture;

    @Override
    public void create() {
        batch = new SpriteBatch();
        // Texture decodes an image file and loads it into GPU memory, it represents a native resource
        blueTankTexture = new Texture(TANK_IMAGE_PATH);
        greenTreeTexture = new Texture(TREE_IMAGE_PATH);

        levelTiledMap = new TmxMapLoader().load(LEVEL_TILE_MAP);
        TiledMapTileLayer groundLayer = MapUtils.getSingleLayer(levelTiledMap);

        Level level = levelLoader.getLevel();
        player = level.getPlayer();
        tanks = level.getTanks();

        CoordinatesCalculator coordinatesCalculator = new CoordinatesCalculator(groundLayer, Interpolation.smooth);
        gameRenderer = gameRendererFactory.createGameRenderer(levelTiledMap, level, coordinatesCalculator, blueTankTexture, greenTreeTexture);
    }

    @Override
    public void render() {
        float deltaTime = Gdx.graphics.getDeltaTime();

        Direction playerDirection = inputController.getDirection();
        player.move(playerDirection, deltaTime);
        for (Tank tank : tanks) {
            tankMovementScheduler.scheduleMovement(tank, deltaTime);
        }

        gameRenderer.render();
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
        levelTiledMap.dispose();
        batch.dispose();
    }

    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        // level width: 10 tiles x 128px, height: 8 tiles x 128px
        config.setWindowedMode(WINDOW_WIDTH, WINDOW_HEIGHT);
        new Lwjgl3Application(new GameDesktopLauncher(), config);
    }
}
