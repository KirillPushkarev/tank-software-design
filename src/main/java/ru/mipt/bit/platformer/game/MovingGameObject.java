package ru.mipt.bit.platformer.game;

import com.badlogic.gdx.math.GridPoint2;

import static com.badlogic.gdx.math.MathUtils.isEqual;

public class MovingGameObject extends GameObject {
    public static final float MOVEMENT_PROGRESS_START = 0f;
    public static final float MOVEMENT_PROGRESS_END = 1f;
    protected final float timeOfPassingOneTile;

    protected final ProgressCalculator progressCalculator;
    protected final ColliderManager colliderManager;
    protected GridPoint2 destinationGridCoordinates = null;
    protected float movementProgress = MOVEMENT_PROGRESS_END;

    public MovingGameObject(GridPoint2 initialCoordinates, int width, int height, float timeOfPassingOneTile, ProgressCalculator progressCalculator, ColliderManager colliderManager) {
        super(initialCoordinates, width, height);
        this.timeOfPassingOneTile = timeOfPassingOneTile;
        this.progressCalculator = progressCalculator;
        this.colliderManager = colliderManager;
    }

    public GridPoint2 getDestinationGridCoordinates() {
        return destinationGridCoordinates;
    }

    public float getMovementProgress() {
        return movementProgress;
    }

    public void move(Direction direction, float deltaTime) {
        handleRotation(direction);
        handleMovement(deltaTime);
    }

    private boolean isStopped() {
        return isEqual(movementProgress, MOVEMENT_PROGRESS_END);
    }

    private void handleRotation(Direction direction) {
        if (isStopped() && direction != Direction.NONE) {
            rotate(direction.getRotation());

            if (!colliderManager.hasCollisionInDirection(getGridCoordinates(), direction)) {
                destinationGridCoordinates = direction.getNextCoordinates(gridCoordinates);
                movementProgress = MOVEMENT_PROGRESS_START;
            }
        }
    }

    private void handleMovement(float deltaTime) {
        if (!isStopped()) {
            movementProgress = progressCalculator.continueProgress(movementProgress, deltaTime, timeOfPassingOneTile);

            if (isStopped()) {
                finishMovement();
            }
        }
    }

    private void finishMovement() {
        gridCoordinates.set(destinationGridCoordinates);
        destinationGridCoordinates = null;
    }
}
