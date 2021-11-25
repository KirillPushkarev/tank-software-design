package ru.mipt.bit.platformer.game;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.jupiter.api.Test;
import ru.mipt.bit.platformer.game.collision.ColliderManager;
import ru.mipt.bit.platformer.game.entity.Direction;
import ru.mipt.bit.platformer.game.entity.Level;
import ru.mipt.bit.platformer.game.level_generator.StreamLevelGenerator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ColliderManagerTest {
    @Test
    void hasCollisionInDirection() throws IOException {
        var generator = new StreamLevelGenerator(new BufferedReader(new StringReader("TTT\nTP_\n___")));
        Level level = generator.generateLevel(3, 3);

        var colliderManager = new ColliderManager();
        colliderManager.setLevel(level);
        GridPoint2 objectCoordinates = new GridPoint2(1, 1);

        assertTrue(colliderManager.hasCollisionInDirection(objectCoordinates, Direction.LEFT));
        assertFalse(colliderManager.hasCollisionInDirection(objectCoordinates, Direction.RIGHT));
        assertTrue(colliderManager.hasCollisionInDirection(objectCoordinates, Direction.UP));
        assertFalse(colliderManager.hasCollisionInDirection(objectCoordinates, Direction.DOWN));
    }
}
