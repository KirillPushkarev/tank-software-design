package ru.mipt.bit.platformer.game.level_generator;

import ru.mipt.bit.platformer.game.Level;

import java.io.IOException;
import java.io.Reader;

public class StreamLevelGenerator implements LevelGeneratorInterface {
    private final Reader reader;

    public StreamLevelGenerator(Reader reader) {
        this.reader = reader;
    }

    @Override
    public int[][] generateLevelLayout(int gridWidth, int gridHeight) throws IOException {
        int[][] grid = new int[gridHeight][gridWidth];

        generateGameObjectPositions(gridWidth, gridHeight, grid, reader);
        reader.close();

        return grid;
    }

    private void generateGameObjectPositions(int gridWidth, int gridHeight, int[][] grid, Reader reader) throws IOException {
        for (int i = 0; i < gridHeight; i++) {
            for (int j = 0; j < gridWidth; j++) {
                var symbol = (char) reader.read();

                switch (symbol) {
                    case '_':
                        grid[i][j] = Level.GRASS_MARKER;
                        break;
                    case 'T':
                        grid[i][j] = Level.OBSTACLE_MARKER;
                        break;
                    case 'X':
                        grid[i][j] = Level.TANK_MARKER;
                        break;
                    case 'P':
                        grid[i][j] = Level.PLAYER_MARKER;
                        break;
                    default:
                        throw new IllegalArgumentException("Unsupported level symbol");
                }
            }
        }
    }
}
