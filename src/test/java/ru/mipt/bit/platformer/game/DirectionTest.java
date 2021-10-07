package ru.mipt.bit.platformer.game;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class DirectionTest {

    @ParameterizedTest
    @MethodSource
    void getNextCoordinates(Direction direction, GridPoint2 coordinates, GridPoint2 nextCoordinates) {
        assertEquals(nextCoordinates, direction.getNextCoordinates(coordinates));
    }

    private static Stream<Arguments> getNextCoordinates() {
        return Stream.of(
                arguments(Direction.UP, new GridPoint2(0, 0), new GridPoint2(0, 1)),
                arguments(Direction.DOWN, new GridPoint2(0, 0), new GridPoint2(0, -1))
        );
    }
}
