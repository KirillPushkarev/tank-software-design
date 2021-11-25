package ru.mipt.bit.platformer.game.level_generator;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.game.ProgressCalculator;
import ru.mipt.bit.platformer.game.collision.ColliderManager;
import ru.mipt.bit.platformer.game.entity.GameObject;
import ru.mipt.bit.platformer.game.entity.Level;
import ru.mipt.bit.platformer.game.entity.Player;
import ru.mipt.bit.platformer.game.entity.Tank;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class LevelGenerator {
    public static final int GRASS_MARKER = 0;
    public static final int OBSTACLE_MARKER = 1;
    public static final int TANK_MARKER = 2;
    public static final int PLAYER_MARKER = 3;

    private final ColliderManager colliderManager = new ColliderManager();
    private final ProgressCalculator progressCalculator = new ProgressCalculator();
    protected Level level;
    private Player player;
    private final List<GameObject> obstacles = new ArrayList<>();
    private final List<Tank> tanks = new ArrayList<>();

    public Level generateLevel(int levelWidth, int levelHeight) throws IOException {
        var levelLayout = generateLevelLayout(levelWidth, levelHeight);
        generateObjectsFromGrid(levelLayout, levelWidth, levelHeight);
        level = new Level(levelWidth, levelHeight, player, obstacles, tanks, colliderManager, progressCalculator);
        colliderManager.setLevel(level);

        return level;
    }

    protected abstract int[][] generateLevelLayout(int gridWidth, int gridHeight) throws IOException;

    private void generateObjectsFromGrid(int[][] grid, int levelWidth, int levelHeight) {
        for (int i = 0; i < levelHeight; i++) {
            for (int j = 0; j < levelWidth; j++) {
                int x = j;
                int y = grid.length - 1 - i;

                if (grid[i][j] == OBSTACLE_MARKER) {
                    addObstacle(x, y);
                }

                if (grid[i][j] == TANK_MARKER) {
                    addTank(x, y);
                }

                if (grid[i][j] == PLAYER_MARKER) {
                    addPlayer(x, y);
                }
            }
        }
    }

    private void addObstacle(int x, int y) {
        GridPoint2 coordinates = new GridPoint2(x, y);
        var obstacle = new GameObject(coordinates, Level.TREE_WIDTH, Level.TREE_HEIGHT);
        obstacles.add(obstacle);
    }

    private void addTank(int x, int y) {
        GridPoint2 coordinates = new GridPoint2(x, y);
        var tank = new Tank(coordinates, Level.TANK_WIDTH, Level.TANK_HEIGHT, progressCalculator, colliderManager);
        tanks.add(tank);
    }

    public void addPlayer(int x, int y) {
        GridPoint2 coordinates = new GridPoint2(x, y);
        this.player = new Player(coordinates, Level.TANK_WIDTH, Level.TANK_HEIGHT, progressCalculator, colliderManager);
    }
}
