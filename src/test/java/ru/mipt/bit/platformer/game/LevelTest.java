package ru.mipt.bit.platformer.game;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static ru.mipt.bit.platformer.GameDesktopLauncher.LEVEL_HEIGHT;
import static ru.mipt.bit.platformer.GameDesktopLauncher.LEVEL_WIDTH;

class LevelTest {
    @ParameterizedTest
    @MethodSource
    void hasObstacleInPosition(GridPoint2 position, boolean hasObstacle) {
        var level = new Level(new int[][]{{1, 1, 1}, {1, 2, 0}, {0, 0, 0}}, LEVEL_WIDTH, LEVEL_HEIGHT);
        assertEquals(hasObstacle, level.hasObstacleInPosition(position));
    }

    private static Stream<Arguments> hasObstacleInPosition() {
        return Stream.of(
                arguments(new GridPoint2(0, 0), true),
                arguments(new GridPoint2(2, 1), false),
                arguments(new GridPoint2(1, 1), false)
        );
    }
}
