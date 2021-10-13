package ru.mipt.bit.platformer.game;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ColliderManagerTest {
    @Test
    void hasCollisionInDirection() {
        var colliderManager = new ColliderManager(new Level(new int[][] {{1, 1, 1}, {1, 2, 0}, {0, 0, 0}}));
        GridPoint2 objectCoordinates = new GridPoint2(1, 1);

        assertTrue(colliderManager.hasCollisionInDirection(objectCoordinates, Direction.LEFT));
        assertFalse(colliderManager.hasCollisionInDirection(objectCoordinates, Direction.RIGHT));
        assertFalse(colliderManager.hasCollisionInDirection(objectCoordinates, Direction.UP));
        assertTrue(colliderManager.hasCollisionInDirection(objectCoordinates, Direction.DOWN));
    }
}
