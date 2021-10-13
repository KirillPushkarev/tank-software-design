package ru.mipt.bit.platformer.game.level_generator;

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
                        grid[i][j] = 0;
                        break;
                    case 'T':
                        grid[i][j] = 1;
                        break;
                    case 'X':
                        grid[i][j] = 2;
                        break;
                    default:
                        throw new IllegalArgumentException("Unsupported level symbol");
                }
            }
        }
    }
}
