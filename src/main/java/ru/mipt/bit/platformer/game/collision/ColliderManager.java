package ru.mipt.bit.platformer.game.collision;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.game.entity.Direction;
import ru.mipt.bit.platformer.game.entity.GameObject;
import ru.mipt.bit.platformer.game.entity.Level;

public class ColliderManager {
    private Level level;

    public ColliderManager() {
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public boolean canMoveInDirection(GridPoint2 coordinates, Direction direction) {
        var collidingObject = getCollidingObject(coordinates, direction);
        var position = direction.getNextCoordinates(coordinates);
        return collidingObject == null && !isBeyondBoundaries(position);
    }

    public GameObject getCollidingObject(GridPoint2 coordinates, Direction direction) {
        var position = direction.getNextCoordinates(coordinates);

        var obstacle = level.getObstacles().stream().filter(o -> o.holdsPosition(position)).findFirst().orElse(null);
        if (obstacle != null) {
            return obstacle;
        }

        var tank = level.getTanks().stream().filter(t -> t.holdsPosition(position)).findFirst().orElse(null);
        if (tank != null) {
            return tank;
        }

        var player = level.getPlayer();
        if (player.holdsPosition(position)) {
            return player;
        }

        return null;
    }

    private boolean isBeyondBoundaries(GridPoint2 position) {
        return position.x < 0 || position.x >= level.getLevelWidth() || position.y < 0 || position.y >= level.getLevelHeight();
    }
}
