package ru.mipt.bit.platformer.game.level_generator;

import ru.mipt.bit.platformer.game.level_generator.LevelGeneratorInterface;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class StreamLevelGenerator implements LevelGeneratorInterface {
    private final String fileName;

    public StreamLevelGenerator(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public int[][] generateLevelLayout(int gridWidth, int gridHeight) throws IOException {
        int[][] grid = new int[gridHeight][gridWidth];

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            generateGameObjectPositions(gridHeight, grid, reader);
        }

        return grid;
    }

    private void generateGameObjectPositions(int gridHeight, int[][] grid, BufferedReader reader) throws IOException {
        for (int i = 0; i < gridHeight; i++) {
            var line = reader.readLine();
            for (int j = 0; j < line.length(); j++) {
                var symbol = line.charAt(j);

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
