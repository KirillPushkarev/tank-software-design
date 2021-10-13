package ru.mipt.bit.platformer.game;

import com.badlogic.gdx.math.GridPoint2;

import java.util.ArrayList;
import java.util.List;

public class Level {
    private static final int OBSTACLE_WIDTH = 128;
    private static final int OBSTACLE_HEIGHT = 128;

    private static final int PLAYER_WIDTH = 92;
    private static final int PLAYER_HEIGHT = 84;

    private Player player;
    private final List<GameObject> obstacles = new ArrayList<>();

    public Player getPlayer() {
        return player;
    }

    public List<GameObject> getObstacles() {
        return obstacles;
    }

    public Level(int[][] grid) {
        ColliderManager colliderManager = new ColliderManager(this);

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == 1) {
                    addObstacle(i, j);
                }

                if (grid[i][j] == 2) {
                    addPlayer(colliderManager, i, j);
                }
            }
        }
    }

    private void addPlayer(ColliderManager colliderManager, int i, int j) {
        GridPoint2 coordinates = new GridPoint2(j, i);
        ProgressCalculator progressCalculator = new ProgressCalculator();
        this.player = new Player(coordinates, PLAYER_WIDTH, PLAYER_HEIGHT, progressCalculator, colliderManager);
    }

    private void addObstacle(int i, int j) {
        GridPoint2 coordinates = new GridPoint2(j, i);
        var obstacle = new GameObject(coordinates, OBSTACLE_WIDTH, OBSTACLE_HEIGHT);
        obstacles.add(obstacle);
    }

    public boolean hasObstacleInPosition(GridPoint2 position) {
        return obstacles.stream().anyMatch(obstacle -> obstacle.gridCoordinates.equals(position));
    }
}
