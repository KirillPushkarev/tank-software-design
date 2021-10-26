package ru.mipt.bit.platformer.game;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.Tank;

import java.util.ArrayList;
import java.util.List;

public class Level {
    public static final int GRASS_MARKER = 0;
    public static final int OBSTACLE_MARKER = 1;
    public static final int TANK_MARKER = 2;
    public static final int PLAYER_MARKER = 3;

    private static final int TREE_WIDTH = 128;
    private static final int OBSTACLE_HEIGHT = 128;

    private static final int TANK_WIDTH = 92;
    private static final int TANK_HEIGHT = 84;

    private final ProgressCalculator progressCalculator = new ProgressCalculator();
    private final int levelWidth;
    private final int levelHeight;
    private Player player;
    private final List<GameObject> obstacles = new ArrayList<>();
    private final List<Tank> tanks = new ArrayList<>();

    public Player getPlayer() {
        return player;
    }

    public List<GameObject> getObstacles() {
        return obstacles;
    }

    public List<Tank> getTanks() {
        return tanks;
    }

    public Level(int[][] grid, int levelWidth, int levelHeight) {
        this.levelWidth = levelWidth;
        this.levelHeight = levelHeight;
        ColliderManager colliderManager = new ColliderManager(this);

        generateObjectsFromGrid(grid, colliderManager);
    }

    private void generateObjectsFromGrid(int[][] grid, ColliderManager colliderManager) {
        for (int i = 0; i < levelHeight; i++) {
            for (int j = 0; j < levelWidth; j++) {
                int x = j;
                int y = grid.length - 1 - i;

                if (grid[i][j] == OBSTACLE_MARKER) {
                    addObstacle(x, y);
                }

                if (grid[i][j] == TANK_MARKER) {
                    addTank(colliderManager, x, y);
                }

                if (grid[i][j] == PLAYER_MARKER) {
                    addPlayer(colliderManager, x, y);
                }
            }
        }
    }

    private void addObstacle(int x, int y) {
        GridPoint2 coordinates = new GridPoint2(x, y);
        var obstacle = new GameObject(coordinates, TREE_WIDTH, OBSTACLE_HEIGHT);
        obstacles.add(obstacle);
    }

    private void addTank(ColliderManager colliderManager, int x, int y) {
        GridPoint2 coordinates = new GridPoint2(x, y);
        var tank = new Tank(coordinates, TANK_WIDTH, TANK_HEIGHT, progressCalculator, colliderManager);
        tanks.add(tank);
    }

    private void addPlayer(ColliderManager colliderManager, int x, int y) {
        GridPoint2 coordinates = new GridPoint2(x, y);
        this.player = new Player(coordinates, TANK_WIDTH, TANK_HEIGHT, progressCalculator, colliderManager);
    }

    public boolean hasObstacleInPosition(GridPoint2 position) {
        return isBeyondBoundaries(position) ||
                obstacles.stream().anyMatch(obstacle -> obstacle.getGridCoordinates().equals(position)) ||
                tanks.stream().anyMatch(tank -> tank.getMovingGameObject().getGridCoordinates().equals(position)) ||
                tanks.stream().anyMatch(tank -> tank.getMovingGameObject().isMoving() && tank.getMovingGameObject().getDestinationGridCoordinates().equals(position)) ||
                player.getMovingGameObject().getGridCoordinates().equals(position) ||
                player.getMovingGameObject().isMoving() && player.getMovingGameObject().getDestinationGridCoordinates().equals(position);
    }

    private boolean isBeyondBoundaries(GridPoint2 position) {
        return position.x < 0 || position.x >= levelWidth || position.y < 0 || position.y >= levelHeight;
    }
}
