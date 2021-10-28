package ru.mipt.bit.platformer.game;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.game.entity.Level;

public class ColliderManager {
    private final Level level;

    public ColliderManager(Level level) {
        this.level = level;
    }

    public boolean hasCollisionInDirection(GridPoint2 objectCoordinates, Direction direction) {
        var potentialPosition = direction.getNextCoordinates(objectCoordinates);
        return hasObstacleInPosition(potentialPosition);
    }

    public boolean hasObstacleInPosition(GridPoint2 position) {
        return isBeyondBoundaries(position) ||
                isObstacle(position);
    }

    private boolean isBeyondBoundaries(GridPoint2 position) {
        return position.x < 0 || position.x >= level.getLevelWidth() || position.y < 0 || position.y >= level.getLevelHeight();
    }

    private boolean isObstacle(GridPoint2 position) {
        return level.getObstacles().stream().anyMatch(obstacle -> obstacle.holdsPosition(position)) ||
                level.getTanks().stream().anyMatch(tank -> tank.getMovingGameObject().holdsPosition(position)) ||
                level.getPlayer().getMovingGameObject().holdsPosition(position);
    }
}
