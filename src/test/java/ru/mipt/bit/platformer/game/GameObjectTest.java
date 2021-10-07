package ru.mipt.bit.platformer.game;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class GameObjectTest {

    @ParameterizedTest
    @MethodSource
    void hasCollisionInDirection(GameObject gameObject, GridPoint2 otherCoordinates, Direction direction, boolean hasCollision) {
        assertEquals(hasCollision, gameObject.hasCollisionInDirection(otherCoordinates, direction));
    }

    private static Stream<Arguments> hasCollisionInDirection() {
        return Stream.of(
                arguments(new GameObject(new GridPoint2(0, 0), 5, 5), new GridPoint2(0, 1), Direction.UP, true),
                arguments(new GameObject(new GridPoint2(0, 0), 5, 5), new GridPoint2(1, 0), Direction.RIGHT, true),
                arguments(new GameObject(new GridPoint2(0, 0), 5, 5), new GridPoint2(0, -2), Direction.DOWN, false)
        );
    }
}
