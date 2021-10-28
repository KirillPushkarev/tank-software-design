package ru.mipt.bit.platformer.game.level_generator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RandomLevelGeneratorTest {
    @Mock
    Random randomGenerator;

    @BeforeEach
    void setUp() {
        when(randomGenerator.nextInt(3)).thenReturn(1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1);
    }

    @Test
    void generateLevelLayout() {
        var generator = new RandomLevelGenerator(randomGenerator);
        assertArrayEquals(new int[][]{{1, 3, 0}, {0, 1, 0}, {0, 0, 0}}, generator.generateLevelLayout(3, 3));
    }
}
