package ru.mipt.bit.platformer.game.level_generator;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class StreamLevelGeneratorTest {
    @Test
    void generateLevelLayout() throws IOException {
        var generator = new StreamLevelGenerator(new BufferedReader(new StringReader("TX__T____")));
        assertArrayEquals(new int[][]{{1, 2, 0}, {0, 1, 0}, {0, 0, 0}}, generator.generateLevelLayout(3, 3));
    }
}
