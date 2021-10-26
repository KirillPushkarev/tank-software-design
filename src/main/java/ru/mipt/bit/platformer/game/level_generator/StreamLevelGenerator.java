package ru.mipt.bit.platformer.game.level_generator;

import ru.mipt.bit.platformer.game.Level;

import java.io.BufferedReader;
import java.io.IOException;

public class StreamLevelGenerator implements LevelGeneratorInterface {
    private final BufferedReader reader;

    public StreamLevelGenerator(BufferedReader reader) {
        this.reader = reader;
    }

    @Override
    public int[][] generateLevelLayout(int gridWidth, int gridHeight) throws IOException {
        int[][] grid = new int[gridHeight][gridWidth];

        generateGameObjectPositions(gridWidth, gridHeight, grid);
        reader.close();

        return grid;
    }

    private void generateGameObjectPositions(int gridWidth, int gridHeight, int[][] grid) throws IOException {
        for (int i = 0; i < gridHeight; i++) {
            String line = reader.readLine();
            for (int j = 0; j < line.length(); j++) {
                var symbol = line.charAt(j);
                int marker;

                switch (symbol) {
                    case '_':
                        marker = Level.GRASS_MARKER;
                        break;
                    case 'T':
                        marker = Level.OBSTACLE_MARKER;
                        break;
                    case 'X':
                        marker = Level.TANK_MARKER;
                        break;
                    case 'P':
                        marker = Level.PLAYER_MARKER;
                        break;
                    default:
                        throw new IllegalArgumentException("Unsupported level symbol");
                }

                grid[i][j] = marker;
            }
        }
    }
}
