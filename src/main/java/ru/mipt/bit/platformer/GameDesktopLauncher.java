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
import ru.mipt.bit.platformer.game.Level;
import ru.mipt.bit.platformer.game.Player;
import ru.mipt.bit.platformer.game.input.InputController;
import ru.mipt.bit.platformer.game.level_generator.StreamLevelGenerator;
import ru.mipt.bit.platformer.game.renderer.GameRendererFactory;
import ru.mipt.bit.platformer.game.renderer.LibGdxGameRendererFactory;
import ru.mipt.bit.platformer.game.renderer.Renderer;
import ru.mipt.bit.platformer.util.FileUtils;
import ru.mipt.bit.platformer.util.MapUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class GameDesktopLauncher implements ApplicationListener {
    public static final String LEVEL_MAP = "level.txt";
    public static final String LEVEL_TMX = "level.tmx";
    public static final String TANK_IMAGE_PATH = "images/tank_blue.png";
    public static final String TREE_IMAGE_PATH = "images/greenTree.png";
    public static final int LEVEL_WIDTH = 10;
    public static final int LEVEL_HEIGHT = 8;
    public static final int WINDOW_WIDTH = 1280;
    public static final int WINDOW_HEIGHT = 1024;

    private TiledMap levelTiledMap;
    private Player player;
    private List<Tank> tanks;

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

        levelTiledMap = new TmxMapLoader().load(LEVEL_TMX);
        TiledMapTileLayer groundLayer = MapUtils.getSingleLayer(levelTiledMap);

        Level level = getLevel();
        player = level.getPlayer();
        tanks = level.getTanks();

        CoordinatesCalculator coordinatesCalculator = new CoordinatesCalculator(groundLayer, Interpolation.smooth);
        gameRenderer = gameRendererFactory.createGameRenderer(levelTiledMap, level, coordinatesCalculator, blueTankTexture, greenTreeTexture);
    }

    private Level getLevel() {
        Level level = null;
        try {
            var inputStream = new BufferedReader(new FileReader(FileUtils.readFileFromResources(LEVEL_MAP)));
            level = new Level(new StreamLevelGenerator(inputStream)
                    .generateLevelLayout(LEVEL_WIDTH, LEVEL_HEIGHT));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return level;
    }

    @Override
    public void render() {
        float deltaTime = Gdx.graphics.getDeltaTime();

        Direction direction = inputController.getDirection();
        player.move(direction, deltaTime);

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
