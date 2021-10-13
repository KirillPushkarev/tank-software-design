package ru.mipt.bit.platformer.game.level_generator;

import java.io.IOException;

public interface LevelGeneratorInterface {
    int[][] generateLevelLayout(int gridWidth, int gridHeight) throws IOException;
}
