package ru.mipt.bit.platformer.game.level_generator;

import ru.mipt.bit.platformer.game.entity.Level;

import java.util.Random;

public class RandomLevelGenerator implements LevelGeneratorInterface {
    private static final int ENVIRONMENT_OBJECT_TYPES_COUNT = 3;
    private final Random randomGenerator;

    public RandomLevelGenerator(Random randomGenerator) {
        this.randomGenerator = randomGenerator;
    }

    @Override
    public int[][] generateLevelLayout(int gridWidth, int gridHeight) {
        int[][] grid = new int[gridHeight][gridWidth];

        generateObstaclePositions(gridWidth, gridHeight, grid, randomGenerator);
        generatePlayerPosition(gridWidth, gridHeight, grid, randomGenerator);

        return grid;
    }

    private void generateObstaclePositions(int gridWidth, int gridHeight, int[][] grid, Random randomGenerator) {
        for (int i = 0; i < gridHeight; i++) {
            for (int j = 0; j < gridWidth; j++) {
                int cellMarker = randomGenerator.nextInt(ENVIRONMENT_OBJECT_TYPES_COUNT);
                grid[i][j] = cellMarker;
            }
        }
    }

    private void generatePlayerPosition(int gridWidth, int gridHeight, int[][] grid, Random randomGenerator) {
        int playerPositionY;
        int playerPositionX;

        do {
            playerPositionY = randomGenerator.nextInt(gridHeight);
            playerPositionX = randomGenerator.nextInt(gridWidth);
        } while (grid[playerPositionY][playerPositionX] != Level.GRASS_MARKER);

        grid[playerPositionY][playerPositionX] = Level.PLAYER_MARKER;
    }
}
