package ru.mipt.bit.platformer.game.entity;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.game.ProgressCalculator;
import ru.mipt.bit.platformer.game.collision.ColliderManager;

import static com.badlogic.gdx.math.MathUtils.isEqual;

public class Bullet extends GameObject {
    public static final float TIME_OF_PASSING_ONE_TILE = 0.2f;
    public static final float MOVEMENT_PROGRESS_START = 0f;
    public static final float MOVEMENT_PROGRESS_END = 1f;
    public static final int DAMAGE = 1;

    private final ProgressCalculator progressCalculator;
    protected final Level level;
    protected final ColliderManager colliderManager;
    protected GridPoint2 destinationCoordinates;
    protected final Direction direction;
    protected float movementProgress = MOVEMENT_PROGRESS_START;

    public Bullet(GridPoint2 gridCoordinates,
                  int width,
                  int height,
                  Direction direction,
                  ProgressCalculator progressCalculator,
                  Level level) {
        super(gridCoordinates, width, height);
        this.direction = direction;
        this.progressCalculator = progressCalculator;
        this.level = level;
        this.colliderManager = level.getColliderManager();

        destinationCoordinates = direction.getNextCoordinates(gridCoordinates);
        rotate(direction.getRotation());
    }

    public GridPoint2 getDestinationCoordinates() {
        return destinationCoordinates;
    }

    @Override
    public Direction getLastDirection() {
        return direction;
    }

    public float getMovementProgress() {
        return movementProgress;
    }

    @Override
    public void liveTimePeriod(float deltaTime) {
        movementProgress = progressCalculator.continueProgress(movementProgress, deltaTime, TIME_OF_PASSING_ONE_TILE);

        if (isEqual(movementProgress, MOVEMENT_PROGRESS_END)) {
            coordinates.set(destinationCoordinates);
            destinationCoordinates = direction.getNextCoordinates(coordinates);
            movementProgress = MOVEMENT_PROGRESS_START;
        }

        checkCollisions();
    }

    private void checkCollisions() {
        if (!colliderManager.canMoveInDirection(coordinates, direction)) {
            var collidingObject = colliderManager.getCollidingObject(coordinates, direction);
            if (collidingObject instanceof Tank) {
                var tank = (Tank) collidingObject;
                tank.applyDamage(DAMAGE);
            }

            level.unregisterBullet(this);
        }
    }
}
