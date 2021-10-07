package ru.mipt.bit.platformer.game;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class PlayerTest {
    @ParameterizedTest
    @MethodSource
    void moveFromInitialPosition(Player player, Direction direction, GameObject obstacle, float deltaTime, float expectedRotation, float expectedProgress) {
        player.move(direction, obstacle, deltaTime);

        assertEquals(expectedRotation, player.getRotation());
        assertEquals(expectedProgress, player.getPlayerMovementProgress());
    }

    private static Stream<Arguments> moveFromInitialPosition() {
        var progressCalculator = new ProgressCalculator();
        var obstacle = new GameObject(new GridPoint2(2, 2), 5, 5);

        return Stream.of(
                arguments(new Player(new GridPoint2(0, 0), 5, 5, progressCalculator), Direction.NONE, obstacle, 0.2f, 0f, 1.0f),
                arguments(new Player(new GridPoint2(0, 0), 5, 5, progressCalculator), Direction.RIGHT, obstacle, 0.2f, 0f, 0.5f),
                arguments(new Player(new GridPoint2(1, 2), 5, 5, progressCalculator), Direction.RIGHT, obstacle, 0.2f, 0f, 1.0f)
        );
    }

    @ParameterizedTest
    @MethodSource
    void moveFromIntermediatePosition(Player player, Direction direction, GameObject obstacle, float deltaTime, float expectedRotation, float expectedProgress) {
        player.move(direction, obstacle, deltaTime);

        assertEquals(expectedRotation, player.getRotation());
        assertEquals(expectedProgress, player.getPlayerMovementProgress());
    }

    private static Stream<Arguments> moveFromIntermediatePosition() {
        var progressCalculator = new ProgressCalculator();
        var obstacle = new GameObject(new GridPoint2(2, 2), 5, 5);
        var player1 = new Player(new GridPoint2(0, 0), 5, 5, progressCalculator);
        player1.move(Direction.UP, obstacle, 0.2f);
        var player2 = new Player(new GridPoint2(0, 0), 5, 5, progressCalculator);
        player2.move(Direction.UP, obstacle, 0.2f);

        return Stream.of(
                arguments(player1, Direction.NONE, obstacle, 0.2f, 90f, 1f),
                arguments(player2, Direction.DOWN, obstacle, 0.2f, 90f, 1f)
        );
    }
}
