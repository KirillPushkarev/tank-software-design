package ru.mipt.bit.platformer.game;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.jupiter.api.Test;
import ru.mipt.bit.platformer.game.collision.ColliderManager;
import ru.mipt.bit.platformer.game.entity.Direction;
import ru.mipt.bit.platformer.game.entity.Level;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ColliderManagerTest {
    @Test
    void hasCollisionInDirection() {
        var colliderManager = new ColliderManager(new Level(new int[][] {{1, 1, 1}, {1, 3, 0}, {0, 0, 0}}, 3, 3));
        GridPoint2 objectCoordinates = new GridPoint2(1, 1);

        assertTrue(colliderManager.hasCollisionInDirection(objectCoordinates, Direction.LEFT));
        assertFalse(colliderManager.hasCollisionInDirection(objectCoordinates, Direction.RIGHT));
        assertTrue(colliderManager.hasCollisionInDirection(objectCoordinates, Direction.UP));
        assertFalse(colliderManager.hasCollisionInDirection(objectCoordinates, Direction.DOWN));
    }
}
