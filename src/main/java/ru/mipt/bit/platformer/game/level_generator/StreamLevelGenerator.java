package ru.mipt.bit.platformer.game.level_generator;

import ru.mipt.bit.platformer.game.entity.Level;

import java.io.BufferedReader;
import java.io.IOException;

public class StreamLevelGenerator extends LevelGenerator {
    private final BufferedReader reader;

    public StreamLevelGenerator(BufferedReader reader) {
        this.reader = reader;
    }

    @Override
    protected int[][] generateLevelLayout(int gridWidth, int gridHeight) throws IOException {
        int[][] grid = new int[gridHeight][gridWidth];
        generateGameObjectPositions(grid);
        return grid;
    }

    private void generateGameObjectPositions(int[][] grid) throws IOException {
        for (int i = 0; i < grid.length; i++) {
            String line = reader.readLine();
            for (int j = 0; j < line.length(); j++) {
                var symbol = line.charAt(j);
                int marker;

                switch (symbol) {
                    case '_':
                        marker = GRASS_MARKER;
                        break;
                    case 'T':
                        marker = OBSTACLE_MARKER;
                        break;
                    case 'X':
                        marker = TANK_MARKER;
                        break;
                    case 'P':
                        marker = PLAYER_MARKER;
                        break;
                    default:
                        throw new IllegalArgumentException("Unsupported level symbol");
                }

                grid[i][j] = marker;
            }
        }
    }
}
