package ru.mipt.bit.platformer.game;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class PlayerTest {
    private static ColliderManager colliderManager;

    @BeforeAll
    static void beforeAll() {
        var level = new Level(new int[][]{
                {0, 0, 0},
                {0, 0, 0},
                {0, 0, 1}
        });
        colliderManager = new ColliderManager(level);
    }

    @ParameterizedTest
    @MethodSource({"moveFromInitialPosition", "moveFromIntermediatePosition"})
    void move(Player player, Direction direction, float deltaTime, float expectedRotation, float expectedProgress, GridPoint2 expectedDestinationCoordinates) {
        player.move(direction, deltaTime);

        assertEquals(expectedRotation, player.getMovingGameObject().getRotation());
        assertEquals(expectedProgress, player.getMovingGameObject().getMovementProgress());
        assertEquals(expectedDestinationCoordinates, player.getMovingGameObject().getDestinationGridCoordinates());
    }

    private static Stream<Arguments> moveFromInitialPosition() {
        var progressCalculator = new ProgressCalculator();

        return Stream.of(
                arguments(new Player(new GridPoint2(0, 0), 5, 5, progressCalculator, colliderManager), Direction.NONE, 0.2f, 0f, 1.0f, null),
                arguments(new Player(new GridPoint2(0, 0), 5, 5, progressCalculator, colliderManager), Direction.RIGHT, 0.2f, 0f, 0.5f, new GridPoint2(1, 0)),
                arguments(new Player(new GridPoint2(1, 2), 5, 5, progressCalculator, colliderManager), Direction.RIGHT, 0.2f, 0f, 1.0f, null)
        );
    }

    private static Stream<Arguments> moveFromIntermediatePosition() {
        var progressCalculator = new ProgressCalculator();
        var player1 = new Player(new GridPoint2(0, 0), 5, 5, progressCalculator, colliderManager);
        player1.move(Direction.UP, 0.2f);
        var player2 = new Player(new GridPoint2(0, 0), 5, 5, progressCalculator, colliderManager);
        player2.move(Direction.UP, 0.2f);

        return Stream.of(
                arguments(player1, Direction.NONE, 0.2f, 90f, 1f, null),
                arguments(player2, Direction.RIGHT, 0.1f, 90f, 0.75f, new GridPoint2(0, 1))
        );
    }
}
