package ru.mipt.bit.platformer.game;

import com.badlogic.gdx.math.GridPoint2;

import static com.badlogic.gdx.math.MathUtils.isEqual;

public class Player extends GameObject {
    public static final float MOVEMENT_PROGRESS_START = 0f;
    public static final float MOVEMENT_PROGRESS_END = 1f;
    protected static final float TIME_OF_PASSING_ONE_TILE = 0.4f;

    protected GridPoint2 playerDestinationGridCoordinates = null;
    protected float playerMovementProgress = MOVEMENT_PROGRESS_END;
    protected final ProgressCalculator progressCalculator;

    public Player(GridPoint2 initialCoordinates, int width, int height, ProgressCalculator progressCalculator) {
        super(initialCoordinates, width, height);
        this.progressCalculator = progressCalculator;
    }

    public GridPoint2 getPlayerDestinationGridCoordinates() {
        return playerDestinationGridCoordinates;
    }

    public float getPlayerMovementProgress() {
        return playerMovementProgress;
    }

    public void move(Direction direction, GameObject obstacle, float deltaTime) {
        handleRotation(direction, obstacle);
        handleMovement(deltaTime);
    }

    private boolean isStopped() {
        return isEqual(playerMovementProgress, MOVEMENT_PROGRESS_END);
    }

    private void handleRotation(Direction direction, GameObject obstacle) {
        if (isStopped() && direction != Direction.NONE) {
            rotate(direction.getRotation());

            if (!hasCollisionInDirection(obstacle.getGridCoordinates(), direction)) {
                playerDestinationGridCoordinates = direction.getNextCoordinates(gridCoordinates);
                playerMovementProgress = MOVEMENT_PROGRESS_START;
            }
        }
    }

    private void handleMovement(float deltaTime) {
        if (!isStopped()) {
            playerMovementProgress = progressCalculator.continueProgress(playerMovementProgress, deltaTime, TIME_OF_PASSING_ONE_TILE);

            if (isStopped()) {
                finishMovement();
            }
        }
    }

    private void finishMovement() {
        gridCoordinates.set(playerDestinationGridCoordinates);
        playerDestinationGridCoordinates = null;
    }
}
