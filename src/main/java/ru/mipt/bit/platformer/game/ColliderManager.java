package ru.mipt.bit.platformer.game;

import com.badlogic.gdx.math.GridPoint2;

public class ColliderManager {
    private final Level level;

    public ColliderManager(Level level) {
        this.level = level;
    }

    public boolean hasCollisionInDirection(GridPoint2 objectCoordinates, Direction direction) {
        var potentialPosition = direction.getNextCoordinates(objectCoordinates);
        return level.hasObstacleInPosition(potentialPosition);
    }
}
