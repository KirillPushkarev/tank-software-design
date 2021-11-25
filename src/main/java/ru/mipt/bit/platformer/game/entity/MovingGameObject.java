package ru.mipt.bit.platformer.game.entity;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.game.ProgressCalculator;
import ru.mipt.bit.platformer.game.collision.ColliderManager;

import static com.badlogic.gdx.math.MathUtils.isEqual;

public class MovingGameObject extends GameObject {
    public static final float MOVEMENT_PROGRESS_START = 0f;
    public static final float MOVEMENT_PROGRESS_END = 1f;
    protected final float timeOfPassingOneTile;

    protected final ProgressCalculator progressCalculator;
    protected final ColliderManager colliderManager;
    protected GridPoint2 destinationCoordinates = null;
    protected Direction lastDirection = Direction.NONE;
    protected float movementProgress = MOVEMENT_PROGRESS_END;

    public MovingGameObject(GridPoint2 initialCoordinates,
                            int width,
                            int height,
                            float timeOfPassingOneTile,
                            ProgressCalculator progressCalculator,
                            ColliderManager colliderManager) {
        super(initialCoordinates, width, height);
        this.timeOfPassingOneTile = timeOfPassingOneTile;
        this.progressCalculator = progressCalculator;
        this.colliderManager = colliderManager;
    }

    public GridPoint2 getDestinationCoordinates() {
        return destinationCoordinates;
    }

    @Override
    public Direction getLastDirection() {
        return lastDirection;
    }

    public float getMovementProgress() {
        return movementProgress;
    }

    @Override
    public boolean isMoving() {
        return destinationCoordinates != null;
    }

    @Override
    public boolean holdsPosition(GridPoint2 position) {
        return super.holdsPosition(position) || isMoving() && destinationCoordinates.equals(position);
    }

    @Override
    public void move(Direction direction) {
        updateRotation(direction);
        if (!isMoving() && direction != Direction.NONE && !colliderManager.hasCollisionInDirection(coordinates, this.lastDirection)) {
            startMovement();
        }
    }

    public void liveTimePeriod(float deltaTime) {
        updatePosition(deltaTime);
    }

    private void updateRotation(Direction direction) {
        if (!isMoving() && direction != Direction.NONE) {
            lastDirection = direction;
            rotate(lastDirection.getRotation());
        }
    }

    private void startMovement() {
        destinationCoordinates = this.lastDirection.getNextCoordinates(coordinates);
        movementProgress = MOVEMENT_PROGRESS_START;
    }

    private void updatePosition(float deltaTime) {
        if (isMoving()) {
            movementProgress = progressCalculator.continueProgress(movementProgress, deltaTime, timeOfPassingOneTile);

            if (isEqual(movementProgress, MOVEMENT_PROGRESS_END)) {
                finishMovement();
            }
        }
    }

    private void finishMovement() {
        coordinates.set(destinationCoordinates);
        destinationCoordinates = null;
    }
}
