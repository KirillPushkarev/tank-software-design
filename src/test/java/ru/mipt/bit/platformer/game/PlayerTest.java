package ru.mipt.bit.platformer.game;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.mipt.bit.platformer.game.entity.Direction;
import ru.mipt.bit.platformer.game.entity.Level;
import ru.mipt.bit.platformer.game.entity.MovingGameObject;
import ru.mipt.bit.platformer.game.entity.Player;
import ru.mipt.bit.platformer.game.level_generator.LevelGenerator;
import ru.mipt.bit.platformer.game.level_generator.StreamLevelGenerator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class PlayerTest {
    private static Level level;

    @BeforeAll
    static void setUp() throws IOException {
        LevelGenerator generator = new StreamLevelGenerator(new BufferedReader(new StringReader("___\n___\n__T")));
        level = generator.generateLevel(3, 3);
    }

    @ParameterizedTest
    @MethodSource({"moveFromInitialPosition", "moveFromIntermediatePositionDirectionNone", "moveFromIntermediatePositionDirectionRight"})
    void move(Player player, Direction direction, float deltaTime, float expectedRotation, float expectedProgress, GridPoint2 expectedDestinationCoordinates) {
        MovingGameObject movingGameObject = player.getMovingGameObject();
        movingGameObject.move(direction, deltaTime);

        assertEquals(expectedRotation, movingGameObject.getRotation());
        assertEquals(expectedProgress, movingGameObject.getMovementProgress());
        assertEquals(expectedDestinationCoordinates, movingGameObject.getDestinationGridCoordinates());
    }

    private static Stream<Arguments> moveFromInitialPosition() {
        GridPoint2 playerCoordinates = new GridPoint2(0, 0);
        level.addPlayer(playerCoordinates.x, playerCoordinates.y);
        float deltaTime = 0.15f;

        return Stream.of(
                arguments(level.getPlayer(), Direction.NONE, deltaTime, 0f, 1.0f, null),
                arguments(level.getPlayer(), Direction.RIGHT, deltaTime, 0f, 0.5f, new GridPoint2(1, 0)),
                arguments(level.getPlayer(), Direction.DOWN, deltaTime, 0f, 1.0f, null)
        );
    }

    private static Stream<Arguments> moveFromIntermediatePositionDirectionNone() {
        GridPoint2 playerCoordinates = new GridPoint2(0, 0);
        level.addPlayer(playerCoordinates.x, playerCoordinates.y);
        float deltaTime = 0.15f;

        var player = level.getPlayer();
        player.getMovingGameObject().move(Direction.UP, 0.2f);

        return Stream.of(
                arguments(level.getPlayer(), Direction.NONE, deltaTime, 90f, 1f, null)
        );
    }

    private static Stream<Arguments> moveFromIntermediatePositionDirectionRight() {
        GridPoint2 playerCoordinates = new GridPoint2(0, 0);
        level.addPlayer(playerCoordinates.x, playerCoordinates.y);
        float deltaTime = 0.15f;

        var player = level.getPlayer();
        player.getMovingGameObject().move(Direction.UP, deltaTime);

        return Stream.of(
                arguments(player, Direction.RIGHT, deltaTime / 2, 90f, 0.75f, new GridPoint2(0, 1))
        );
    }
}
