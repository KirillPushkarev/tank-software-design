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
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Interpolation;
import ru.mipt.bit.platformer.game.*;
import ru.mipt.bit.platformer.game.renderer.GameRendererFactory;
import ru.mipt.bit.platformer.game.renderer.LibGdxGameRendererFactory;
import ru.mipt.bit.platformer.game.renderer.Renderer;
import ru.mipt.bit.platformer.util.MapUtils;

public class GameDesktopLauncher implements ApplicationListener {
    public static final String LEVEL_TMX = "level.tmx";
    public static final String TANK_IMAGE_PATH = "images/tank_blue.png";
    public static final String TREE_IMAGE_PATH = "images/greenTree.png";

    private TiledMap levelMap;
    private Player player;
    private GameObject treeObstacle;
    private CoordinatesCalculator coordinatesCalculator;
    private ProgressCalculator progressCalculator;

    private final InputController inputController = new InputController();

    private final GameRendererFactory gameRendererFactory = new LibGdxGameRendererFactory();
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

        levelMap = new TmxMapLoader().load(LEVEL_TMX);
        TiledMapTileLayer groundLayer = MapUtils.getSingleLayer(levelMap);
        coordinatesCalculator = new CoordinatesCalculator(groundLayer, Interpolation.smooth);
        progressCalculator = new ProgressCalculator();

        GridPoint2 playerCoordinates = new GridPoint2(1, 1);
        player = new Player(playerCoordinates, blueTankTexture.getWidth(), blueTankTexture.getHeight(), coordinatesCalculator, progressCalculator);

        GridPoint2 treeCoordinates = new GridPoint2(1, 3);
        treeObstacle = new GameObject(treeCoordinates, greenTreeTexture.getWidth(), greenTreeTexture.getHeight());

        gameRenderer = gameRendererFactory.createGameRenderer(levelMap, player, treeObstacle, coordinatesCalculator, blueTankTexture, greenTreeTexture);
    }

    @Override
    public void render() {
        float deltaTime = Gdx.graphics.getDeltaTime();

        Direction direction = inputController.getDirection();
        player.move(direction, treeObstacle, deltaTime);

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
        levelMap.dispose();
        batch.dispose();
    }

    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        // level width: 10 tiles x 128px, height: 8 tiles x 128px
        config.setWindowedMode(1280, 1024);
        new Lwjgl3Application(new GameDesktopLauncher(), config);
    }
}
