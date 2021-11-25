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
    @MethodSource({"liveTimePeriodInitialPositionData", "liveTimePeriodIntermediatePositionData"})
    void liveTimePeriod(Player player, float deltaTime, float expectedProgress, GridPoint2 expectedCoordinates, GridPoint2 expectedDestinationCoordinates) {
        MovingGameObject movingGameObject = player.getMovingGameObject();
        movingGameObject.liveTimePeriod(deltaTime);

        assertEquals(expectedProgress, movingGameObject.getMovementProgress());
        assertEquals(expectedCoordinates, movingGameObject.getCoordinates());
        assertEquals(expectedDestinationCoordinates, movingGameObject.getDestinationCoordinates());
    }

    private static Stream<Arguments> liveTimePeriodInitialPositionData() {
        GridPoint2 playerInitialCoordinates = new GridPoint2(0, 0);
        level.setPlayerPosition(playerInitialCoordinates.x, playerInitialCoordinates.y);

        var player = level.getPlayer();
        player.getMovingGameObject().move(Direction.RIGHT);

        return Stream.of(
                arguments(level.getPlayer(), 0.15f, 0.5f, playerInitialCoordinates, new GridPoint2(1, 0))
        );
    }

    private static Stream<Arguments> liveTimePeriodIntermediatePositionData() {
        GridPoint2 playerInitialCoordinates = new GridPoint2(0, 0);
        level.setPlayerPosition(playerInitialCoordinates.x, playerInitialCoordinates.y);

        var player = level.getPlayer();
        player.getMovingGameObject().move(Direction.UP);
        player.getMovingGameObject().liveTimePeriod(0.15f);

        return Stream.of(
                arguments(player, 0.075f, 0.75f, playerInitialCoordinates, new GridPoint2(0, 1))
        );
    }
}
