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
    protected GridPoint2 destinationGridCoordinates = null;
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

    public GridPoint2 getDestinationGridCoordinates() {
        return destinationGridCoordinates;
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
        return destinationGridCoordinates != null;
    }

    @Override
    public boolean holdsPosition(GridPoint2 position) {
        return super.holdsPosition(position) || isMoving() && destinationGridCoordinates.equals(position);
    }

    @Override
    public void move(Direction direction, float deltaTime) {
        updateRotation(direction);
        updateMovement(direction, deltaTime);
    }

    private void updateRotation(Direction direction) {
        if (!isMoving() && direction != Direction.NONE) {
            this.lastDirection = direction;
            rotate(this.lastDirection.getRotation());
        }
    }

    private void updateMovement(Direction direction, float deltaTime) {
        if (!isMoving() && direction != Direction.NONE && !colliderManager.hasCollisionInDirection(getGridCoordinates(), this.lastDirection)) {
            startMovement();
        }

        if (isMoving()) {
            movementProgress = progressCalculator.continueProgress(movementProgress, deltaTime, timeOfPassingOneTile);

            if (isEqual(movementProgress, MOVEMENT_PROGRESS_END)) {
                finishMovement();
            }
        }
    }

    private void startMovement() {
        destinationGridCoordinates = this.lastDirection.getNextCoordinates(gridCoordinates);
        movementProgress = MOVEMENT_PROGRESS_START;
    }

    private void finishMovement() {
        gridCoordinates.set(destinationGridCoordinates);
        destinationGridCoordinates = null;
    }
}
